package com.nvn41091.service.dto;

import com.nvn41091.domain.Module;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class UserDTO implements Serializable {
    private Long id;
    private String userName;
    private String passwordHash;
    private String fullName;
    private String email;
    private String imageUrl;
    private Boolean status;
    private String langKey;
    private String activationKey;
    private String resetKey;
    private String createdBy;
    private Timestamp createDate;
    private Timestamp resetDate;
    private String lastModifiedBy;
    private Timestamp lastModifiedDate;
    private String fingerprint;
    private String lstRole;
    private List<String> roles;
    private List<Module> menus;

    public UserDTO() {
    }

    public List<Module> getMenus() {
        return menus;
    }

    public void setMenus(List<Module> menus) {
        this.menus = menus;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getResetDate() {
        return resetDate;
    }

    public void setResetDate(Timestamp resetDate) {
        this.resetDate = resetDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getLstRole() {
        return lstRole;
    }

    public void setLstRole(String lstRole) {
        this.lstRole = lstRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO user = (UserDTO) o;
        return id.equals(user.id) &&
                status.equals(user.status) &&
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
                Objects.equals(fingerprint, user.fingerprint) &&
                Objects.equals(lstRole, user.lstRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, passwordHash, fullName, email, imageUrl, status, langKey, activationKey, resetKey, createdBy, createDate, resetDate, lastModifiedBy, lastModifiedDate, fingerprint, lstRole);
    }
}
