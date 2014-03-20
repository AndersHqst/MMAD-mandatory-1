package com.hqst.bookshelf.app;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by ahkj on 19/03/14.
 */
public class BookshelfManager {
    private static final String TAG = BookshelfManager.class.toString();
    private BookshelfDatabaseHelper mHelper;
    private static BookshelfManager sBookshelfManager;
    

    private BookshelfManager(Context context) {
        // Use application context to assure we have something long-lived
        mHelper = new BookshelfDatabaseHelper(context.getApplicationContext());
    }

    public static BookshelfManager get(Context context){
        if (sBookshelfManager == null){
            sBookshelfManager = new BookshelfManager(context);
        }
        return sBookshelfManager;
    }

    /*
        Insert a new book.
     */
    public Book insertBook(Book book){
        book.setId(mHelper.insert(book));
        return book;
    }

    /*
        Save an existing book.
     */
    public int saveBook(Book book){
        Log.i(TAG, "save book " + book);
        if(book.getId() == Book.NOT_COMMITED_ID){
            Log.e(TAG, "Saving book with invalid id. Should use Book.create to create new books.");
            return -1;
        }
        return mHelper.save(book);
    }

    /*
        Get a book by id.
     */
    public Book getBook(long id){
        Book book = null;
        BookshelfDatabaseHelper.BookCursor cursor = mHelper.queryBook(id);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()) {
            book = cursor.getBook();
        }
        cursor.close();
        return book;
    }

    public BookshelfDatabaseHelper.BookCursor getAllBooks(){
        return mHelper.queryBook();
    }

    public BookshelfDatabaseHelper.BookCursor searchBooks(String searchTerm){
        return mHelper.queryBook(searchTerm);
    }

    public int deleteBook(Book book){
        return mHelper.delete(book);
    }
}
