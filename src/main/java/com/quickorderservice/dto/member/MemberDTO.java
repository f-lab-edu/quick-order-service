package com.quickorderservice.dto.member;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {

    private String id;
    private String password;
    private String name;
    private String tel;
    private String email;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return ((MemberDTO) obj).getId().equals(this.id);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
