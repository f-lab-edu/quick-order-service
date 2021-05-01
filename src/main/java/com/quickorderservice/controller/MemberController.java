package com.quickorderservice.controller;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.service.member.MemberService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

 /*
        회원 관리 API
        회원 목록 조회: GET /members
        회원 등록: POST /members
        회원 조회: GET /members/{id}
        회원 수정: PATCH /members/{id}
        회원 삭제: DELETE /members/{id}
 */

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
    public MemberDTO findMemberById(@PathVariable String id) throws IllegalAccessException {
        return memberService.findMemberById(id);
    }

    @PostMapping
    public int joinMember(@ModelAttribute MemberDTO memberDTO) throws Exception {
        return memberService.joinMember(memberDTO);
    }

    @PatchMapping("/edit/info")
    public int editMemberInfo(@ModelAttribute MemberDTO memberDTO) {
        return memberService.editMemberInfo(memberDTO);
    }

    @PatchMapping("edit/password")
    public int editMemberPassword(String id, String oldPassword, String newPassword) throws Exception {
        return memberService.editMemberPassword(id,oldPassword,newPassword);
    }

    @DeleteMapping
    public int deleteMember(String id, String password) throws Exception {
        return memberService.deleteMember(id, password);
    }
}
