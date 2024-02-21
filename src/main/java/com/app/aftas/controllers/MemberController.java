package com.app.aftas.controllers;

import com.app.aftas.dto.MemberDto;
import com.app.aftas.handlers.response.ResponseMessage;
import com.app.aftas.models.Member;
import com.app.aftas.services.MemberService;
import jakarta.validation.Valid;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@PreAuthorize("hasRole('MANAGER')")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/{id}")
    public ResponseEntity getMemberById(@PathVariable Long id) {
        return com.app.aftas.handlers.response.ResponseMessage.ok( memberService.getMemberById(id), "Success");
    }

    @GetMapping
    public ResponseEntity getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        if(members.isEmpty()) {
            return com.app.aftas.handlers.response.ResponseMessage.notFound("Member not found");
        }else {
            return com.app.aftas.handlers.response.ResponseMessage.ok(members, "Success");
        }
    }
    @GetMapping("/paginate")
    public ResponseEntity getAllMembersPaginate(@RequestParam @DefaultValue("0") Integer page, @RequestParam Integer size) {
        List<Member> members;
        if (size != null)
            members = memberService.getAllMembersPaginated(PageRequest.of(page, size));
        else
            members = memberService.getAllMembers();
        if (members.isEmpty()) {
            return ResponseMessage.notFound("Member not found");
        } else {
            return ResponseMessage.ok(members,"Success");
        }
    }

    @PostMapping
    public ResponseEntity addMember(@Valid @RequestBody MemberDto memberDTO) {
        Member memberSaved = memberService.addMember(memberDTO.toMember());
        if(memberSaved  == null) {
            return com.app.aftas.handlers.response.ResponseMessage.badRequest("Member not created");
        }else {
            return com.app.aftas.handlers.response.ResponseMessage.created(memberSaved , "Member created successfully");
        }
    }

    @GetMapping("/search")
    public ResponseEntity searchMember(@RequestBody String name) {
        List<Member> members = memberService.findByNameOrMembershipNumberOrFamilyName(name);
        if(members.isEmpty()) {
            return ResponseMessage.notFound("Member not found");
        }else {
            return ResponseMessage.ok(members, "Success");
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity updateMember(@RequestBody Member member, @PathVariable Long id) {
//        Member memberUpdated = memberService.updateMember(member, id);
//        if(memberUpdated == null) {
//            return com.app.aftas.handlers.response.ResponseMessage.badRequest("Member not updated");
//        }else {
//            return com.app.aftas.handlers.response.ResponseMessage.created(memberUpdated, "Member updated successfully");
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMember(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        if(member == null) {
            return com.app.aftas.handlers.response.ResponseMessage.notFound("Member not found");
        }else {
            memberService.deleteMember(id);
            return com.app.aftas.handlers.response.ResponseMessage.ok(null, "Member deleted successfully");
        }
    }
}
