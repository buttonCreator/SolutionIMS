<h1 align="center">Решение для Java-разработчик в команду корпоративной шины данных и микосервисов 
<h2 align="center">Чтобы посмотреть основную часть решения, перейдите, пожалуйста, по этой <a href="https://github.com/buttonCreator/SolutionIMS/tree/master/src/main/java/com/InMemorySimple/SolutionIMS">ссылке</a> </h3>
  <p> ВОПРОС: Необходимо организовать хранение этих записей в памяти с соблюдением требований: </p>
  <p> Язык: Java</p>
  <p> Реализовано также с посощью: Spring Framework, REST API</p>
  <p> База данных: MySQL</p>
<p></p>
<h3> 1. предоставить возможность добавлять новые записи: </h4>
  <p> - В файле <a href="https://github.com/buttonCreator/SolutionIMS/blob/master/src/main/java/com/InMemorySimple/SolutionIMS/controllers/ControllerDB.java">ControllerDB</a> реализовано добавление структуры в базу данных, когда на сервер поступает POST-запрос</p>
  <h4>ControlllerDB:</h4>
  
```java
@PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody PersonDTO personDTO, BindingResult bindingResult) {
        personService.add(convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
```
  
  <h4>Service</h4>
  
```java
public void add(Person person) {
        personRepository.save(person);
    }
```
  
<h3> 2. предоставить возможность удалять более не нужные записи:</h4>
  <p> - В том же файле <a href="https://github.com/buttonCreator/SolutionIMS/blob/master/src/main/java/com/InMemorySimple/SolutionIMS/controllers/ControllerDB.java">ControllerDB</a>, что и в 1-ом пункте реализовал удаление</p>
  
  <h4>ControlllerDB:</h4>
  
```java
  @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        personService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
```
  
  <h4>Service</h4>
  
```java
  public void delete(int id) {
        personRepository.deleteById(id);
    }
```
  
  <h3> 3. предоставить возможность изменять запись:</h4>
  <p> - В том же файле <a href="https://github.com/buttonCreator/SolutionIMS/blob/master/src/main/java/com/InMemorySimple/SolutionIMS/controllers/ControllerDB.java">ControllerDB</a>, что и в 1-ом пункте реализовал изменение данных</p>
  
  <h4>ControlllerDB:</h4>
  
```java
  @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody PersonDTO personDTO, @PathVariable("id") int id) {
        personService.update(convertToPerson(personDTO), id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
```
   <h4>Service</h4>
  
```java
  public void update(Person person, int id) {
        person.setId(id);
        personRepository.save(person);
    }
```
  
  <h3> 4. получать полный набор записи по любому из полей с одинаковой алгоритмической сложностью (не медленнее log(n)):</h3>
  
  <h4>MySQL</h4>
  
  ```sql
  CREATE table person(
    id int PRIMARY KEY AUTO_INCREMENT,
    account bigint,
    name varchar(45),
    value double
);

create index index_account ON person (account);
create index index_name ON person (name);
create index index_value ON person (value);

```
  
  <h4>ControlllerDB:</h4>
  
  <p> Для поля account:</p>
  
```java
  @PostMapping("/searchAccount")
    public ResponseEntity<List<PersonDTO>> searchAccount(@RequestParam("query") long accountQuery) {
        return new ResponseEntity<>(convertListToDTO(personService.searchAccount(accountQuery), this::convertToPersonDTO), HttpStatus.OK);
    }
```
  
  <p> Для поля name:</p>
  
```java
  @PostMapping("/searchName")
    public ResponseEntity<List<PersonDTO>> searchName(@RequestParam("query") String nameQuery) {
        return new ResponseEntity<>(convertListToDTO(personService.searchName(nameQuery), this::convertToPersonDTO), HttpStatus.OK);
    }
```
  
  <p> Для поля value:</p>
  
```java
  @PostMapping("/searchValue")
    public ResponseEntity<List<PersonDTO>> searchValue(@RequestParam("query") double valueQuery) {
        return new ResponseEntity<>(convertListToDTO(personService.searchValue(valueQuery), this::convertToPersonDTO), HttpStatus.OK);
    }
```
  
  <h4>Sevice:</h4>
  
  <p> Для поля account:</p>
  
```java
  public List<Person> searchAccount(Long accountQuery) {
        return personRepository.findByAccountEquals(accountQuery);
    }
```
  
  <p> Для поля name:</p>
  
```java
  public List<Person> searchName(String nameQuery) {
        return personRepository.findByNameStartingWith(nameQuery);
    }
```
  
  <p> Для поля value:</p>
  
```java
  public List<Person> searchValue(double valueQuery) {
        return personRepository.findByValueEquals(valueQuery);
    }
```
  
  <h4>Repository</h4>
  
```java
  public interface PersonRepository extends JpaRepository<Person, Integer> {

    List<Person> findByAccountEquals(long searchAccount);

    List<Person> findByNameStartingWith(String searchName);

    List<Person> findByValueEquals(double searchValue);

}
```
  
  <h3> 5. выбрать наиболее экономный способ хранения данных в памяти</h3>
  
  <p>Базу данных я выбрал реляционную (потому что у меня жёстко структурированные данные, следовательно лучше использовать реляционные базы данных), а именно MySQL, так как это база данных неплохо справляется с большим количеством данных, чем другие базы данных (в моём случае)</p>
  
