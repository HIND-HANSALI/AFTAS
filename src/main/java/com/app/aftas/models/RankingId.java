package com.app.aftas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Embeddable
public class RankingId implements Serializable {

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "competition_id")
    private Long competitionId;

}
