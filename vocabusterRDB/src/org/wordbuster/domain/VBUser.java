package org.wordbuster.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class VBUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5901428787783894579L;

	private String userid;
    
    /**
     * 최초 사용일
     */
    private Date firstUseDate;
    
    /**
     * 마지막 사용일
     */
    private Date latestUseDate;
    
	public VBUser(){
    	
    }
    
    public VBUser(String nickName){
    	
    }
    
    public VBUser(String userid, Date firstUseDate, Date latestUseDate){
    	this.userid = userid;
    	this.firstUseDate = firstUseDate;
    	this.latestUseDate = latestUseDate;
    }

    


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getFirstUseDate() {
		return firstUseDate;
	}

	public void setFirstUseDate(Date firstUseDate) {
		this.firstUseDate = firstUseDate;
	}

	public Date getLatestUseDate() {
		return latestUseDate;
	}

	public void setLatestUseDate(Date latestUseDate) {
		this.latestUseDate = latestUseDate;
	}
    
    

}
