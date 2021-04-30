package com.quickorderservice.mapper;

import com.quickorderservice.dto.member.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*

 반환 값 :     iBATIS                        MyBatis
 SELECT : SELECT문에 해당하는 결과 <-> SELECT문에 해당하는 결과
 INSERT : NULL                  <-> 1(다중 INSERT도 마찬가지)
 UPDATE : 1                     <-> UPDATE된 행의 갯수 반환(없으면 0)
 DELETE : DELETE된 행의 갯수      <-> DELETE된 행의 갯수(없으면 0)
 */

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
