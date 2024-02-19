package com.app.aftas.dto.request;
import com.app.aftas.enums.IdentityDocumentType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String userName;

    private String email;

    private String password;
    private String nationality;
    private int membershipNumber;
    private String identityNumber;
    private IdentityDocumentType identityDocumentType;
}
