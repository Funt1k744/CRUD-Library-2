package ru.crud.models;

import javax.validation.constraints.Max;

public class Person {
    private int personId;

    private String fio;
    @Max(value = 2023, message = "Pizdish")
    private Integer birthday;

    public Person() {

    }

    public Person(int personId, String fio, Integer birthday) {
        this.personId = personId;
        this.fio = fio;
        this.birthday = birthday;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Integer getBirthday() {
        return birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }
}
