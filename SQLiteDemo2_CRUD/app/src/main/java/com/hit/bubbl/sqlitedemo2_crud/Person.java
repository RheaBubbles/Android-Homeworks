package com.hit.bubbl.sqlitedemo2_crud;

/**
 * @author Bubbles
 * @create 2018/7/16
 * @Describe
 */
public class Person {

    private int personId, age;
    private String name;

    public Person() {

    }

    public Person(int personId, String name, int age) {
        super();
        this.personId = personId;
        this.name = name;
        this.age = age;
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

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", name=" + name +
                ", age=" + age +
                "}";
    }
}
