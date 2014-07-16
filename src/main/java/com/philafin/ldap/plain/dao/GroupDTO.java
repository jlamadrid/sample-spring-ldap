package com.philafin.ldap.plain.dao;

/**
 *
 */
public class GroupDTO {

    String name;
    String description;
    String distinguishedName;
    String cn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String toString() {

        StringBuffer dtoString = new StringBuffer("Group=[");

        dtoString.append(" name = " + name);
        dtoString.append(" description = " + description);
        dtoString.append(" cn = " + cn);
        dtoString.append(" dn = " + distinguishedName);
        dtoString.append(" ]");

        return dtoString.toString();
    }
}