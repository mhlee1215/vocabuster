package org.wordbuster.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.wordbuster.domain.VBMyWordSearchVO;
import org.wordbuster.domain.VBWordMap;

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
	
	public VBWordMap retrieveWordMap(String userid, String wordName){
		VBWordMap param = new VBWordMap();
		param.setUserid(userid);
		param.setWordName(wordName);
		VBWordMap result = (VBWordMap)getSqlMapClientTemplate().queryForObject("WordMapSql.retrieveWordMap", param);
		return result;
	}
	
	public List<VBWordMap> retrieveMyWordMap(VBMyWordSearchVO searchVO){
		List<VBWordMap> result = (List<VBWordMap>)getSqlMapClientTemplate().queryForList("WordMapSql.retrieveUserWordMapList", searchVO);
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
		getSqlMapClientTemplate().update("WordMapSql.updateWordMap", wordMap);
		return true;
	}
	
	
	/**
	 * 해당 유저의 모든 wordmap 정보를 지움 
	 * @param userid
	 * @return
	 */
	public boolean deleteUserWordMap(String userid){
		VBWordMap wordMap = new VBWordMap();
		wordMap.setUserid(userid);
		getSqlMapClientTemplate().update("WordMapSql.updateWordMap", wordMap);
		return true;
	}
}
