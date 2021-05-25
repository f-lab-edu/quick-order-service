package com.quickorderservice.controller;

import com.quickorderservice.dto.member.MemberDTO;

import java.util.List;

import com.quickorderservice.exception.member.NotFoundMemberException;
import com.quickorderservice.service.member.MemberLoginService;
import com.quickorderservice.service.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler
    public ResponseEntity notFoundExceptionHandler(NotFoundMemberException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
