package org.wordbuster.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.wordbuster.PMF;
import org.wordbuster.domain.VBUser;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import javax.jdo.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class VBUserService {
	static Logger logger = Logger.getLogger(VBUserService.class);
	/**
	 * Get vbuser from session
	 * @param req
	 * @return
	 */
	public static VBUser getVBUser(HttpServletRequest req){
		VBUser vbuser = (VBUser) req.getSession().getAttribute("vbuser");
		
		if(vbuser == null){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			UserService userService = UserServiceFactory.getUserService();
		    User user = userService.getCurrentUser();
		    
			Key userKey = KeyFactory.createKey(VBUser.class.getSimpleName(), user.getEmail());
			vbuser =  pm.getObjectById(VBUser.class, userKey); 
			
			if(vbuser != null)
				setVBUser(vbuser, req);
		}
		if(vbuser == null){
			logger.debug("NEW USER is COMMING!");
		}
		return vbuser;
	}
	
	/**
	 * Save vbuser to session
	 * @param user
	 * @param req
	 */
	public static void setVBUser(VBUser user, HttpServletRequest req){
		req.getSession().setAttribute("vbusesr", user);
	}
	
	/**
	 * VB 사용자 추가
	 * @param req
	 * @return
	 */
	public static VBUser addUser(HttpServletRequest req){
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
        return null;
	}
	
}
