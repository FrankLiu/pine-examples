package io.pine.examples.hello.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTests {
	
	@Autowired
	private UserService userSerivce;
	
	@Before
	public void setUp() {
		// 准备，清空user表
		userSerivce.deleteAllUsers();
	}
	
	@Test
	public void test() throws Exception {
		// 插入5个用户
		userSerivce.create("a", 1);
		userSerivce.create("b", 2);
		userSerivce.create("c", 3);
		userSerivce.create("d", 4);
		userSerivce.create("e", 5);
		// 查数据库，应该有5个用户
		Assert.assertEquals(5, userSerivce.countAllUsers().intValue());
		// 删除两个用户
		userSerivce.deleteByName("a");
		userSerivce.deleteByName("e");
		// 查数据库，应该有5个用户
		Assert.assertEquals(3, userSerivce.countAllUsers().intValue());
	}
}
