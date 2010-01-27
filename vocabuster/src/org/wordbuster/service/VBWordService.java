package org.wordbuster.service;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;

import org.wordbuster.PMF;
import org.wordbuster.domain.VBUser;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordInfo;
import org.wordbuster.domain.VBWordMap;

import com.google.appengine.api.datastore.Key;

public class VBWordService {
	public static List<VBWordMap> syncWordMapWithWord(List<VBWordMap> wordMapList){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		for(int i = 0 ; i < wordMapList.size() ; i++){
			VBWord word = pm.getObjectById(VBWord.class, wordMapList.get(i).getWordKey());
			//VBWord detached = pm.detachCopy(word);
			
			wordMapList.get(i).setWord(word);
			//List<VBWordInfo> wordInfoList = (List<VBWordInfo>) pm.detachCopyAll(detached.getWordInfoList());
			//detached.setWordInfoList(wordInfoList);
			//wordMapList.get(i).setWord(detached);
		}
		return wordMapList;
	}
	
	public static VBWord getVBWord(Key wordKey){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		VBWord word = pm.getObjectById(VBWord.class, wordKey);
		return word;
	}
	
	public static Integer getVBWordCount(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query wordQuery = pm.newQuery(VBWord.class);
		wordQuery.setResult("count(this)");
		Integer wordQueryResult = null;
		try {
			wordQueryResult = (Integer) wordQuery.execute();
		} finally {
			wordQuery.closeAll();
		}
		return wordQueryResult;
	}
	
	public static Integer getVBUserWordCount(HttpServletRequest req){
		VBUser vbuser = VBUserService.getVBUser(req);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query wordQuery = pm.newQuery(VBWordMap.class);
		wordQuery.setResult("count(this)");
		wordQuery.setFilter("userKey == searchUserKey");
		wordQuery.declareParameters("String searchUserKey");
		Integer wordQueryResult = null;
		try {
			wordQueryResult = (Integer) wordQuery.execute(vbuser.getKey());
		} finally {
			wordQuery.closeAll();
		}
		return wordQueryResult;
	}
	
	public static String getRandomMeaning(VBWord word){
		Random oRandom = new Random();
		int meaningIndex = oRandom.nextInt(word.getWordInfoList().size());
		return word.getWordInfoList().get(meaningIndex).getShortMeaning();
	}
	
	public static String getVBWordRandomMeaningByIndexExceptTarget(int index, VBWord targetWord){
		System.out.println("index : "+index);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query wordQuery = pm.newQuery(VBWord.class);
		wordQuery.setResultClass(VBWord.class);
		wordQuery.setFilter("key != targetWordKey");
		wordQuery.declareParameters("String targetWordKey");
		//wordQuery.setRange(index, index+2);
		VBWord result = null;
		String resultMeaning = "";
		List<VBWord> wordList = null;
		try{
			//result = (VBWord) wordQuery.execute(targetWord.getKey());
			wordList = (List<VBWord>)wordQuery.execute(targetWord.getKey());
			System.out.println("list size : "+wordList.size());
			result = wordList.get(index);
			System.out.println("selected choice: "+result.getWordName());
			resultMeaning = getRandomMeaning(result);
		} finally{
			
		}
		System.out.println("getVBWord: "+result);
		return resultMeaning;
	}
	
	public static List<String> makeSelection(VBWord targetWord, int selectionSize, int answerIndex){
		int maxSize = VBWordService.getVBWordCount();
		Integer[] selectionQueue = makeRandQueue(selectionSize-1, maxSize-1);
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
				wordList.add(candidate);
				queueIndex++;	
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
	
	public static void main(String[] argv){
		Integer test = VBWordService.getVBWordCount();
		System.out.println(test);
		
	}
	
	
}















