package com.quickorderservice.mapper;

import com.quickorderservice.dto.member.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    int insertMember(MemberDTO memberDTO);

    int deleteMember(String id);

    MemberDTO selectMemberById(String id);

    int updateMember(MemberDTO memberDTO);

    int updateMemberPassword(MemberDTO memberDTO);

    int deleteAllMembers();

    List<MemberDTO> selectAllMembers();
}
