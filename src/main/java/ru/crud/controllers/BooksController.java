package ru.crud.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.crud.dao.BookDAO;
import ru.crud.dao.PersonDAO;
import ru.crud.models.Book;
import ru.crud.models.Person;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person) {
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("bookTakeTest", bookDAO.takenTest(id));
        model.addAttribute("personFio", bookDAO.takenFio(id));
        model.addAttribute("people", personDAO.index());
        return "books/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book) {
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, @PathVariable("id") int id) {
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.assignPerson(person, id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/free")
    public String freeBook(@PathVariable("id") int id) {
        bookDAO.freeBook(id);
        return "redirect:/books/{id}";
    }

}
