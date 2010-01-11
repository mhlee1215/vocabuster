package org.wordbuster.web;

import java.io.IOException;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wordbuster.PMF;


@Controller
public class wordController  {
	
	@RequestMapping("/addWords.do")
	public ModelAndView addWords(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String wordStr = req.getParameter("content");
		String[] words = wordStr.split("\n");

		//System.out.println("user : " + user);
		System.out.println("wordStr : " + wordStr);
		System.out.println("words : " + words);
		//System.out.println("date : " + date);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			//pm.makePersistent(greeting);
		} finally {
			pm.close();
		}

		
		ModelAndView result = new ModelAndView("ajaxResult/addWordsResult");
		result.addObject("resutStr", "hihihi");
		return result;
	}
}
