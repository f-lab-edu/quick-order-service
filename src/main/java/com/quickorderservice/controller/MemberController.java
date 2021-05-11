package com.quickorderservice.controller;

import com.quickorderservice.dto.member.MemberDTO;

import java.util.List;

import com.quickorderservice.service.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

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
    public int editMemberInfo(@ModelAttribute MemberDTO memberDTO) {
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
    public String login(String userId, String password, HttpSession session) {
        MemberDTO loginMember = memberService.login(userId, password);
        session.setAttribute("loginMember", loginMember.getUserId());
        return userId;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginMember");
        return "logout";
    }

}
