package com.app.aftas.dto.response;


import com.app.aftas.enums.AuthorityEnum;
import com.app.aftas.models.Authority;
import com.app.aftas.models.Role;


import java.util.List;

public record RoleResponseDTO(
        String name,
        List<AuthorityEnum> authorities,
        boolean isDefault
) {
    public static RoleResponseDTO fromRole(Role role){
        return new RoleResponseDTO(
                role.getName(),
                role.getAuthorities().stream().map(Authority::getName).toList(),
                role.isDefault()
        );
    }
}
