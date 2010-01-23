package org.wordbuster.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.wordbuster.PMF;
import org.wordbuster.domain.VBUser;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordCategory;
import org.wordbuster.domain.VBWordInfo;
import org.wordbuster.domain.VBWordMap;
import org.wordbuster.domain.VBWordQuizVO;
import org.wordbuster.domain.VBWordSearchVO;
import org.wordbuster.service.VBUserService;
import org.wordbuster.util.MeaningGatherer;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@Controller
public class wordQuizController extends MultiActionController {
	static Logger logger = Logger.getLogger(wordQuizController.class);
	
	@RequestMapping("/getWordQuestion.do")
	public ModelAndView getWordQuestion(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBWordQuizVO vBWordQuizVO = new VBWordQuizVO();
		bind(req, vBWordQuizVO);
		VBUser vbuser = VBUserService.getVBUser(req);
		Set<Key> wordMapKey = vbuser.getWordMapKey();
		List<VBWord> wordList = null;
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query query = pm.newQuery(VBWordMap.class);
		//query.setOrdering("insertedCount desc");
		
		
		query.setFilter("key == wordMapKey");
		query.declareParameters("String wordMapKey");
		
	    
		List<VBWordMap> wordMapList = null;
		try {
			wordMapList = (List<VBWordMap>)query.execute(wordMapKey);
			
			//pm.get
			//pm.makePersistent(word);
		} finally {
			//pm.close();
			query.closeAll();
		}
		
		Set<Key> wordKey = new LinkedHashSet<Key>();
		System.out.println("myWodList: "+wordKey.size());
		for(int i = 0 ; i < wordMapList.size() ; i++)
		{
			VBWordMap wordMap = wordMapList.get(i);
			wordKey.add(wordMap.getWordKey());
		}
		
		Query searchWordQuery = pm.newQuery(VBWord.class);
		searchWordQuery.setFilter("key == searchWordKey");
		searchWordQuery.declareParameters("String searchWordKey");
		searchWordQuery.setRange(0, 1);
		
		try {
			wordList = (List<VBWord>)searchWordQuery.execute(wordKey);
			
		} finally {
			query.closeAll();
		}
		
		VBWord targetWord = null;
		int answerNumber = 0;
		for(int i = 0 ; i < wordList.size(); i++){
			targetWord = wordList.get(i);
		}
		
		List<VBWord> selections = new LinkedList<VBWord>();
		selections.add(targetWord);
		
		
		for(int i = 0 ; i < wordList.size() ; i++)
			System.out.println("i: "+i+", "+wordList.get(i).getWordName());
		
		ModelAndView result = new ModelAndView("ajaxResult/wordQuiz");
		result.addObject("targetWord", targetWord);
		result.addObject("answerNumber", answerNumber);
		result.addObject("selections", selections);
		result.addObject("vBWordQuizVO", vBWordQuizVO);
		
		return result;
	}
	
	@RequestMapping("/submitAnswer.do")
	public ModelAndView submitAnswer(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
		VBWordQuizVO vBWordQuizVO = new VBWordQuizVO();
		bind(req, vBWordQuizVO);
		System.out.println("VO: "+vBWordQuizVO);
		Key wordMapKey = VBWordMap.createKey(user, vBWordQuizVO.getQuizWordName());
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(VBWordMap.class);
		query.setFilter("key == wordMapKey");
		query.declareParameters("String wordMapKey");
		
		VBWordMap targetWordMap = null;
		List<VBWordMap> wordMapList = null;
		try {
			wordMapList = (List<VBWordMap>)query.execute(wordMapKey);
		} finally {
			query.closeAll();
		}
		if(wordMapList.size() > 0){
			targetWordMap = wordMapList.get(0);
		}else{
			System.out.println("Couldn't find word to check..[1]");
		}
		if(targetWordMap != null){
			if("Y".equals(vBWordQuizVO.getIsCorrect())){
				targetWordMap.correct();
				System.out.println(vBWordQuizVO.getQuizWordName()+" is correct..");
			}
			else{
				targetWordMap.wrong();
				System.out.println(vBWordQuizVO.getQuizWordName()+" is wrong..");
			}
		}else{
			System.out.println("Couldn't find word to check..[2]");
		}
		
		return null;
	}
	
	
}
