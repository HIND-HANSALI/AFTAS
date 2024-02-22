package com.app.aftas.services.Impl;

import com.app.aftas.dto.request.TokenDto;
import com.app.aftas.dto.response.TokenResponseDTO;
import com.app.aftas.enums.IdentityDocumentType;
import com.app.aftas.enums.TokenType;
//import com.app.aftas.models.Token;
import com.app.aftas.models.Token;
import com.app.aftas.repositories.MemberRepository;
import com.app.aftas.config.JwtService;
import com.app.aftas.dto.request.AuthenticationRequest;
import com.app.aftas.dto.request.RegisterRequest;
import com.app.aftas.dto.response.AuthenticationResponse;
import com.app.aftas.models.Member;
//import com.app.aftas.repositories.TokenRepository;
import com.app.aftas.repositories.TokenRepository;
import com.app.aftas.services.AuthenticationService;
import com.app.aftas.services.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final MemberRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final TokenRepository tokenRepository;

    @Override
    public List<AuthenticationResponse> getAllUsers() {
        List<Member> users = userRepository.findAll();
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        if (authorities.contains("VIEW_USERS"))
            return users.stream()
                    .map(this::mapUserToAuthenticationResponse)
                    .collect(Collectors.toList());
        else return null;


    }
    private AuthenticationResponse mapUserToAuthenticationResponse(Member user) {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUserName(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = Member.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accessDate(LocalDate.now())
                .nationality(request.getNationality())
                .identityNumber(request.getIdentityNumber())
                .membershipNumber(request.getMembershipNumber())
                .identityDocumentType(IdentityDocumentType.IDENTITY_CARD)
                .role(roleService.findDefaultRole().orElse(null))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken)
                .userName(user.getUsername())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userName(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }


    private void saveUserToken(Member user, String jwtToken) {
        var token = Token.builder()
                .member(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Member user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUserName(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .token(accessToken)
                        .userName(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }




}
