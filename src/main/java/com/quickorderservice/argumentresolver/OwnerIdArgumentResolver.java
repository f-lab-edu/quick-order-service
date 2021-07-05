package com.quickorderservice.argumentresolver;

import com.quickorderservice.annotation.OwnerId;
import com.quickorderservice.exception.auth.NeedLoginException;
import com.quickorderservice.service.owner.OwnerLoginService;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@AllArgsConstructor
public class OwnerIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final OwnerLoginService loginService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(OwnerId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String ownerId = loginService.getLoginOwnerId();

        if (ownerId == null)
            throw new NeedLoginException("로그인이 필요합니다.");

        return ownerId;
    }
}
