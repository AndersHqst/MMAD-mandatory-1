package com.hqst.bookshelf.app;

/**
 * Created by ahkj on 19/03/14.
 */
public class Book {
    private long mId;
    private String mTitle;
    private String mAuthor;
    private int pages;

    private Book() {}

    public Book(long id) {
        mId = id;
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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getId() {
        return mId;
    }

    @Override
    public String toString() {
        return mTitle + " by " + mAuthor;
    }
}
