package com.philafin.ldap.console.legacy;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;
import java.util.logging.Logger;

/**
 *
 */
public abstract class LDAPBase {

    // Logger
    private static final Logger LOG = Logger.getLogger(LDAPBase.class.getName());

    public static final String CONTEXT_FACTORY_STRING = "com.sun.jndi.ldap.LdapCtxFactory";
    public static final String SECURITY_AUTHENTICATION_TYPE = "simple";
    public static final String SECURITY_PRINCIPAL_STRING = "icmgdev@icmg.com"; //"uid=admin,ou=system"
    public static final String SECURITY_CREDENTIALS_STRING = "Admin0714";
    public static final String LDAP_PROVIDER_URL = "ldap://icmg.com:389";
    public static final String LDAP_SEARCH_BASE = "dc=icmg,dc=com";

    protected LdapContext ldapContext = null;
    protected SearchControls searchControls;
    public static String[] returnAttributes = {"sAMAccountName", "givenName", "cn", "mail"};

    public LDAPBase(){
        //initializing search controls
        searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setReturningAttributes(returnAttributes);
    }

    protected void setReturnAttributes(String[] returnAttributes){
        this.returnAttributes = returnAttributes;
    }

    protected void setSearchControls(SearchControls searchControls){
        this.searchControls = searchControls;
    }
    /**
     * @return
     */
    protected LdapContext getLdapContext() {

        if (ldapContext == null) {

            try {

                Hashtable<String, String> env = new Hashtable<String, String>();
                env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY_STRING);
                env.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION_TYPE);
                env.put(Context.SECURITY_PRINCIPAL, SECURITY_PRINCIPAL_STRING);
                env.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS_STRING);
                env.put(Context.PROVIDER_URL, LDAP_PROVIDER_URL);

                /**
                 * http://docs.oracle.com/javase/jndi/tutorial/ldap/referral/jndi.html
                 * http://stackoverflow.com/questions/14136091/ldap-search-is-very-slow
                 *
                 * this is set to eliminate exceptions
                 */
                env.put(Context.REFERRAL, "follow");

                //ensures that objectSID attribute values will be returned as a byte[] instead of a String
                env.put("java.naming.ldap.attributes.binary", "objectSID");

                //the following is helpful in debugging errors
                //env.put("com.sun.jndi.ldap.trace.ber", System.err);

                ldapContext = new InitialLdapContext(env, null);

                System.out.println("Connection Successful.");

            } catch (NamingException nex) {
                System.out.println("LDAP Connection: FAILED");
                nex.printStackTrace();
            }

        }

        return ldapContext;

    }


    /**
     * closes the LDAP connection with Domain controller
     */
    public void closeLdapConnection() {
        try {
            if (ldapContext != null)
                ldapContext.close();
        } catch (NamingException e) {
            LOG.severe(e.getMessage());
        }
    }
}

