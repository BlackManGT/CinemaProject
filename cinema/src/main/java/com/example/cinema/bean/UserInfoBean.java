package com.example.cinema.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class UserInfoBean{
    @Id(autoincrement = true)
    private long sid;
    private long birthday;
    private int id;
    private long lastLoginTime;
    private String nickName;
    private String phone;
    private int sex;
    private String headPic;
    private String emails;
    private String sessionId;
    private String userId;
    private String pwd;
    @Generated(hash = 1266438379)
    public UserInfoBean(long sid, long birthday, int id, long lastLoginTime,
            String nickName, String phone, int sex, String headPic, String emails,
            String sessionId, String userId, String pwd) {
        this.sid = sid;
        this.birthday = birthday;
        this.id = id;
        this.lastLoginTime = lastLoginTime;
        this.nickName = nickName;
        this.phone = phone;
        this.sex = sex;
        this.headPic = headPic;
        this.emails = emails;
        this.sessionId = sessionId;
        this.userId = userId;
        this.pwd = pwd;
    }
    @Generated(hash = 1818808915)
    public UserInfoBean() {
    }
    public long getSid() {
        return this.sid;
    }
    public void setSid(long sid) {
        this.sid = sid;
    }
    public long getBirthday() {
        return this.birthday;
    }
    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getLastLoginTime() {
        return this.lastLoginTime;
    }
    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getSex() {
        return this.sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getHeadPic() {
        return this.headPic;
    }
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
    public String getEmails() {
        return this.emails;
    }
    public void setEmails(String emails) {
        this.emails = emails;
    }
    public String getSessionId() {
        return this.sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
