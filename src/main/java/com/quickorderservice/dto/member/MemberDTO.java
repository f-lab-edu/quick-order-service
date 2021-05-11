package com.quickorderservice.dto.member;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {

    private Long uid;
    private String userId;
    private String password;
    private String name;
    private String tel;
    private String email;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return ((MemberDTO) obj).getUserId().equals(this.getUserId());
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
