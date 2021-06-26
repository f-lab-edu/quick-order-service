package com.quickorderservice.service.member;

import com.quickorderservice.exception.auth.NeedLoginException;
import com.quickorderservice.repository.RedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class MemberLoginService {

    private final String MEMBER_ID = "memberID";
    private final MemberService memberService;
    private final HttpSession httpSession;
    private final RedisRepository redisRepository;

    public void login(String userId, String password) {
        memberService.findMemberByIdAndPassword(userId, password);
        httpSession.setAttribute(MEMBER_ID,userId);
        redisRepository.set(httpSession.getId(), httpSession);
    }

    public void logout() {
        redisRepository.remove(httpSession.getId());
    }

    public String getLoginMemberId() {
        HttpSession session = (HttpSession) redisRepository.get(httpSession.getId());

        if(session==null)
            throw new NeedLoginException("로그인이 필요합니다.");

        return (String) session.getAttribute(MEMBER_ID);
    }
}
