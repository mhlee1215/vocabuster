package org.wordbuster.domain;


public class VBUser{

	private String email;
	private String isdelete;
	private String password;
	private String status;
	private int seq;
	
	
    /**
     * 등록일
     */
    private String admindate;
    
    /**
     * 마지막 사용일
     */
    private String latestUseDate;
    
	public VBUser(){
    	
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getAdmindate() {
        return admindate;
    }

    public void setAdmindate(String admindate) {
        this.admindate = admindate;
    }

    public String getLatestUseDate() {
        return latestUseDate;
    }

    public void setLatestUseDate(String latestUseDate) {
        this.latestUseDate = latestUseDate;
    }
    
	

    

}
