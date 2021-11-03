package com.incom.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基底コントローラクラス。
 */
@RestController
public abstract class BaseRestController<R extends JpaRepository<T, ID>, T, ID> {

    private R repository;

    /**
     * コンストラクタ.
     *
     * @param repository リポジトリ
     */
    public BaseRestController(R repository) {
        this.repository = repository;
    }

    public R getRepository() {
        return this.repository;
    }

    /**
     * 全件表示。
     *
     * @return 検索結果
     */
    @GetMapping
    public ResponseEntity<List<T>> get() {
        return ResponseEntity.ok(getRepository().findAll());
    }

    /**
     * 1件表示。
     *
     * @param id
     * @return 検索結果
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<T> getOne(@PathVariable("id") ID id) {

        T entity = getRepository().findById(id).orElse(null);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity);
    }

    /**
     * 登録。
     *
     * @param entity リクエスト
     * @return レスポンス
     * @throws IOException
     */
    @PostMapping
    public ResponseEntity<T> create(@Valid @RequestBody T entity) {
        entity = getRepository().save(entity);
        return ResponseEntity.ok(entity);
    }

    /**
     * 更新。
     *
     * @param entity リクエスト
     * @return レスポンス
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<T> update(@PathVariable("id") ID id, @Validated @RequestBody T entity) {
        entity = getRepository().save(entity);
        return ResponseEntity.ok(entity);
    }

    /**
     * 削除。
     *
     * @param id
     * @return 204:No Content
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> list(@PathVariable("id") ID id) {
        getRepository().deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
