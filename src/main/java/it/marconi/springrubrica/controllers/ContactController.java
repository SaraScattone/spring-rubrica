package it.marconi.springrubrica.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.marconi.springrubrica.domains.Contact;
import it.marconi.springrubrica.domains.ContactForm;
import it.marconi.springrubrica.services.ContactService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;
   
    @GetMapping("/new")
    public ModelAndView newContactForm() {
        return new ModelAndView("contact-form").addObject(new ContactForm());
    }

    @PostMapping("/new")
    public ModelAndView handleNewContact(@ModelAttribute @Valid ContactForm contactForm, BindingResult br) {

        //verifico se ci sono stati degli errori di validazione
        if(br.hasErrors())
            return new ModelAndView("contact-form");

        Contact contact = contactService.save(contactForm);

        //apllico pattern PRG
        return new ModelAndView("redirect:/contacts?id=" + contact.getId());
    }
    
    @GetMapping(params = "id")
    public ModelAndView showContact(@RequestParam("id") UUID contactId) {

        Optional<Contact> opContact = contactService.get(contactId);

        if(opContact.isPresent())
            return new ModelAndView("contact-detail").addObject("contact", opContact.get());
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contatto non trovato");
    }

    @GetMapping
    public ModelAndView showContactList() {
        return new ModelAndView("contact-list").addObject("contacts", contactService.findAll());
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteContact(@PathVariable("id") UUID contactId, RedirectAttributes attr){
        
        contactService.deleteById(contactId);

        //aggiunto boolean agli attributi del redirect
        attr.addFlashAttribute("deleted", true);
        return new ModelAndView("redirect:/contacts");
    }
}
