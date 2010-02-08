package org.wordbuster.dao;

import java.util.List;

import org.wordbuster.domain.VBWordMap;

public class VBWordMapDAO {
	
	public Integer getVBUserWordCount(String email){
		return 0;
	}
	
	public List<VBWordMap> retrieveUserWordMap(String userid){
		return null;
	}
	
	public VBWordMap retrieveWordMap(String userid, String wordName){
		return null;
	}
	
	public boolean insertWordMap(VBWordMap wordMap){
		return false;
	}
	
	public boolean updateWordMap(VBWordMap wordMap){
		return false;
	}
	
	public boolean deleteWordMap(String userid, String wordName){
		return false;
	}
	
	public boolean deleteUserWordMap(String userid){
		return false;
	}
}
