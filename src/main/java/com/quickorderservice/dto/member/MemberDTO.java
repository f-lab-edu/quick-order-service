package com.quickorderservice.dto.member;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDTO {

    private String id;
    private String password;
    private String name;
    private String tel;
    private String email;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
