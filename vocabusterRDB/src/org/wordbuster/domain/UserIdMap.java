package org.wordbuster.domain;

public class UserIdMap {
	String externalId = "";
	int internalId = 0;
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public int getInternalId() {
		return internalId;
	}
	public void setInternalId(int internalId) {
		this.internalId = internalId;
	}
	
	@Override
	public String toString() {
		return "[UserIdMap START]\nexternalId\t:\t" + externalId + "\ninternalId\t:\t" + internalId
				+ "\n[UserIdMap END]";
	}
	
	
}
