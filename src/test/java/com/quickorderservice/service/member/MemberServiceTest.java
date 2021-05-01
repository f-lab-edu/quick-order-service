package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.utiles.SHA256;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void memberServiceDITest() {
        Assertions.assertThat(memberService).isNotNull();
    }

    @BeforeEach
    void before() {
        memberService.deleteAllMember();
    }

    @Test
    void joinMember() throws Exception {
        MemberDTO member = new MemberDTO("test", "1234", "jang", "010-0000-0000",
                "test@naver.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        int result = memberService.joinMember(member);
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void findMemberById() throws Exception {
        MemberDTO member = new MemberDTO("test", "1234", "jang", "010-0000-0000",
                "test@naver.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberService.joinMember(member);

        MemberDTO findMember = memberService.findMemberById(member.getId());

        Assertions.assertThat(member).isEqualTo(findMember);
    }

    @Test
    void findMemberByIdWithNoMember() throws IllegalAccessException {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalAccessException.class, () -> {
            MemberDTO findMember = memberService.findMemberById("noMember");
        });
    }

    @Test
    void deleteMember() throws Exception {
        MemberDTO member = new MemberDTO("test", "1234", "jang", "010-0000-0000",
                "test@naver.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberService.joinMember(member);


        org.junit.jupiter.api.Assertions.assertThrows(IllegalAccessException.class, () -> {
            memberService.deleteMember(member.getId(), "11");
        });

        int result = memberService.deleteMember(member.getId(), "1234");
        Assertions.assertThat(result).isEqualTo(1);
        org.junit.jupiter.api.Assertions.assertThrows(IllegalAccessException.class, () -> {
            MemberDTO findMember = memberService.findMemberById(member.getId());
        });
    }

    @Test
    void editMemberInfo() throws Exception {
        MemberDTO member = new MemberDTO("test", "1234", "jang", "010-0000-0000",
                "test@naver.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberService.joinMember(member);

        MemberDTO editedMember = new MemberDTO("test", "1234", "park", "010-1111-2222",
                "test@google.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberService.editMemberInfo(editedMember);
    }

    @Test
    void editMemberPassword() throws Exception {
        MemberDTO member = new MemberDTO("test", "1234", "jang", "010-0000-0000",
                "test@naver.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberService.joinMember(member);

        String newPassword = "7890";

        int result = memberService.editMemberPassword(member.getId(), "1234", newPassword);

        MemberDTO findMember = memberService.findMemberById(member.getId());
        Assertions.assertThat(result).isEqualTo(1);
        Assertions.assertThat(findMember.getPassword()).isEqualTo(SHA256.encBySha256(newPassword));
    }
}