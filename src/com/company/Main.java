package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Library library;
    static Reader loggedReader;

    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Harry Potter ", "Rarl Marx"));
        books.add(new Book("Shaitanat ", "Tohir Malik"));
        books.add(new Book("Chelovek-nevidimka ", "Herbert Wales"));
        List<Reader> readers = new ArrayList<>();

        readers.add(new Reader("Tom", "tom", "tom1324"));
        readers.add(new Reader("Jerry", "jerry", "jerry1324"));
        readers.add(new Reader("Spike", "spike", "spike1324"));

        library = new Library(readers, books);
        try {
            ObjectInputStream ois =new ObjectInputStream(new FileInputStream("database"));
            library = (Library) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" ");
        }

        System.out.println("Добро пожаловать в систему управлением библиотекой");
        System.out.println("------------------------------------");

        mainMenu();
    }

    static void mainMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("1. Поиск книг");
            System.out.println("2. Просмотр всех книг");
            System.out.println("3. Просмотр всех читателей");
            System.out.println("4. Просмотр всех взятых книг");
            System.out.println("5. Добавить книгу");
            System.out.println((loggedReader != null) ?  "6. Взять книгу" : "6. Войти");
            System.out.println((loggedReader != null) ? "7. Вернуть книгу" : "7. Зарегистрироваться");
            System.out.println("8. Выход");
            try {
                int choice = scanner.nextInt();
                if (choice < 9 && choice > 0) {
                    switch (choice) {
                        case 1:
                            Book.searchBook();
                            break;


                        case 2:
                            Book.getAllBooks(library.getBooksList());
                            break;


                        case 3:
                            Reader.getAllReaders(library.getReaderList());
                            break;


                        case 4:
                            Book.getAllTakenBooks(library.getBooksList());
                            break;


                        case 5:
                            Book.addNewBook();
                            break;


                        case 6:
                            if (loggedReader != null) {
                                Reader.takeBook();
                            } else login();
                            break;


                        case 7:
                            if (loggedReader != null) {
                                Reader.returnBook();
                            } else Reader.createAccount();
                            break;


                        case 8:
                            System.out.println("Выход из системы...");
                            try {
                                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database"));
                                oos.writeObject(library);
                                oos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("Запись не удалась!");
                            }
                            System.exit(0);
                    }

                } else  {
                    System.out.println("Введите правильное число!");
                    scanner.next();
                }


            } catch (Exception e) {
                System.out.println("Неверный формат ввода! Повторите ввод:");
                scanner.next();
            }
        }

    }



    private static void login() {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        while (true){
            try {
                System.out.println( "Введите ваш логин: ");
                String dlyaLogin = scanner.nextLine();
                System.out.println("Введите ваш пароль: ");
                String dlyaPassword = scanner.nextLine();
                while (true){
                    for (Reader r: library.getReaderList()) {
                        if(r.getLogin().equals(dlyaLogin) && r.getPassword().equals(dlyaPassword)){
                            System.out.println("Здравствуйте, " + r.getName() + "! ");
                            System.out.println("-----------------------------------");
                            loggedReader = r;
                            mainMenu();
                        }

                    }
                    if (count < 2){
                        count++;
                        System.out.println("Неправильный логин или пароль! У вас осталось " + (3 -count) + " попыток");
                        System.out.println("Введите ваш логин:");
                        dlyaLogin = scanner.nextLine();
                        System.out.println("Введите ваш пароль:");
                        dlyaPassword = scanner.nextLine();
                    } else {
                        System.out.println("Не верно введенные данные: 3 раза. Система закрывается");
                        System.exit(0);
                    }
                }

            }catch (Exception e ){
                System.out.println("Неправильный формат ввода!");
            }
        }


    }
}