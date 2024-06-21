package ru.gb.jj;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Program {


    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            Person person = new Person();
            person.setName("worker " + (i + 1));
            person.setAge(ThreadLocalRandom.current().nextInt(18, 60));
            person.setSalary(ThreadLocalRandom.current().nextInt(35000, 150000));
            person.setDepart(new Department(ThreadLocalRandom.current().nextInt(1, 6)));
            personList.add(person);
        }

        for (int i = 0; i < personList.size(); i++) {
            System.out.println(personList.get(i));
        }

        //System.out.println(findMostYoungestPerson(personList));

        //System.out.println(findMostExpensiveDepartment(personList));


//        for (Map.Entry <Department, List<Person>> people : groupByDepartment(personList).entrySet()){
//            System.out.println(people);
//        }


//        for (Map.Entry <String, List<Person>> people : groupByDepartmentName(personList).entrySet()){
//            System.out.println(people);
//        }

//        for (Map.Entry <String, Person> people : getDepartmentOldestPerson(personList).entrySet()){
//            System.out.println(people);
//        }

        System.out.println("________________________");
        System.out.println();
        List<Person> people = cheapPersonsInDepartment(personList);
        for (int i = 0; i < people.size(); i++) {
            System.out.println(people.get(i));
        }
    }


    private static class Department {
        private String name;

        public Department(int i) {
            name = "Department #" + i;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Department that = (Department) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }
// TODO: геттеры, сеттеры
    }

    private static class Person {
        private String name;
        private int age;
        private double salary;
        private Department depart;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public Department getDepart() {
            return depart;
        }

        public void setDepart(Department depart) {
            this.depart = depart;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", " + depart +
                    '}';
        }

        // TODO: геттеры, сеттеры
    }

    /**
     * Найти самого молодого сотрудника
     */
    static Optional<Person> findMostYoungestPerson(List<Person> people) {
        // FIXME: ваша реализация здесь
        return people.stream().
                min((o1, o2) -> Integer.compare(o1.getAge(), o2.getAge()));

    }

    /**
     * Найти департамент, в котором работает сотрудник с самой большой зарплатой
     */
    static Optional<Department> findMostExpensiveDepartment(List<Person> people) {
        return people.stream()
                .max((o1, o2) -> Double.compare(o1.getSalary(), o2.getSalary()))
                .map(Person::getDepart);

    }

    /**
     * Сгруппировать сотрудников по департаментам
     */
    static Map<Department, List<Person>> groupByDepartment(List<Person> people) {
        return people.stream()
                .collect(Collectors.groupingBy(Person::getDepart));
    }

    /**
     * Сгруппировать сотрудников по названиям департаментов
     */
    static Map<String, List<Person>> groupByDepartmentName(List<Person> people) {
        // FIXME: ваша реализация здесь
        return people.stream()
                .collect(Collectors.groupingBy(person -> person.getDepart().getName()));
    }

    /**
     * В каждом департаменте найти самого старшего сотрудника
     */
    static Map<String, Person> getDepartmentOldestPerson(List<Person> people) {
        return people.stream().collect(Collectors.toMap(
                person -> person.getDepart().getName(),
                b -> b,
                (a, b) -> {
                    if (a.getAge() > b.getAge()) return a;
                    return b;
                }
        ));
    }

    /**
     * *Найти сотрудников с минимальными зарплатами в своем отделе
     * (прим. можно реализовать в два запроса)
     */
    static List<Person> cheapPersonsInDepartment(List<Person> people) {
        Map<String, Person> map = people.stream()
                .collect(Collectors.toMap(
                        person -> person.getDepart().getName(),
                        b -> b,
                        (a, b) -> {
                            if (a.getSalary() < b.getSalary()) return a;
                            return b;
                        }));
        List<Person> personList = new ArrayList<>(map.values());

        return personList;
    }


}
