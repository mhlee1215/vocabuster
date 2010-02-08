package org.wordbuster.dao;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.wordbuster.domain.VBUser;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class VBUserDAO extends SqlMapClientDaoSupport{
	
	@Resource(name="sqlMapClient")
	 protected void initDAO(SqlMapClient sqlMapClient) {        
		 this.setSqlMapClient(sqlMapClient);
	 }
	
	public VBUser retrieveUserByPassword(String userid, String password){
		return null;
	}
	
	public VBUser retrieveUser(String userid){
		return null;
	}
	
	public boolean updateUser(VBUser user){
		return false;
	}
	
	public boolean deleteUser(String userid){
		return false;
	}
	
	public boolean insertUser(VBUser user){
		return false;
	}
	
	
}
