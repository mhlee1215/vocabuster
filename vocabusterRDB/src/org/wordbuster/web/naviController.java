package org.wordbuster.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.wordbuster.domain.VBNavigationVO;
import org.wordbuster.domain.VBWordSearchVO;

@Controller
public class naviController extends MultiActionController{

	@RequestMapping("/index.do")
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBNavigationVO vBNavigationVO = new VBNavigationVO();
		bind(req,vBNavigationVO);
		vBNavigationVO.setPageName("home");
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
