package com.incom.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品クラス。
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 名前 */
    @NotNull
    private String name;

    /** 画像 */
    @NotNull
    private byte[] image;

    /** 画像種別 */
    private String imageType;

    /** 商品種別 */
    @ManyToOne
    private ItemType itemType;

}
