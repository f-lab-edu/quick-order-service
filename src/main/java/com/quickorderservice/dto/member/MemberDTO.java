package com.quickorderservice.dto.member;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {

    private Long uid;
    private String memberId;
    private String password;
    private String name;
    private String tel;
    private String email;
    private String address;
    private double lat;
    private double lon;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return ((MemberDTO) obj).getMemberId().equals(this.getMemberId());
    }

}
