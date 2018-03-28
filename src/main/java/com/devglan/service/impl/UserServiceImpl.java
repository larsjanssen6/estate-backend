package com.devglan.service.impl;

import com.devglan.dao.UserDao;
import com.devglan.model.Role;
import com.devglan.model.User;
import com.devglan.model.UserDto;
import com.devglan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
	}

	private List<SimpleGrantedAuthority> getAuthority(User user) {
		return Arrays.asList(new SimpleGrantedAuthority(user.getRole().toString()));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public boolean delete(long id) {
		try {
			userDao.delete(id);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	@Override
	public User findOne(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User findById(Long id) {
		return userDao.findOne(id);
	}

	@Override
    public User save(UserDto user) {
	    User newUser = new User();
	    newUser.setUsername(user.getUsername());
	    newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
	    newUser.setAddress(user.getAddress());
	    newUser.setBirthdate(user.getBirthdate());
	    newUser.setCity(user.getCity());
	    newUser.setDate_joined(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
	    newUser.setId(user.getId());
	    newUser.setFirst_name(user.getFirst_name());
	    newUser.setSurname(user.getSurname());
	    newUser.setRole(user.getRole());
	    newUser.setStatus_id(user.getStatus_id());
	    newUser.setZipcode(user.getZipcode());
	    newUser.setProfession(user.getProfession());
        return userDao.save(newUser);
    }

	@Override
	public User update(UserDto user) {
		User currUser = this.findById(user.getId());

		currUser.setUsername(user.getUsername());
		currUser.setAddress(user.getAddress());
		currUser.setBirthdate(user.getBirthdate());
		currUser.setCity(user.getCity());
		currUser.setId(user.getId());
		currUser.setFirst_name(user.getFirst_name());
		currUser.setSurname(user.getSurname());
		currUser.setRole(user.getRole_id());
		currUser.setStatus_id(user.getStatus_id());
		currUser.setZipcode(user.getZipcode());
		currUser.setProfession(user.getProfession());

		return userDao.save(currUser);
	}

	@Override
	public void removeAdmin(String name) {
		User user = userDao.findByUsername(name);
		user.setRole(Role.Member);
		userDao.save(user);
	}
}
