package com.incom.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品種別クラス。
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemType {

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 名前 */
    @NotNull
    private String name;

    /** 金額 */
    @NotNull
    @Min(1)
    private Long price;

    /** 特別価格リスト */
    @OneToMany(mappedBy = "itemType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SpecialPrice> specialPrices = new ArrayList<SpecialPrice>();

    /** 特別価格 */
    public Long getSpecialPrice() {

        for (SpecialPrice specialPrice : specialPrices) {
            if (specialPrice.getCustomer().getId()
                    .equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
                return specialPrice.getPrice();
            }
        }
        return price;
    }

}
