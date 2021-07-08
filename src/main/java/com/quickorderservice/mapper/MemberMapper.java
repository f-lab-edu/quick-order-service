package com.quickorderservice.mapper;

import com.quickorderservice.dto.member.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    int insertMember(MemberDTO memberDTO);

    int deleteMember(Long uid);

    MemberDTO selectMemberById(String memberId);

    MemberDTO selectMemberByUid(Long uid);

    int updateMember(MemberDTO memberDTO);

    int updateMemberPassword(MemberDTO memberDTO);

    List<MemberDTO> selectAllMembers();

    MemberDTO selectMemberByIdAndPassword(@Param("memberId") String memberId, @Param("password") String password);

}
