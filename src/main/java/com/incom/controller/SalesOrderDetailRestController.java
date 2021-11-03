package com.incom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incom.model.SalesOrderDetail;
import com.incom.model.SalesOrderDetailRepository;

/**
 * 注文商品画面のコントローラクラス。
 */
@RestController
@RequestMapping("/salesorderdetails")
public class SalesOrderDetailRestController
        extends BaseRestController<SalesOrderDetailRepository, SalesOrderDetail, Long> {

    @Autowired
    public SalesOrderDetailRestController(SalesOrderDetailRepository respository) {
        super(respository);
    }
}
