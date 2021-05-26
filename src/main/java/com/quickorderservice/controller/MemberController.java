package com.quickorderservice.controller;

import com.quickorderservice.dto.member.MemberDTO;

import java.util.List;

import com.quickorderservice.service.member.MemberLoginService;
import com.quickorderservice.service.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberLoginService loginService;
    private final ResponseEntity<String> RESPONSE_OK = ResponseEntity.ok().body("OK");

    @GetMapping
    public List<MemberDTO> findAllMembers() {
        return memberService.findAllMembers();
    }

    @GetMapping("/{id}")
    public MemberDTO findMemberById(@PathVariable String id) {
        return memberService.findMemberById(id);
    }

    @PostMapping("/join")
    public int joinMember(@RequestBody MemberDTO memberDTO) {
        return memberService.joinMember(memberDTO);
    }

    @PatchMapping("/edit/info")
    public ResponseEntity editMemberInfo(@RequestBody MemberDTO memberDTO) {
        memberService.editMemberInfo(memberDTO);
        return RESPONSE_OK;
    }

    @PatchMapping("/edit/password")
    public ResponseEntity editMemberPassword(String userId, String oldPassword, String newPassword) {
        memberService.editMemberPassword(userId, oldPassword, newPassword);
        return RESPONSE_OK;
    }

    @DeleteMapping
    public int deleteMember(String id, String password) {
        return memberService.deleteMember(id, password);
    }

    @PostMapping("/login")
    public ResponseEntity login(String userId, String password) {
        loginService.login(userId, password);
        return RESPONSE_OK;
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        loginService.logout();
        return RESPONSE_OK;
    }

    @GetMapping("/login")
    public String getMember() {
        return loginService.getLoginMemberId();
    }

}
