package com.quickorderservice.dto.rider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RiderDTO {

    private Long uid;
    private String riderId;
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
        return ((RiderDTO) obj).getUid().equals(this.getUid());
    }

}
