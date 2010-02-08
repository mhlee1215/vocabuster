package org.wordbuster.web;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordInfo;
import org.wordbuster.domain.VBWordMap;
import org.wordbuster.domain.VBWordSearchVO;
import org.wordbuster.service.VBWordService;
import org.wordbuster.util.MeaningGatherer;


@Controller
public class adminController extends MultiActionController {
	@Autowired
	private VBWordService wordService;
	
	@RequestMapping("/adminRecrowling.do")
	public ModelAndView adminRecrowling(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBWordSearchVO vBWordSearchVO = new VBWordSearchVO();
		bind(req, vBWordSearchVO);
		System.out.println("VO: "+vBWordSearchVO);
		//String keyword = ServletRequestUtils.getStringParameter(req, "keyword", "");
		List<VBWord> wordList = null;
		HashMap<String, String> map;
		
		wordList = wordService.retrieveWord(vBWordSearchVO);//(List<VBWord>)query.execute(vBWordSearchVO.getSearchKeyword());
		
		
		MeaningGatherer mg = new MeaningGatherer();
		try{
			//Query wordInfoQuery = pm.newQuery(VBWordInfo.class);
			//wordInfoQuery.deletePersistentAll();
			wordService.deleteWordInfoAll();
			for(int i = 0 ; i < wordList.size() ; i++){
				String wordStr = wordList.get(i).getWordName();
				System.out.println("rcrowling: "+wordStr);
				
				//Set isNew flag to false
				VBWord word = mg.analysisWord(wordStr, false);
				//PersistenceManager deletePm = JDOHelper.getPersistenceManager(vbuser);
				if(word == null){
					
					//pm.deletePersistent(wordList.get(i));
					//wordList.remove(i);
					//i--;
				}
				else{
					if(word.getWordInfoList() != null && word.getWordInfoList().size() > 0){
					//	pm.makePersistent(word);
					}
				}
				//wordList.set(i, mg.analysisWord(wordStr));
				//deletePm.close();
			}
		}catch(Exception e){
			
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
		
		wordMapList = wordService.retrieveWordMapListAll();//(List<VBWordMap>)query.execute();
		
		
		//PersistenceManager wordPm = PMF.get().getPersistenceManager();
		for(int i = 0 ; i < wordMapList.size() ; i++){
			
			boolean isInvalid = false;
			VBWord word = wordService.getVBWord(wordMapList.get(i).getWordName());//pm.getObjectById(VBWord.class, wordMapList.get(i).getWordKey());
			if(word == null) isInvalid = true;
			
			if(isInvalid){
				System.out.println("Can't found "+wordMapList.get(i).getWordName()+"'s info..delete.");
				wordService.deleteWord(wordMapList.get(i).getWordName());
			}
		}
		System.out.println(":::::::WOrdMapValidation END:::::");
		ModelAndView result = new ModelAndView("ajaxResult/adminWordMapValidationResult");
		return result;
	}
	
	@RequestMapping("/adminDeleteWordMapAll.do")
	public ModelAndView adminDeleteWordMapAll(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		boolean queryResult = wordService.deleteWordMapAll();
		ModelAndView result = new ModelAndView("ajaxResult/adminWordMapValidationResult");
		return result;
	}
	
	@RequestMapping("/adminDeleteWordAll.do")
	public ModelAndView adminDeleteWordAll(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		boolean queryResult = wordService.deleteWordAll();
		ModelAndView result = new ModelAndView("ajaxResult/adminWordMapValidationResult");
		return result;
	}
}
