package com.sabel.bilderrahmen.Admin.resources;

import com.sabel.bilderrahmen.Admin.Group;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Created by robin on 18.04.17.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupPool {

    @XmlElementWrapper(name = "Groups")
    @XmlElement(name = "Group")
    private  ArrayList<Group> groupArrayList = null;
    @XmlTransient
    private static GroupPool instance = null;

    private GroupPool() {
        if(groupArrayList==null)
        groupArrayList = new ArrayList<>();
    }

    public static GroupPool getInstance(){
        if (instance == null) {
            instance = new GroupPool();
        }
        return instance;
    }

    @XmlTransient
    public  ArrayList<Group> getGroupArrayList() {
        return groupArrayList;
    }

    public  void setGroupArrayList(ArrayList<Group> ArrayList) {
        groupArrayList = ArrayList;
    }

    public  Group getGroupByName(String name) {
        for (Group c : groupArrayList)
            if (c.getGroupName().equals(name))
                return c;
        return null;
    }

    public  ArrayList<Group> getGreoupByName(ArrayList<String> names) {
        ArrayList<Group> groups = new ArrayList<>();
        for (String name : names) {
            groups.add(getGroupByName(name));
        }
        return groups;
    }

    public  boolean addGroup(Group group) {
        if (groupArrayList.contains(group)) {
            System.err.println("Gruppe bereits in der Liste");
            return false;
        }
        groupArrayList.add(group);
        if (!groupArrayList.contains(group)) {
            System.err.println("Nicht hinzugefügt");
            return false;
        }
        return true;
    }

    public  boolean removeGroup(Group group) {
        if (!groupArrayList.contains(group)) {
            System.err.println("Gruppe nicht in der Liste");
            return false;
        }
        groupArrayList.remove(group);
        if (groupArrayList.contains(group)) {
            System.err.println("Client nicht gelöscht");
            return false;
        }
        return true;
    }
}
