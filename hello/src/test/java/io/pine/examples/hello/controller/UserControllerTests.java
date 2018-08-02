package io.pine.examples.hello.controller;

import io.pine.examples.hello.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {
	private static final long TEST_USER_ID = 1;
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserController userController;
	
	@Before
    public void setup() {
		User frank = new User("frank", 30);
		frank.setId(1L);
		User liujun = new User("liujun", 40);
		liujun.setId(2L);
        given(this.userController.getUser(TEST_USER_ID)).willReturn(new User());
//        given(this.userController.getUsers()).willReturn(Lists.newArrayList(frank, liujun));
    }
	
	@Test
	public void test() throws Exception {
		// 测试UserController
		RequestBuilder request = null;

		// 1、get查一下user列表，应该为空
		request = get("/users/");
		mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));

		// 2、post提交一个user
		request = post("/users/").param("id", "1").param("name", "测试大师").param("age", "20");
		mvc.perform(request).andExpect(content().string(equalTo("success")));

		// 3、get获取user列表，应该有刚才插入的数据
		request = get("/users/");
		mvc.perform(request).andExpect(status().isOk())
				.andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"测试大师\",\"age\":20}]")));

		// 4、put修改id为1的user
		request = put("/users/1").param("name", "测试终极大师").param("age", "30");
		mvc.perform(request).andExpect(content().string(equalTo("success")));

		// 5、get一个id为1的user
		request = get("/users/1");
		mvc.perform(request).andExpect(content().string(equalTo("{\"id\":1,\"name\":\"测试终极大师\",\"age\":30}")));

		// 6、del删除id为1的user
		request = delete("/users/1");
		mvc.perform(request).andExpect(content().string(equalTo("success")));

		// 7、get查一下user列表，应该为空
		request = get("/users/");
		mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));

	}
}
