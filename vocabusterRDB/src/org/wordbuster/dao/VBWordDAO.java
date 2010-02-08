package org.wordbuster.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordInfo;
import org.wordbuster.domain.VBWordMap;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class VBWordDAO extends SqlMapClientDaoSupport {
	
	@Resource(name="sqlMapClient")
	 protected void initDAO(SqlMapClient sqlMapClient) {        
		 this.setSqlMapClient(sqlMapClient);
	 }
	
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
		getSqlMapClientTemplate().insert("WordSql.insertWord", vBWord);
		return false;
	}
	
	public boolean updateWord(VBWord vBWord){
		return false;
	}
	
	public boolean deleteWord(String wordName){
		return false;
	}
	
	
	

	
	 
	
	
	
	
	
}

