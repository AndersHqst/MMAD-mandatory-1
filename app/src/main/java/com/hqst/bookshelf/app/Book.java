package com.hqst.bookshelf.app;

import android.content.Context;

/**
 * Created by ahkj on 19/03/14.
 */
public class Book {
    public final static long NOT_COMMITED_ID = -1;
    private long mId;
    private String mTitle;
    private String mAuthor;
    private int pages;

    /*
        Instanciate a new uncommied book.
     */
    public Book(){
        mId = Book.NOT_COMMITED_ID;
    }

    /*
        Create a new book with valid id.
     */
    public static Book create(Context context){
        return BookshelfManager.get(context).insertBook(new Book());
    }

    /*
        Save this book.
     */
    public int save(Context context){
        return BookshelfManager.get(context).saveBook(this);
    }

    /*
        Delete this book.
     */
    public int delete(Context context){
        return BookshelfManager.get(context).deleteBook(this);
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

    public void setId(long id) {
        mId = id;
    }

    @Override
    public String toString() {
        return mTitle + " by " + mAuthor;
    }
}
