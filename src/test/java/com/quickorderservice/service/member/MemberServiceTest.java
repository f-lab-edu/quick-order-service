package com.quickorderservice.service.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    void memberServiceDITest() {
        Assertions.assertThat(memberService).isNotNull();
    }
}