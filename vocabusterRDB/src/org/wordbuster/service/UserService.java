package org.wordbuster.service;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.mail.EmailException;

import org.wordbuster.domain.User;
import org.wordbuster.domain.UserIdMap;

public interface UserService {
	public int readUser(User user) throws Exception;
	public User readUserData(User user) throws Exception;
	public int createUser(User user) throws Exception;
	//public void deleteUser(User user);
	public void updateUser(User user);
	
	public List<User> findAll();
	public int activateUser(String id);
	public int deleteUser(String id);
	public int deleteUser(String id, boolean isDeleteRow);
	public int findPassword(String id) throws EmailException, MalformedURLException;
	
	public UserIdMap getUserIdMap(String externalId);
	public void createUserIdMap(UserIdMap userIdMap);
	public int getNextUserIdMap();
	
}
