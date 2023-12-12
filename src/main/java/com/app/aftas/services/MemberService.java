package com.app.aftas.services;

import com.app.aftas.models.Member;

import java.util.List;

public interface MemberService {
    List<Member> getAllMembers();
    Member getMemberById(Long id);
    Member addMember(Member member);
    List<Member> searchMember(String name);
    List<Member> findByNameOrMembershipNumberOrFamilyName(String searchTerm);
    Member updateMember(Member member, Long id);
    void deleteMember(Long id);
}
