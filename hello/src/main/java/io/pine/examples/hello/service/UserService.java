package io.pine.examples.hello.service;

import io.pine.examples.hello.domain.User;
import io.pine.examples.hello.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private UserRepository userRepository;
	
	@Transactional
    public void create(String name, Integer age) {
		userRepository.save(new User(name, age));
    }
	
    @Transactional
    public List<User> getAllUsers() {
    	return userRepository.findAll();
    }
    
    public Long countAllUsers(){
    	return userRepository.count();
    }
    
    public User getUser(Long id){
    	return userRepository.getOne(id);
    }
    
    public User getUserByName(String name){
    	return userRepository.findByName(name);
    }
    
    @Transactional
    public void updateUser(User user){
    	userRepository.save(user);
    }
    
    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
    
    @Transactional
    public void deleteUser(Long id) {
    	userRepository.deleteById(id);
    }
    
    @Transactional
    public void deleteByName(String name) {
    	User user = userRepository.findByName(name);
    	if(user == null){
    		return;
    	}
        userRepository.delete(user);
    }
    
}
