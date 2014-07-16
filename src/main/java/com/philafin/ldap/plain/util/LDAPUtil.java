package com.philafin.ldap.plain.util;

import com.philafin.ldap.plain.dao.ContactDTO;
import com.philafin.ldap.plain.dao.GroupDTO;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * Utility class that wraps Springs LdapTemplate and provides single location
 * for ldap related queries.
 * <p/>
 * https://www.google.com/support/enterprise/static/gapps/docs/admin/en/gads/admin/ldap.5.4.html
 * http://technet.microsoft.com/en-us/library/cc261947(v=office.12).aspx
 */
public class LDAPUtil {

    private LdapTemplate ldapTemplate;

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public List<ContactDTO> findByLastName(String lastName) {

        /*
        LdapQuery query = query()
                .base("dc=icmg,dc=com")
                .attributes("cn", "sn")
                .where("objectclass").is("person")
                .and("sn").is(lastName);
        */
        LdapQuery query = query()
                .where("objectclass").is("person").and("sn").is(lastName);

        return ldapTemplate.search(query,
                new AttributesMapper<ContactDTO>() {
                    public ContactDTO mapFromAttributes(Attributes attrs) throws NamingException {

                        Attribute dn = attrs.get("distinguishedname");
                        Attribute cn = attrs.get("cn");
                        Attribute email = attrs.get("mail");
                        Attribute department = attrs.get("department");
                        Attribute title = attrs.get("title");

                        ContactDTO contactDTO = new ContactDTO();
                        contactDTO.setDistinguishedName((String) dn.get());
                        contactDTO.setCn((String) cn.get());
                        contactDTO.setMail((String) email.get());
                        contactDTO.setTitle((String) title.get());
                        contactDTO.setDepartment((String) department.get());

                        return contactDTO;
                    }
                });
    }

    public List<ContactDTO> listAllUsers() {

        LdapQuery query = query()
                .where("objectclass").is("person");

        return ldapTemplate.search(query,
                new AttributesMapper<ContactDTO>() {
                    public ContactDTO mapFromAttributes(Attributes attrs) throws NamingException {

                        Attribute dn = attrs.get("distinguishedname");
                        Attribute cn = attrs.get("cn");
                        Attribute email = attrs.get("mail");
                        Attribute department = attrs.get("department");
                        Attribute title = attrs.get("title");

                        ContactDTO contactDTO = new ContactDTO();
                        contactDTO.setDistinguishedName((String) dn.get());
                        contactDTO.setCn((String) cn.get());

                        if (email != null)
                            contactDTO.setMail((String) email.get());

                        if (title != null)
                            contactDTO.setTitle((String) title.get());

                        if (department != null)
                            contactDTO.setDepartment((String) department.get());

                        return contactDTO;
                    }
                });
    }

    public List<ContactDTO> searchByDepartment(String department) {

        LdapQuery query = query()
                .where("objectclass").is("person").and("department").is(department);

        return ldapTemplate.search(query,
                new AttributesMapper<ContactDTO>() {
                    public ContactDTO mapFromAttributes(Attributes attrs) throws NamingException {

                        Attribute dn = attrs.get("distinguishedname");
                        Attribute cn = attrs.get("cn");
                        Attribute email = attrs.get("mail");
                        Attribute department = attrs.get("department");
                        Attribute title = attrs.get("title");

                        ContactDTO contactDTO = new ContactDTO();
                        contactDTO.setDistinguishedName((String) dn.get());
                        contactDTO.setCn((String) cn.get());

                        if (email != null)
                            contactDTO.setMail((String) email.get());

                        if (title != null)
                            contactDTO.setTitle((String) title.get());

                        if (department != null)
                            contactDTO.setDepartment((String) department.get());

                        return contactDTO;
                    }
                });
    }

    public List<GroupDTO> allGroups() {

        LdapQuery query = query()
                .where("objectclass").is("group");

        return ldapTemplate.search(query,
                new AttributesMapper<GroupDTO>() {
                    public GroupDTO mapFromAttributes(Attributes attrs) throws NamingException {

                        Attribute name = attrs.get("name");
                        Attribute cn = attrs.get("cn");
                        Attribute description = attrs.get("description");
                        Attribute dn = attrs.get("distinguishedname");

                        GroupDTO dto = new GroupDTO();

                        if (name != null)
                            dto.setName((String) name.get());

                        if (cn != null)
                            dto.setCn((String) attrs.get("cn").get());

                        if (dn != null)
                            dto.setDistinguishedName((String) attrs.get("distinguishedname").get());

                        if (description != null)
                            dto.setDescription((String) attrs.get("description").get());

                        return dto;
                    }
                });
    }

    public List<GroupDTO> allGroupsByDepartment(String department) {

        //OU=Groups,OU=Marketing,DC=icmg,DC=com
        LdapQuery query = query()
                .where("objectclass").is("group").and("OU").is(department).and("OU").is("Groups");

        return ldapTemplate.search(query,
                new AttributesMapper<GroupDTO>() {
                    public GroupDTO mapFromAttributes(Attributes attrs) throws NamingException {

                        Attribute name = attrs.get("name");
                        Attribute cn = attrs.get("cn");
                        Attribute description = attrs.get("description");
                        Attribute dn = attrs.get("distinguishedname");

                        GroupDTO dto = new GroupDTO();

                        if (name != null)
                            dto.setName((String) name.get());

                        if (cn != null)
                            dto.setCn((String) attrs.get("cn").get());

                        if (dn != null)
                            dto.setDistinguishedName((String) attrs.get("distinguishedname").get());

                        if (description != null)
                            dto.setDescription((String) attrs.get("description").get());

                        return dto;
                    }
                });
    }
}
