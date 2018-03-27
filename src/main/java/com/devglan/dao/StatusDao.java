package com.devglan.dao;

import com.devglan.model.Status;
import org.springframework.data.repository.CrudRepository;

public interface StatusDao extends CrudRepository<Status, Long> {
    Status findById(long id);
}
