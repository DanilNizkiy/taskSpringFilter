package example.service.repository;

import example.model.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @SuppressWarnings("JpaQlInspection")
    @Query("select c from Contact c")
    List<Contact> getContacts(Pageable pageable);


    @Query(" SELECT COUNT(*) FROM Contact")
    int getDatabaseLength();


}
