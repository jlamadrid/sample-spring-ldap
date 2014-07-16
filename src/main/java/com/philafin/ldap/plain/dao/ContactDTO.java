package com.philafin.ldap.plain.dao;

/**
 *
 */
public class ContactDTO {

    String mail;
    String department;
    String title;
    String distinguishedName;
    String cn;

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String toString() {

        StringBuffer contactDTOStr = new StringBuffer("Person=[");

        contactDTOStr.append(" mail = " + mail);
        contactDTOStr.append(" title = " + title);
        contactDTOStr.append(" department = " + department);
        contactDTOStr.append(" dn = " + distinguishedName);
        contactDTOStr.append(" cn = " + cn);
        contactDTOStr.append(" ]");

        return contactDTOStr.toString();
    }
}