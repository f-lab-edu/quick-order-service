package com.quickorderservice.utiles.geo;

import com.quickorderservice.exception.GeoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Geocoding {

    @Value("${geo.api.id}")
    private String API_KEY_ID;
    @Value("${geo.api.key}")
    private String API_KEY;
    @Value("${geo.api.url}")
    private String API_URL;

    public GeoData getGeoDataByAddress(String completeAddress) {
        String url = API_URL + completeAddress;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-NCP-APIGW-API-KEY-ID", API_KEY_ID);
        headers.set("X-NCP-APIGW-API-KEY", API_KEY);

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<GeoData> response = restTemplate.exchange(url, HttpMethod.GET, request, GeoData.class);

        GeoData geoData = response.getBody();
        if (geoData.getMeta().getCount() == 0)
            throw new GeoException("잘못된 주소를 입력하였습니다.");

        return response.getBody();
    }

    public LatLonData getLatLon(String completeAddress) {
        GeoData geoData = getGeoDataByAddress(completeAddress);
        GeoData.Address[] addresses = geoData.getAddresses();
        double lat = addresses[0].getX();
        double lon = addresses[0].getY();
        return new LatLonData(lat, lon);
    }

}
