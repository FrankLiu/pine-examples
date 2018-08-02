package io.pine.examples.hello;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.pine.examples.hello.controller.HelloController;
import io.pine.examples.hello.controller.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StarterApplicationTests {
	
	private MockMvc mvc;
	
	@Before
	public void setUp() throws Exception{
		HelloController helloController = new HelloController();
		UserController userController = new UserController();
		mvc = MockMvcBuilders.standaloneSetup(helloController, userController).build();
	}
	
	@Test
	public void testHelloController() throws Exception{
		mvc.perform(get("/io/pine/examples/hello").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("Hello")));
	}

	
}
