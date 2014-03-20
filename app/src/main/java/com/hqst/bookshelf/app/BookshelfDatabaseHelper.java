package com.hqst.bookshelf.app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ahkj on 19/03/14.
 */
public class BookshelfDatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "books.sqlite";

    // Book table
    private static final String TABLE_BOOK = "book";
    private static final String COLUMN_BOOK_ID = "_id";
    private static final String COLUMN_BOOK_TITLE = "title";
    private static final String COLUMN_BOOK_AUTHOR = "author";
    private static final String COLUMN_BOOK_PAGES = "pages";
    private static final String TAG = BookshelfDatabaseHelper.class.toString();

    public BookshelfDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create the book table
        sqLiteDatabase.execSQL("create table " + TABLE_BOOK +
                " (" + COLUMN_BOOK_ID + " integer primary key autoincrement, "
                     + COLUMN_BOOK_TITLE + " varchar(255), "
                     + COLUMN_BOOK_AUTHOR + " varchar(255), "
                     + COLUMN_BOOK_PAGES + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // do migrations
    }

    /*
    Insert a book. Returns the id of the created book.
     */
    public long insert(Book book){
        Log.i(TAG, "insert book " + book);
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BOOK_TITLE, book.getTitle());
        cv.put(COLUMN_BOOK_AUTHOR, book.getAuthor());
        cv.put(COLUMN_BOOK_PAGES, book.getPages());
        return getWritableDatabase().insert(TABLE_BOOK, null, cv);
    }

    /*
        Save (update) an existing book.
     */
    public int save(Book book){
        Log.i(TAG, "save book " + book);
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BOOK_TITLE, book.getTitle());
        cv.put(COLUMN_BOOK_AUTHOR, book.getAuthor());
        cv.put(COLUMN_BOOK_PAGES, book.getPages());
        cv.put(COLUMN_BOOK_ID, book.getId());
        return getWritableDatabase().update(TABLE_BOOK,
                cv,
                COLUMN_BOOK_ID + " = ?",
                new String[]{ String.valueOf(book.getId())});
    }

    /*
        Delete a book from the book table matched on book id.
     */
    public int delete(Book book){
        return getWritableDatabase().delete(TABLE_BOOK,
                    COLUMN_BOOK_ID + " = ?",
                    new String[]{ String.valueOf(book.getId())});
    }

    /*
        Returns a cursor over items matching a search term
        in the book title or author column.
     */
    public BookCursor queryBook(String searchTerm){
         Cursor wrapped = getReadableDatabase().rawQuery(
                 "Select * from " + TABLE_BOOK +
                 " where " + COLUMN_BOOK_TITLE +
                 " like '%" + searchTerm + "%'" +
                 " or " + COLUMN_BOOK_AUTHOR +
                 " like '%" + searchTerm + "%'" +
                 " order by " + COLUMN_BOOK_AUTHOR,
                 new String[]{});
        return new BookCursor(wrapped);
    }

    /*
        Query to fetch all items in the book table;
     */
    public BookCursor queryBook(){
        Cursor wrapped = getReadableDatabase().rawQuery(
                "Select * from " + TABLE_BOOK +
                " order by " + COLUMN_BOOK_AUTHOR,
                new String[]{});
        return new BookCursor(wrapped);
    }

    /*
        Query the book table on book id
     */
    public BookCursor queryBook(long bookId){
        Cursor wrapped = getReadableDatabase().query(TABLE_BOOK,
                null, // all columns
                COLUMN_BOOK_ID + " = ?", //from columns
                new String[]{ String.valueOf(bookId) }, //args
                null, // group by
                null, // order by
                null, // having
                "1"); //limit
        return new BookCursor(wrapped);
    }

    public class BookCursor extends CursorWrapper {
        public BookCursor(Cursor cursor) {
            super(cursor);
        }

        public Book getBook(){
            if(isBeforeFirst() || isAfterLast()){
                return null;
            }

            Book book = new Book();
            book.setId(getLong(getColumnIndex(COLUMN_BOOK_ID)));
            book.setTitle(getString(getColumnIndex(COLUMN_BOOK_TITLE)));
            book.setAuthor(getString(getColumnIndex(COLUMN_BOOK_AUTHOR)));
            book.setPages(getInt(getColumnIndex(COLUMN_BOOK_PAGES)));
            return book;
        }
    }
}
