package com.eventmate.serviceImpl;

import com.eventmate.dao.ContactDao;
import com.eventmate.dao.UserDao;
import com.eventmate.dto.ContactDto;
import com.eventmate.dto.UserDto;
import com.eventmate.dto.form.ContactFormDto;
import com.eventmate.entity.Contact;
import com.eventmate.entity.User;
import com.eventmate.error.AppException;
import com.eventmate.error.Error;
import com.eventmate.mapper.ContactMapper;
import com.eventmate.service.ContactService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContactServiceImpl extends AbstractServiceImpl<ContactDto, Contact> implements ContactService {

    private final ContactMapper contactMapper;
    private final ContactDao contactDao;
    // private final UserService userService;
    private final UserDao userDao;

    public ContactServiceImpl(ContactMapper contactMapper, ContactDao contactDao, UserDao userDao) {
        this.contactMapper = contactMapper;
        this.contactDao = contactDao;
        //     this.userService = userService;
        this.userDao = userDao;
    }

    @Override
    public JpaRepository<Contact, Long> getRepository() {
        return contactDao;
    }

    @Override
    public ContactDto convert(Contact entity) {
        return contactMapper.convert(entity);
    }

    @Override
    public Contact convert(ContactDto dto) {
        return contactMapper.convert(dto);
    }

    @Override
    public List<ContactDto> entitiesToDtos(List<Contact> entities) {
        return entities.stream().map(e -> contactMapper.convert(e)).collect(Collectors.toList());
    }

    @Override
    public List<Contact> dtosToEntities(List<ContactDto> dtos) {
        return dtos.stream().map(e -> contactMapper.convert(e)).collect(Collectors.toList());
    }

    @Override
    public ContactDto createOrRetrieveExisting(ContactFormDto contactForm) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto) auth.getPrincipal();

        if (contactForm.getSecondUserId().equals(principal.getId())) {
            throw new AppException(Error.IDENTICAL_USER_CONTACT);
        }


        User firstUser = userDao.findById(principal.getId()).orElseThrow(() -> new AppException(Error.USER_NOT_EXISTS));
        User secondUser = userDao.findById(contactForm.getSecondUserId()).orElseThrow(() -> new AppException(Error.USER_NOT_EXISTS));

        Optional<Contact> existingContact;
        existingContact = contactDao.findByFirstPersonAndSecondPersonAndRemovalDateNull(firstUser, secondUser);
        if (existingContact.isPresent()) return contactMapper.convert(existingContact.get());

        existingContact = contactDao.findByFirstPersonAndSecondPersonAndRemovalDateNull(secondUser, firstUser);
        if (existingContact.isPresent()) return contactMapper.convert(existingContact.get());

        Contact contact = new Contact();
        contact.setFirstPerson(firstUser);
        contact.setSecondPerson(secondUser);
        contact.setCreationDate(LocalDateTime.now());

        return contactMapper.convert(contactDao.save(contact));

        // todo
        /*
        if contact exists to wyślij powiadominienie że uzytkownik ktorego znasz odpowiedział na twoja oferte

        else nowy kontakt
         */
    }

    @Override
    public List<ContactDto> getUserContacts(User user) {
        List<Contact> contacts = Stream.concat(contactDao.findByFirstPersonAndRemovalDateNull(user).stream(),
                contactDao.findBySecondPersonAndRemovalDateNull(user).stream()).collect(Collectors.toList());
        return entitiesToDtos(contacts);
    }

    @Override
    public void delete(Long id) {
        Contact contact = findById(id);
        contact.setRemovalDate(LocalDateTime.now());
        contactDao.save(contact);
    }

    @Override
    public ContactDto getOne(Long id) {
        return convert(findById(id));
    }

    private Contact findById(Long id) {
        Contact contact = contactDao.getOne(id);
        if (contact.getRemovalDate() != null) throw new AppException(Error.CONTACT_REMOVED);
        return contact;
    }
}
