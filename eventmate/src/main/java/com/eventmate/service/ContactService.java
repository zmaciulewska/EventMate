package com.eventmate.service;

import com.eventmate.dto.ContactDto;
import com.eventmate.dto.form.ContactFormDto;
import com.eventmate.entity.Contact;
import com.eventmate.entity.User;

import java.util.List;

public interface ContactService extends AbstractService<ContactDto> {

    ContactDto createOrRetrieveExisting(ContactFormDto contactForm);

    List<ContactDto> getUserContacts(User user);
}
