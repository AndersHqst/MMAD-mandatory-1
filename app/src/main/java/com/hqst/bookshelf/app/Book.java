package com.hqst.bookshelf.app;

/**
 * Created by ahkj on 19/03/14.
 */
public class Book {
    private String mTitle;
    private String mAuthor;

    public Book(String title, String author) {
        mTitle = title;
        mAuthor = author;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public String toString() {
        return mTitle + " by " + mAuthor;
    }
}
