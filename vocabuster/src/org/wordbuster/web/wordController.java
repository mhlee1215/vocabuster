package org.wordbuster.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wordbuster.PMF;
import org.wordbuster.domain.wordInfo;
import org.wordbuster.util.MeaningGatherer;


@Controller
public class wordController  {
	
	@RequestMapping("/addWords.do")
	public ModelAndView addWords(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("utf-8");
		String wordStr = req.getParameter("content");
		Vector<String> wordList = new Vector<String>();
		HashMap<String, List<wordInfo>> meaningVector = new HashMap<String, List<wordInfo>>();
		
		if(wordStr == null) wordStr = "testify";
		
		if(wordStr != null){
			String[] words = wordStr.split("\r\n");
	
			//System.out.println("user : " + user);
			System.out.println("wordStr : " + wordStr);
			System.out.println("words : " + words);
			//System.out.println("date : " + date);
			
			
			MeaningGatherer mg = new MeaningGatherer();
			for(int i = 0 ; i < words.length ; i++){
				wordList.add(words[i]);
				meaningVector.put(words[i], mg.getMeaning(words[i]));
			}
	
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				//pm.makePersistent(greeting);
			} finally {
				pm.close();
			}
		}
		
		ModelAndView result = new ModelAndView("ajaxResult/addWordsResult");
		result.addObject("resultStr", "hihihi");
		result.addObject("wordList", wordList);
		result.addObject("wordMeaning", meaningVector);
		return result;
	}
	
	public static void main(String[] argv) throws IOException{
		MeaningGatherer mg = new MeaningGatherer();
		mg.getMeaning("testify");
	}
}
