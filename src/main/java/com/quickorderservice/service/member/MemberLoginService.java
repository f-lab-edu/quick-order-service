package com.quickorderservice.service.member;

import com.quickorderservice.exception.auth.NeedLoginException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class MemberLoginService {

    private final String MEMBER_ID = "memberID";
    private final MemberService memberService;
    private final HttpSession httpSession;

    public void login(String userId, String password) {
        memberService.findMemberByIdAndPassword(userId, password);
        httpSession.setAttribute(MEMBER_ID, userId);
    }

    public void logout() {
        httpSession.removeAttribute(MEMBER_ID);
    }

    public String getLoginMemberId() {

        String userId = (String) httpSession.getAttribute(MEMBER_ID);

        if (userId == null)
            throw new NeedLoginException("로그인이 필요합니다.");

        return userId;
    }
}
