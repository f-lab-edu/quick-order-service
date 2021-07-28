package com.quickorderservice.utiles.geo;

import com.quickorderservice.utiles.geo.GeoData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class Geocoding {

    private static String API_KEY_ID;
    private static String API_KEY;

    @Value("${geo.api.key}")
    private void setValue(String key) {
        API_KEY = key;
    }

    @Value("${geo.api.id}")
    private void setValue2(String id) {
        API_KEY_ID = id;
    }

    public static GeoData getGeoDataByAddress(String completeAddress) {

        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + completeAddress;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-NCP-APIGW-API-KEY-ID", API_KEY_ID);
        headers.set("X-NCP-APIGW-API-KEY", API_KEY);

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<GeoData> response = restTemplate.exchange(url, HttpMethod.GET, request, GeoData.class);

        return response.getBody();
    }

}
