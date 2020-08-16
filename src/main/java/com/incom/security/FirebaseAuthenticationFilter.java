package com.incom.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.incom.model.Customer;
import com.incom.model.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Firebase認証フィルタークラス。
 */
@Slf4j
@Component
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    /** 顧客リポジトリ */
    @Autowired
    CustomerRepository customerRepository;

    /** 認証ヘッダを識別する文字列 */
    private static String AUTH_HEADER = "Authorization";
    private static String AUTH_BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        setAuthentication(request, response);
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(HttpServletRequest request,
            HttpServletResponse response) {

        // リクエストヘッダからトークンを取得
        String idToken = Optional.ofNullable(request.getHeader(AUTH_HEADER)).orElse("").replaceFirst(AUTH_BEARER, "");
        if (StringUtils.isEmpty(idToken)) {
            return;
        }

        try {

            // トークンを検証
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

            // 認証トークンを作成
            Customer customer = customerRepository.findById(firebaseToken.getEmail()).get();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    firebaseToken.getEmail(), firebaseToken,
                    AuthorityUtils.createAuthorityList(customer.getRole().name()));

            // 認証トークンをコンテキストにセット
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("successfully authenticated.");

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.debug("Fail to authenticate.", ex);
        }
    }
}