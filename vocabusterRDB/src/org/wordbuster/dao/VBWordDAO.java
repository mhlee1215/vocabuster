package org.wordbuster.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordInfo;
import org.wordbuster.domain.VBWordMap;

@Repository
public class VBWordDAO extends SqlMapClientDaoSupport {
	
	public int getVBWordCount(){
		return 0;
	}
	
	public int getVBUserWordCount(){
		return 0;
	}
	
	public VBWord retrieveWord(String wordName){
		return null;
	}
	
	public List<VBWord> searchWord(String keyword){
		return null;
	}
	
	public boolean insertWord(VBWord vBWord){
		return false;
	}
	
	public boolean updateWord(VBWord vBWord){
		return false;
	}
	
	public boolean deleteWord(String wordName){
		return false;
	}
	
	
	

	
	 
	
	
	
	
	
}

