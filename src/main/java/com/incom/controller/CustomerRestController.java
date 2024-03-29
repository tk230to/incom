package com.incom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incom.model.Customer;
import com.incom.model.CustomerRepository;

/**
 * 顧客画面のコントローラクラス。
 */
@RestController
@RequestMapping("/customers")
public class CustomerRestController extends BaseRestController<CustomerRepository, Customer, String> {

    @Autowired
    public CustomerRestController(CustomerRepository repository) {
        super(repository);
    }

    /**
     * チェック。
     *
     * @param customer 顧客画面入力値
     * @return 顧客情報
     */
    @PostMapping("/validate")
    public ResponseEntity<Customer> validate(@Validated @RequestBody Customer customer) {
        return ResponseEntity.ok(customer);
    }

    /**
     * 検索。
     *
     * @param name
     * @param tel
     * @return
     */
    @GetMapping("/search")
    public List<Customer> search(@RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "tel", required = false, defaultValue = "") String tel) {

        if (name.equals("") && tel.equals("")) {
            return getRepository().findAll();
        }
        if (!name.equals("")) {
            return getRepository().findByNameContaining(name);
        }
        return getRepository().findByTelEquals(tel);
    }
}
