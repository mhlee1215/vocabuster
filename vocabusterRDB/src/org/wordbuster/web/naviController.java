package org.wordbuster.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.wordbuster.domain.User;
import org.wordbuster.domain.UserIdMap;
import org.wordbuster.domain.VBNavigationVO;
import org.wordbuster.domain.VBWordSearchVO;
import org.wordbuster.service.UserService;

@Controller
public class naviController extends MultiActionController{

	@Autowired
	private final UserService userService = null;
	
	@RequestMapping("/index.do")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse resp) throws Exception{
		VBNavigationVO vBNavigationVO = new VBNavigationVO();
		bind(request,vBNavigationVO);
		vBNavigationVO.setPageName("home");
		
		String userid = (String)request.getSession().getAttribute("userid");
	    
	    if(userid == null){
    		String remoteHost = request.getRemoteHost();
    		UserIdMap userIdMap = new UserIdMap();
    		UserIdMap result = userService.getUserIdMap(remoteHost);
    		
    		if(result == null){
    			int nextId = userService.getNextUserIdMap();
    			userIdMap.setExternalId(remoteHost);
    			userIdMap.setInternalId(nextId);
    			userService.createUserIdMap(userIdMap);
    			User user = new User();
    			user.setId(Integer.toString(userIdMap.getInternalId()));
    			user.setInternalid((userIdMap.getInternalId()));
    			user.setPassword("N/A");
    			userService.createUser(user);
    			
    			result = userService.getUserIdMap(remoteHost);
    		}
    		
    		request.getSession().setAttribute("userid", Integer.toString(result.getInternalId()));
    		request.getSession().setAttribute("externalid", remoteHost);
    		
    		
	    }
		
		ModelAndView result = new ModelAndView("task/homeInfo");
		result.addObject("vBWordSearchVO", vBNavigationVO);
		return result;
	}
	
	@RequestMapping("/addWords.do")
	public ModelAndView addWords(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBNavigationVO vBNavigationVO = new VBNavigationVO();
		bind(req,vBNavigationVO);
		vBNavigationVO.setPageName("addwords");
		ModelAndView result = new ModelAndView("task/addWords");
		result.addObject("vBWordSearchVO", vBNavigationVO);
		return result;
	}
	
	@RequestMapping("/startQuiz.do")
	public ModelAndView startQuiz(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBNavigationVO vBNavigationVO = new VBNavigationVO();
		bind(req,vBNavigationVO);
		vBNavigationVO.setPageName("quiz");
		ModelAndView result = new ModelAndView("task/startQuz");
		result.addObject("vBWordSearchVO", vBNavigationVO);
		return result;
	}
	
	@RequestMapping("/wordListForm.do")
	public ModelAndView wordListForm(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBWordSearchVO vBWordSearchVO = new VBWordSearchVO();
		bind(req, vBWordSearchVO);
		vBWordSearchVO.setPageName("wordlist");
		ModelAndView result = new ModelAndView("task/showWords");
		result.addObject("vBWordSearchVO", vBWordSearchVO);
		return result;
	}
	
	@RequestMapping("/myWordListForm.do")
	public ModelAndView myWordListForm(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBWordSearchVO vBWordSearchVO = new VBWordSearchVO();
		bind(req, vBWordSearchVO);
		vBWordSearchVO.setPageName("mywords");
		ModelAndView result = new ModelAndView("task/showMyWords");
		result.addObject("vBWordSearchVO", vBWordSearchVO);
		return result;
	}
	
	@RequestMapping("/adminForm.do")
	public ModelAndView adminForm(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBWordSearchVO vBWordSearchVO = new VBWordSearchVO();
		bind(req, vBWordSearchVO);
		vBWordSearchVO.setPageName("admin");
		ModelAndView result = new ModelAndView("task/adminPage");
		result.addObject("vBWordSearchVO", vBWordSearchVO);
		return result;
	}
	
	
}
