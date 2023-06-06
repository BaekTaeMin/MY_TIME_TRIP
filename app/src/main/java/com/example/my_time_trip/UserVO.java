package com.example.my_time_trip;

public class UserVO {
    private int userNo;
    private String userID;
    private String userPW;

    public UserVO() {};

    public int getUserNo() { return userNo; }
    public void setUserNo(int userNo) { this.userNo = userNo; }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getUserPW() { return userPW; }
    public void setUserPW(String userPW) { this.userPW = userPW; }
}
