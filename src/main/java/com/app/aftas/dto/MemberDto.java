package com.app.aftas.dto;

import com.app.aftas.enums.IdentityDocumentType;
import com.app.aftas.models.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MemberDto (
        @NotNull(message = "Name is required")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        String name,

        @NotNull(message = "Family name is required")
        @Size(min = 2, max = 50, message = "Family name must be between 2 and 50 characters")
        String familyName,

        @PastOrPresent(message = "Access date must be in the past or present")
        @Temporal(TemporalType.DATE)
        LocalDate accessDate,

        @NotNull(message = "Nationality is required")
        String nationality,
        @NotNull(message = "Identity number cannot be null")
        @Size(min = 2, max = 50, message = "Identity number must be between 2 and 50 characters")
        @Column(unique = true)
        String identityNumber,


        int membershipNumber,
        IdentityDocumentType identityDocumentType

){
        public Member toMember(){
                return Member.builder()
                        .name(name)
                        .familyName(familyName)
                        .nationality(nationality)
                        .identityDocumentType(identityDocumentType).
                        identityNumber(identityNumber)
                        .membershipNumber(membershipNumber)
                        .accessDate(accessDate)
                        .build();
        }

}
