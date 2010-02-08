package org.wordbuster.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.wordbuster.domain.VBWordMap;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class VBWordMapDAO extends SqlMapClientDaoSupport{
	
	@Resource(name="sqlMapClient")
	 protected void initDAO(SqlMapClient sqlMapClient) {        
		 this.setSqlMapClient(sqlMapClient);
	 }
	
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
