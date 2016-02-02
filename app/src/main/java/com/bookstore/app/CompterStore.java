package com.bookstore.app;

import java.util.List;

/**
 * Created by dempseyj on 19/01/2016.
 */
public class CompterStore {

    private static CompterStore instance = null;

    public static CompterStore getInstance() {
        if (instance == null) {
            instance = new CompterStore();
        }
        return instance;
    }

    private List<Computer> mComputers;

    private CompterStore() {}

    public List<Computer> getBooks() {
        return mComputers;
    }

    public void setBooks(List<Computer> mComputers) {
        this.mComputers = mComputers;
    }
}
