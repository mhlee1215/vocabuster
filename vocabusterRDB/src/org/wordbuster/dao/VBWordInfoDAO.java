package org.wordbuster.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.wordbuster.domain.VBWordInfo;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class VBWordInfoDAO extends SqlMapClientDaoSupport{
	
	@Resource(name="sqlMapClient")
	 protected void initDAO(SqlMapClient sqlMapClient) {        
		 this.setSqlMapClient(sqlMapClient);
	 }
	
	public List<VBWordInfo> retrieveWordInfo(String wordName){
		return null;
	}
	
	public boolean insertWordInfoList(VBWordInfo wordInfo){
		return true;
	}
	
	public boolean deleteWordInfoList(String wordName){
		return true;
	}
	
	
}
