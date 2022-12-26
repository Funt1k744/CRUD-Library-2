package ru.crud.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.crud.models.Book;
import ru.crud.models.Person;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?, ?, ?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updateBook) {
        jdbcTemplate.update("UPDATE Book SET person_id=?, name=?, author=?, year=? WHERE book_id=?", updateBook.getPersonId(), updateBook.getName(), updateBook.getAuthor(), updateBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE book_id=?", id);
    }

    public Person takenFio(int id) {
        return jdbcTemplate.query("SELECT person.fio FROM person JOIN book on person.person_id = book.person_id where book.book_id = ?", new Object[] {id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public boolean takenTest(int id) {
        return !(show(id).getPersonId() == null);
    }

    public void assignPerson(Person person, int id) {
        jdbcTemplate.update("UPDATE Book SET person_id=? where book_id=?", person.getPersonId(), id);
    }

    public void freeBook(int id) {
        jdbcTemplate.update("update book set person_id = null where book_id=?", id);
    }
}
