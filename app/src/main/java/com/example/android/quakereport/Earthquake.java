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
package com.example.android.quakereport;

/**
 * An {@link Earthquake} object contains information related to a single earthquake.
 */
public class Earthquake {

    private String mAuthor;
    private String mTitle;
    private Integer mBookNumber;

    /**
     * Constructs a new {@link Earthquake} object.
     *
     * @param title is the magnitude (size) of the earthquake
     * @param author is the location where the earthquake happened
     */
    public Earthquake(String title, String author, Integer booknumber) {
        mTitle = title;
        mAuthor = author;
        mBookNumber = booknumber;
    }

    /**
     * Returns the magnitude of the earthquake.
     */
    public String getTitle() {
        return mTitle;
    }
    public Integer getBookNumber() {
        return mBookNumber;
    }

    /**
     * Returns the location of the earthquake.
     */
    public String getAuthor() {
        return mAuthor;
    }

}
