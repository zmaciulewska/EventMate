package com.eventmate.dao;

import com.eventmate.entity.Contact;
import com.eventmate.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactDao extends AbstractDao<Contact> {

   Optional<Contact> findByFirstPersonAndSecondPerson(User firstPerson, User secondPerson);

    List<Contact> findByFirstPerson(User firstPerson);

    List<Contact> findBySecondPerson(User secondPerson);
}
