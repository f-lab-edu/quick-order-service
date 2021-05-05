package com.quickorderservice.mapper;

import com.quickorderservice.dto.member.MemberDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
class MemberMapperTest {

    @Autowired
    MemberMapper memberMapper;

    @Test
    void memberServiceDITest() {
        Assertions.assertThat(memberMapper).isNotNull();
    }

    @Test
    void insertMemberAndFindMemberById() {
        MemberDTO memberDTO = new MemberDTO(
                         "test1","1234","test",
                      "010-0000-000","test@gmail.com","seoul",
                          LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0));

        int result = memberMapper.insertMember(memberDTO);
        Assertions.assertThat(result).isEqualTo(1);

        MemberDTO findMember = memberMapper.selectMemberById(memberDTO.getId());
        Assertions.assertThat(memberDTO).isEqualTo(findMember);

    }

    @Test
    void deleteMember() {
        MemberDTO memberDTO = new MemberDTO(
                "test1","1234","test",
                "010-0000-000","test@gmail.com","seoul",
                LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0));

        memberMapper.insertMember(memberDTO);

        int result = memberMapper.deleteMember(memberDTO.getId());
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void findAllMembers() {
        MemberDTO memberDTO1 = new MemberDTO(
                "test1","1234","test",
                "010-0000-000","test@gmail.com","seoul",
                LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0));
        MemberDTO memberDTO2 = new MemberDTO(
                "test2","1234","test",
                "010-0000-000","test@gmail.com","seoul",
                LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0));

        memberMapper.insertMember(memberDTO1);
        memberMapper.insertMember(memberDTO2);

        List<MemberDTO> members = memberMapper.selectAllMembers();

        Assertions.assertThat(members.size()).isEqualTo(2);

        members.forEach(System.out::println);
    }

    @Test
    void updateMember() {
        MemberDTO memberDTO1 = new MemberDTO(
                "test1","1234","test",
                "010-0000-000","test@gmail.com","seoul",
                LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0));

        memberMapper.insertMember(memberDTO1);

        String newName = "myName";
        memberDTO1.setName(newName);
        int result = memberMapper.updateMember(memberDTO1);

        Assertions.assertThat(result).isEqualTo(1);

        MemberDTO findMember = memberMapper.selectMemberById(memberDTO1.getId());

        Assertions.assertThat(findMember.getName()).isEqualTo(newName);
    }

    @Test
    void updateMemberPassword() {
        MemberDTO memberDTO1 = new MemberDTO(
                "test1","1234","test",
                "010-0000-000","test@gmail.com","seoul",
                LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0));

        memberMapper.insertMember(memberDTO1);

        String newPassword =  "9876";
        memberDTO1.setPassword(newPassword);
        int result = memberMapper.updateMemberPassword(memberDTO1);

        Assertions.assertThat(result).isEqualTo(1);

        MemberDTO findMember = memberMapper.selectMemberById(memberDTO1.getId());

        Assertions.assertThat(findMember.getPassword()).isEqualTo(newPassword);
    }
}