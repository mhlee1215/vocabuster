package org.wordbuster.domain;

/**
 * 
 * @author sutting
 * 
 */

public class User {
	
	public static int	STATUS_NOT_FOUNDED			= 1;
	public static int	STATUS_FOUNDED				= 2;
	public static int	STATUS_SUCCESS_REGISTER		= 3;
	public static int	STATUS_SUCCESS_ACTIVATED	= 4;
	public static int	STATUS_SUCCESS_DELETED		= 5;
	public static int	STATUS_ALREADY_REGISTEREDED	= 6;
	public static int	STATUS_ALREADY_ACTIVATED	= 7;
	public static int	STATUS_ALREADY_DELETED		= 8;
	public static int	STATUS_SUCCESS_SEND_FINDPASSWORD = 9;
	public static int	STATUS_FAIL_SEND_FINDPASSWORD = 10;
	
	private String	id			= "";	// ID
	private int  internalid  = 0;   // ID
	private String	password	= "";	// PASSWORD
	private String	isactivated	= "";	// WHETHER THE ACCOUNT IS ACTIVATED OR NOT
	private String	isdeleted	= "";	// WHETHER THE ACCOUNT IS DELETEDED OR NOT
	private String registeredtime = "";
	private String deletedtime = "";
	private String activatedtime = "";
	

	public int getInternalid() {
        return internalid;
    }

    public void setInternalid(int internalid) {
        this.internalid = internalid;
    }

    public String getRegisteredtime() {
		return registeredtime;
	}

	public void setRegisteredtime(String registeredtime) {
		this.registeredtime = registeredtime;
	}

	public String getDeletedtime() {
		return deletedtime;
	}

	public void setDeletedtime(String deletedtime) {
		this.deletedtime = deletedtime;
	}

	public String getActivatedtime() {
		return activatedtime;
	}

	public void setActivatedtime(String activatedtime) {
		this.activatedtime = activatedtime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsactivated() {
		return isactivated;
	}

	public void setIsactivated(String isactivated) {
		this.isactivated = isactivated;
	}

	public String getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}

    @Override
    public String toString() {
        return "User [activatedtime=" + activatedtime + ", deletedtime="
                + deletedtime + ", id=" + id + ", internalid=" + internalid
                + ", isactivated=" + isactivated + ", isdeleted=" + isdeleted
                + ", password=" + password + ", registeredtime="
                + registeredtime + "]";
    }


	



}
