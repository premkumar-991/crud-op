package com.java.server.main;

import java.util.List;


public interface UserService {
	public List<User> getUser();
	public void save(User user);
	public User findById(Integer id);
	public void delete(User user);
	
}
