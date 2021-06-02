package com.quickorderservice.aop.auth;

import com.quickorderservice.exception.auth.NeedLoginException;
import com.quickorderservice.service.member.MemberLoginService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class AuthAop {

    private final MemberLoginService loginService;

    @Before("@annotation(com.quickorderservice.aop.auth.LoginCheck)")
    public void memberLoginCheck() {
        String memberId = loginService.getLoginMemberId();

        if (memberId == null) {
            throw new NeedLoginException();
        }
    }
}
