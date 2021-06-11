package com.quickorderservice.aop.auth;

import com.quickorderservice.exception.auth.NeedLoginException;
import com.quickorderservice.service.member.MemberLoginService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class AuthAop {

    private final MemberLoginService loginService;

    @Around("@annotation(com.quickorderservice.aop.auth.LoginCheck)")
    public void memberLoginCheck(ProceedingJoinPoint jp) throws Throwable {
        String memberId = loginService.getLoginMemberId();

        if (memberId == null) {
            throw new NeedLoginException();
        }

        jp.getArgs()[0] = memberId;
        jp.proceed(jp.getArgs());
    }
}
