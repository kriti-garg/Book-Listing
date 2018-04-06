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

/**
 * An {@link BookListing} object contains information related to a single book.
 */
public class BookListing {

    private String mAuthor;
    private String mTitle;
    private Integer mBookNumber;

    /** Website URL of thumbnail of the book */
    private String mUrl;


    /**
     * Constructs a new {@link BookListing} object.
     *
     * @param title is the title of the book
     * @param author is the author of the book
     * @param booknumber is the Book Number of the book
     * @param url is the website URL to find the thumbnail of the book.
     */
    public BookListing(String title, String author, Integer booknumber, String url) {
        mTitle = title;
        mAuthor = author;
        mBookNumber = booknumber;
        mUrl = url;
    }

    /**
     * Returns the title of the book.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns the Book Number of the book.
     */
    public Integer getBookNumber() {
        return mBookNumber;
    }

    /**
     * Returns the author's name of the book.
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * Returns the website URL to find the thumbnail of the book.
     */
    public String getUrl() {
        return mUrl;
    }
}
