package org.wordbuster.web;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wordbuster.domain.VBUser;
import org.wordbuster.service.VBUserService;


@Controller
public class userController {

	@RequestMapping("/addUser.do")
	public ModelAndView addUser(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView result = new ModelAndView("index");
		
		
		VBUserService.addUser(req);
		        
		return result;
	}
}
