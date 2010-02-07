package org.wordbuster.service;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wordbuster.dao.VBUserDAO;
import org.wordbuster.dao.VBWordDAO;
import org.wordbuster.dao.VBWordInfoDAO;
import org.wordbuster.domain.VBUser;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordInfo;
import org.wordbuster.domain.VBWordMap;


@Service
public class VBWordService {
	
	@Autowired
	private VBWordDAO wordDAO;
	
	@Autowired
	private VBUserDAO userDAO;
	
	@Autowired
	private VBWordInfoDAO wordInfoDAO;
	
	
	public VBWord getVBWord(String wordName){
		VBWord word = wordDAO.retrieveWord(wordName);//pm.getObjectById(VBWord.class, wordKey);
		return word;
	}
	
	public Integer getVBWordCount(){
		return wordDAO.getVBWordCount();
	}
	
	public static Integer getVBUserWordCount(HttpServletRequest req){
		VBUser vbuser = VBUserService.getVBUser(req);
		return 0;
	}
	
	public String getRandomMeaning(VBWord word){
		List<VBWordInfo> wordInfo = wordInfoDAO.retrieveWordInfo(word.getWordName());
		Random oRandom = new Random();
		int meaningIndex = oRandom.nextInt(wordInfo.size());
		return wordInfo.get(meaningIndex).getShortMeaning();
	}
	
	public String getVBWordRandomMeaningByIndexExceptTarget(int index, VBWord targetWord){
		System.out.println("index : "+index);
		//PersistenceManager pm = PMF.get().getPersistenceManager();
		Query wordQuery = pm.newQuery(VBWord.class);
		wordQuery.setResultClass(VBWord.class);
		//wordQuery.setFilter("key != targetWordKey");
		//wordQuery.declareParameters("String targetWordKey");
		wordQuery.setRange(index, index+1);
		VBWord result = null;
		String resultMeaning = "";
		List<VBWord> wordList = null;
		try{
			//result = (VBWord) wordQuery.execute(targetWord.getKey());
			wordList = (List<VBWord>)wordQuery.execute(targetWord.getKey());
			System.out.println("list size : "+wordList.size());
			result = wordList.get(0);
			System.out.println("selected choice: "+result.getWordName());
			if(result.getWordName().equals(targetWord.getWordName()))
				return null;
			else
				resultMeaning = getRandomMeaning(result);
		} finally{
			//pm.close();
		}
		System.out.println("getVBWord: "+result);
		return resultMeaning;
	}
	
	public List<String> makeSelection(VBWord targetWord, int selectionSize, int answerIndex){
		int maxSize = wordDAO.getVBWordCount();
		Integer[] selectionQueue = makeRandQueue(selectionSize, maxSize-1);
		Vector<String> wordList = new Vector<String>();
		int queueIndex = 0;
		for(int i = 0 ; i < selectionSize ; i++)
		{
			if(i == answerIndex){
				String targetMeaning = getRandomMeaning(targetWord);
				System.out.println("answer : "+targetMeaning);
				wordList.add(targetMeaning);
			}else{
				System.out.println(selectionQueue[queueIndex]);
				String candidate = getVBWordRandomMeaningByIndexExceptTarget(selectionQueue[queueIndex], targetWord);
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
		
		return result;
	}
}