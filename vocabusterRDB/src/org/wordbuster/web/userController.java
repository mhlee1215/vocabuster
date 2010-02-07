package org.wordbuster.web;

import java.util.Calendar;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wordbuster.PMF;
import org.wordbuster.domain.VBUser;
import org.wordbuster.service.VBUserService;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Controller
public class userController {

	@RequestMapping("/addUser.do")
	public ModelAndView addUser(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView result = new ModelAndView("index");
		
		
		VBUserService.addUser(req);
		        
		return result;
	}
}
