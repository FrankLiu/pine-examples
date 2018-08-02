package io.pine.examples.hello.controller;

import io.pine.examples.hello.domain.User;
import io.pine.examples.hello.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ApiOperation(value = "获取用户列表", notes = "", produces = "application/json")
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public List<User> getUsers(){
		return userService.getAllUsers();
	}
	
	@ApiOperation(value="创建用户", notes="根据User对象创建用户", produces = "application/json")
//	@ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "User")
	@RequestMapping(value="/", method=RequestMethod.POST) 
    public String postUser(@ModelAttribute User user) {
		userService.create(user.getName(), user.getAge()); 
        return "success"; 
    }
	
	@ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息", produces = "application/json")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public User getUser(@PathVariable Long id){
		return userService.getUser(id);
	}
	
	@ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息", produces = "application/json")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
		@ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "User")
	})
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, produces = "application/json") 
    public String putUser(@PathVariable Long id, @ModelAttribute User user) {
		user.setId(id);
        userService.updateUser(user);
        return "success"; 
    } 

	@ApiOperation(value="删除用户", notes="根据url的id来指定删除对象", produces = "application/json")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE) 
    public String deleteUser(@PathVariable Long id) { 
        userService.deleteUser(id);
        return "success"; 
    } 
}
