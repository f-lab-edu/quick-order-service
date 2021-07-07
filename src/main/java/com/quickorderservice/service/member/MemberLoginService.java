package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.exception.auth.NeedLoginException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class MemberLoginService {

    private final String MEMBER_UID = "memberUid";
    private final MemberService memberService;
    private final HttpSession httpSession;

    public void login(String memberId, String password) {
        MemberDTO member = memberService.findMemberByIdAndPassword(memberId, password);
        httpSession.setAttribute(MEMBER_UID, member.getUid());
    }

    public void logout() {
        httpSession.removeAttribute(MEMBER_UID);
    }

    public Long getLoginMemberUid() {
        Long memberUid = (Long) httpSession.getAttribute(MEMBER_UID);

        if (memberUid == null)
            throw new NeedLoginException("로그인이 필요합니다.");

        return memberUid;
    }
}
