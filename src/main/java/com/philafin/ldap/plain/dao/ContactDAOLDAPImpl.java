package com.philafin.ldap.plain.dao;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.List;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 *
 */
public class ContactDAOLDAPImpl implements IContactDAO {

    private LdapTemplate ldapTemplate;

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    private class ContactAttributeMapper implements AttributesMapper {

        public Object mapFromAttributes(Attributes attributes) throws NamingException {
            ContactDTO contactDTO = new ContactDTO();

            Attribute mail = attributes.get("mail");
            if(mail != null)
                contactDTO.setMail((String)mail.get());

            return contactDTO;
        }

    }

    @Override
    public List getAllContactNames() {

        ldapTemplate.setIgnorePartialResultException(true);

        //return ldapTemplate.search("", "(objectClass=person)",
        return ldapTemplate.search(
                query().where("objectclass").is("person"),
                new AttributesMapper() {
                    public Object mapFromAttributes(Attributes attrs) throws NamingException {
                        Attribute dn = attrs.get("distinguishedname");
                        Attribute cn = attrs.get("cn");
                        ContactDTO contactDTO = new ContactDTO();
                        contactDTO.setDistinguishedName((String)dn.get());
                        contactDTO.setCn((String)cn.get());
                        return contactDTO;
                    }
            });
    }

    @Override
    public List getContactDetails(String objectclass) {

        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectClass",objectclass));

        ldapTemplate.setIgnorePartialResultException(true);

        System.out.println("LDAP Query " + andFilter.encode());

        return ldapTemplate.search("", andFilter.encode(),new ContactAttributeMapper());
    }
}
