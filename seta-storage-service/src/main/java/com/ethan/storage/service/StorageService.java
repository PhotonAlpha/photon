package com.ethan.storage.service;

import com.ethan.storage.dao.StorageDao;
import com.ethan.storage.entity.Storage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @className: StorageService
 * @author: Ethan
 * @date: 22/4/2021
 **/
@Service
public class StorageService {
    @Resource
    private StorageDao storageDao;

    public boolean decrease(Long productId, Integer count) {
        Optional<Storage> storageOptional = storageDao.findByProductId(productId);
        boolean success = false;
        if (storageOptional.isPresent()) {
            Storage storage = storageOptional.get();
            Integer residue = storage.getResidue();
            Integer used = storage.getUsed();
            residue = residue - count;
            used = used + count;

            storage.setResidue(residue);
            storage.setUsed(used);
            storageDao.save(storage);
            success = true;

        }
        return success;
    }
}
