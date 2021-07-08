package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class MemberLoginService implements LoginService {

    private final String MEMBER_ID = "MemberId";
    private final MemberService memberService;
    private final HttpSession httpSession;

    public void login(String userId, String password) {
        MemberDTO loginMember = memberService.findMemberByIdAndPassword(userId, password);
        httpSession.setAttribute(MEMBER_ID, userId);
    }

    public void logout() {
        httpSession.removeAttribute(MEMBER_ID);
    }

    public Long getLoginUid() {
        Long userId = (Long) httpSession.getAttribute(MEMBER_ID);
        return userId;
    }
}
