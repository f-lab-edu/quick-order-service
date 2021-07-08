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
    public void editMemberInfo(@MemberId Long memberUid, @RequestBody MemberDTO editedMember) {
        memberService.editMemberInfo(editedMember);
    }

    @PatchMapping("/my-infos/password")
    public void editMemberPassword(@MemberId Long memberUid, String oldPassword, String newPassword) {
        memberService.editMemberPassword(memberUid, oldPassword, newPassword);
    }

    @DeleteMapping
    public void deleteMember(@MemberId Long memberUid, String password) {
        memberService.deleteMember(memberUid, password);
    }

    @PostMapping("/login")
    public void login(String memberId, String password) {
        loginService.login(memberId, password);
    }

    @PostMapping("/logout")
    public void logout() {
        memberLoginService.logout();
    }

    @GetMapping("/login")
    public MemberDTO getLogin(@MemberId Long memberUid) {
        return memberService.findMemberByUid(memberUid);
    }
}
