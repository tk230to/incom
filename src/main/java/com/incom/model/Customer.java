package com.incom.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.firebase.auth.FirebaseToken;
import com.incom.security.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 顧客クラス。
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Customer {

    /** ID */
    @Id
    private String id;

    /** メールアドレス */
    @NotNull
    @NotBlank
    private String email;

    /** 名前 */
    @NotNull
    @NotBlank
    private String name;

    /** 権限 */
    @NotNull
    @Enumerated
    private Role role = Role.ROLE_USER;

    /** カート商品リスト */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @OrderBy("id asc")
    private List<CartItem> cartItems = new ArrayList<CartItem>();

    /** カート商品リスト */
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        if (cartItems != null) {
            for (CartItem cartItem : cartItems) {
                cartItem.setCustomer(this);
            }
        }
    }

    /** カート商品リスト */
    public void addCartItems(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setCustomer(this);
    }

    /** 納品先住所商品リスト */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<Address>();

    /** 納品先住所商品リスト */
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
        if (addresses != null) {
            for (Address address : addresses) {
                address.setCustomer(this);
            }
        }
    }

    /** 納品先住所商品リスト */
    public void addAddresses(Address address) {
        this.addresses.add(address);
        address.setCustomer(this);
    }

    /** 注文リスト */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<SalesOrder> salesOrders = new ArrayList<SalesOrder>();

    /** 注文リスト */
    public void setSalesOrders(List<SalesOrder> salesOrders) {
        this.salesOrders = salesOrders;
        if (salesOrders != null) {
            for (SalesOrder salesOrder : salesOrders) {
                salesOrder.setCustomer(this);
            }
        }
    }

    /** 注文リスト */
    public void addSalesOrders(SalesOrder salesOrder) {
        this.salesOrders.add(salesOrder);
        salesOrder.setCustomer(this);
    }

    /** 特別価格リスト */
    @Valid
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<SpecialPrice> specialPrices = new ArrayList<SpecialPrice>();

    /** 特別価格リスト */
    public void setSpecialPrices(List<SpecialPrice> specialPrices) {
        this.specialPrices = specialPrices;
        if (specialPrices != null) {
            for (SpecialPrice specialPrice : specialPrices) {
                specialPrice.setCustomer(this);
            }
        }
    }

    /** 特別価格リスト */
    public void addSpecialPrices(SpecialPrice specialPrice) {
        this.specialPrices.add(specialPrice);
        specialPrice.setCustomer(this);
    }

    /**
     * コンストラクタ
     * @param firebaseToken firebaseトークン
     */
    public Customer(FirebaseToken firebaseToken) {

        setId(firebaseToken.getUid());

        Optional<String> email = Optional.ofNullable(firebaseToken.getEmail());
        setEmail(email.orElse("未設定"));

        Optional<String> name = Optional.ofNullable(firebaseToken.getName());
        setName(name.orElse("ゲスト"));
    }
}
