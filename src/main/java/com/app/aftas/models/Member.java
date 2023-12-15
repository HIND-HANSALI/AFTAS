package com.app.aftas.models;

import com.app.aftas.enums.IdentityDocumentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import liquibase.exception.DatabaseException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private int membershipNumber;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Family name cannot be null")
    @Size(min = 2, max = 50, message = "Family name must be between 2 and 50 characters")
    private String familyName;

    @NotNull(message = "Access date cannot be null")
    @PastOrPresent(message = "Access date must be in the past or present")
    @Temporal(TemporalType.DATE)
    private LocalDate accessDate;

    @NotNull(message = "nationality cannot be null")
    private String nationality;

    @Enumerated(EnumType.STRING)
    private IdentityDocumentType identityDocumentType;

    @NotNull(message = "Identity number cannot be null")
    @Size(min = 2, max = 50, message = "Identity number must be between 2 and 50 characters")
    private String identityNumber;

    @OneToMany(mappedBy = "member")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Ranking> rankings;

    @OneToMany(mappedBy = "member")
    private List<Hunting> huntings;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;


}
