package org.wordbuster.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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
import org.wordbuster.service.VBWordService;
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
		vBWordQuizVO.initVO();
		bind(req, vBWordQuizVO);
		System.out.println("VO: "+vBWordQuizVO);
		VBUser vbuser = VBUserService.getVBUser(req);
		//List<VBWord> wordList = null;
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(VBWordMap.class);
		query.setOrdering("insertCount desc");
		String filterStr = "userKey == searchUserKey";
		String parameterStr = "String searchUserKey";
		query.setFilter(filterStr);
		query.declareParameters(parameterStr);
		
		List<VBWordMap> wordMapList = null;//vbuser.getWordMapList();
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			System.out.println("searchUserKey: "+vbuser.getKey());
			params.put("searchUserKey", vbuser.getKey());
			wordMapList = (List<VBWordMap>)query.executeWithMap(params);
		} finally {
			query.closeAll();
		}

		Integer selectionSize = vBWordQuizVO.getSelectionCount();
		VBWord targetWord = null;
		int answerNumber = 0;
		Random nRandom = new Random();
		answerNumber = nRandom.nextInt(selectionSize);
		boolean isFindTarget = false;
		while(!isFindTarget){
			for(int i = 0 ; i < wordMapList.size(); i++){
				wordMapList.get(i).init();
				System.out.println(wordMapList.get(i));
				if(wordMapList.get(i).isPossibleToSelect()){
					targetWord = VBWordService.getVBWord(wordMapList.get(i).getWordKey());
					wordMapList.get(i).setDelay();
					
					//단어 맵 동기화
					PersistenceManager wordMapPm = JDOHelper.getPersistenceManager(wordMapList.get(i));
					if(wordMapPm == null)
						wordMapPm = PMF.get().getPersistenceManager();
					try {
						wordMapPm.makePersistent(wordMapList.get(i));
					} finally {
						wordMapPm.close();
					}
					isFindTarget = true;
					break;
				}else{
					wordMapList.get(i).decreaseDelay();
				}
			}
		}
		
		PersistenceManager wordMapPm = JDOHelper.getPersistenceManager(wordMapList);
		if(wordMapPm == null)
			wordMapPm = PMF.get().getPersistenceManager();
		try {
			//유저 맵에 추가
			wordMapPm.makePersistentAll(wordMapList);
		} finally {
			wordMapPm.close();
		}
		
		System.out.println("targetWord: "+targetWord);
		
		//Integer maxCount = VBWordService.getVBWordCount();
		
		List<String> selectionList = VBWordService.makeSelection(targetWord, selectionSize, answerNumber);
		for(int i = 0 ; i < selectionList.size() ;i++)
			System.out.println(selectionList.get(i));
		
		ModelAndView result = new ModelAndView("ajaxResult/wordQuiz");
		result.addObject("targetWord", targetWord);
		result.addObject("answerNumber", answerNumber);
		result.addObject("selectionList", selectionList);
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
