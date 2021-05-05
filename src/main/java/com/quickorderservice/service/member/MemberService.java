package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;

import java.util.List;

/*
    1. 회원 가입 (UserDTO)
    2. 회원 조회 (id)
    3. 회원 정보 수정 (id, UserDTO)
    4. 회원 비밀번호 수정 (id, pw)
    5. 회원 탈퇴 (id, pw)
    6. 회원 전체 조회
    7. 회원 전체 삭제
 */

public interface MemberService {

    int joinMember(MemberDTO memberDTO) throws Exception;

    MemberDTO findMemberById(String id) throws IllegalAccessException;

    int editMemberInfo(MemberDTO editedMemberDTO);

    int editMemberPassword(String id, String oldPassword, String newPassword) throws Exception;

    int deleteMember(String id, String password) throws Exception;

    List<MemberDTO> findAllMembers();

    int deleteAllMember();

}
