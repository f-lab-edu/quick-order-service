package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;

import java.util.List;

public interface MemberService {

    int joinMember(MemberDTO memberDTO);

    MemberDTO findMemberById(String id);

    int editMemberInfo(MemberDTO editedMemberDTO);

    int editMemberPassword(String id, String oldPassword, String newPassword);

    int deleteMember(String id, String password);

    List<MemberDTO> findAllMembers();

}
