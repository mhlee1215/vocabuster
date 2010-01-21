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

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Controller
public class userController {

	@RequestMapping("/addUser.do")
	public ModelAndView addUser(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView result = new ModelAndView("/index");
		
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        Key userKey = KeyFactory.createKey(VBUser.class.getSimpleName(), user.getEmail());
        Calendar c = Calendar.getInstance();
        Date firstUseDate = c.getTime();
        
        VBUser vBUser = new VBUser();
        vBUser.setKey(userKey);
        vBUser.setUser(user);
        vBUser.setFirstUseDate(firstUseDate);
        
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(vBUser);
        } finally {
            pm.close();
        }
        
		return result;
	}
}
