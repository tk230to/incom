package com.incom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incom.model.Address;
import com.incom.model.AddressRepository;

/**
 * 納品先住所コントローラクラス。
 */
@RestController
@RequestMapping("/addresses")
public class AddressRestController extends BaseRestController<AddressRepository, Address, Long> {

    @Autowired
    public AddressRestController(AddressRepository repository) {
        super(repository);
    }
}
