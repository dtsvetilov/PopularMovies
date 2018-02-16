package com.example.popularmovies.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesResponse implements IJsonDeserialize {
    private int page;
    private int totalResults;
    private int totalPages;
    private List<Movie> results;

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    @Override
    public void fillPropertiesByJsonObject(JSONObject jsonObject) {
        page = jsonObject.optInt("page");
        totalResults = jsonObject.optInt("total_results");
        totalPages = jsonObject.optInt("total_pages");

        results = new ArrayList<>();
        JSONArray resultsJsonArray = jsonObject.optJSONArray("results");
        if (resultsJsonArray == null || resultsJsonArray.length() == 0)
            return;

        for (int i = 0; i < resultsJsonArray.length(); i++) {
            JSONObject resultJsonObject = resultsJsonArray.optJSONObject(i);
            if (resultJsonObject == null)
                continue;

            Movie movie = new Movie();
            movie.fillPropertiesByJsonObject(resultJsonObject);

            results.add(movie);
        }
    }
}
