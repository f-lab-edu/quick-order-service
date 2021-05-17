package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class MemberLoginServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberLoginService loginService;
    private String MEMBER_ID = "test";
    private String MEMBER_PASSWORD = "1234";
    private String MEMBER_WRONG_ID = "hello";
    private String MEMBER_WRONG_PASSWORD = "12345";

    @BeforeEach
    public void beforeEach() {
        MemberDTO member = new MemberDTO(null, "test", "1234", "jang", "010-0000-0000",
                "test@naver.com", "korea", LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));
        memberService.joinMember(member);
    }

    @Test
    @DisplayName("정상적으로 로그인하면 httpStatus.OK를 반환한다.")
    public void loginSuccess() {
        HttpStatus httpStatus = loginService.login(MEMBER_ID, MEMBER_PASSWORD);
        Assertions.assertThat(httpStatus).isEqualTo(httpStatus.OK);
    }

    @Test
    @DisplayName("정상적으로 로그인하면 loginService에서 멤버의 ID를 반환가능하다.")
    public void loginSuccessCheckId() {
        HttpStatus httpStatus = loginService.login(MEMBER_ID, MEMBER_PASSWORD);
        Assertions.assertThat(loginService.getMemberId()).isEqualTo(MEMBER_ID);
    }

    @Test
    @DisplayName("암호가 다르면 httpStatus.BAD_REQUEST 반환한다.")
    public void loginFailWithWrongPassword() {
        HttpStatus httpStatus = loginService.login(MEMBER_ID, MEMBER_WRONG_PASSWORD);
        Assertions.assertThat(httpStatus).isEqualTo(httpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("존재하지 않은 아이디로 로그인시도하면 httpStatus.BAD_REQUEST 반환한다.")
    public void loginFailWithNoMember() {
        HttpStatus httpStatus = loginService.login(MEMBER_WRONG_ID, MEMBER_WRONG_PASSWORD);
        Assertions.assertThat(httpStatus).isEqualTo(httpStatus.BAD_REQUEST);
    }
}