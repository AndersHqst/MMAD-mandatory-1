package com.hqst.bookshelf.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ahkj on 19/03/14.
 */
public class BookshelfListFragment extends Fragment {
    private static final String TAG = BookshelfListFragment.class.toString();

    public static final int REQUEST_CODE_RELOAD_BOOKS = 1;
    public static final String EXTRA_BOOK_ID = "com.hqst.bookshelf.app.EXTRA_BOOK_ID";

    private Button mNewBookButton;
    private Button mSearchButton;
    private EditText mSearchEditText;
    private ListView mList;
    private BookCursorAdapter mAdapter;
    private String mSearchTerm = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf_list, container, false);

        mNewBookButton = (Button)view.findViewById(R.id.button_new_book);
        mNewBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateBookActivity.class);
                startActivityForResult(intent, REQUEST_CODE_RELOAD_BOOKS);
            }
        });

        mSearchButton = (Button)view.findViewById(R.id.button_search);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Search for book - update list n stuff");
                BookshelfDatabaseHelper.BookCursor cursor = BookshelfManager.get(getActivity()).searchBooks(mSearchTerm);
                ((BookCursorAdapter)mList.getAdapter()).changeCursor(cursor);
            }
        });

        mSearchEditText = (EditText)view.findViewById(R.id.editText_search_books);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                mSearchTerm = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mList = (ListView)view.findViewById(R.id.list_books);
        Cursor cursor = BookshelfManager.get(getActivity()).getAllBooks();
        mAdapter = new BookCursorAdapter(getActivity(), cursor);
        mList.setAdapter(mAdapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long _id) {
                Intent intent = new Intent(getActivity(), ViewBookActivity.class);
                intent.putExtra(EXTRA_BOOK_ID, _id);
                startActivityForResult(intent, REQUEST_CODE_RELOAD_BOOKS);
            }
        });
        return view;
    }

    private class BookCursorAdapter extends CursorAdapter{

        public BookCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            Log.i(TAG, "newView");
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return (View)inflater.inflate(R.layout.list_view, viewGroup, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            Log.i(TAG, "bindView");
            // Here the reuse of views is already handled by the super class
            Book book = ((BookshelfDatabaseHelper.BookCursor)cursor).getBook();

            TextView titleView = (TextView) view.findViewById(R.id.textView_list_view_title);
            titleView.setText(book.getTitle());

            TextView authorView = (TextView) view.findViewById(R.id.textView_list_view_author);
            authorView.setText(book.getAuthor());
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult");
        if(requestCode == REQUEST_CODE_RELOAD_BOOKS){
            if(resultCode == Activity.RESULT_OK){
                Log.i(TAG, "onActivityResult OK");
                // Should be the cursor adapter requerying the db
                Cursor cursor = BookshelfManager.get(getActivity()).getAllBooks();
                ((BookCursorAdapter)mList.getAdapter()).changeCursor(cursor);
                ((BaseAdapter)mList.getAdapter()).notifyDataSetChanged();
            }
        }
        else {
            Log.i(TAG, "onActivityResult NOT OK");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
