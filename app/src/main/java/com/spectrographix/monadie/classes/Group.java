package com.spectrographix.monadie.classes;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable {

    private String groupId;
    private String groupName;
    private String groupAdmin;
    private String groupMembers;

    private boolean isSelected;

    public Group(String groupId, String groupName, String groupAdmin, String groupMembers)
    {
        this.groupId =groupId;
        this.groupName =groupName;
        this.groupAdmin =groupAdmin;
        this.groupMembers =groupMembers;
    }

    public Group(){

    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(String groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}