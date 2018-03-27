package com.devglan.service;

import com.devglan.model.Status;

import java.util.List;

public interface StatusService {
    Status save(Status status);
    List<Status> findAll();
    boolean delete(long id);
    Status findById(Long id);
    Status update(Status status);
}
