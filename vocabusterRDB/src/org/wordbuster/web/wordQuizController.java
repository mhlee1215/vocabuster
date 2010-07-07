package org.wordbuster.web;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.wordbuster.domain.VBMyWordSearchVO;
import org.wordbuster.domain.VBUser;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordMap;
import org.wordbuster.domain.VBWordQuizVO;
import org.wordbuster.service.VBUserService;
import org.wordbuster.service.VBWordService;

@Controller
public class wordQuizController extends MultiActionController {
	static Logger logger = Logger.getLogger(wordQuizController.class);
	
	@Autowired
	private VBWordService wordService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getWordQuestion.do")
	public ModelAndView getWordQuestion(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		//UserService userService = UserServiceFactory.getUserService();
        //User user = userService.getCurrentUser();
        
		VBWordQuizVO vBWordQuizVO = new VBWordQuizVO();
		vBWordQuizVO.initVO();
		bind(req, vBWordQuizVO);
		System.out.println("VO: "+vBWordQuizVO);
		String userid = (String) req.getSession().getAttribute("userid");

		vBWordQuizVO.setUserid(userid);
		//delay count 가 5인 경우, 10이면 충분함?
		vBWordQuizVO.setFromIndex("0");
		vBWordQuizVO.setToIndex("10");
		
		VBMyWordSearchVO vBWordSearchVO = new VBMyWordSearchVO();
		vBWordSearchVO.setSearchUserid(userid);
		vBWordSearchVO.setSearchCategory(vBWordQuizVO.getQuestionCategory());
		int myWordListCount = wordService.retrieveMyWordCount(vBWordSearchVO);
		
		if(!vBWordQuizVO.getStageIndex().isEmpty() && !vBWordQuizVO.getStageMaxIndex().isEmpty()){
			int stageIndex = Integer.parseInt(vBWordQuizVO.getStageIndex());
			int stageMaxIndex = Integer.parseInt(vBWordQuizVO.getStageMaxIndex());
			int realStartIndex = (int) Math.ceil(((((double)myWordListCount)/stageMaxIndex) * (stageIndex-1)));
			int realEndIndex = (int) Math.ceil(((((double)myWordListCount)/stageMaxIndex)));
			vBWordQuizVO.setQuestionFromIndex(Integer.toString(realStartIndex));
			vBWordQuizVO.setQuestionToIndex(Integer.toString(realEndIndex));
		}
		
		
		List<VBWordMap> wordMapList = wordService.retrieveQuestion(vBWordQuizVO);

		Integer selectionSize = vBWordQuizVO.getSelectionCount();
		VBWordMap targetWord = null;
		int answerNumber = 0;
		Random nRandom = new Random();
		answerNumber = nRandom.nextInt(selectionSize);
		boolean isFindTarget = false;
		//적절한 단어를 찾을때까지 계속
		while(!isFindTarget){
			for(int i = 0 ; i < wordMapList.size(); i++){
				wordMapList.get(i).init();
				System.out.println(wordMapList.get(i));
				String candidateWordName = wordMapList.get(i).getWordName();
				//Key wordMapKey = VBWordMap.createKey(user, wordMapList.get(i).getWordName());
				VBWordMap candidateWordMap = wordService.retrieveWordMap(userid, candidateWordName);//pm.getObjectById(VBWordMap.class, wordMapKey);
				
				if(candidateWordMap.isPossibleToSelect()){
					targetWord = candidateWordMap;//wordService.retrieveWord(candidateWordName);//VBWordService.getVBWord(candidateWordMap.getWordKey());
					candidateWordMap.setDelay();
					isFindTarget = true;
				}else{
					candidateWordMap.decreaseDelay();
				}
				
				wordService.updateWordMap(candidateWordMap);
				
				if(isFindTarget)
					break;
				
				
			}
		}
		
		System.out.println("targetWord: "+targetWord);
		
		List<String> selectionList = wordService.makeSelection(userid, targetWord, selectionSize, answerNumber);
		//for(int i = 0 ; i < selectionList.size() ;i++)
		//	System.out.println(selectionList.get(i));
		
		String learningRate = wordService.retrieveLearningRate(vBWordQuizVO);
		
		ModelAndView result = new ModelAndView("ajaxResult/wordQuiz");
		result.addObject("targetWord", targetWord);
		result.addObject("answerNumber", answerNumber);
		result.addObject("selectionList", selectionList);
		result.addObject("vBWordQuizVO", vBWordQuizVO);
		result.addObject("learningRate", learningRate);
		
		return result;
	}
	
	@RequestMapping("/submitAnswer.do")
	public ModelAndView submitAnswer(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		//UserService userService = UserServiceFactory.getUserService();
        //User user = userService.getCurrentUser();
		VBWordQuizVO vBWordQuizVO = new VBWordQuizVO();
		bind(req, vBWordQuizVO);
		System.out.println("submitAnswer VO: "+vBWordQuizVO);
		System.out.println("isCorrect: "+req.getParameterValues("isCorrect"));
		String userid = (String) req.getSession().getAttribute("userid");
		
		//Key wordMapKey = VBWordMap.createKey(user, vBWordQuizVO.getQuizWordName());
		
		//PersistenceManager pm = PMF.get().getPersistenceManager();
		
		VBWordMap targetWordMap = wordService.retrieveWordMap(userid, vBWordQuizVO.getQuizWordName());//pm.getObjectById(VBWordMap.class, wordMapKey);
		try{
			if("Y".equals(vBWordQuizVO.getIsCorrect())){
				targetWordMap.correct();
				System.out.println(vBWordQuizVO.getQuizWordName()+" is correct..");
			}
			else{
				targetWordMap.wrong();
				System.out.println(vBWordQuizVO.getQuizWordName()+" is wrong..");
			}
			
			wordService.updateWordMap(targetWordMap);
			
		}catch(Exception e){
			
		}
		return null;
	}
}
