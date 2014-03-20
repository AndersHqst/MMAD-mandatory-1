package com.hqst.bookshelf.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ahkj on 19/03/14.
 */
public class CreateBookFragment extends Fragment {

    private static final String TAG = CreateBookFragment.class.toString();

    EditText mTitleEditText;
    EditText mAuthorEditText;
    EditText mPagesEditText;
    Button mSaveButton;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        View view = inflater.inflate(R.layout.fragment_create_book, container, false);

        mTitleEditText = (EditText)view.findViewById(R.id.editText_create_book_title);
        mAuthorEditText = (EditText)view.findViewById(R.id.editText_create_book_author);
        mPagesEditText = (EditText)view.findViewById(R.id.editText_create_book_pages);

        mSaveButton = (Button)view.findViewById(R.id.button_create_book_submit);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Book book = Book.create(getActivity());
                book.setTitle(mTitleEditText.getText().toString());
                book.setAuthor(mAuthorEditText.getText().toString());
                String pages = mPagesEditText.getText().toString();
                book.setPages(Integer.valueOf(pages));
                book.save(getActivity());

                Toast.makeText(getActivity(), getString(R.string.toast_book_created), Toast.LENGTH_SHORT).show();

                // Pop activity
                // Send result about book having been created
                if(NavUtils.getParentActivityName(getActivity()) != null){
                    getActivity().setResult(Activity.RESULT_OK);
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                if(NavUtils.getParentActivityName(getActivity()) != null){
                    getActivity().setResult(Activity.RESULT_OK);
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
