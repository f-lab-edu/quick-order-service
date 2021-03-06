package com.quickorderservice.mapper;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.utiles.geo.LatLonData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
                null, "test1", "1234", "test",
                "010-0000-000", "test@gmail.com", "seoul", 0, 0,
                LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        int result = memberMapper.insertMember(memberDTO, "1234", new LatLonData(0, 0));
        Assertions.assertThat(result).isEqualTo(1);

        MemberDTO findMember = memberMapper.selectMemberById(memberDTO.getMemberId());
        Assertions.assertThat(memberDTO).isEqualTo(findMember);

    }

    @Test
    @DisplayName("userID와 password로 DB에 있는 멤버를 정상적으로 가져온다.")
    void insertMemberAndFindMemberByIdAndPassword() {
        MemberDTO memberDTO = new MemberDTO(
                null, "test1", "1234", "test",
                "010-0000-000", "test@gmail.com", "seoul", 0, 0,
                LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        int result = memberMapper.insertMember(memberDTO, "1234", new LatLonData(0, 0));
        Assertions.assertThat(result).isEqualTo(1);

        MemberDTO findMember = memberMapper.selectMemberByIdAndPassword(memberDTO.getMemberId(), memberDTO.getPassword());
        Assertions.assertThat(memberDTO).isEqualTo(findMember);

    }

    @Test
    void deleteMember() {
        MemberDTO memberDTO = new MemberDTO(
                null, "test1", "1234", "test",
                "010-0000-000", "test@gmail.com", "seoul", 0, 0,
                LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberMapper.insertMember(memberDTO, "1234", new LatLonData(0, 0));

        MemberDTO findMember = memberMapper.selectMemberById(memberDTO.getMemberId());
        int result = memberMapper.deleteMember(findMember.getUid());
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void findAllMembers() {
        MemberDTO memberDTO1 = new MemberDTO(
                null, "test1", "1234", "test",
                "010-0000-000", "test@gmail.com", "seoul", 0, 0,
                LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));
        MemberDTO memberDTO2 = new MemberDTO(
                null, "test2", "1234", "test",
                "010-0000-000", "test@gmail.com", "seoul", 0, 0,
                LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberMapper.insertMember(memberDTO1, "1234", new LatLonData(0, 0));
        memberMapper.insertMember(memberDTO2, "1234", new LatLonData(0, 0));

        List<MemberDTO> members = memberMapper.selectAllMembers();

        Assertions.assertThat(members.size()).isEqualTo(2);

        members.forEach(System.out::println);
    }

    @Test
    void updateMember() {
        MemberDTO memberDTO1 = new MemberDTO(
                null, "test1", "1234", "test",
                "010-0000-000", "test@gmail.com", "seoul", 0, 0,
                LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberMapper.insertMember(memberDTO1, "1234", new LatLonData(0, 0));

        MemberDTO findMember = memberMapper.selectMemberById(memberDTO1.getMemberId());

        MemberDTO memberDTO2 = new MemberDTO(
                findMember.getUid(), "edit", "4567", "newName",
                "010-1111-2222", "test@gmail.com", "seoul", 0, 0,
                LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        int result = memberMapper.updateMember(memberDTO2);

        Assertions.assertThat(result).isEqualTo(1);

        MemberDTO findMember2 = memberMapper.selectMemberById(memberDTO1.getMemberId());

        Assertions.assertThat(findMember2.getName()).isEqualTo("newName");
    }

    @Test
    void updateMemberPassword() {
        MemberDTO memberDTO1 = new MemberDTO(
                null, "test1", "1234", "test",
                "010-0000-000", "test@gmail.com", "seoul", 0, 0,
                LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberMapper.insertMember(memberDTO1, "1234", new LatLonData(0, 0));

        String newPassword = "9876";
        MemberDTO findMember1 = memberMapper.selectMemberById(memberDTO1.getMemberId());

        int result = memberMapper.updateMemberPassword(findMember1, newPassword);

        Assertions.assertThat(result).isEqualTo(1);

        MemberDTO findMember2 = memberMapper.selectMemberById(memberDTO1.getMemberId());

        Assertions.assertThat(findMember2.getPassword()).isEqualTo(newPassword);
    }
}