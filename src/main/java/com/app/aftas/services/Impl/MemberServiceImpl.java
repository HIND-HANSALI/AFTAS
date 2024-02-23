package com.app.aftas.services.Impl;

import com.app.aftas.dto.MemberDto;
import com.app.aftas.models.Member;
import com.app.aftas.repositories.MemberRepository;
import com.app.aftas.services.MemberService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @Override
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member id " + id + " not found"));
    }
//    @Override
//    public List<Member> getAllMembers() {
//        return memberRepository.findAll();
//    }
public List<MemberDto> getAllMembers() {
    List<Member> members = memberRepository.findAll();
    return members.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
}

    private MemberDto convertToDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getUsername(),
                member.getAccessDate(),
                member.getNationality(),
                member.getIdentityNumber(),
                member.getMembershipNumber(),
                member.getIdentityDocumentType()
        );
    }

    @Override
    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public List<Member> getAllMembersPaginated(org.springframework.data.domain.Pageable pageable) {
        return memberRepository.findAll(pageable).getContent();
    }


    @Override
    public List<Member> findByNameOrMembershipNumberOrFamilyName(String searchTerm){
        return memberRepository.findByMembershipNumberOrNameOrFamilyName(searchTerm);
    }

//    @Override
//    public Member updateMember(Member member, Long id) {
//        Member existingMember = getMemberById(id);
//        existingMember.setName(member.getName());
//        existingMember.setFamilyName(member.getFamilyName());
//        existingMember.setAccessDate(member.getAccessDate());
//        existingMember.setNationality(member.getNationality());
//        existingMember.setIdentityDocumentType(member.getIdentityDocumentType());
//        existingMember.setIdentityNumber(member.getIdentityNumber());
//        return memberRepository.save(existingMember);
//    }

    @Override
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
