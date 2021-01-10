package com.eventmate.dao;

import com.eventmate.entity.Contact;
import com.eventmate.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactDao extends AbstractDao<Contact> {
    Contact findByIdAndRemovalDateNull(Long id);

   Optional<Contact> findByFirstPersonAndSecondPersonAndRemovalDateNull(User firstPerson, User secondPerson);

    List<Contact> findByFirstPersonAndRemovalDateNull(User firstPerson);

    List<Contact> findBySecondPersonAndRemovalDateNull(User secondPerson);
}
