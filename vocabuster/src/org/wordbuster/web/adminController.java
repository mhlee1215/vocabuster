package org.wordbuster.web;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.wordbuster.PMF;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordInfo;
import org.wordbuster.domain.VBWordMap;
import org.wordbuster.domain.VBWordSearchVO;
import org.wordbuster.util.MeaningGatherer;


@Controller
public class adminController extends MultiActionController {
	
	@RequestMapping("/adminRecrowling.do")
	public ModelAndView adminRecrowling(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBWordSearchVO vBWordSearchVO = new VBWordSearchVO();
		bind(req, vBWordSearchVO);
		System.out.println("VO: "+vBWordSearchVO);
		//String keyword = ServletRequestUtils.getStringParameter(req, "keyword", "");
		List<VBWord> wordList = null;
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(VBWord.class);
		query.setOrdering("insertedCount desc");
		if(!vBWordSearchVO.getSearchKeyword().equals("")){
			query.setFilter("wordName == searchWordName");
			query.declareParameters("String searchWordName");
		}
		try {
			wordList = (List<VBWord>)query.execute(vBWordSearchVO.getSearchKeyword());
		} finally {
			query.closeAll();
		}
		
		
		MeaningGatherer mg = new MeaningGatherer();
		try{
			Query wordInfoQuery = pm.newQuery(VBWordInfo.class);
			wordInfoQuery.deletePersistentAll();
			for(int i = 0 ; i < wordList.size() ; i++){
				String wordStr = wordList.get(i).getWordName();
				System.out.println("rcrowling: "+wordStr);
				
				//Set isNew flag to false
				VBWord word = mg.analysisWord(wordStr, false);
				//PersistenceManager deletePm = JDOHelper.getPersistenceManager(vbuser);
				if(word == null){
					pm.deletePersistent(wordList.get(i));
					//wordList.remove(i);
					//i--;
				}
				else{
					if(word.getWordInfoList() != null && word.getWordInfoList().size() > 0)
						pm.makePersistent(word);
				}
				//wordList.set(i, mg.analysisWord(wordStr));
				//deletePm.close();
			}
			//pm.makePersistentAll(wordList);
		} finally{
			pm.close();
		}
		
		ModelAndView result = new ModelAndView("ajaxResult/adminRecrowlingResult");
		return result;
	}
	
	@RequestMapping("/adminWordMapValidation.do")
	public ModelAndView adminWordMapValidation(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		System.out.println(":::::::WOrdMapValidation Start:::::");
		VBWordSearchVO vBWordSearchVO = new VBWordSearchVO();
		bind(req, vBWordSearchVO);
		System.out.println("VO: "+vBWordSearchVO);
		//String keyword = ServletRequestUtils.getStringParameter(req, "keyword", "");
		List<VBWordMap> wordMapList = null;
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(VBWordMap.class);
		
		try {
			wordMapList = (List<VBWordMap>)query.execute();
		} finally {
			query.closeAll();
		}
		
		
		PersistenceManager wordPm = PMF.get().getPersistenceManager();
		for(int i = 0 ; i < wordMapList.size() ; i++){
			
			boolean isInvalid = false;
			try{
				VBWord word = pm.getObjectById(VBWord.class, wordMapList.get(i).getWordKey());
				if(word == null) isInvalid = true;
			}catch(Exception e){
				isInvalid = true;
			}
			
			if(isInvalid){
				System.out.println("Can't found "+wordMapList.get(i).getWordName()+"'s info..delete.");
				pm.deletePersistent(wordMapList.get(i));
			}
		}
		System.out.println(":::::::WOrdMapValidation END:::::");
		ModelAndView result = new ModelAndView("ajaxResult/adminWordMapValidationResult");
		return result;
	}
	
	@RequestMapping("/adminDeleteWordMapAll.do")
	public ModelAndView adminDeleteWordMapAll(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		List<VBWordMap> wordMapList = null;
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(VBWordMap.class);
		
		try {
			wordMapList = (List<VBWordMap>)query.execute();
		} finally {
			query.closeAll();
		}
		pm.deletePersistentAll(wordMapList);
		
		ModelAndView result = new ModelAndView("ajaxResult/adminWordMapValidationResult");
		return result;
	}
	
	@RequestMapping("/adminDeleteWordAll.do")
	public ModelAndView adminDeleteWordAll(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		
		List<VBWord> wordList = null;
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(VBWord.class);
		
		try {
			wordList = (List<VBWord>)query.execute();
		} finally {
			query.closeAll();
		}
		pm.deletePersistentAll(wordList);
		ModelAndView result = new ModelAndView("ajaxResult/adminWordMapValidationResult");
		return result;
	}
}
