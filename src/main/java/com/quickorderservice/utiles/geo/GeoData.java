package com.quickorderservice.utiles.geo;

import lombok.*;

@Getter
@ToString
public class GeoData {

    private String status;
    private Meta meta;
    private Address[] addresses;

    @Getter
    @ToString
    static class Meta {
        private int totalCount;
        private int page;
        private int count;
    }

    @Getter
    @ToString
    public static class Address {
        private String roadAddress;
        private String jibunAddress;
        private String englishAddress;
        private float x;
        private float y;
        private float distance;
    }

}

