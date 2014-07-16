package com.philafin.ldap.console.legacy;

import java.io.IOException;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

/**
 * Sample program how to use ActiveDirectory class in Java
 */
public class ConsoleLDAPClient {

    private LDAPActiveDirectory ldapActiveDirectory;

    public ConsoleLDAPClient(){

        ldapActiveDirectory = new LDAPActiveDirectory();
    }

    public void searchBy(String searchTerm, String choice) throws NamingException {

        //Searching
        NamingEnumeration<SearchResult> result = ldapActiveDirectory.searchUser(searchTerm, choice, null);

        if(result.hasMore()) {
            SearchResult rs= (SearchResult)result.next();
            Attributes attrs = rs.getAttributes();

            String temp = attrs.get("samaccountname").toString();
            System.out.println("Username	: " + temp.substring(temp.indexOf(":")+1));
            temp = attrs.get("givenname").toString();
            System.out.println("Name         : " + temp.substring(temp.indexOf(":")+1));
            temp = attrs.get("mail").toString();
            System.out.println("Email ID	: " + temp.substring(temp.indexOf(":")+1));
            temp = attrs.get("cn").toString();
            System.out.println("Display Name : " + temp.substring(temp.indexOf(":")+1) + "\n\n");

        } else  {
            System.out.println("No search result found!");
        }
    }

    public void findAccountByName(String searchTerm) throws NamingException {

        //1) lookup the ldap account
        SearchResult srLdapUser =
                ldapActiveDirectory.findAccountByAccountName(searchTerm, null);

        //2) get the SID of the users primary group
        String primaryGroupSID = getPrimaryGroupSID(srLdapUser);

        //3) get the users Primary Group
        String primaryGroupName = ldapActiveDirectory.findGroupBySID(primaryGroupSID);

    }

    /**
     * Parse result set for primary group SID
     * @param srLdapUser
     * @return
     * @throws NamingException
     */
    public String getPrimaryGroupSID(SearchResult srLdapUser) throws NamingException {

        byte[] objectSID = (byte[])srLdapUser.getAttributes().get("objectSid").get();
        String strPrimaryGroupID = (String)srLdapUser.getAttributes().get("primaryGroupID").get();

        String strObjectSid = decodeSID(objectSID);

        return strObjectSid.substring(0, strObjectSid.lastIndexOf('-') + 1) + strPrimaryGroupID;
    }

    public void getUserAttributes(String username) throws NamingException {

        /**
         * return only these attributes
         */
        String[] returnAttributes = {
                "distinguishedName",
                "sn",
                "givenname",
                "mail",
                "telephonenumber",
                "department"
        };

        ldapActiveDirectory.getUserBasicAttributes(username, returnAttributes);
    }

    public void anotherSearch() throws NamingException {
        ldapActiveDirectory.anotherSearch();
    }

    /**
     * The binary data is in the form:
     * byte[0] - revision level
     * byte[1] - count of sub-authorities
     * byte[2-7] - 48 bit authority (big-endian)
     * and then count x 32 bit sub authorities (little-endian)
     *
     * The String value is: S-Revision-Authority-SubAuthority[n]...
     *
     * Based on code from here - http://forums.oracle.com/forums/thread.jspa?threadID=1155740&tstart=0
     */
    public String decodeSID(byte[] sid) {

        final StringBuilder strSid = new StringBuilder("S-");

        // get version
        final int revision = sid[0];
        strSid.append(Integer.toString(revision));

        //next byte is the count of sub-authorities
        final int countSubAuths = sid[1] & 0xFF;

        //get the authority
        long authority = 0;
        //String rid = "";
        for(int i = 2; i <= 7; i++) {
            authority |= ((long)sid[i]) << (8 * (5 - (i - 2)));
        }
        strSid.append("-");
        strSid.append(Long.toHexString(authority));

        //iterate all the sub-auths
        int offset = 8;
        int size = 4; //4 bytes for each sub auth
        for(int j = 0; j < countSubAuths; j++) {
            long subAuthority = 0;
            for(int k = 0; k < size; k++) {
                subAuthority |= (long)(sid[offset + k] & 0xFF) << (8 * k);
            }

            strSid.append("-");
            strSid.append(subAuthority);

            offset += size;
        }

        return strSid.toString();
    }

    private void closeLdapConnection(){
        ldapActiveDirectory.closeLdapConnection();
    }

	/**
	 * @param args
	 * @throws NamingException 
	 */
	public static void main(String[] args) throws NamingException, IOException {

		System.out.println("\n\nQuerying Active Directory Using Java");
		System.out.println("------------------------------------");

		String choice = "username";
		String searchTerm = "jl25292";
        
        ConsoleLDAPClient activeDirectoryConsoleClient = new ConsoleLDAPClient();

        activeDirectoryConsoleClient.searchBy(searchTerm, choice);

        activeDirectoryConsoleClient.getUserAttributes(searchTerm);

        activeDirectoryConsoleClient.findAccountByName(searchTerm);

        activeDirectoryConsoleClient.anotherSearch();

		//Closing LDAP Connection
        activeDirectoryConsoleClient.closeLdapConnection();
	}
}
