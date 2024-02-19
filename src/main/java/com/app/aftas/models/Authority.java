package com.app.aftas.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.app.aftas.enums.AuthorityEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"roles"}, allowSetters = true)
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "authorities")
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    private AuthorityEnum name;
}
