package com.incom.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.incom.model.CartItemRepository;
import com.incom.model.SalesOrder;
import com.incom.model.SalesOrderRepository;
import com.incom.util.MailUtils;

/**
 * 注文画面のコントローラクラス。
 */
@RestController
@RequestMapping("/salesorders")
public class SalesOrderRestController extends BaseRestController<SalesOrderRepository, SalesOrder, Long> {

    @Autowired
    public SalesOrderRestController(SalesOrderRepository respository) {
        super(respository);
    }

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MailUtils mailUtils;

    /**
     * 登録。
     *
     * @param entity リクエスト
     * @return レスポンス
     * @throws IOException
     */
    @PostMapping
    public ResponseEntity<SalesOrder> create(@Valid @RequestBody SalesOrder salesOrder) {
        salesOrder = getRepository().save(salesOrder);
        salesOrder.getCustomer().getCartItems().forEach(cartItem -> cartItemRepository.delete(cartItem));
        mailUtils.send(salesOrder.getCustomer().getEmail(), getMailText(salesOrder));
        return ResponseEntity.ok(salesOrder);
    }

    /**
     * 注文メール本文作成
     * @param salesOrder
     * @return
     */
    private String getMailText(SalesOrder salesOrder) {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);

        Map<String, Object> variables = new HashMap<>();
        variables.put("salesOrder", salesOrder);
        Context context = new Context();
        context.setVariables(variables);
        String text = engine.process("/templates/mail/order.txt", context);
        return text;
    }
}
