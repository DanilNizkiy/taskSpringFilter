package example.service;

import example.model.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactService {


    List<Contact> getContacts(int page, int pageSize);

    int getDatabaseLength();

    List<Contact> getAll();

    void seve(Contact contact);

    void flush();

}
