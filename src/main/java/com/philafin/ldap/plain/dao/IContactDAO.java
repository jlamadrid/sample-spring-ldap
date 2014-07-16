package com.philafin.ldap.plain.dao;

import java.util.List;

/**
 *
 */
public interface IContactDAO {

    public List getAllContactNames();

    public List getContactDetails(String commonName);
}
