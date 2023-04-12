package com.InMemorySimple.SolutionIMS.controllers;

import com.InMemorySimple.SolutionIMS.dto.PersonDTO;
import com.InMemorySimple.SolutionIMS.models.Person;
import com.InMemorySimple.SolutionIMS.services.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
public class ControllerDB {

    private final PersonService personService;
    private final ModelMapper modelMapper;

    @Autowired
    public ControllerDB(PersonService personService, ModelMapper modelMapper) {
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody PersonDTO personDTO, BindingResult bindingResult) {
        personService.add(convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PersonDTO>> getAll() {
        return new ResponseEntity<>(convertListToDTO(personService.get(), this::convertToPersonDTO), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody PersonDTO personDTO, @PathVariable("id") int id) {
        personService.update(convertToPerson(personDTO), id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        personService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/searchAccount")
    public ResponseEntity<List<PersonDTO>> searchAccount(@RequestParam("query") long accountQuery) {
        return new ResponseEntity<>(convertListToDTO(personService.searchAccount(accountQuery), this::convertToPersonDTO), HttpStatus.OK);
    }

    @PostMapping("/searchName")
    public ResponseEntity<List<PersonDTO>> searchName(@RequestParam("query") String nameQuery) {
        return new ResponseEntity<>(convertListToDTO(personService.searchName(nameQuery), this::convertToPersonDTO), HttpStatus.OK);
    }

    @PostMapping("/searchValue")
    public ResponseEntity<List<PersonDTO>> searchValue(@RequestParam("query") double valueQuery) {
        return new ResponseEntity<>(convertListToDTO(personService.searchValue(valueQuery), this::convertToPersonDTO), HttpStatus.OK);
    }

    public Person convertToPerson(PersonDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Person.class);
    }

    public <R, E> List<R> convertListToDTO(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter).collect(Collectors.toList());
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }


}
