package com.incom.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 顧客リポジトリクラス。
 */
public interface CustomerRepository extends JpaRepository<Customer, String> {

    List<Customer> findByNameContaining(String name);

    List<Customer> findByTelEquals(String tel);
}
