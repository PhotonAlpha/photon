package com.ethan.storage.dao;

import com.ethan.storage.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * @className: StorageDao
 * @author: Ethan
 * @date: 20/4/2021
 **/
public interface StorageDao extends JpaRepository<Storage, Long> {
    Optional<Storage> findByProductId(Long productId);
}
