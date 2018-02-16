package com.example.popularmovies.data;


import com.example.popularmovies.utilities.JsonUtils;

import org.json.JSONObject;

import java.util.List;

public class Movie implements IJsonDeserialize {
    private String posterPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private List<Integer> genreIds;
    private int id;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private double popularity;
    private int voteCount;
    private boolean video;
    private double voteAverage;

    public String getPosterPath() {
        return posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    @Override
    public void fillPropertiesByJsonObject(JSONObject jsonObject) {
        posterPath = jsonObject.optString("poster_path");
        adult = jsonObject.optBoolean("adult");
        overview = jsonObject.optString("overview");
        releaseDate = jsonObject.optString("release_date");
        genreIds = JsonUtils.optIntList(jsonObject, "genre_ids");
        id = jsonObject.optInt("id");
        originalTitle = jsonObject.optString("original_title");
        originalLanguage = jsonObject.optString("original_language");
        title = jsonObject.optString("title");
        backdropPath = jsonObject.optString("backdrop_path");
        popularity = jsonObject.optDouble("popularity");
        voteCount = jsonObject.optInt("vote_count");
        video = jsonObject.optBoolean("video");
        voteAverage = jsonObject.optDouble("vote_average");
    }
}
