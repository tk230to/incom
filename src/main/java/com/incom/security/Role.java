package com.incom.security;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role implements GrantedAuthority {

    // ゲスト
    ROLE_ANONYMOUS(1, "ゲスト"),

    // 一般
    ROLE_USER(2, "一般"),

    // 管理者
    ROLE_ADMIN(3, "管理者");

    public static final String ANONYMOUS = "ANONYMOUS";
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    /** ID */
    private final int id;

    /** 表示名 */
    private final String name;

    @Override
    public String getAuthority() {
        return name();
    }

    /**
     * JSONデシリアライザー。
     *
     * @param id ロールID
     * @return ロール
     */
    @JsonCreator
    public static Role forValues(@JsonProperty("id") int id) {
        for (Role role : Role.values()) {
            if (role.id == id) {
                return role;
            }
        }
        return null;
    }
}
