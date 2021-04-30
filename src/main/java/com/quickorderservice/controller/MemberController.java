package com.quickorderservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /*
        회원 관리 API
        회원 목록 조회: GET /users
        회원 등록: POST /users
        회원 조회: GET /users/{userId}
        회원 수정: PATCH /users/{userId}
        회원 삭제: DELETE /users/{userId
 */

@RestController
@RequestMapping("/members")
public class MemberController {
}
