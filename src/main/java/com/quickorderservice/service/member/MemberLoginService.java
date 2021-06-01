package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.exception.member.NotFoundMemberException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class MemberLoginService {

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

    public String getLoginMemberId() {
        String userId = (String) httpSession.getAttribute(MEMBER_ID);

        if(userId == null)
            throw new NotFoundMemberException("로그인이 필요합니다.");

        return (String) httpSession.getAttribute(MEMBER_ID);
    }
}
