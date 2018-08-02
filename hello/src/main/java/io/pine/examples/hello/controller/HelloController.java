package io.pine.examples.hello.controller;

import io.pine.examples.hello.ApiException;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@ApiOperation(value="测试接口", notes="返回Hello")
	@RequestMapping(value = "/io/pine/examples/hello", method=RequestMethod.GET)
	public String index(){
		return "Hello";
	}
	
	@ApiOperation(value="测试错误返回接口", notes="返回实体ErrorInfo")
	@RequestMapping(value = "/_json_err", method=RequestMethod.GET)
    public String json() throws ApiException {
        throw new ApiException("发生错误2");
    }
	
}
