package com.quickorderservice.service;

public interface LoginService {

    void login(String id, String password);

    void logout();

    long getLoginUid();
}
