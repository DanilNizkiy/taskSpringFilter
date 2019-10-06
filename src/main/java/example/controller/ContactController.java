package example.controller;

import example.model.Error;
import example.model.Contact;
import example.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/hello")
public class ContactController {

    @Autowired
    private ContactService contactService ;

    private int pageSizeForDatabase = 30;

    @RequestMapping(value = "/contacts", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Model> getFilteredData(@RequestParam(value = "nameFilter") String nameFilter,
                               @RequestParam(value = "responseSize")Integer responseSize,
                               final Model model) {

        List<Contact> contactsList = filterContacts(nameFilter,responseSize);
       return checkAnswer(contactsList,model);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Model>  getAll(@RequestParam(value = "page")Integer page,
                                               @RequestParam(value = "pageSize")Integer pageSize,
                                               final Model model) {
        List<Contact> contactsList = contactService.getContacts(page,pageSize);
        return checkAnswer(contactsList,model);
    }

    @RequestMapping(value = "/getDatabaseLength", method = RequestMethod.GET, produces = "application/json")
    public String  getDatabaseLength() {
        return "The number of rows in the database = " + contactService.getDatabaseLength() + "\n";
    }


    private ResponseEntity<Model> checkAnswer(List<Contact> contactsList, Model model){
        if (contactsList == null || contactsList.size() == 0) {
            Error error = new Error(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute(error);
            return new ResponseEntity<Model>(model, HttpStatus.NOT_FOUND);
        }
        model.addAttribute("contacts", contactsList);
        return new ResponseEntity<Model>(model, HttpStatus.OK);
    }


    private List<Contact> filterContacts(String nameFilter, int responseSize) {

        List<Contact> contactsList = new ArrayList<>();
        Pattern pattern = Pattern.compile(nameFilter);

        int databaseLength = contactService.getDatabaseLength();
        int numberOfDatabaseQueries = databaseLength / pageSizeForDatabase;

        if (databaseLength % pageSizeForDatabase != 0)
            numberOfDatabaseQueries++;

        for (int i = 0; i < numberOfDatabaseQueries; i++) {

            List<Contact> temporaryContactsList = contactService.getContacts(i,pageSizeForDatabase);

            for (Contact c : temporaryContactsList) {
                Matcher matcher = pattern.matcher(c.getName());
                if (!matcher.matches())
                    if (contactsList.size() < responseSize)
                    contactsList.add(c);
                    else
                        return contactsList;
            }
        }

        return contactsList;

    }

   // загрузить базу с txt документа
    @RequestMapping(value = "/addDbContacts", method = RequestMethod.GET)
    public String addContactsTxt() throws IOException {

        FileInputStream fileInputStream = new FileInputStream("/opt/database_loading.txt");
        StringBuilder stringBuilder = new StringBuilder();
        byte[] buffer2 = new byte[1024];
        while (fileInputStream.read(buffer2) != -1){
            String h = new String(buffer2, StandardCharsets.UTF_8);
            stringBuilder.append(h);
        }
        Pattern pattern =
                Pattern.compile("\\w+", Pattern.UNICODE_CHARACTER_CLASS
                        | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(stringBuilder.toString());
        SortedSet<String> cont = new TreeSet<>();

        while (matcher.find())
            cont.add(matcher.group());

        for (String word : cont)
            contactService.seve(new Contact(word));

        return "successfully \n";
    }
}
