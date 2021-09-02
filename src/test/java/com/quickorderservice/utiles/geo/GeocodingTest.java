package com.quickorderservice.utiles.geo;

import com.quickorderservice.exception.GeoException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GeocodingTest {

    @Autowired
    Geocoding geocoding;

    @Test
    @DisplayName("정상적으로 주소를 입력하면 geo 데이터를 불러온다.")
    public void geo() {
        GeoData geoDataByAddress = geocoding.getGeoDataByAddress("분당구 불정로 1");
        GeoData.Address[] addresses = geoDataByAddress.getAddresses();

        Assertions.assertThat(geoDataByAddress.getStatus()).isEqualTo("OK");
        Assertions.assertThat(addresses.length).isNotEqualTo(0);
    }

    @Test
    @DisplayName("잘못된 주소를 입력하면 예외를 발생시킨다")
    public void geoWithWrongAddress() {
        assertThrows(GeoException.class, () -> {
            GeoData geoDataByAddress = geocoding.getGeoDataByAddress("잘못된 주소");
        });
    }
}