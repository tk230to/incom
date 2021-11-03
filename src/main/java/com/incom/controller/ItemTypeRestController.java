package com.incom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incom.model.ItemType;
import com.incom.model.ItemTypeRepository;

/**
 * 商品種別画面のコントローラクラス。
 */
@RestController
@RequestMapping("/itemTypes")
public class ItemTypeRestController extends BaseRestController<ItemTypeRepository, ItemType, Long> {

    @Autowired
    public ItemTypeRestController(ItemTypeRepository respository) {
        super(respository);
    }

}
