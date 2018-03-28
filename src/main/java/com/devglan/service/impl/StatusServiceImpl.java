package com.devglan.service.impl;

import com.devglan.dao.StatusDao;
import com.devglan.model.Status;
import com.devglan.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service(value = "statusService")
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusDao statusDao;
    @Override
    public Status save(Status status) {
        return statusDao.save(status);
    }

    @Override
    public List<Status> findAll() {
        List<Status> list = new ArrayList<>();
        statusDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public boolean delete(long id) {
        try {
            statusDao.delete(id);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public Status findById(Long id) {
        return statusDao.findById(id);
    }

    @Override
    public Status update(Status status) {
        return statusDao.save(status);
    }
}
