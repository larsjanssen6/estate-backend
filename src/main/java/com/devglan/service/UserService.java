package com.devglan.service;

import com.devglan.model.User;
import com.devglan.model.UserDto;

import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    List<User> findAllMembers();
    List<User> findAllPotentialMembers();
    boolean delete(long id);
    User findOne(String username);
    User findById(Long id);
    User update(UserDto user);

    void removeAdmin(String name);
}
