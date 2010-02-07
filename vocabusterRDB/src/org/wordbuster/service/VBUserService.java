package org.wordbuster.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wordbuster.dao.VBUserDAO;
import org.wordbuster.domain.VBUser;

@Service
public class VBUserService {
	
	@Autowired
	private VBUserDAO userDAO;
	
	static Logger logger = Logger.getLogger(VBUserService.class);
	/**
	 * Get vbuser from session
	 * @param req
	 * @return
	 */
	public static VBUser getVBUser(HttpServletRequest req){
		VBUser vbuser = (VBUser) req.getSession().getAttribute("vbuser");
		
		if(vbuser == null){
		    
			vbuser =  null;//pm.getObjectById(VBUser.class, userKey); 
			
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
        Calendar c = Calendar.getInstance();
        Date firstUseDate = c.getTime();
        
        String userid = "";
        
        VBUser vBUser = new VBUser();
        vBUser.setUserid(userid);
        vBUser.setFirstUseDate(firstUseDate);
        
        //pm.makePersistent(vBUser);
        return null;
	}
	
}
