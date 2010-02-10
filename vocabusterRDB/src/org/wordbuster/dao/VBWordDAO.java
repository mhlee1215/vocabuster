package org.wordbuster.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordInfo;
import org.wordbuster.domain.VBWordMap;
import org.wordbuster.domain.VBWordSearchVO;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class VBWordDAO extends SqlMapClientDaoSupport {
	
	@Resource(name="sqlMapClient")
	 protected void initDAO(SqlMapClient sqlMapClient) {        
		 this.setSqlMapClient(sqlMapClient);
	 }
	
	public int getVBWordCount(){
		int result = (Integer)getSqlMapClientTemplate().queryForObject("WordSql.retrieveWordCountAll");
		return result;
	}
	
	public VBWord retrieveWord(String wordName){
		VBWord result = (VBWord)getSqlMapClientTemplate().queryForObject("WordSql.findWord", wordName);
		return result;
	}
	
	public List<VBWord> searchWord(VBWordSearchVO searchVO){
		List<VBWord> result = (List<VBWord>)getSqlMapClientTemplate().queryForList("WordSql.searchWord", searchVO);
		return result;
	}
	
	public boolean insertWord(VBWord vBWord){
		getSqlMapClientTemplate().insert("WordSql.insertWord", vBWord);
		return false;
	}
	
	public boolean updateWord(VBWord vBWord){
		getSqlMapClientTemplate().update("WordSql.updateWord", vBWord);
		return false;
	}
	
	public boolean deleteWord(String wordName){
		VBWord word = new VBWord();
		word.setWordName(wordName);
		getSqlMapClientTemplate().delete("WordSql.deleteWord", word);
		return false;
	}
	
	
	

	
	 
	
	
	
	
	
}

