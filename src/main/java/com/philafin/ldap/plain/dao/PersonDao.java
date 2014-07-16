package com.philafin.ldap.plain.dao;

import com.philafin.ldap.plain.domain.Person;
import java.util.List;

/**
 * Data Access Object interface for the Person entity.
 */
public interface PersonDao {

   List<String> getAllPersonNames();

   List<Person> findAll();

   Person findByPrimaryKey(String country, String company, String fullname);
}
