package com.quickorderservice.mapper;

import com.quickorderservice.dto.member.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    int insertMember(@Param("member") MemberDTO memberDTO, @Param("password") String password, @Param("lat") double lat, @Param("lon") double lon);

    int deleteMember(Long uid);

    MemberDTO selectMemberById(String memberId);

    MemberDTO selectMemberByUid(Long uid);

    int updateMember(MemberDTO memberDTO);

    int updateMemberPassword(MemberDTO memberDTO, String password);

    List<MemberDTO> selectAllMembers();

    MemberDTO selectMemberByIdAndPassword(@Param("memberId") String memberId, @Param("password") String password);

}
