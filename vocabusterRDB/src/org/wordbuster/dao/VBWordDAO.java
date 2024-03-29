package org.wordbuster.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.wordbuster.domain.VBCategory;
import org.wordbuster.domain.VBCategorySearchVO;
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
	
	public List<VBWord> retriveWordAll(VBWordSearchVO searchVO){
		searchVO.setPageIndex(-1);
		searchVO.setPageOffset(-1);
		List<VBWord> result = (List<VBWord>)getSqlMapClientTemplate().queryForList("WordSql.searchWord", searchVO);
		return result;
	}	
	
	public List<VBWord> searchWord(VBWordSearchVO searchVO){
		List<VBWord> result = (List<VBWord>)getSqlMapClientTemplate().queryForList("WordSql.searchWord", searchVO);
		return result;
	}
	
	public Integer searchWordCount(VBWordSearchVO searchVO){
		int result = (Integer)getSqlMapClientTemplate().queryForObject("WordSql.searchWordCount", searchVO);
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
	
	
	public List<VBCategory> retrieveCategory(VBCategorySearchVO vo){
		List<VBCategory> result = (List<VBCategory>)getSqlMapClientTemplate().queryForList("WordSql.retrieveCategories", vo);
		return result;
	}
	
	public int updateCategory(VBCategorySearchVO vo){
		int result = getSqlMapClientTemplate().update("WordSql.updateCategories", vo);
		return result;
	}
	
	public int deleteCategory(VBCategorySearchVO vo){
		int result = getSqlMapClientTemplate().delete("WordSql.deleteCategories", vo);
		return result;
	}
	
	public Object insertCategory(VBCategorySearchVO vo){
		Object result = getSqlMapClientTemplate().insert("WordSql.insertCategories", vo);
		return result;
	}
	
	

	
	 
	
	
	
	
	
}

