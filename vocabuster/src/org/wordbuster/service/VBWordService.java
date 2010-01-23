package org.wordbuster.service;

import java.util.List;

import javax.jdo.PersistenceManager;

import org.wordbuster.PMF;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordMap;

public class VBWordService {
	public static List<VBWordMap> syncWordMapWithWord(List<VBWordMap> wordMapList){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		for(int i = 0 ; i < wordMapList.size() ; i++){
			VBWord word = pm.getObjectById(VBWord.class, wordMapList.get(i).getWordKey());
			//VBWord detached = pm.detachCopy(word);
			wordMapList.get(i).setWord(word);
		}
		return wordMapList;
	}
}
