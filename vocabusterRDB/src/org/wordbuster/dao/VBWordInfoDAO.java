package org.wordbuster.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.wordbuster.domain.User;
import org.wordbuster.domain.VBWordInfo;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class VBWordInfoDAO extends SqlMapClientDaoSupport{
	
	@Resource(name="sqlMapClient")
	 protected void initDAO(SqlMapClient sqlMapClient) {        
		 this.setSqlMapClient(sqlMapClient);
	 }
	
	public List<VBWordInfo> retrieveWordInfo(String wordName){
		VBWordInfo wordInfo = new VBWordInfo();
		wordInfo.setWordname(wordName);
		List<VBWordInfo> array = getSqlMapClientTemplate().queryForList("WordInfoSql.retrieveWordInfo", wordInfo);
		return array;
	}
	
	public boolean insertWordInfoList(VBWordInfo wordInfo){
		getSqlMapClientTemplate().insert("WordInfoSql.insertWordInfo", wordInfo);
		return true;
	}
	
	public boolean deleteWordInfoList(String wordName){
		VBWordInfo wordInfo = new VBWordInfo();
		wordInfo.setWordname(wordName);
		getSqlMapClientTemplate().delete("WordInfoSql.deleteWordInfo", wordInfo);
		return true;
	}
}
