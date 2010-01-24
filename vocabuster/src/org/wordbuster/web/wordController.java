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
public class wordController extends MultiActionController {
	
	@RequestMapping("/addOneWord.do")
	public ModelAndView addOneWord(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("utf-8");
		String wordStr = req.getParameter("word");
		System.out.println("wordStr: "+wordStr);
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
		VBUser vbuser = VBUserService.getVBUser(req);
		Set<Key> userWordMapKey = vbuser.getWordMapKey();
		
		String addWordResult = "";
		String addWordMapResult = "";
		String isSuccess = "1";
		String sampleMeaning = "";
		
		MeaningGatherer mg = new MeaningGatherer();
		
		//Determine whether inserted word is already registered or not
		VBWord alreadyRegisteredWord = null;
		
		//Search from total word pool
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(VBWord.class);
		query.setFilter("wordName == searchWordName");
		query.declareParameters("String searchWordName");
	    
		try {
			List<VBWord> registeredWordList = null;
			registeredWordList = (List<VBWord>)query.execute(wordStr);
			if(registeredWordList.size() > 0){
				alreadyRegisteredWord = registeredWordList.get(0);
			}
		} finally {
			query.closeAll();
		}
		
		VBWord word = null;
		//If the word is already registered
		if(alreadyRegisteredWord != null){
			//wordListAlreadyExisted.add(words[i]);
			word = alreadyRegisteredWord;
			word.increaseInsertedCount();
			addWordResult = "existed";
		}
		//It is first time to register
		else{
			word = mg.analysisWord(wordStr);
			addWordResult = "new";
		}
		
		if(word.getWordInfoList() == null || word.getWordInfoList().size() == 0){
			isSuccess = "0";
		}else{
			isSuccess = "1";
			sampleMeaning = word.getWordInfoList().get(0).getShortMeaning();
		}
		
		
		PersistenceManager wordPm = JDOHelper.getPersistenceManager(word);
		if(wordPm == null)
			wordPm = PMF.get().getPersistenceManager();
		try {
			//단어 모음에 추가
			wordPm.makePersistent(word);
		} finally {
			wordPm.close();
		}
		
		//Now, we have to determine whether the user have already got the word which wanna to register
//		
		VBWordMap userWordMap = null;
		Key wordMapKey = VBWordMap.createKey(user, wordStr);
		if(userWordMapKey.contains(wordMapKey)){
			
			pm = PMF.get().getPersistenceManager();
			Query findWordMapQuery = pm.newQuery(VBWordMap.class);
		    query.setFilter("key == wordMapKey");
		    query.declareParameters("String wordMapKey");
			List<VBWordMap> foundWordMap = (List<VBWordMap>)findWordMapQuery.execute(wordMapKey);
			if(foundWordMap.size() > 0 ){
				userWordMap = foundWordMap.get(0);
				userWordMap.increaseInsertCount();
			}
			else{
				System.out.println("error!!!!!!!!!");
			}
			addWordMapResult = "existed";
		}else{
			userWordMapKey.add(wordMapKey);
			userWordMap = new VBWordMap();
			userWordMap.setKey(wordMapKey);
			userWordMap.setUserKey(vbuser.getKey());
			userWordMap.setWordKey(word.getKey());
			userWordMap.init();
			userWordMap.setWordName(word.getWordName());
			
			System.out.println("added! vbuser: "+vbuser.getUser().getEmail());
			vbuser.getWordMapKey().add(wordMapKey);
			
			PersistenceManager wordMapPm = PMF.get().getPersistenceManager();
			try {
				//유저 맵에 추가
				wordMapPm.makePersistent(userWordMap);
			} finally {
				wordMapPm.close();
			}
			
			addWordMapResult = "new";
		}
		
		
		PersistenceManager userPm = JDOHelper.getPersistenceManager(vbuser);
		if(userPm == null)
			userPm = PMF.get().getPersistenceManager();
		try {
			//사용자 보유 단어 추가 
			userPm.makePersistent(vbuser);
		} finally {
			userPm.close();
		}
		
		ModelAndView result = new ModelAndView("ajaxResult/addOneWordResult");
		result.addObject("addWordResult", addWordResult);
		result.addObject("addWordMapResult", addWordMapResult);
		result.addObject("isSuccess", isSuccess);
		result.addObject("sampleMeaning", sampleMeaning);
		return result;
	}
	
	@RequestMapping("/addWords.do")
	public ModelAndView addWords(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        VBUser vbuser = VBUserService.getVBUser(req);
        Set<Key> userWordMapKey = vbuser.getWordMapKey();
		req.setCharacterEncoding("utf-8");
		String wordStr = req.getParameter("content");
		Vector<String> wordList = new Vector<String>();
		Vector<String> wordListAlreadyExisted = new Vector<String>();
		Vector<String> wordListTypo = new Vector<String>();
		
		HashMap<String, List<VBWordInfo>> meaningVector = new HashMap<String, List<VBWordInfo>>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
	        
		
		//userWordMap.setUser(user);
		//Vector<VBWord> userWordList = new Vector<VBWord>();
		
		
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
				//Determine whether inserted word is already registered or not
				VBWord alreadyRegisteredWord = null;
				
				//Search from total word pool
				pm = PMF.get().getPersistenceManager();
				Query query = pm.newQuery(VBWord.class);
				query.setFilter("wordName == searchWordName");
				query.declareParameters("String searchWordName");
			    
				try {
					List<VBWord> registeredWordList = null;
					registeredWordList = (List<VBWord>)query.execute(words[i]);
					if(registeredWordList.size() > 0){
						alreadyRegisteredWord = registeredWordList.get(0);
					}
				} finally {
					query.closeAll();
				}
				
				VBWord word = null;
				//If the word is already registered
				if(alreadyRegisteredWord != null){
					wordListAlreadyExisted.add(words[i]);
					word = alreadyRegisteredWord;
					word.increaseInsertedCount();
				}
				//It is first time to register
				else{
					word = mg.analysisWord(words[i]);
					wordList.add(words[i]);
					meaningVector.put(words[i], word.getWordInfoList());
				}
				
				PersistenceManager wordPm = JDOHelper.getPersistenceManager(word);
				if(wordPm == null)
					wordPm = PMF.get().getPersistenceManager();
				try {
					//단어 모음에 추가
					wordPm.makePersistent(word);
				} finally {
					wordPm.close();
				}
				
				//Now, we have to determine whether the user have already got the word which wanna to register
//				
				VBWordMap userWordMap = null;
				Key wordMapKey = VBWordMap.createKey(user, words[i]);
				if(userWordMapKey.contains(wordMapKey)){
					
					pm = PMF.get().getPersistenceManager();
					Query findWordMapQuery = pm.newQuery(VBWordMap.class);
				    query.setFilter("key == wordMapKey");
				    query.declareParameters("String wordMapKey");
					List<VBWordMap> foundWordMap = (List<VBWordMap>)findWordMapQuery.execute(wordMapKey);
					if(foundWordMap.size() > 0 ){
						userWordMap = foundWordMap.get(0);
						userWordMap.increaseInsertCount();
					}
					else{
						System.out.println("error!!!!!!!!!");
					}
				}else{
					userWordMapKey.add(wordMapKey);
					userWordMap = new VBWordMap();
					userWordMap.setKey(wordMapKey);
					userWordMap.setUserKey(vbuser.getKey());
					userWordMap.setWordKey(word.getKey());
					userWordMap.init();
					userWordMap.setWordName(word.getWordName());
					
					System.out.println("added! vbuser: "+vbuser.getUser().getEmail());
					vbuser.getWordMapKey().add(wordMapKey);
					
					PersistenceManager wordMapPm = PMF.get().getPersistenceManager();
					try {
						//유저 맵에 추가
						wordMapPm.makePersistent(userWordMap);
					} finally {
						wordMapPm.close();
					}
				}
			}
			
			PersistenceManager userPm = JDOHelper.getPersistenceManager(vbuser);
			if(userPm == null)
				userPm = PMF.get().getPersistenceManager();
			try {
				//사용자 보유 단어 추가 
				userPm.makePersistent(vbuser);
			} finally {
				userPm.close();
			}
			//pm.close();
		}
		
		//userWordMap.setWords(userWordList);
		
		ModelAndView result = new ModelAndView("ajaxResult/addWordsResult");
		result.addObject("wordList", wordList);
		result.addObject("wordMeaning", meaningVector);
		result.addObject("wordListAlreadyExisted", wordListAlreadyExisted);
		result.addObject("wordListTypo", wordListTypo);
		return result;
	}
	
	@RequestMapping("/wordList.do")
	public ModelAndView wordList(HttpServletRequest req, HttpServletResponse resp) throws Exception{
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
		
		System.out.println("wordSize: "+wordList.size());
		for(int i = 0 ; i < wordList.size() ; i++)
			System.out.println(wordList.get(i).getWordName());
		
		ModelAndView result = new ModelAndView("ajaxResult/wordListResult");
		result.addObject("wordList", wordList);
		result.addObject("vBWordSearchVO", vBWordSearchVO);
		return result;
	}
	
	@RequestMapping("/wordListForm.do")
	public ModelAndView wordListForm(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBWordSearchVO vBWordSearchVO = new VBWordSearchVO();
		bind(req, vBWordSearchVO);
		ModelAndView result = new ModelAndView("task/showWords");
		result.addObject("vBWordSearchVO", vBWordSearchVO);
		return result;
	}
	
	@RequestMapping("/myWordListForm.do")
	public ModelAndView myWordListForm(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBWordSearchVO vBWordSearchVO = new VBWordSearchVO();
		bind(req, vBWordSearchVO);
		ModelAndView result = new ModelAndView("task/showMyWords");
		result.addObject("vBWordSearchVO", vBWordSearchVO);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/myWordList.do")
	public ModelAndView myWordList(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBWordSearchVO vBWordSearchVO = new VBWordSearchVO();
		bind(req, vBWordSearchVO);
		VBUser vbuser = VBUserService.getVBUser(req);
		Set<Key> wordMapKey = vbuser.getWordMapKey();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query query = pm.newQuery(VBWordMap.class);
		
		query.setOrdering("wordName asc");
		if(!vBWordSearchVO.getSearchOrder().equals(""))
			query.setOrdering(vBWordSearchVO.getSearchOrder());
		
		String filterStr = "key == wordMapKey";
		String parameterStr = "String wordMapKey, String searchWordName";
		if(!vBWordSearchVO.getSearchKeyword().equals(""))
			filterStr +=" && wordName == searchWordName";
		
		query.setFilter(filterStr);
		query.declareParameters(parameterStr);
		query.setRange(0, 30);
	    
		List<VBWordMap> wordMapList = null;
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			System.out.println("wordMapKeySize: "+wordMapKey.size());
			params.put("wordMapKey", wordMapKey);
			params.put("searchWordName", vBWordSearchVO.getSearchKeyword());
			wordMapList = (List<VBWordMap>)query.executeWithMap(params);
			
		} finally {
			query.closeAll();
		}
		VBWordService.syncWordMapWithWord(wordMapList);
		
		ModelAndView result = new ModelAndView("ajaxResult/myWordListResult");
		result.addObject("wordMapList", wordMapList);
		result.addObject("vBWordSearchVO", vBWordSearchVO);
		return result;
	}
	
	
	
	public static void main(String[] argv) throws IOException{
		MeaningGatherer mg = new MeaningGatherer();
		VBWord word = mg.analysisWord("testify");
	}
}
