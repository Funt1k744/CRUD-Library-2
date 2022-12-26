package ru.crud.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.crud.models.Book;
import ru.crud.models.Person;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(fio, birthday) VALUES (?, ?)", person.getFio(), person.getBirthday());
    }

    public void update(int id, Person updatePerson) {
        jdbcTemplate.update("UPDATE Person SET fio=?, birthday=? WHERE person_id=?", updatePerson.getFio(),updatePerson.getBirthday(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }

    public List<Book> taken(int id) {
        return jdbcTemplate.query("SELECT book.name, book.author, book.year FROM Person JOIN book on Person.person_id = book.person_id where book.person_id=?", new Object[] {id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public boolean takenTest(int id) {
        return !taken(id).isEmpty();
    }
}
