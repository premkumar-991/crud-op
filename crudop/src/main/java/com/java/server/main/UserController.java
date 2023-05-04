package com.java.server.main;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RefreshScope
@RequestMapping("/api")
public class UserController extends User {
	@Autowired
	private UserService userService;
	@Value("${changestate}")
    private String changestate;
	

	@GetMapping("/users")
	public List<User> getUser() {
		return userService.getUser();
	}

	@PostMapping("/save")
	public User saveUser(@RequestBody User user) {
		user.setState(changestate);
		userService.save(user);
		return user;
	}
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable Integer id) throws UserNotFoundException {
		User user = userService.findById(id);
		if (user == null) {
            throw new UserNotFoundException("User not found with id " + id);
        }
		return user;
	}

	@DeleteMapping("/delete/{id}")
	public User deleteUser(@PathVariable Integer id) throws UserNotFoundException {
		User user = userService.findById(id);
		 if (user == null) {
	            throw new UserNotFoundException("User not found with id " + id);
	        }
		userService.delete(user);
		return user;
	}

	@PutMapping("/update/{id}")
	public User updateUser(@PathVariable Integer id, @RequestBody User userDetail) throws UserNotFoundException {
           User user = userService.findById(id);
           if (user == null) {
               throw new UserNotFoundException("User not found with id " + id);
           }
			user.setUserName(userDetail.getUserName());
			user.setMobileNo(userDetail.getMobileNo());
			user.setEmailId(userDetail.getEmailId());
			user.setCity(userDetail.getCity());
			user.setPassword(userDetail.getPassword());
			user.setState(changestate);
			userService.save(user);
		    return user;
	}
	 @ExceptionHandler(UserNotFoundException.class)
	    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }
}