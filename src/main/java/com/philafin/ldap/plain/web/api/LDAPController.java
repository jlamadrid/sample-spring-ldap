package com.philafin.ldap.plain.web.api;

import com.philafin.ldap.plain.dao.ContactDTO;
import com.philafin.ldap.plain.dao.GroupDTO;
import com.philafin.ldap.plain.util.LDAPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *
 */
@Controller
public class LDAPController {

    @Autowired
    private LDAPUtil ldapUtil;

    @RequestMapping(method = GET, value = "/api/ldap/bylastname/{lastName}")
    public @ResponseBody List<ContactDTO> searchByLastName(@PathVariable String lastName) {
        List<ContactDTO> contacts = ldapUtil.findByLastName(lastName);
        return contacts;
    }

    @RequestMapping(method = GET, value = "/api/ldap/bydepartment/{department}")
    public @ResponseBody List<ContactDTO> searchByDepartment(@PathVariable String department) {
        List<ContactDTO> contacts = ldapUtil.searchByDepartment(department);
        return contacts;
    }

    @RequestMapping(method = GET, value = "/api/ldap/allusers")
    public @ResponseBody List<ContactDTO> listAllUsers() {
        List<ContactDTO> contacts = ldapUtil.listAllUsers();
        return contacts;
    }

    @RequestMapping(method = GET, value = "/api/ldap/allgroups")
    public @ResponseBody List<GroupDTO> listAllGroups() {
        List<GroupDTO> groups = ldapUtil.allGroups();
        return groups;
    }

    @RequestMapping(method = GET, value = "/api/ldap/groupsbydepartment/{department}")
    public @ResponseBody List<GroupDTO> searchGroupsByDepartment(@PathVariable String department) {
        List<GroupDTO> groups = ldapUtil.allGroupsByDepartment(department);
        return groups;
    }
}
