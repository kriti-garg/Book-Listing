/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.booklisting;

import android.content.Intent;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookListingActivity extends AppCompatActivity implements LoaderCallbacks<List<BookListing>> {

    public static final String LOG_TAG = BookListingActivity.class.getName();

    /**
     * URL for book data from the Google API
     */
    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=search+";

    /**
     * Variable value for the book loader ID as we want to load page again and
     * agian a new book is searched
     */
    private int BOOK_LOADER_ID = 0;

    /**
     * Adapter for the list of books
     */
    private BookListingAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    /**
     * EditView for user to provide input
     */
    private EditText userInput;

    /**
     * Query button to be pressed to search the book
     */
    private Button query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booklisting_activity);
        userInput = (EditText) findViewById(R.id.inputText);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);
        mEmptyStateTextView.setVisibility(View.GONE);

        query = (Button) findViewById(R.id.search);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookListingAdapter(this, new ArrayList<BookListing>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which produces toast specifying the
        // Title of the selected book.
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current book that was clicked on
                BookListing currentBook = mAdapter.getItem(position);

                //Make a toast with the title of the book
                Toast.makeText(getApplicationContext(), currentBook.getTitle(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Set an item Long click listener on the ListView, which sends an intent to a web browser
        // to open a the thumbnail of the selected book.
        bookListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Find the current book that was clicked on
                BookListing currentBook = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(currentBook.getUrl());

                // Create a new intent to view the book URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
                return true;
            }
        });


        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View loadingIndicator = findViewById(R.id.loading_indicator);
                loadingIndicator.setVisibility(View.VISIBLE);
                mEmptyStateTextView.setVisibility(View.GONE);

                BOOK_LOADER_ID++;

                // Get a reference to the ConnectivityManager to check state of network connectivity
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                // Clear the mAdapter
                mAdapter.clear();

                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {
                    // Get a reference to the LoaderManager, in order to interact with loaders.
                    LoaderManager loaderManager = getLoaderManager();

                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    loaderManager.initLoader(BOOK_LOADER_ID, null, BookListingActivity.this);
                } else {
                    // Otherwise, display error
                    // Hide loading indicator so error message will be visible
                    loadingIndicator.setVisibility(View.GONE);

                    // Clear the mAdapter
                    mAdapter.clear();

                    // Update empty state with no connection error message
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public Loader<List<BookListing>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookListingLoader(this, getUrl());
    }

    @Override
    public void onLoadFinished(Loader<List<BookListing>> loader, List<BookListing> books) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous book data
        mAdapter.clear();

        // If there is a valid list of {@link BookListing}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mEmptyStateTextView.setVisibility(View.GONE);
            mAdapter.addAll(books);
        } else {
            // Set empty state text to display "No books found."
            mEmptyStateTextView.setText(R.string.no_books);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<BookListing>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    /** Format the url taking the input from the user */
    private String getUrl() {
        String UserInput = userInput.getText().toString().trim().replaceAll("\\s+", "+");
        return BOOK_REQUEST_URL + UserInput;
    }
}
