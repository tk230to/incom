package com.incom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incom.security.Role;

/**
 * ロールRestコントローラクラス。
 */
@RestController
@RequestMapping("/roles")
public class RoleRestController {

    /**
     * 全件表示。
     *
     * @return 検索結果
     */
    @GetMapping
    public ResponseEntity<Role[]> get() {
        return ResponseEntity.ok(Role.values());
    }
}
