package com.quickorderservice.controller;

import com.quickorderservice.annotation.MemberId;
import com.quickorderservice.dto.member.MemberDTO;

import java.util.List;

import com.quickorderservice.service.LoginService;
import com.quickorderservice.service.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final LoginService memberLoginService;

    @GetMapping
    public List<MemberDTO> findAllMembers() {
        return memberService.findAllMembers();
    }

    @GetMapping("/{id}")
    public MemberDTO findMemberById(@PathVariable String id) {
        return memberService.findMemberById(id);
    }

    @PostMapping
    public int joinMember(@RequestBody MemberDTO memberDTO) {
        return memberService.joinMember(memberDTO);
    }

    @PatchMapping
    public void editMemberInfo(@MemberId String userId, @RequestBody MemberDTO editedMember) {
        memberService.editMemberInfo(editedMember);
    }

    @PatchMapping("/my-infos/password")
    public void editMemberPassword(@MemberId String userId, String oldPassword, String newPassword) {
        memberService.editMemberPassword(userId, oldPassword, newPassword);
    }

    @DeleteMapping
    public void deleteMember(@MemberId String userId, String password) {
        memberService.deleteMember(userId, password);
    }

    @PostMapping("/login")
    public void login(String userId, String password) {
        memberLoginService.login(userId, password);
    }

    @PostMapping("/logout")
    public void logout() {
        memberLoginService.logout();
    }

}
