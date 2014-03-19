package com.hqst.bookshelf.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ahkj on 19/03/14.
 */
public class BookshelfListFragment extends Fragment {
    private static final String TAG = BookshelfListFragment.class.getName().toString();
    private static final int REQUEST_CODE_CREATE_BOOK = 1;

    Button mNewBookButton;
    Button mSearchButton;
    EditText mSearchEditText;
    ListView mList;

    ArrayList<Book> mBooks = new ArrayList<Book>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf_list, container, false);

        // Test data
        mBooks.add(new Book("Title 1", "Author 1"));
        mBooks.add(new Book("Title 2", "Author 2"));
        mBooks.add(new Book("Title 3", "Author 3"));
        mNewBookButton = (Button)view.findViewById(R.id.button_new_book);
        mNewBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateBookActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CREATE_BOOK);
            }
        });

        mNewBookButton = (Button)view.findViewById(R.id.button_search);
        mNewBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Search for book - update list n stuff");
            }
        });

        mSearchEditText = (EditText)view.findViewById(R.id.editText_search_books);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                Log.i(TAG, "Text change: " + charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mList = (ListView)view.findViewById(R.id.list_books);
        ListAdapter adapter = new ArrayAdapter<Book>(getActivity(), android.R.layout.simple_list_item_1, mBooks);
        mList.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult");
        if(requestCode == REQUEST_CODE_CREATE_BOOK){
            if(resultCode == Activity.RESULT_OK){
                // Should be the cursor adapter requerying the db
                ((BaseAdapter)mList.getAdapter()).notifyDataSetChanged();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
