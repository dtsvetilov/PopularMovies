package com.example.popularmovies.data;

import com.example.popularmovies.utilities.JsonUtils;

import org.json.JSONObject;

import java.util.List;

public class MovieDetails implements IJsonDeserialize {
    private boolean adult;
    private String backdropPath;
    private int budget;
    private List<Genre> genres;
    private String homepage;
    private int id;
    private String imdbId;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private double popularity;
    private String posterPath;
    private List<ProductionCompany> productionCompanies;
    private List<ProductionCountry> productionCountries;
    private String releaseDate;
    private int revenue;
    private int runtime;
    private List<SpokenLanguage> spokenLanguages;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double voteAverage;
    private int voteCount;

    public boolean isAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getBudget() {
        return budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getId() {
        return id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public void fillPropertiesByJsonObject(JSONObject jsonObject) {
        adult = jsonObject.optBoolean("adult");
        backdropPath = jsonObject.optString("backdrop_path");
        budget = jsonObject.optInt("budget");
        genres = JsonUtils.optArrayList(jsonObject, "genres", Genre.class);
        homepage = jsonObject.optString("homepage");
        id = jsonObject.optInt("id");
        imdbId = jsonObject.optString("imdb_id");
        originalLanguage = jsonObject.optString("original_language");
        originalTitle = jsonObject.optString("original_title");
        overview = jsonObject.optString("overview");
        popularity = jsonObject.optDouble("popularity");
        posterPath = jsonObject.optString("poster_path");
        productionCompanies = JsonUtils.optArrayList(jsonObject, "production_companies", ProductionCompany.class);
        productionCountries = JsonUtils.optArrayList(jsonObject, "production_countries", ProductionCountry.class);
        releaseDate = jsonObject.optString("release_date");
        revenue = jsonObject.optInt("revenue");
        runtime = jsonObject.optInt("runtime");
        spokenLanguages = JsonUtils.optArrayList(jsonObject, "spoken_languages", SpokenLanguage.class);
        status = jsonObject.optString("status");
        tagline = jsonObject.optString("tagline");
        title = jsonObject.optString("title");
        video = jsonObject.optBoolean("video");
        voteAverage = jsonObject.optDouble("vote_average");
        voteCount = jsonObject.optInt("vote_count");
    }
}


