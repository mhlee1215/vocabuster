package org.wordbuster.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.wordbuster.domain.VBMyWordSearchVO;
import org.wordbuster.domain.VBUser;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordCategory;
import org.wordbuster.domain.VBWordInfo;
import org.wordbuster.domain.VBWordMap;
import org.wordbuster.domain.VBWordQuizVO;
import org.wordbuster.domain.VBWordSearchVO;
import org.wordbuster.service.UserService;
import org.wordbuster.service.VBUserService;
import org.wordbuster.service.VBWordService;
import org.wordbuster.util.DownloadUtil;
import org.wordbuster.util.MeaningGatherer;

@Controller
public class wordController extends MultiActionController {
	
	@Autowired
	private VBWordService wordService;
	
	@Autowired
	private UserService userService;
	
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
		//UserService userService = UserServiceFactory.getUserService();
        //User user = userService.getCurrentUser();
		String userid = (String) req.getSession().getAttribute("userid");
		if(userid == null) return null;
		
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
		
		String correctedStr = "";
		
		//PersistenceManager wordPm = PMF.get().getPersistenceManager();
		//PersistenceManager wordMapPm = PMF.get().getPersistenceManager();
		
		try{
			//Search from total word pool
			//Determine whether inserted word is already registered or not
			VBWord word = null;
			List<VBWordInfo> wordInfoList = null;
			boolean isAlreadyExisted = false;
			try{
				word = wordService.retrieveWord(wordStr);// wordPm.getObjectById(VBWord.class, VBWord.createKey(wordStr));
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Couldn't find int word pool.");
			}
			//If the word is already registered
			if(word != null){
				//wordListAlreadyExisted.add(words[i]);
				word.increaseInsertedCount();
				addWordResult = "existed";
				isAlreadyExisted = true;
				wordInfoList = wordService.retriveWordInfo(wordStr);
			}
			//It is first time to register
			else{
				word = mg.analysisWord(wordStr);
				wordInfoList = word.getWordInfoList();
				
				
				//Word correction 이 일어난 경우(오타가 들어갔는 경우)
				if(!word.getWordName().equals(wordStr)){
					correctedStr = word.getWordName();
					VBWord checkWord = null;
					try{
						checkWord = wordService.retrieveWord(word.getWordName());// wordPm.getObjectById(VBWord.class, VBWord.createKey(wordStr));
					}catch(Exception e){
						e.printStackTrace();
						System.out.println("Couldn't find int word pool.");
					}
					//If the word is already registered
					if(checkWord != null){
						//wordListAlreadyExisted.add(words[i]);
						checkWord.increaseInsertedCount();
						addWordResult = "existed";
						isAlreadyExisted = true;
						word = checkWord;
						wordInfoList = wordService.retriveWordInfo(word.getWordName());
					}else{
						addWordResult = "new";
						isAlreadyExisted = false;
					}
				}else{
					addWordResult = "new";
					isAlreadyExisted = false;
					if(word == null)
						addWordResult = "not found";
				}
			}
			if(word != null){
				if(wordInfoList == null || wordInfoList.size() == 0){
					isSuccess = "0";
				}else{
					isSuccess = "1";
					sampleMeaning = wordInfoList.get(0).getShortmeaning();
				}
				
				if(isAlreadyExisted){
					//입력수 업데이트
					wordService.updateWord(word);
				}else{
					//word 추가
					wordService.insertWord(word);
					//wordinfo 추가
					wordService.insertWordInfoList(word.getWordName(), word.getWordInfoList());
				}
			}
			
			if(word != null){
				//Now, we have to determine whether the user have already got the word which wanna to register
				VBWordMap userWordMap = null;
				try{
					userWordMap = wordService.retrieveWordMap(userid, word.getWordName());//wordMapPm.getObjectById(VBWordMap.class, wordMapKey);
				}catch(Exception e){
					System.out.println("Couldn't find in word map pool.");
				}
				//유저의 단어로 이미 등록되어 있는 경우
				if(userWordMap != null){	
					System.out.println("word "+userWordMap.getWordName()+"is already registered "+userWordMap.getInsertCount()+" time(s).");
					userWordMap.increaseInsertCount();
					addWordMapResult = "existed";
					wordService.updateWordMap(userWordMap);
				}
				//유저의 단어로 처음 등록되는 경우 
				else{
					userWordMap = new VBWordMap();
					userWordMap.setUserid(userid);
					userWordMap.init();
					userWordMap.setWordName(word.getWordName());
					addWordMapResult = "new";
					
					try{
						//유저 맵에 추가
						wordService.insertWordMap(userWordMap);
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
			e.printStackTrace();
			addWordMapResult = "error";
			isSuccess = "0";
		}finally{
			//wordPm.close();
			//wordMapPm.close();
		}
		
		ModelAndView result = new ModelAndView("ajaxResult/addOneWordResult");
		result.addObject("addWordResult", addWordResult);
		result.addObject("addWordMapResult", addWordMapResult);
		result.addObject("isSuccess", isSuccess);
		result.addObject("sampleMeaning", sampleMeaning);
		result.addObject("correctedStr", correctedStr);
		return result;
	}
	
	@RequestMapping("/wordList.do")
	public ModelAndView wordList(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		VBWordSearchVO vBWordSearchVO = new VBWordSearchVO();
		bind(req, vBWordSearchVO);
		System.out.println("VO: "+vBWordSearchVO);
		List<VBWord> wordList = wordService.retrieveWord(vBWordSearchVO);//(List<VBWord>)query.execute(vBWordSearchVO.getSearchKeyword());
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
		VBMyWordSearchVO vBWordSearchVO = new VBMyWordSearchVO();
		bind(req, vBWordSearchVO);
		String userid = (String) req.getSession().getAttribute("userid");
		vBWordSearchVO.setSearchUserid(userid);
		
		List<VBWordMap> wordMapList = wordService.retrieveMyWord(vBWordSearchVO);
		wordService.syncWordMapWithWord(wordMapList);
		
		ModelAndView result = new ModelAndView("ajaxResult/myWordListResult");
		result.addObject("wordMapList", wordMapList);
		result.addObject("vBWordSearchVO", vBWordSearchVO);
		return result;
	}
	
	
	@RequestMapping("/exportAsExcel.do")
	public ModelAndView exportAsExcel(HttpServletRequest request, HttpServletResponse rsponse) throws Exception{
        String original_filename = "test";//(String)request.getSession().getAttribute("downloadName");
        String reportContent = "test....";//(String)request.getSession().getAttribute("documentContent");
        
        byte[] data = reportContent.getBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        original_filename+=".html";
        DownloadUtil.download(request, rsponse, bis, original_filename, 0, null);
        return null;
    }
}
