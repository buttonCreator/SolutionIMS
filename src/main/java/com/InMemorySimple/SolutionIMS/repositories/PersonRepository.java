package com.InMemorySimple.SolutionIMS.repositories;

import com.InMemorySimple.SolutionIMS.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    List<Person> findByAccountEquals(long searchAccount);

    List<Person> findByNameStartingWith(String searchName);

    List<Person> findByValueEquals(double searchValue);

}
