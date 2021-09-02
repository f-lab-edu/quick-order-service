package com.quickorderservice.mapper;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.utiles.geo.LatLonData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    int insertMember(@Param("member") MemberDTO memberDTO, @Param("password") String password, @Param("latlon") LatLonData latLonData);

    int deleteMember(Long uid);

    MemberDTO selectMemberById(String memberId);

    MemberDTO selectMemberByUid(Long uid);

    int updateMember(MemberDTO memberDTO);

    int updateMemberPassword(@Param("member") MemberDTO memberDTO, @Param("password") String password);

    List<MemberDTO> selectAllMembers();

    MemberDTO selectMemberByIdAndPassword(@Param("memberId") String memberId, @Param("password") String password);

}
