package it.marconi.springrubrica.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.marconi.springrubrica.repositories.ContactRepository;

@Service
public class ContactService {
    
    @Autowired //serve a forzare la dependacy injection
    private ContactRepository contactRepo;

    
}
