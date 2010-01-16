package org.wordbuster.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wordbuster.PMF;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordCategory;
import org.wordbuster.domain.VBWordInfo;
import org.wordbuster.util.MeaningGatherer;


@Controller
public class wordController  {
	
	@RequestMapping("/addWords.do")
	public ModelAndView addWords(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("utf-8");
		String wordStr = req.getParameter("content");
		Vector<String> wordList = new Vector<String>();
		
		HashMap<String, List<VBWordInfo>> meaningVector = new HashMap<String, List<VBWordInfo>>();
		
		if(wordStr == null) wordStr = "testify";
		
		if(wordStr != null){
			String[] words = wordStr.split("\n");
			//System.out.println("user : " + user);
			System.out.println("wordStr : " + wordStr);
			for(int i = 0 ; i < words.length ; i++)
				System.out.println("count["+i+"] : "+words[i]);
			//System.out.println("date : " + date);
			
			
			MeaningGatherer mg = new MeaningGatherer();
			for(int i = 0 ; i < words.length ; i++){
				List<VBWordInfo> wordInfoList = mg.getMeaning(words[i]);
				List<VBWordCategory> wordCategoryList = new Vector<VBWordCategory>();
				
				wordList.add(words[i]);
				meaningVector.put(words[i], wordInfoList);
				
				VBWord word = new VBWord();
				word.setWordName(words[i]);
				word.setWordInfoList(wordInfoList);
				
				PersistenceManager pm = PMF.get().getPersistenceManager();
				try {
					pm.makePersistent(word);
				} finally {
					pm.close();
				}
			}
		}
		
		ModelAndView result = new ModelAndView("ajaxResult/addWordsResult");
		result.addObject("wordList", wordList);
		result.addObject("wordMeaning", meaningVector);
		return result;
	}
	
	@RequestMapping("/wordList.do")
	public ModelAndView wordList(HttpServletRequest req, HttpServletResponse resp){
		String keyword = ServletRequestUtils.getStringParameter(req, "keyword", "");
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(VBWord.class);
		//query.setOrdering("insertedCount desc");
		
		if(!keyword.equals("")){
			query.setFilter("wordName == searchWordName");
			query.declareParameters("String searchWordName");
		}
	    
		List<VBWord> wordList = null;
		
		try {
			wordList = (List<VBWord>)query.execute(keyword);
			
			//pm.get
			//pm.makePersistent(word);
		} finally {
			//pm.close();
			query.closeAll();
		}
		
		ModelAndView result = new ModelAndView("ajaxResult/wordListResult");
		result.addObject("wordList", wordList);
		return result;
	}
	
	public static void main(String[] argv) throws IOException{
		MeaningGatherer mg = new MeaningGatherer();
		mg.getMeaning("testify");
	}
}
