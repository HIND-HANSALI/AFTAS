package com.app.aftas.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ranking {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @EmbeddedId
    private RankingId id;

    private int rank;
    private int score;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "member_id")
    @MapsId("memberId")
    private Member member;

    @ManyToOne
//            (fetch = FetchType.EAGER)
//    @JsonBackReference


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "competition_id")
    @MapsId("competitionId")
    private Competition competition;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;
}
