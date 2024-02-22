package com.app.aftas.dto.response;

import com.app.aftas.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDTO {
    private String token;
    private TokenType tokenType;
}
