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
    public ResponseEntity<Model>  getAllCards(@RequestParam(value = "nameFilter") String nameFilter,
                               @RequestParam(value = "responseSize")Integer responseSize,
                               final Model model) {

        List<Contact> contactsList = filterContacts(nameFilter,responseSize);

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

    //загрузить базу с txt документа
//    @RequestMapping(value = "/addDbContacts", method = RequestMethod.GET)
//    public String addContactsTxt() throws IOException {
//
//        FileInputStream fileInputStream = new FileInputStream("/home/user/aaa.txt");
//        StringBuilder stringBuilder = new StringBuilder();
//        byte[] buffer2 = new byte[1024];
//        while (fileInputStream.read(buffer2) != -1){
//            String h = new String(buffer2, StandardCharsets.UTF_8);
//            stringBuilder.append(h);
//        }
//        Pattern pattern =
//                Pattern.compile("\\w+", Pattern.UNICODE_CHARACTER_CLASS
//                        | Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(stringBuilder.toString());
//        SortedSet<String> cont = new TreeSet<>();
//
//        while (matcher.find())
//            cont.add(matcher.group());
//
//        for (String word : cont)
//            contactService.seve(new Contact(word));
//
//        return "successfully";
//    }
}
