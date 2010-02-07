package org.wordbuster.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordMap;

@Repository
public class VBWordDAO extends SqlMapClientDaoSupport {
	VBWord retrieveWord(String wordName){
		return null;
	}
	
	boolean insertWord(VBWord vBWord){
		return false;
	}
	
	boolean updateWord(VBWord vBWord){
		return false;
	}
	
	boolean deleteWord(String wordName){
		return false;
	}
	
	List<VBWordMap> retrieveUserWordMap(String userid){
		return null;
	}
	
	VBWordMap retrieveWordMap(String userid, String wordName){
		return null;
	}
	
	boolean insertWordMap(VBWordMap wordMap){
		return false;
	}
	
	boolean updateWordMap(VBWordMap wordMap){
		return false;
	}
	
	boolean deleteWordMap(String userid, String wordName){
		return false;
	}
	
	boolean deleteUserWordMap(String userid){
		return false;
	}
	
	
	
	
}

