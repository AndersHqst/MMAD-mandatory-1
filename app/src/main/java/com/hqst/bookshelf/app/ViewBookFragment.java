package com.hqst.bookshelf.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ahkj on 20/03/14.
 */
public class ViewBookFragment extends Fragment {
    private static final String TAG = ViewBookFragment.class.toString();

    private TextView mTitleTextView;
    private TextView mAuthorTextView;
    private TextView mPagesTextView;
    private Button mDeleteButton;
    private long mBookId;

    private ViewBookFragment(){}
    private ViewBookFragment(long bookId){
        mBookId = bookId;
    }

    public static ViewBookFragment newInstance(long bookId){
        return new ViewBookFragment(bookId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.fragment_view_book, container, false);

        if(mBookId != -1) {

            final Book book = BookshelfManager.get(getActivity()).getBook(mBookId);

            mTitleTextView = (TextView) view.findViewById(R.id.textView_view_book_title);
            mTitleTextView.setText(book.getTitle());

            mAuthorTextView = (TextView) view.findViewById(R.id.textView_view_book_author);
            mAuthorTextView.setText(book.getAuthor());

            mPagesTextView = (TextView) view.findViewById(R.id.textView_view_book_pages);
            mPagesTextView.setText("" + book.getPages());

            mDeleteButton = (Button)view.findViewById(R.id.button_view_book_delete);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    book.delete(getActivity());

                    Toast.makeText(getActivity(), getString(R.string.toast_book_deleted), Toast.LENGTH_SHORT).show();
                    if(NavUtils.getParentActivityName(getActivity()) != null){
                        getActivity().setResult(Activity.RESULT_OK);
                        NavUtils.navigateUpFromSameTask(getActivity());
                    }
                }
            });
        }
        else {
            Log.e(TAG, "Error. Trying to view book with id=-1");
        }
        return view;
    }
}
