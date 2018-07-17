package com.hit.bubbl.sqlitedemo3_bdlist;

/**
 * @author Bubbles
 * @create 2018/7/17
 * @Describe
 */

public class Person {

    private int personId, age, account;
    private String name;

    public Person() {

    }

    public Person(int personId, String name, int age, int account) {
        super();
        this.personId = personId;
        this.name = name;
        this.age = age;
        this.account = account;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", name=" + name +
                ", age=" + age +
                ", account=" + account +
                "}";
    }
}
