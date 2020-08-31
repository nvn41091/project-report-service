package com.nvn41091.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
    private long id;
    private String userName;
    private String passwordHash;
    private String fullName;
    private String email;
    private String imageUrl;
    private int status;
    private String langKey;
    private String activationKey;
    private String resetKey;
    private String createdBy;
    private Timestamp createDate;
    private Timestamp resetDate;
    private String lastModifiedBy;
    private Timestamp lastModifiedDate;
    private String sessionLogin;
    private String ipLogin;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password_hash")
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Basic
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "lang_key")
    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    @Basic
    @Column(name = "activation_key")
    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    @Basic
    @Column(name = "reset_key")
    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    @Basic
    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "create_date")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "reset_date")
    public Timestamp getResetDate() {
        return resetDate;
    }

    public void setResetDate(Timestamp resetDate) {
        this.resetDate = resetDate;
    }

    @Basic
    @Column(name = "last_modified_by")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @Column(name = "last_modified_date")
    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Basic
    @Column(name = "session_login")
    public String getSessionLogin() {
        return sessionLogin;
    }

    public void setSessionLogin(String sessionLogin) {
        this.sessionLogin = sessionLogin;
    }

    @Basic
    @Column(name = "ip_login")
    public String getIpLogin() {
        return ipLogin;
    }

    public void setIpLogin(String ipLogin) {
        this.ipLogin = ipLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                status == user.status &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(passwordHash, user.passwordHash) &&
                Objects.equals(fullName, user.fullName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(imageUrl, user.imageUrl) &&
                Objects.equals(langKey, user.langKey) &&
                Objects.equals(activationKey, user.activationKey) &&
                Objects.equals(resetKey, user.resetKey) &&
                Objects.equals(createdBy, user.createdBy) &&
                Objects.equals(createDate, user.createDate) &&
                Objects.equals(resetDate, user.resetDate) &&
                Objects.equals(lastModifiedBy, user.lastModifiedBy) &&
                Objects.equals(lastModifiedDate, user.lastModifiedDate) &&
                Objects.equals(sessionLogin, user.sessionLogin) &&
                Objects.equals(ipLogin, user.ipLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, passwordHash, fullName, email, imageUrl, status, langKey, activationKey, resetKey, createdBy, createDate, resetDate, lastModifiedBy, lastModifiedDate, sessionLogin, ipLogin);
    }
}
