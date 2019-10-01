package example.service;


import example.model.Contact;
import example.service.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository repository;


    @Transactional
    public List<Contact> getContacts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
       return repository.getContacts(pageable);
    }

    @Override
    public int getDatabaseLength() {
        return repository.getDatabaseLength();
    }

    public List<Contact> getAll() {
        return repository.findAll();
    }

    @Override
    public void seve(Contact contact) {
        repository.save(contact);
    }

    @Override
    public void flush() {
        repository.flush();
    }

}






