package com.app.aftas.services;

import com.app.aftas.dto.request.AuthenticationRequest;
import com.app.aftas.dto.request.RegisterRequest;
import com.app.aftas.dto.response.AuthenticationResponse;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest user);
    List<AuthenticationResponse> getAllUsers();
    AuthenticationResponse authenticate(AuthenticationRequest user);
}
