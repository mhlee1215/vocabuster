package org.wordbuster.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
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
	 * @throws ServletRequestBindingException 
	 */
	public VBUser getVBUser(HttpServletRequest req) throws ServletRequestBindingException{
		return (VBUser) req.getSession().getAttribute("vbuser");
	}
	
	public VBUser getVBUser(String email, String password) throws ServletRequestBindingException{
		return userDAO.retrieveUserByPassword(email, password);
	}
	
	/**
	 * Save vbuser to session
	 * @param user
	 * @param req
	 */
	public void setVBUser(VBUser user, HttpServletRequest req){
		req.getSession().setAttribute("vbusesr", user);
	}
	
	/**
	 * VB 사용자 추가
	 * @param req
	 * @return
	 * @throws ServletRequestBindingException 
	 */
	public VBUser addUser(HttpServletRequest req) throws ServletRequestBindingException{
		
        String email = ServletRequestUtils.getRequiredStringParameter(req, "email");
        String password = ServletRequestUtils.getRequiredStringParameter(req, "password");
        
        VBUser vBUser = new VBUser();
        vBUser.setEmail(email);
        vBUser.setPassword(password);
        
        userDAO.insertUser(vBUser);
        return vBUser;
	}
	
}
