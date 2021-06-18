package com.quickorderservice.controller;

import com.quickorderservice.annotation.MemberId;
import com.quickorderservice.dto.member.MemberDTO;

import java.util.List;

import com.quickorderservice.service.member.MemberLoginService;
import com.quickorderservice.service.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberLoginService loginService;

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
    public int deleteMember(String id, String password) {
        return memberService.deleteMember(id, password);
    }

    @PostMapping("/login")
    public void login(String userId, String password) {
        loginService.login(userId, password);
    }

    @PostMapping("/logout")
    public void logout() {
        loginService.logout();
    }

    @GetMapping("/login")
    public String getMember() {
        return loginService.getLoginMemberId();
    }

}
