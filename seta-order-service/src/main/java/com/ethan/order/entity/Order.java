package com.ethan.order.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * @className: Order
 * @author: Ethan
 * @date: 20/4/2021
 **/
@Data
@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "count")
    private Integer count;
    @Column(name = "money")
    private BigDecimal money;
    /**
     * 订单状态 0 创建中  1已完成
     */
    @Column(name = "status")
    private Integer status;

    @Transient
    private Boolean timeout;
}
