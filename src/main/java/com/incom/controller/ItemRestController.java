package com.incom.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incom.model.Item;
import com.incom.model.ItemRepository;

/**
 * 商品画面のコントローラクラス。
 */
@RestController
@RequestMapping("/items")
public class ItemRestController extends BaseRestController<ItemRepository, Item, Long> {

    @Autowired
    public ItemRestController(ItemRepository respository) {
        super(respository);
    }

    /**
     * 全件表示。
     *
     * @return 検索結果
     */
    @GetMapping("/cart")
    public ResponseEntity<List<Item>> getForCart() {

        List<Item> items = getRepository().findAll();

        return ResponseEntity.ok(items);
    }

    /**
     * 登録。
     *
     * @param entity リクエスト
     * @return レスポンス
     * @throws IOException
     */
    // @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<Item> create(@Valid @RequestBody Item entity) {
        entity = getRepository().save(entity);
        return ResponseEntity.ok(entity);
    }

    /**
     * 更新。
     *
     * @param entity リクエスト
     * @return レスポンス
     */
    // @Secured("ROLE_ADMIN")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Item> update(@PathVariable("id") Long id, @Validated @RequestBody Item entity) {
        entity = getRepository().save(entity);
        return ResponseEntity.ok(entity);
    }

    /**
     * 削除。
     *
     * @param id
     * @return 204:No Content
     */
    // @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> list(@PathVariable("id") Long id) {
        getRepository().deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
