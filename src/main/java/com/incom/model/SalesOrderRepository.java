package com.incom.model;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 注文リポジトリクラス。
 */
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {

}
