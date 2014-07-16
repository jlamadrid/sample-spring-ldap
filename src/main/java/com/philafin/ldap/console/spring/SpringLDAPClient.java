package com.philafin.ldap.console.spring;

import com.philafin.ldap.plain.dao.ContactDAOLDAPImpl;
import com.philafin.ldap.plain.dao.ContactDTO;
import com.philafin.ldap.plain.dao.IContactDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 *
 */
public class SpringLDAPClient {

    public static void main(String[] args) {
        try {
            ApplicationContext context= new ClassPathXmlApplicationContext("applicationContext.xml");

            IContactDAO ldapContact = (ContactDAOLDAPImpl)context.getBean("ldapContact");

            //List contactList = ldapContact.getContactDetails("jl25292");

            List<ContactDTO> contactList =ldapContact.getAllContactNames();

            int count = 0;
            for( int i = 0 ; i < contactList.size(); i++){
                System.out.println((contactList.get(i)).toString());
                count++;
            }
            System.out.println("\n" + count);

        } catch (DataAccessException e) {
            System.out.println("Error occured " + e.getCause());
        }
    }
}
