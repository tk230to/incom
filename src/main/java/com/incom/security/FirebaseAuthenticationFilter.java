package com.incom.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    CustomerRepository customerRepository;

    /** 認証ヘッダを識別する文字列 */
    private static String AUTH_HEADER = "Authorization";
    private static String AUTH_BEARER = "Bearer ";

    /**
     * コンストラクタ。
     * @param customerRepository
     */
    public FirebaseAuthenticationFilter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        setAuthentication(request, response);
        filterChain.doFilter(request, response);
    }

    private FirebaseToken verifyIdToken(HttpServletRequest request, HttpServletResponse response) {

        // リクエストヘッダからトークンを取得
        String idToken = Optional.ofNullable(request.getHeader(AUTH_HEADER)).orElse("").replaceFirst(AUTH_BEARER, "");
        if (StringUtils.isEmpty(idToken)) {
            return null;
        }

        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return firebaseToken;

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.debug("Fail to authenticate.", ex);
            return null;
        }
    }

    private void setAuthentication(HttpServletRequest request, HttpServletResponse response) {

        // トークンを検証
        FirebaseToken firebaseToken = verifyIdToken(request, response);
        if (firebaseToken == null) {
            return;
        }

        // 顧客取得
        Customer customer = customerRepository.findById(firebaseToken.getUid()).orElse(null);
        if (customer == null) {
            customer = new Customer(firebaseToken);
            customer = customerRepository.save(customer);
        }

        // 認証トークンを作成
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                firebaseToken.getUid(), firebaseToken,
                AuthorityUtils.createAuthorityList(customer.getRole().name()));

        // 認証トークンをコンテキストにセット
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("successfully authenticated.");
    }
}