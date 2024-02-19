package com.app.aftas.dto.request;

import com.app.aftas.models.Authority;
import com.app.aftas.models.Role;

import java.util.List;

public record RoleRequestDTO(
    String name,
    List<Authority> authorities,
    boolean isDefault
){
        public Role toRole(){
            return Role.builder()
                    .name(name)
                    .isDefault(isDefault)
                    .authorities(authorities)
                    .build();
        }
    }