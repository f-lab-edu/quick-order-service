package com.quickorderservice.service.member;

import com.quickorderservice.repository.RedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class MemberLoginService {

    private final MemberService memberService;
    private final HttpSession httpSession;
    private final RedisRepository redisRepository;

    public void login(String userId, String password) {
        memberService.findMemberByIdAndPassword(userId, password);
        redisRepository.set(httpSession.getId(), userId);
    }

    public void logout() {
        redisRepository.remove(httpSession.getId());
    }

    public String getLoginMemberId() {
        return redisRepository.get(httpSession.getId());
    }
}
