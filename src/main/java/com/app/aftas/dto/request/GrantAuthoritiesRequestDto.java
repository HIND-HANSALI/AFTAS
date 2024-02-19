package com.app.aftas.dto.request;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GrantAuthoritiesRequestDto {
    Long authorityId;
    Long roleId;
}
