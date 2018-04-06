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

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * An {@link BookListingAdapter} knows how to create a list item layout for each book
 * in the data source (a list of {@link BookListing} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class BookListingAdapter extends ArrayAdapter<BookListing> {

    /**
     * Constructs a new {@link BookListingAdapter}.
     *
     * @param context of the app
     * @param books is the list of books, which is the data source of the adapter
     */
    public BookListingAdapter(Context context, List<BookListing> books) {
        super(context, 0, books);
    }

    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of books.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.booklisting_list_item, parent, false);
        }

        // Find the book at the given position in the list of books
        BookListing currentBook = getItem(position);

        // Find the TextView with view ID magnitude
        TextView booknumberView = (TextView) listItemView.findViewById(R.id.booknumber);

        // Display the book number of the current book in that TextView
        booknumberView.setText(currentBook.getBookNumber().toString());

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) booknumberView.getBackground();
        // Get the appropriate background color based on the current book magnitude
        int magnitudeColor = getMagnitudeColor(currentBook.getBookNumber());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        // Find the TextView with view ID location
        TextView authorView = (TextView) listItemView.findViewById(R.id.author);

        // Display the location of the current book in that TextView
        authorView.setText(currentBook.getAuthor());

        // Find the TextView with view ID location offset
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);

        // Display the location offset of the current book in that TextView
        titleView.setText(currentBook.getTitle());

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    /**
     * Return the color for the booknumber circle based on the intensity of the book.
     *
     * @param booknumber of the book
     */
    private int getMagnitudeColor(Integer booknumber) {
        int booknumberColorResourceId;
        switch (booknumber) {
            case 0:
            case 1:
                booknumberColorResourceId = R.color.magnitude1;
                break;
            case 2:
                booknumberColorResourceId = R.color.magnitude2;
                break;
            case 3:
                booknumberColorResourceId = R.color.magnitude3;
                break;
            case 4:
                booknumberColorResourceId = R.color.magnitude4;
                break;
            case 5:
                booknumberColorResourceId = R.color.magnitude5;
                break;
            case 6:
                booknumberColorResourceId = R.color.magnitude6;
                break;
            case 7:
                booknumberColorResourceId = R.color.magnitude7;
                break;
            case 8:
                booknumberColorResourceId = R.color.magnitude8;
                break;
            case 9:
                booknumberColorResourceId = R.color.magnitude9;
                break;
            default:
                booknumberColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), booknumberColorResourceId);
    }
}
