package org.wordbuster.web;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.wordbuster.domain.VBUser;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordMap;
import org.wordbuster.domain.VBWordQuizVO;
import org.wordbuster.service.VBUserService;
import org.wordbuster.service.VBWordService;

@Controller
public class wordQuizController extends MultiActionController {
	static Logger logger = Logger.getLogger(wordQuizController.class);
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getWordQuestion.do")
	public ModelAndView getWordQuestion(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
		VBWordQuizVO vBWordQuizVO = new VBWordQuizVO();
		vBWordQuizVO.initVO();
		bind(req, vBWordQuizVO);
		System.out.println("VO: "+vBWordQuizVO);
		VBUser vbuser = VBUserService.getVBUser(req);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(VBWordMap.class);
		query.setOrdering("score asc, insertCount desc");
		String filterStr = "userKey == searchUserKey";
		String parameterStr = "String searchUserKey";
		query.setFilter(filterStr);
		query.declareParameters(parameterStr);
		//delay count 가 5인 경우, 10이면 충분함?
		query.setRange(0, 10);

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
		//적절한 단어를 찾을때까지 계속
		while(!isFindTarget){
			for(int i = 0 ; i < wordMapList.size(); i++){
				wordMapList.get(i).init();
				System.out.println(wordMapList.get(i));
				
				Key wordMapKey = VBWordMap.createKey(user, wordMapList.get(i).getWordName());
				VBWordMap candidateWordMap = pm.getObjectById(VBWordMap.class, wordMapKey);
				
				if(candidateWordMap.isPossibleToSelect()){
					targetWord = VBWordService.getVBWord(candidateWordMap.getWordKey());
					candidateWordMap.setDelay();
					isFindTarget = true;
					break;
				}else{
					candidateWordMap.decreaseDelay();
				}
			}
		}
		
		try {
			
		} finally {
			pm.close();
		}
		
		System.out.println("targetWord: "+targetWord);
		
		List<String> selectionList = VBWordService.makeSelection(targetWord, selectionSize, answerNumber);
		//for(int i = 0 ; i < selectionList.size() ;i++)
		//	System.out.println(selectionList.get(i));
		
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
		
		VBWordMap targetWordMap = pm.getObjectById(VBWordMap.class, wordMapKey);
		try{
			if("Y".equals(vBWordQuizVO.getIsCorrect())){
				targetWordMap.correct();
				System.out.println(vBWordQuizVO.getQuizWordName()+" is correct..");
			}
			else{
				targetWordMap.wrong();
				System.out.println(vBWordQuizVO.getQuizWordName()+" is wrong..");
			}
		} finally {
			pm.close();
		}
		return null;
	}
}
