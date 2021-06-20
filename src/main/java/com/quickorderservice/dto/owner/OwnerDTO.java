package com.quickorderservice.dto.owner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OwnerDTO {

    private Long uid;
    private String ownerId;
    private String password;
    private String name;
    private String tel;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return ((OwnerDTO) obj).getOwnerId().equals(this.getOwnerId());
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
