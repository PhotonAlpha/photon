package com.ethan.storage.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @className: Storage
 * @author: Ethan
 * @date: 20/4/2021
 **/
@Data
@Entity
@Table(name = "t_storage")
public class Storage {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;
    @Column(name = "total")
    private Integer total;
    @Column(name = "used")
    private Integer used;
    @Column(name = "residue")
    private Integer residue;
}
