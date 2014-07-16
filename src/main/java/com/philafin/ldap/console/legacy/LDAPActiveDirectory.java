package com.philafin.ldap.console.legacy;

import java.util.logging.Logger;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

/**
 *
 */
public class LDAPActiveDirectory extends LDAPBase {

    // Logger
    private static final Logger LOG = Logger.getLogger(LDAPActiveDirectory.class.getName());

    private String baseFilter = "(&((&(objectCategory=Person)(objectClass=User)))";

    /**
     *
     */
    public LDAPActiveDirectory() {

    }

    /**
     * search the Active directory by username/email id for given search base
     */
    public NamingEnumeration<SearchResult> searchUser(String searchValue, String searchBy, String[] returnAttributes) throws NamingException {

        if(returnAttributes != null){
            searchControls.setReturningAttributes(returnAttributes);
        }else{
            searchControls.setReturningAttributes(null);
        }

        String filter = getFilter(searchValue, searchBy);

        return getLdapContext().search(LDAP_SEARCH_BASE, filter, this.searchControls);
    }

    /**
     * active directory filter string value
     */
    private String getFilter(String searchValue, String searchBy) {
        String filter = this.baseFilter;
        if (searchBy.equals("email")) {
            filter += "(mail=" + searchValue + "))";
        } else if (searchBy.equals("username")) {
            filter += "(samaccountname=" + searchValue + "))";
        }
        return filter;
    }

    public SearchResult findAccountByAccountName(String accountName, String[] returnAttributes) throws NamingException {

        if(returnAttributes != null){
            searchControls.setReturningAttributes(returnAttributes);
        }else{
            searchControls.setReturningAttributes(null);
        }

        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + accountName + "))";

        NamingEnumeration<SearchResult> results =
                getLdapContext().search(LDAP_SEARCH_BASE, searchFilter, searchControls);

        SearchResult searchResult = null;
        if (results.hasMoreElements()) {
            searchResult = (SearchResult) results.nextElement();

            //make sure there is not another item available, there should be only 1 match
            if (results.hasMoreElements()) {
                System.err.println("Matched multiple users for the accountName: " + accountName);
                return null;
            }
        }

        return searchResult;
    }

    public String findGroupBySID(String primaryGroupSID) throws NamingException {

        String searchFilter = "(&(objectClass=group)(objectSid=" + primaryGroupSID + "))";

        NamingEnumeration<SearchResult> results =
                getLdapContext().search(LDAP_SEARCH_BASE, searchFilter, searchControls);

        if (results.hasMoreElements()) {
            SearchResult searchResult = (SearchResult) results.nextElement();

            //make sure there is not another item available, there should be only 1 match
            if (results.hasMoreElements()) {
                System.err.println("Matched multiple groups for the group with SID: " + primaryGroupSID);
                return null;
            } else {
                return (String) searchResult.getAttributes().get("sAMAccountName").get();
            }
        }
        return null;
    }

    public void getUserBasicAttributes(String username, String[] returnAttributes ) throws NamingException {


        if(returnAttributes != null){
            searchControls.setReturningAttributes(returnAttributes);
        }

        String searchFilter = "sAMAccountName=" + username;

        NamingEnumeration answer =
                getLdapContext().search(LDAP_SEARCH_BASE, searchFilter, searchControls);

        if (answer.hasMore()) {
            Attributes attrs = ((SearchResult) answer.next()).getAttributes();
            System.out.println("distinguishedName " + attrs.get("distinguishedName"));
            System.out.println("givenname " + attrs.get("givenname"));
            System.out.println("sn " + attrs.get("sn"));
            System.out.println("mail " + attrs.get("mail"));
            System.out.println("telephonenumber " + attrs.get("telephonenumber"));
            System.out.println("department " + attrs.get("department"));
        } else {
            System.out.println("Invalid username");
        }
    }

    public void anotherSearch() throws NamingException {

        //Filters = "(objectclass=person)", "(objectclass=group)"
        NamingEnumeration results =
                getLdapContext().search(LDAP_SEARCH_BASE, "(&(objectclass=user)(objectcategory=person))", searchControls);

        while (results.hasMore()) {
            SearchResult searchResult = (SearchResult) results.next();
            Attributes attributes = searchResult.getAttributes();
            Attribute attr = attributes.get("cn");
            String cn = (String) attr.get();
            System.out.println(" CN Value = " + cn);
        }

    }
}
