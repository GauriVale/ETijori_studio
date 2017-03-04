package com.gsv.etijori;

public class AccountCredentials {
    private String mAccountName;
	private String mUserName;
	private String mPassword;

	public AccountCredentials() {

	}

	public AccountCredentials(String userName, String password) {
		this.mUserName = userName;
		this.mPassword = password;
	}

	public AccountCredentials(String userName, String password, String accountName) {
		this.mUserName = userName;
		this.mPassword = password;
		this.mAccountName = accountName;
	}

    public String getAccountName() {
        return mAccountName;
    }

    public void setAccountName(String accountName) {
        this.mAccountName = accountName;
    }

	public String getUserName() {
		return mUserName;
	}
	
	public void setUserName(String userName) {
		this.mUserName = userName;
	}
	
	public String getPassword() {
		return mPassword;
	}
	
	public void setPassword(String password) {
		mPassword = password;
	}

	@Override
	public String toString() {
		return "AccountCredentials{" +
				"mAccountName='" + mAccountName + '\'' +
				", mUserName='" + mUserName + '\'' +
				", mPassword='" + mPassword + '\'' +
				'}';
	}
}
