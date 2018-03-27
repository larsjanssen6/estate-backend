package com.devglan.model;

import java.sql.Date;

public class UserDto {

	 private long id;
	    private String username;
	    private String password;
	    private String first_name;
	    private String surname;
	    private String address;
	    private String zipcode;
	    private String city;
	    private String profesion;
	    private Role role;
	    private int status_id;
	    private Date date_joined;
	    private Date birthdate;

	    public long getId() {
	        return id;
	    }

	    public void setId(long id) {
	        this.id = id;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getFirst_name() {
	        return first_name;
	    }

	    public void setFirst_name(String first_name) {
	        this.first_name = first_name;
	    }

	    public String getSurname() {
	        return surname;
	    }

	    public void setSurname(String surname) {
	        this.surname = surname;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public String getZipcode() {
	        return zipcode;
	    }

	    public void setZipcode(String zipcode) {
	        this.zipcode = zipcode;
	    }

	    public String getCity() {
	        return city;
	    }

	    public void setCity(String city) {
	        this.city = city;
	    }

	    public String getProfesion() {
	        return profesion;
	    }

	    public void setProfesion(String profesion) {
	        this.profesion = profesion;
	    }

	    public Role getRole() {
	        return role;
	    }

	    public void setRole(Role role_id) {
	        this.role = role_id;
	    }

	    public int getStatus_id() {
	        return status_id;
	    }

	    public void setStatus_id(int status_id) {
	        this.status_id = status_id;
	    }

	    public Date getDate_joined() {
	        return date_joined;
	    }

	    public void setDate_joined(Date date_joined) {
	        this.date_joined = date_joined;
	    }

	    public Date getBirthdate() {
	        return birthdate;
	    }

	    public void setBirthdate(Date birthdate) {
	        this.birthdate = birthdate;
	    }
}
