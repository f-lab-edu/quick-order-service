package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;
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

    public HttpStatus login(String userId, String password) {
        try {
            MemberDTO loginMember = memberService.findMemberByIdAndPassword(userId, password);
            httpSession.setAttribute(MEMBER_ID, userId);
        } catch (IllegalArgumentException e) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    public HttpStatus logout() {
        httpSession.removeAttribute(MEMBER_ID);
        return HttpStatus.OK;
    }

    public String getMemberId() {
        return (String) httpSession.getAttribute(MEMBER_ID);
    }
}
