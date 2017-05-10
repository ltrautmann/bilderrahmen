package com.sabel.bilderrahmen.Admin;

import com.sabel.bilderrahmen.Admin.interfaces.Got_Pictures;
import com.sabel.bilderrahmen.Admin.resources.GroupPool;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by robin on 18.04.17.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Client implements Got_Pictures {

    @XmlElement
    private String name;
    @XmlElement
    private String mac;
    @XmlElementWrapper(name = "Groups")
    @XmlElement(name = "Group")
    private ArrayList<String> groups;
    @XmlElementWrapper(name = "IgnoredPictures")
    @XmlElement(name = "Pictures")
    private ArrayList<Picture_Properties> ignoredPictures;
    @XmlElementWrapper(name = "SpecialPictures")
    @XmlElement(name = "Pictures")
    private ArrayList<Picture_Properties> privatePictures;

    public Client(String name, String mac) {
        this.name = name;
        this.mac = mac;

        groups = new ArrayList<String>();
        ignoredPictures = new ArrayList<Picture_Properties>();
        privatePictures = new ArrayList<Picture_Properties>();
    }

    private Client() {
        ignoredPictures = new ArrayList<Picture_Properties>();
        privatePictures = new ArrayList<Picture_Properties>();

    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }

    public ArrayList<Picture_Properties> getIgnoredPictures() {
        return ignoredPictures;
    }

    public void setIgnoredPictures(ArrayList<Picture_Properties> ignoredPictures) {
        this.ignoredPictures = ignoredPictures;
    }

    public ArrayList<Picture_Properties> getPrivatePictures() {
        return privatePictures;
    }

    public void setPrivatePictures(ArrayList<Picture_Properties> privatePictures) {
        this.privatePictures = privatePictures;
    }

    @Override
    public Picture_Properties getPictureAt(int index) {
        return privatePictures.get(index);
    }


    @Override
    public int size() {
        return privatePictures.size();
    }


    public boolean addPrivatePicture(Picture_Properties privatePicture) {
        if (privatePictures.contains(privatePicture)) {
            return false;
        }
        privatePictures.add(new Picture_Properties(privatePicture.getPresentationTime(), privatePicture.getName()));
        return privatePictures.contains(privatePicture);
    }

    public boolean removePrivatePictureByName(String pictureName) {
        boolean deleteSuccedet = false;
        Iterator<Picture_Properties> iterator = privatePictures.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(pictureName)) {
                iterator.remove();
                deleteSuccedet = true;
            }
        }
        return deleteSuccedet;
    }

    public boolean addGroup(Group group) {
        if (groups.contains(group.getGroupName())) {
            return false;
        }
        groups.add(group.getGroupName());
        return groups.contains(group.getGroupName());
    }

    public boolean removeGroup(String groupname) {
        boolean removed = false;
        Iterator<String> iterator = groups.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(groupname)) {
                iterator.remove();
                removed = true;
            }
        }
        return removed;
    }

    public boolean removeIgnoredPicture(Picture_Properties pictureName) {
        if (!ignoredPictures.contains(pictureName)) {
            System.out.println(pictureName);
            return false;
        }
        ignoredPictures.remove(pictureName);
        if (ignoredPictures.contains(pictureName)) {
            System.err.println("Bild noch immer ignoriert");
            return false;
        }
        return true;
    }

    public boolean addIgnoredPicture(Picture_Properties pictureName) {
        if (ignoredPictures.contains(pictureName)) {
            System.err.println("Bild wird bereits ignoriert");
            return false;
        }
        ignoredPictures.add(pictureName);
        if (!ignoredPictures.contains(pictureName)) {
            System.err.println("Bild wird nicht ignoriert");
            return false;

        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addIgnoredPicture(List selectedValuesList) {
        for (Object o : selectedValuesList) {
            if (o instanceof Picture_Properties) {
                addIgnoredPicture((Picture_Properties) o);
            }
        }


    }

    public void addPrivatePicture(List selectedValuesList) {
        for (Object o : selectedValuesList) {
            if (o instanceof Picture_Properties) {
                addPrivatePicture((Picture_Properties) o);
            }
        }


    }

    public void addGroup(List selectedValuesList) {
        for (Object o : selectedValuesList) {
            if (o instanceof Group) {
                addGroup((Group) o);
            } else {
                System.err.println("its not a group its a " + o);
            }
        }

    }

    public void removeIgnoredPicture(List selectedValuesList) {
        for (Object o : selectedValuesList) {
            if (o instanceof Picture_Properties) {
                removeIgnoredPicture((Picture_Properties) o);
            } else {
                System.err.println("its not a picture its a " + o);
            }
        }

    }

    public void removePrivatePictureByName(List selectedValuesList) {
        for (Object o : selectedValuesList) {
            if (o instanceof Picture_Properties) {
                removePrivatePictureByName(o.toString());
            }
        }

    }


    public void removeGroup(List selectedValuesList) {
        for (Object o : selectedValuesList) {
            if (o instanceof Group || o instanceof String) {
                removeGroup((String) o);
            }
            else
            System.err.println("Its not a group its a " + o);
        }

    }

    public ArrayList<Picture_Properties> getShownPictures() {
        ArrayList<Picture_Properties> allPics = new ArrayList<>();

        ArrayList<Group> groupByName = GroupPool.getInstance().getGroupByName(getGroups());
        for (Group group : groupByName) {
            for (Picture_Properties p : group.getPictureList()) {
                if (!allPics.contains(p)) {
                    allPics.add(p);
                }
            }
        }
        for (Picture_Properties iPic : getIgnoredPictures()) {
            allPics.remove(iPic);
        }

        for (Picture_Properties p : getPrivatePictures()) {
            if (allPics.contains(p)) {
                allPics.remove(p);
            }
            allPics.add(p);
        }

        return allPics;
    }

}
