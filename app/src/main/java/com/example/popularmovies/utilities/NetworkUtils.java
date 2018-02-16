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
package com.example.popularmovies.utilities;

import android.net.Uri;

import com.example.popularmovies.data.IJsonDeserialize;
import com.example.popularmovies.data.MovieDetails;
import com.example.popularmovies.data.MoviesResponse;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public final class NetworkUtils {

    private static final String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
    private static final String API_BASE_URL = "https://api.themoviedb.org/3";
    private final static String API_KEY_PARAM = "api_key";

    public static Uri buildMovieImageUri(String path) {
        Uri.Builder uriBuilder = Uri.parse(MOVIE_POSTER_BASE_URL)
                .buildUpon()
                .appendEncodedPath(path);

        Uri builtUri = uriBuilder.build();
        return builtUri;
    }

    private static URL buildUrl(String apiKey, String... pathComponents) {
        Uri.Builder uriBuilder = Uri.parse(API_BASE_URL).buildUpon();

        for (String pathComponent : pathComponents) {
            uriBuilder.appendEncodedPath(pathComponent);
        }

        uriBuilder = uriBuilder.appendQueryParameter(API_KEY_PARAM, apiKey);

        Uri builtUri = uriBuilder.build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    private static <T extends IJsonDeserialize> T sendRequest(URL requestUrl, Class<T> instanceClass) throws Exception {
        HttpURLConnection httpConnection = null;
        InputStream responseInputStream = null;

        T response;

        try {
            URLConnection urlConnection = requestUrl.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");

            httpConnection = (HttpURLConnection) urlConnection;

            httpConnection.setRequestMethod("GET");
            httpConnection.setConnectTimeout(2000);
            httpConnection.setReadTimeout(2000);

            int statusCode = httpConnection.getResponseCode();
            if (statusCode != 200)
                throw new Exception("Http Error: Status Code - " + statusCode);

            responseInputStream = httpConnection.getInputStream();
            BufferedReader responseBufferedReader = new BufferedReader(new InputStreamReader(responseInputStream));

            String line;
            StringBuilder responseStringBuilder = new StringBuilder();
            while ((line = responseBufferedReader.readLine()) != null) {
                responseStringBuilder.append(line);
            }

            if (responseStringBuilder.length() == 0)
                return null;

            String responseString = responseStringBuilder.toString();
            JSONObject jsonObject = new JSONObject(responseString);

            response = instanceClass.newInstance();
            response.fillPropertiesByJsonObject(jsonObject);
        } finally {
            if (responseInputStream != null)
                responseInputStream.close();

            if (httpConnection != null)
                httpConnection.disconnect();
        }

        return response;
    }

    public static MoviesResponse popular(String apiKey) throws Exception {
        URL requestUrl = buildUrl(apiKey, "movie", "popular");
        MoviesResponse moviesResponse = sendRequest(requestUrl, MoviesResponse.class);
        return moviesResponse;
    }

    public static MoviesResponse topRated(String apiKey) throws Exception {
        URL requestUrl = buildUrl(apiKey, "movie", "top_rated");
        MoviesResponse moviesResponse = sendRequest(requestUrl, MoviesResponse.class);
        return moviesResponse;
    }

    public static MovieDetails movieDetails(String apiKey, int movieId) throws Exception {
        URL requestUrl = buildUrl(apiKey, "movie", String.valueOf(movieId));
        MovieDetails movieDetails = sendRequest(requestUrl, MovieDetails.class);
        return movieDetails;
    }
}