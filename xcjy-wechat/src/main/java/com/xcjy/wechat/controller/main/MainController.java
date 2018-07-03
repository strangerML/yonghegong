package com.xcjy.wechat.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 
 * @author BQ
 *
 */
@Controller
public class MainController {
	
	@RequestMapping("/main")
	public String goMainPage() {
		return "main/main";
	}
	
	
}
