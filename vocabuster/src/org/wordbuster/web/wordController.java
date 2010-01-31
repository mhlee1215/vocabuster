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
	
	/**
	 * 하나의 단어를 추가하는 부분
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/addOneWord.do")
	public ModelAndView addOneWord(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		//인풋 인코딩 = UTF-8
		req.setCharacterEncoding("utf-8");
		//추가할 단어 가져옴
		String wordStr = req.getParameter("word");
		System.out.println("wordStr: "+wordStr);
		//사용자 정보 가져옴 
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
		VBUser vbuser = VBUserService.getVBUser(req);

		//단어 추가 결과 
		String addWordResult = "";
		//유저 단어로 추가 결과
		String addWordMapResult = "";
		//성공 여부
		String isSuccess = "1";
		//샘플 단어 뜻
		String sampleMeaning = "";
		//의미 수집기 생성
		MeaningGatherer mg = new MeaningGatherer();
		
		PersistenceManager wordPm = PMF.get().getPersistenceManager();
		PersistenceManager wordMapPm = PMF.get().getPersistenceManager();
		
		try{
			//Search from total word pool
			//Determine whether inserted word is already registered or not
			VBWord word = null;
			try{
				word = wordPm.getObjectById(VBWord.class, VBWord.createKey(wordStr));
			}catch(Exception e){
				System.out.println("Couldn't find int word pool.");
			}
			//If the word is already registered
			if(word != null){
				//wordListAlreadyExisted.add(words[i]);
				word.increaseInsertedCount();
				addWordResult = "existed";
			}
			//It is first time to register
			else{
				word = mg.analysisWord(wordStr);
				
				addWordResult = "new";
				if(word == null)
					addWordResult = "not found";
			}
			if(word != null){
				if(word.getWordInfoList() == null || word.getWordInfoList().size() == 0){
					isSuccess = "0";
				}else{
					isSuccess = "1";
					sampleMeaning = word.getWordInfoList().get(0).getShortMeaning();
				}
				
				//PersistenceManager wordPm = JDOHelper.getPersistenceManager(word);
				if(wordPm == null)
					wordPm = PMF.get().getPersistenceManager();
				try {
					//단어 모음에 추가
					wordPm.makePersistent(word);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			if(word != null){
				//Now, we have to determine whether the user have already got the word which wanna to register
				Key wordMapKey = VBWordMap.createKey(user, word.getWordName());
				
				VBWordMap userWordMap = null;
				try{
					userWordMap = wordMapPm.getObjectById(VBWordMap.class, wordMapKey);
				}catch(Exception e){
					System.out.println("Couldn't find in word map pool.");
				}
				//유저의 단어로 이미 등록되어 있는 경우
				if(userWordMap != null){	
					System.out.println("word "+userWordMap.getWordName()+"is already registered "+userWordMap.getInsertCount()+" time(s).");
					userWordMap.increaseInsertCount();
					addWordMapResult = "existed";
				}
				//유저의 단어로 처음 등록되는 경우 
				else{
					userWordMap = new VBWordMap();
					userWordMap.setPrimarykey(wordMapKey);
					userWordMap.setKey(wordMapKey);
					userWordMap.setUserKey(vbuser.getKey());
					userWordMap.setWordKey(word.getKey());
					userWordMap.init();
					userWordMap.setWordName(word.getWordName());
					addWordMapResult = "new";
					
					try{
						//유저 맵에 추가
						wordMapPm.makePersistent(userWordMap);
						System.out.println("insertedCount: "+userWordMap.getInsertCount());
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}else{
				addWordMapResult = "fail";
				isSuccess = "0";
			}
		}catch(Exception e){
			addWordMapResult = "error";
			isSuccess = "0";
		}finally{
			wordPm.close();
			wordMapPm.close();
		}
		
		ModelAndView result = new ModelAndView("ajaxResult/addOneWordResult");
		result.addObject("addWordResult", addWordResult);
		result.addObject("addWordMapResult", addWordMapResult);
		result.addObject("isSuccess", isSuccess);
		result.addObject("sampleMeaning", sampleMeaning);
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/myWordList.do")
	public ModelAndView myWordList(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBWordSearchVO vBWordSearchVO = new VBWordSearchVO();
		bind(req, vBWordSearchVO);
		VBUser vbuser = VBUserService.getVBUser(req);
		//Set<Key> wordMapKey = vbuser.getWordMapKey();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		
		Query query = pm.newQuery(VBWordMap.class);
		
		query.setOrdering("wordName asc");
		if(!vBWordSearchVO.getSearchOrder().equals(""))
			query.setOrdering(vBWordSearchVO.getSearchOrder());
		
		String filterStr = "userKey == searchUserKey";
		String parameterStr = "String searchUserKey, String searchWordName";
		if(!vBWordSearchVO.getSearchKeyword().equals(""))
			filterStr +=" && wordName == searchWordName";
		
		query.setFilter(filterStr);
		query.declareParameters(parameterStr);
//		query.setRange(0, 30);
	    
		List<VBWordMap> wordMapList = null;//vbuser.getWordMapList();
		//System.out.println("wordMapList: "+wordMapList);
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			System.out.println("searchUserKey: "+vbuser.getKey());
			params.put("searchUserKey", vbuser.getKey());
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
