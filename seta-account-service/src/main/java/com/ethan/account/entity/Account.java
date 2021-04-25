package com.ethan.account.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @className: Account
 * @author: Ethan
 * @date: 20/4/2021
 **/
@Data
@Entity
@Table(name = "t_account")
public class Account {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "used")
    private BigDecimal used;
    @Column(name = "residue")
    private BigDecimal residue;
}
