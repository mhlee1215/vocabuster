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
import org.wordbuster.domain.VBWordSearchVO;
import org.wordbuster.util.MeaningGatherer;


@Controller
public class adminController extends MultiActionController {
	
	@RequestMapping("/adminForm.do")
	public ModelAndView adminForm(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBWordSearchVO vBWordSearchVO = new VBWordSearchVO();
		bind(req, vBWordSearchVO);
		ModelAndView result = new ModelAndView("task/adminPage");
		result.addObject("vBWordSearchVO", vBWordSearchVO);
		return result;
	}
	
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
				//List<VBWordInfo> wordInfoList = mg.getMeaning(wordList.get(i).getWordName());
				//wordList.get(i).setWordInfoList(wordInfoList);
				//PersistenceManager wordInfoPm = PMF.get().getPersistenceManager();
				//wordInfoPm.makePersistentAll(wordInfoList);
				//wordInfoPm.close();
				String wordStr = wordList.get(i).getWordName();
				VBWord word = mg.analysisWord(wordList.get(i), wordStr);
				System.out.println("rcrowling.."+word);
				//PersistenceManager deletePm = JDOHelper.getPersistenceManager(vbuser);
				if(word == null){
					pm.deletePersistent(wordList.get(i));
					//wordList.remove(i);
					//i--;
				}
				else
					pm.makePersistent(word);
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
}
