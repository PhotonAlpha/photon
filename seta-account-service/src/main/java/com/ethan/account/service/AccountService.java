package com.ethan.account.service;

import com.ethan.account.dao.AccountDao;
import com.ethan.account.entity.Account;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * @className: StorageService
 * @author: Ethan
 * @date: 22/4/2021
 **/
@Service
public class AccountService {
    @Resource
    private AccountDao accountDao;

    public boolean decrease(Long userId, BigDecimal price) {
        Optional<Account> accountOptional = accountDao.findByUserId(userId);
        boolean success = false;
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            BigDecimal used = account.getUsed();
            BigDecimal residue = account.getResidue();

            BigDecimal addUsedVal = used.add(price);
            BigDecimal subtractVal = residue.subtract(price);

            account.setUsed(addUsedVal);
            account.setResidue(subtractVal);
            accountDao.save(account);
            success = true;
        }
        return success;
    }
}
