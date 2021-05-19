package com.quickorderservice.controller;

import com.quickorderservice.dto.member.MemberDTO;

import java.util.List;

import com.quickorderservice.exception.member.NotFoundMemberException;
import com.quickorderservice.service.member.MemberLoginService;
import com.quickorderservice.service.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

    @PatchMapping("/edit/info")
    public int editMemberInfo(@RequestBody MemberDTO memberDTO) {
        return memberService.editMemberInfo(memberDTO);
    }

    @PatchMapping("edit/password")
    public int editMemberPassword(String id, String oldPassword, String newPassword) {
        return memberService.editMemberPassword(id, oldPassword, newPassword);
    }

    @DeleteMapping
    public int deleteMember(String id, String password) {
        return memberService.deleteMember(id, password);
    }

    @PostMapping("/login")
    public HttpStatus login(String userId, String password) {
        try {
            MemberDTO loginMember = loginService.login(userId, password);
        } catch (NotFoundMemberException e) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    @PostMapping("/logout")
    public HttpStatus logout() {
        loginService.logout();
        return HttpStatus.OK;
    }

    @GetMapping("/login")
    public String getMember() {
        return loginService.getLoginMemberId();
    }
}
