package com.app.aftas.repositories;

import com.app.aftas.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @Query(value =
            "SELECT * FROM member WHERE membership_number LIKE :searchTerm " +
                    "OR name LIKE %:searchTerm% OR family_name LIKE %:searchTerm%", nativeQuery = true)
    List<Member> findByMembershipNumberOrNameOrFamilyName(@Param("searchTerm") String searchTerm);
//List<Member> findByMembershipNumberOrNameOrFamilyName(@Param("searchTerm") String searchTerm);
}
