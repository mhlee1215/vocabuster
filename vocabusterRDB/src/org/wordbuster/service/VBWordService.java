package org.wordbuster.service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wordbuster.dao.VBUserDAO;
import org.wordbuster.dao.VBWordDAO;
import org.wordbuster.dao.VBWordInfoDAO;
import org.wordbuster.dao.VBWordMapDAO;
import org.wordbuster.domain.VBCategory;
import org.wordbuster.domain.VBCategorySearchVO;
import org.wordbuster.domain.VBMyWordSearchVO;
import org.wordbuster.domain.VBUser;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordInfo;
import org.wordbuster.domain.VBWordMap;
import org.wordbuster.domain.VBWordQuizVO;
import org.wordbuster.domain.VBWordSearchVO;


@Service
public class VBWordService {
	
	@Autowired
	private VBWordDAO wordDAO;
	
	@Autowired
	private VBUserDAO userDAO;
	
	@Autowired
	private VBWordInfoDAO wordInfoDAO;
	
	@Autowired
	private VBWordMapDAO wordMapDAO;
	
	
	public VBWord getVBWord(String wordName){
		VBWord word = wordDAO.retrieveWord(wordName);//pm.getObjectById(VBWord.class, wordKey);
		return word;
	}
	
	public Integer getVBWordCount(){
		return wordDAO.getVBWordCount();
	}
	
	public Integer getVBUserWordCount(String userid){
		VBWordMap wordMap = new VBWordMap();
		wordMap.setUserid(userid);
		return wordMapDAO.getVBUserWordCount(wordMap);
	}
	
	public String getRandomMeaning(VBWordMap word){
		return word.getMeaningbundle();
//		List<VBWordInfo> wordInfo = wordInfoDAO.retrieveWordInfo(word.getWordName());
//		Random oRandom = new Random();
//		int meaningIndex = oRandom.nextInt(wordInfo.size());
//		return wordInfo.get(meaningIndex).getShortmeaning();
	}
	
	public String getVBWordRandomMeaningByIndexExceptTarget(String userid, int index, VBWordMap targetWord){
		System.out.println("index : "+index);
		//PersistenceManager pm = PMF.get().getPersistenceManager();
		//Query wordQuery = pm.newQuery(VBWord.class);
		//wordQuery.setResultClass(VBWord.class);
		//wordQuery.setFilter("key != targetWordKey");
		//wordQuery.declareParameters("String targetWordKey");
		//wordQuery.setRange(index, index+1);
		VBWordMap result = null;
		String resultMeaning = "";
		List<VBWord> wordList = null;
		List<VBWordMap> wordMapList = null;
		
		VBMyWordSearchVO searchVO = new VBMyWordSearchVO();
		searchVO.setSearchExcludeKeyword(targetWord.getWordName());
		searchVO.setPageOffset(index);
		searchVO.setPageSize(1);
		searchVO.setSearchUserid(userid);
		wordMapList = wordMapDAO.retrieveMyWordMap(searchVO);
		
		try{
			//result = (VBWord) wordQuery.execute(targetWord.getKey());
			//wordList = null;//(List<VBWord>)wordQuery.execute(targetWord.getKey());
			System.out.println("list size : "+wordMapList.size());
			result = wordMapList.get(0);
			System.out.println("selected choice: "+result.getWordName());
			if(result.getWordName().equals(targetWord.getWordName()))
				return null;
			else
				resultMeaning = getRandomMeaning(result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//pm.close();
		}
		System.out.println("getVBWord: "+result);
		return resultMeaning;
	}
	
	public List<String> makeSelection(String userid, VBWordMap targetWord, int selectionSize, int answerIndex){
		int maxSize = wordDAO.getVBWordCount();
		System.out.println("::selectionSize: "+selectionSize);
		System.out.println("::maxSize-1: "+(maxSize-1));
		Integer[] selectionQueue = makeRandQueue(selectionSize, maxSize-1);
		Vector<String> wordList = new Vector<String>();
		int queueIndex = 0;
		
		for(int i = 0 ; i < selectionSize && i < selectionQueue.length; i++)
		{
			if(i == answerIndex){
				String targetMeaning = getRandomMeaning(targetWord);
				System.out.println("answer : "+targetMeaning);
				wordList.add(targetMeaning);
			}else{
				System.out.println(selectionQueue[queueIndex]);
				String candidate = getVBWordRandomMeaningByIndexExceptTarget(userid, selectionQueue[queueIndex], targetWord);
				queueIndex++;
				if(candidate == null){
					i--;
				}else{
					wordList.add(candidate);
				}
					
			}
		}
		
		return wordList;
	}
	
	public static Integer[] makeRandQueue(int size, int maxSize){
		if(size > maxSize) return null;
		Integer[] result = new Integer[size];
		Integer[] sequence = new Integer[maxSize];
		
		//초기화
		for(int i = 0 ; i < maxSize ; i++)
			sequence[i] = i;
		
		//랜덤 큐 채우기 
		Random oRandom = new Random();
		for(int i = 0 ; i < size ; i++){
			int selection = oRandom.nextInt(maxSize-i);//(int)Math.random()/(maxSize-i);
			result[i] = sequence[selection];
			sequence[selection] = sequence[maxSize-1-i];
		}
		
		for(Integer val : result)
			System.out.println("makeRandQueue: "+val);
		
		return result;
	}
	
	
	//COMPLEX
	public List<VBWordMap> retrieveMyWord(VBMyWordSearchVO searchVO){
		return wordMapDAO.retrieveMyWordMap(searchVO);
	}
	
	public Integer retrieveMyWordCount(VBMyWordSearchVO searchVO){
		return wordMapDAO.retrieveMyWordMapCount(searchVO);
	}
	
	public List<VBWordMap> retrieveQuestion(VBWordQuizVO quizVO){
		return wordMapDAO.retrieveQuestion(quizVO);
	}
	
	//VBWORD
	
	////INSERT
	public boolean insertWord(VBWord word){
		return wordDAO.insertWord(word);
	}
	
	
	////SEARCH
	public List<VBWord> retrieveWord(VBWordSearchVO searchVO){
		List<VBWord> wordList = wordDAO.searchWord(searchVO);
		return wordList;
	}
	
	public Integer retrieveWordCount(VBWordSearchVO searchVO){
		Integer wordListCount = wordDAO.searchWordCount(searchVO);
		return wordListCount;
	}
	
	public List<VBWord> retrieveWordAll(VBWordSearchVO searchVO){
		List<VBWord> wordList = wordDAO.retriveWordAll(searchVO);
		return wordList;
	}
	
	public List<VBWord> retrieveWordAll(){
		return wordDAO.searchWord(new VBWordSearchVO());
	}
	public VBWord retrieveWord(String wordName){
	    return wordDAO.retrieveWord(wordName);
	}
	
	////UPDATE
	public boolean updateWord(VBWord word){
		return wordDAO.updateWord(word);
	}
	////DELETE
	public boolean deleteWord(String wordName){
		return wordDAO.deleteWord(wordName);
	}
	public boolean deleteWordAll(){
		return wordDAO.deleteWord("");
	}
	
	public void syncWordMapWithWord(List<VBWordMap> wordMapList){
		for(VBWordMap wordMap : wordMapList)
			syncWordMap(wordMap);
	}
	
	public void syncWordMap(VBWordMap wordMap){
		VBWord word = wordDAO.retrieveWord(wordMap.getWordName());
		syncWord(word);
		wordMap.setWord(word);
	}
	
	public void syncWord(VBWord word){
		List<VBWordInfo> wordInfoList = wordInfoDAO.retrieveWordInfo(word.getWordName());
		word.setWordInfoList(wordInfoList);
	}
	
	
	//VBWORDINFO
	////INSERT
	public boolean insertWordInfoList(String wordName, List<VBWordInfo> wordInfoList){
		for(int i = 0 ; i < wordInfoList.size() ; i++){
			VBWordInfo wordInfo = wordInfoList.get(i);
			wordInfo.setSeq(Integer.toString(i));
			wordInfoDAO.insertWordInfoList(wordInfo);
		}
		return true;
	}
	////SEARCH
	public List<VBWordInfo> retriveWordInfo(String wordName){
		return wordInfoDAO.retrieveWordInfo(wordName);
	}
	////UPDATE
	////DELETE
	public boolean deleteWordInfoAll(){
		return wordInfoDAO.deleteWordInfoList("");
	}
	
	//VBWORDMAP
	////INSERT
	public boolean insertWordMap(VBWordMap wordMap){
		wordMapDAO.insertWordMap(wordMap);
		return true;
	}
	////SEARCH
	public VBWordMap retrieveWordMap(String userid, String wordName, String category){
		return wordMapDAO.retrieveWordMap(userid, wordName, category);
	}
	public VBWordMap retrieveWordMap(String userid, String wordName){
		return wordMapDAO.retrieveWordMap(userid, wordName, "");
	}
	public List<VBWordMap> retrieveWordMapListAll(){
		return wordMapDAO.retrieveUserWordMap("");
	}
	////UPDATE
	public boolean updateWordMap(VBWordMap wordMap){
		wordMapDAO.updateWordMap(wordMap);
		return false;
	}
	////DELETE
	public boolean deleteWordMapAll(){
		return wordMapDAO.deleteWordMap("", "");
	}
	
	public boolean deleteWordMap(VBMyWordSearchVO searchVO){
		String userid = searchVO.getSearchUserid();
		String wordName = searchVO.getSearchKeyword();
		return wordMapDAO.deleteWordMap(userid, wordName);
	}
	
	//ETC
	public void updateWordStatus(HttpServletRequest request){
		String userid = (String)request.getSession().getAttribute("userid");
		int wordCountAll = getVBWordCount();
		int userWordCount = getVBUserWordCount(userid);
		request.getSession().setAttribute("wordcount", wordCountAll);
		request.getSession().setAttribute("userwordcount", userWordCount);
	}
	
	public List<VBCategory> retrieveCategory(VBCategorySearchVO vo){
		return wordDAO.retrieveCategory(vo);
	}
	
	public int updateCategory(VBCategorySearchVO vo){
		return wordDAO.updateCategory(vo);
	}
	
	public int deleteCategory(VBCategorySearchVO vo){
		return wordDAO.deleteCategory(vo);
	}
	
	public Object insertCategory(VBCategorySearchVO vo){
		return wordDAO.insertCategory(vo);
	}
	
	public String retrieveLearningRate(VBWordQuizVO searchVO){
		return wordMapDAO.retrieveLearningRate(searchVO);
	}
}