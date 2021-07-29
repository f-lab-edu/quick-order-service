package com.quickorderservice.utiles.geo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GeocodingTest {

    @Test
    @DisplayName("정상적으로 주소를 입력하면 geo 데이터를 불러온다.")
    public void geo() {
        GeoData geoDataByAddress = new Geocoding().getGeoDataByAddress("분당구 불정로 1");
        GeoData.Address[] addresses = geoDataByAddress.getAddresses();

        Assertions.assertThat(geoDataByAddress.getStatus()).isEqualTo("OK");
        Assertions.assertThat(addresses.length).isNotEqualTo(0);
    }

    @Test
    @DisplayName("잘못된 주소를 입력하면 geo 데이터는 비어있다.")
    public void geoWithWrongAddress() {
        GeoData geoDataByAddress = new Geocoding().getGeoDataByAddress("잘못된 주소");

        GeoData.Meta meta = geoDataByAddress.getMeta();
        GeoData.Address[] addresses = geoDataByAddress.getAddresses();

        Assertions.assertThat(addresses).isEmpty();
        Assertions.assertThat(meta.getCount()).isEqualTo(0);
        Assertions.assertThat(geoDataByAddress.getStatus()).isEqualTo("OK");
        Assertions.assertThat(geoDataByAddress.getAddresses().length).isEqualTo(0);
    }
}