package com.ethan.account.dao;

import com.ethan.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * @className: StorageDao
 * @author: Ethan
 * @date: 20/4/2021
 **/
public interface AccountDao extends JpaRepository<Account, Long> {
    Optional<Account> findByUserId(Long userId);
}
