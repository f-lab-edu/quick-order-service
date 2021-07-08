package com.quickorderservice.argumentresolver;

import com.quickorderservice.annotation.MemberId;
import com.quickorderservice.exception.auth.NeedLoginException;
import com.quickorderservice.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@AllArgsConstructor
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final LoginService memberLoginService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Long memberUid = loginService.getLoginMemberUid();

        if (memberUid == null)
            throw new NeedLoginException();

        return memberUid;
    }
}
