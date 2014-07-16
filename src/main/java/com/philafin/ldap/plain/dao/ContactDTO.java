package com.philafin.ldap.plain.dao;

/**
 *
 */
public class ContactDTO {

    String mail;
    String sap;
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

    public String getSap() {
        return sap;
    }

    public void setSap(String sap) {
        this.sap = sap;
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
        contactDTOStr.append(" sap = " + sap);
        contactDTOStr.append(" dn = " + distinguishedName);
        contactDTOStr.append(" cd = " + cn);
        contactDTOStr.append(" ]");

        return contactDTOStr.toString();
    }
}