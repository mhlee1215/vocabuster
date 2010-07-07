package org.wordbuster.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.wordbuster.domain.User;
import org.wordbuster.domain.UserIdMap;
import org.wordbuster.domain.VBCategory;
import org.wordbuster.domain.VBCategorySearchVO;
import org.wordbuster.domain.VBMyWordSearchVO;
import org.wordbuster.domain.VBNavigationVO;
import org.wordbuster.domain.VBWordSearchVO;
import org.wordbuster.service.UserService;
import org.wordbuster.service.VBWordService;

@Controller
public class naviController extends MultiActionController{

	@Autowired
	private final UserService userService = null;
	
	@Autowired
	private final VBWordService wordService = null;
	
	
	@RequestMapping("/index.do")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse resp) throws Exception{
		String submittedUserId = ServletRequestUtils.getStringParameter(request, "submittedUserId", "");
		String loginComplete = ServletRequestUtils.getStringParameter(request, "loginComplete", "false");
		String loginFail = ServletRequestUtils.getStringParameter(request, "loginFail", "false");
		String logoutComplete = ServletRequestUtils.getStringParameter(request, "logoutComplete", "false");
		String registerComplete = ServletRequestUtils.getStringParameter(request, "registerComplete", "false");
		String registerFail = ServletRequestUtils.getStringParameter(request, "registerFail", "false");
		
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
	    
	    wordService.updateWordStatus(request);
		
		ModelAndView result = new ModelAndView("task/homeInfo");
		result.addObject("vBWordSearchVO", vBNavigationVO);
		result.addObject("loginComplete", loginComplete);
		result.addObject("loginFail", loginFail);
		result.addObject("logoutComplete", logoutComplete);
		result.addObject("registerComplete", registerComplete);
		result.addObject("registerFail", registerFail);
		result.addObject("submittedUserId", submittedUserId);
		return result;
	}
	
	@RequestMapping("/addWords.do")
	public ModelAndView addWords(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBNavigationVO vBNavigationVO = new VBNavigationVO();
		bind(req,vBNavigationVO);
		vBNavigationVO.setPageName("addwords");
		
		VBCategorySearchVO vo = new VBCategorySearchVO();
		List<VBCategory> categories = wordService.retrieveCategory(vo);
		ModelAndView result = new ModelAndView("task/addWords");
		result.addObject("vBWordSearchVO", vBNavigationVO);
		result.addObject("categories", categories);
		return result;
	}
	
	@RequestMapping("/startQuiz.do")
	public ModelAndView startQuiz(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBNavigationVO vBNavigationVO = new VBNavigationVO();
		bind(req,vBNavigationVO);
		vBNavigationVO.setPageName("quiz");
		ModelAndView result = new ModelAndView("task/startQuz");
		
		VBCategorySearchVO vo = new VBCategorySearchVO();
		List<VBCategory> categories = wordService.retrieveCategory(vo);
		
		result.addObject("vBWordSearchVO", vBNavigationVO);
		result.addObject("categories", categories);
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
		VBMyWordSearchVO vBWordSearchVO = new VBMyWordSearchVO();
		bind(req, vBWordSearchVO);
		vBWordSearchVO.setPageName("mywords");
		
		VBCategorySearchVO vo = new VBCategorySearchVO();
		List<VBCategory> categories = wordService.retrieveCategory(vo);
		
		ModelAndView result = new ModelAndView("task/showMyWords");
		result.addObject("vBWordSearchVO", vBWordSearchVO);
		result.addObject("categories", categories);
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
