package org.wordbuster.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.wordbuster.domain.VBMyWordSearchVO;
import org.wordbuster.domain.VBWordMap;
import org.wordbuster.domain.VBWordQuizVO;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class VBWordMapDAO extends SqlMapClientDaoSupport{
	
	@Resource(name="sqlMapClient")
	 protected void initDAO(SqlMapClient sqlMapClient) {        
		 this.setSqlMapClient(sqlMapClient);
	 }
	
	public Integer getVBUserWordCount(VBWordMap wordMap){
		int result = (Integer)getSqlMapClientTemplate().queryForObject("WordMapSql.retrieveWordMapCount", wordMap);
		return result;
	}
	
	public List<VBWordMap> retrieveUserWordMap(String userid){
		return null;
	}
	
	public VBWordMap retrieveWordMap(String userid, String wordName, String category){
		VBWordMap param = new VBWordMap();
		param.setUserid(userid);
		param.setWordName(wordName);
		param.setCategoryid(category);
		List<VBWordMap> result = (List<VBWordMap>)getSqlMapClientTemplate().queryForList("WordMapSql.retrieveWordMap", param);
		if(result.size() > 0)
			return result.get(0);
		else
			return null;
	}
	
	public List<VBWordMap> retrieveMyWordMap(VBMyWordSearchVO searchVO){
		List<VBWordMap> result = (List<VBWordMap>)getSqlMapClientTemplate().queryForList("WordMapSql.retrieveUserWordMapList", searchVO);
		return result;
	}
	
	public Integer retrieveMyWordMapCount(VBMyWordSearchVO searchVO){
		int result = (Integer)getSqlMapClientTemplate().queryForObject("WordMapSql.retrieveUserWordMapListCount", searchVO);
		return result;
	}
	
	public boolean insertWordMap(VBWordMap wordMap){
		getSqlMapClientTemplate().insert("WordMapSql.insertWordMap", wordMap);
		return true;
	}
	
	public boolean updateWordMap(VBWordMap wordMap){
		getSqlMapClientTemplate().update("WordMapSql.updateWordMap", wordMap);
		return false;
	}
	
	public boolean deleteWordMap(String userid, String wordName){
		VBWordMap wordMap = new VBWordMap();
		wordMap.setUserid(userid);
		wordMap.setWordName(wordName);
		getSqlMapClientTemplate().delete("WordMapSql.deleteWordMap", wordMap);
		return true;
	}
	
	public List<VBWordMap> retrieveQuestion(VBWordQuizVO quizVO){
		List<VBWordMap> result = (List<VBWordMap>)getSqlMapClientTemplate().queryForList("WordMapSql.retrieveQuestion", quizVO);
		return result;
	}
	
	public String retrieveLearningRate(VBWordQuizVO searchVO){
		String result = (String)getSqlMapClientTemplate().queryForObject("WordMapSql.retrieveLearningRate", searchVO);
		return result;
	}
	
}
