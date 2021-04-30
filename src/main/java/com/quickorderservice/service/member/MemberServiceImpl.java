package com.quickorderservice.service.member;

import com.quickorderservice.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private  final MemberMapper memberMapper;

}
