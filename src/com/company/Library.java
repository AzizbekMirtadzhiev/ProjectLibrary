package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Library implements Serializable {
    private List <Reader> readerList;
    private List <Book> booksList;
    static List<Integer > ids = new ArrayList<>();

    public Library(List<Reader> readerList, List<Book> booksList) {
        this.readerList = readerList;
        this.booksList = booksList;
    }

    public List<Reader> getReaderList() {
        return readerList;
    }

    public void setReaderList(List<Reader> readerList) {
        this.readerList = readerList;
    }

    public List<Book> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<Book> booksList) {
        this.booksList = booksList;
    }

    public static int genUniqueId() {
        int id = 0;
        while (true) {
            id = new Random().nextInt(1000);
            if (checkForDuplicates(id)) {
                ids.add(id);
                break;
            }
        }
        return id;
    }

    private static boolean checkForDuplicates(int id) {
        for (int i : ids) {
            return i != id ;
        }

        return true;


    }
}
