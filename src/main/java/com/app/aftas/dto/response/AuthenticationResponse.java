package com.app.aftas.dto.response;
import com.app.aftas.models.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String userName;
    private String token;
    private String email;
    private Role role;
}
