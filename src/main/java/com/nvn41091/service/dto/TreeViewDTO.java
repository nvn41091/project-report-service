package com.nvn41091.service.dto;

public class TreeViewDTO {
    private String id;
    private Long parentId;
    private String name;
    private Boolean checked;

    public TreeViewDTO(String id, Long parentId, String name, Boolean checked) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
