package com.incom.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 納品先住所クラス。
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 宛名 */
    @NotNull
    private String name;

    /** 郵便番号 */
    @NotNull
    private String postalCode;

    /** 都道府県 */
    @NotNull
    private String level1;

    /** 市区町村 */
    @NotNull
    private String level2;

    /** 番地・マンション名（1行目） */
    @NotNull
    private String line1;

    /** 番地・マンション名（2行目） */
    private String line2;

    /** 電話番号 */
    private String tel;

    /** 顧客 */
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Customer customer;
}
