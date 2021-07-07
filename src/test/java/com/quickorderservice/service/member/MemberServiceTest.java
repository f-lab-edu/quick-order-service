package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.exception.member.NotFoundMemberException;
import com.quickorderservice.exception.member.EditMemberException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberLoginService loginService;

    @Test
    @DisplayName("memberService 의존 관계 주입 확인")
    void memberServiceDITest() {
        Assertions.assertThat(memberService).isNotNull();
    }

    @Test
    @DisplayName("회원 가입")
    void joinMember() {
        MemberDTO member = new MemberDTO(null, "test", "1234", "jang", "010-0000-0000",
                "test@naver.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        int result = memberService.joinMember(member);
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("회원 조회")
    void findMemberById() {
        MemberDTO member = new MemberDTO(null, "test", "1234", "jang", "010-0000-0000",
                "test@naver.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberService.joinMember(member);

        MemberDTO findMember = memberService.findMemberById(member.getMemberId());

        Assertions.assertThat(member).isEqualTo(findMember);
    }

    @Test
    @DisplayName("없는 회원 조회")
    void findMemberByIdWithNoMember() {
        assertThrows(NotFoundMemberException.class, () -> {
            MemberDTO findMember = memberService.findMemberById("noMember");
        });
    }

    @Test
    @DisplayName("정상적인 회원 삭제시 1을 반환한다.")
    void deleteMember() {
        MemberDTO member = new MemberDTO(null, "test", "1234", "jang", "010-0000-0000",
                "test@naver.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberService.joinMember(member);
        loginService.login(member.getMemberId(), "1234");

        int result = memberService.deleteMember(loginService.getLoginMemberUid(), "1234");
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("회원 삭제시 비밀번호를 잘못 입력하면 NotFoundMemberException이 발생한다.")
    void deleteMemberWithWrongPassword() {
        MemberDTO member = new MemberDTO(null, "test", "1234", "jang", "010-0000-0000",
                "test@naver.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberService.joinMember(member);

        assertThrows(NotFoundMemberException.class, () -> {
            memberService.deleteMember(member.getUid(), "11");
        });
    }

    @Test
    @DisplayName("정상적으로 회원 정보 수정시 에러가 발생하지 않는다.")
    void editMemberInfo() {
        MemberDTO member = new MemberDTO(null, "test", "1234", "jang", "010-0000-0000",
                "test@naver.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberService.joinMember(member);
        loginService.login(member.getMemberId(), "1234");
        MemberDTO findMember = memberService.findMemberById(member.getMemberId());

        memberService.editMemberInfo(findMember);
    }

    @Test
    @DisplayName("정상적으로 비밀번호 수정시 에러가 발생하지 않는다.")
    void editMemberPassword() {
        String oldPassword = "1234";
        String newPassword = "7890";
        MemberDTO member = new MemberDTO(null, "test", oldPassword, "jang", "010-0000-0000",
                "test@naver.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberService.joinMember(member);
        loginService.login(member.getMemberId(), oldPassword);

        memberService.editMemberPassword(loginService.getLoginMemberUid(), oldPassword, newPassword);
    }

    @Test
    @DisplayName("비밀번호 수정 시 기존의 비밀번호를 잘못입력하면 NotFoundMemberException 발생한다.")
    void editMemberPasswordWithWrongPassword() {
        String oldPassword = "1234";
        String newPassword = "7890";
        MemberDTO member = new MemberDTO(null, "test", oldPassword, "jang", "010-0000-0000",
                "test@naver.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberService.joinMember(member);
        loginService.login(member.getMemberId(), oldPassword);

        assertThrows(NotFoundMemberException.class, () -> {
            memberService.editMemberPassword(member.getUid(), oldPassword + 1, newPassword);
        });
    }

    @Test
    @DisplayName("비밀번호 수정 시 기존의 비밀번호와 같으면 EditMemberException이 발생한다.")
    void editMemberPasswordWithSamePassword() {
        String oldPassword = "1234";
        String newPassword = "1234";
        MemberDTO member = new MemberDTO(null, "test", oldPassword, "jang", "010-0000-0000",
                "test@naver.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));

        memberService.joinMember(member);
        loginService.login(member.getMemberId(), oldPassword);

        assertThrows(EditMemberException.class, () -> {
            memberService.editMemberPassword(member.getUid(), oldPassword, newPassword);
        });
    }
}