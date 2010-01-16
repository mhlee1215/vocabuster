package org.wordbuster.domain;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.Date;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class VBUser {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
    @Persistent
	private User user;
    
    @Persistent
    private Date firstUseDate;
    
    @Persistent
    private Date latestUseDate;
    
    public VBUser(Key key, User user, Date firstUseDate, Date latestUseDate){
    	this.key = key;
    	this.user = user;
    	this.firstUseDate = firstUseDate;
    	this.latestUseDate = latestUseDate;
    }

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
