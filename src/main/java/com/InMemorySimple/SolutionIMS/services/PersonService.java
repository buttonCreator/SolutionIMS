package com.InMemorySimple.SolutionIMS.services;

import com.InMemorySimple.SolutionIMS.models.Person;
import com.InMemorySimple.SolutionIMS.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void add(Person person) {
        personRepository.save(person);
    }

    @Transactional(readOnly = true)
    public List<Person> get() {
        return personRepository.findAll();
    }

    public void delete(int id) {
        personRepository.deleteById(id);
    }

    public void update(Person person, int id) {
        person.setId(id);
        personRepository.save(person);
    }

    public List<Person> searchAccount(Long accountQuery) {
        return personRepository.findByAccountEquals(accountQuery);
    }

    public List<Person> searchName(String nameQuery) {
        return personRepository.findByNameStartingWith(nameQuery);
    }

    public List<Person> searchValue(double valueQuery) {
        return personRepository.findByValueEquals(valueQuery);
    }

}
