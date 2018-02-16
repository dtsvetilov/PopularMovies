package com.example.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.data.IUIAppendableObject;
import com.example.popularmovies.data.MovieDetails;
import com.example.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MovieDetails> {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    private static final int DEFAULT_MOVIE_ID = -1;

    private static final String LOADER_MOVIE_ID = "loader_movie_id";

    private LinearLayout mContentContainerLl;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private static final int MOVIE_DETAILS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        int movieId = intent.getIntExtra(EXTRA_MOVIE_ID, DEFAULT_MOVIE_ID);
        if (movieId == DEFAULT_MOVIE_ID) {
            closeOnError();
            return;
        }

        mContentContainerLl = findViewById(R.id.content_container_ll);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        Bundle loaderBundle = new Bundle();
        loaderBundle.putInt(LOADER_MOVIE_ID, movieId);
        getSupportLoaderManager().initLoader(MOVIE_DETAILS_LOADER_ID, loaderBundle, DetailActivity.this);
    }

    @Override
    public Loader<MovieDetails> onCreateLoader(int id, final Bundle loaderArgs) {
        final int movieId = loaderArgs.getInt(LOADER_MOVIE_ID, -1);
        if (movieId == DEFAULT_MOVIE_ID)
            return null;

        return new AsyncTaskLoader<MovieDetails>(this) {
            MovieDetails mMoviesData;

            @Override
            protected void onStartLoading() {
                if (mMoviesData != null) {
                    deliverResult(mMoviesData);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public MovieDetails loadInBackground() {
                MovieDetails movieDetails = null;
                try {
                    String apiKey = getString(R.string.tmdb_api_key);
                    movieDetails = NetworkUtils.movieDetails(apiKey, movieId);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return movieDetails;
            }

            @Override
            public void deliverResult(MovieDetails data) {
                mMoviesData = data;
                super.deliverResult(data);

            }
        };
    }

    @Override
    public void onLoadFinished(Loader<MovieDetails> loader, MovieDetails data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (data == null) {
            showErrorMessage();
        } else {
            populateUI(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<MovieDetails> loader) {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }

    private void showErrorMessage() {
        mContentContainerLl.setVisibility(View.GONE);
        mLoadingIndicator.setVisibility(View.GONE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void populateUI(MovieDetails movieDetails) {
        mContentContainerLl.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setVisibility(View.GONE);

        TextView adultTv = findViewById(R.id.adult_tv);
        TextView budgetTv = findViewById(R.id.budget_tv);
        TextView genresTv = findViewById(R.id.genres_tv);
        TextView homepageTv = findViewById(R.id.homepage_tv);
        TextView originalLanguageTv = findViewById(R.id.original_language_tv);
        TextView originalTitleTv = findViewById(R.id.original_title_tv);
        TextView overviewTv = findViewById(R.id.overview_tv);
        TextView popularityTv = findViewById(R.id.popularity_tv);
        TextView productionCompaniesTv = findViewById(R.id.production_companies_tv);
        TextView productionCountriesTv = findViewById(R.id.production_countries_tv);
        TextView releaseDateTv = findViewById(R.id.release_date_tv);
        TextView revenueTv = findViewById(R.id.revenue_tv);
        TextView runtimeTv = findViewById(R.id.runtime_tv);
        TextView spokenLanguagesTv = findViewById(R.id.spoken_languages_tv);
        TextView statusTv = findViewById(R.id.status_tv);
        TextView taglineTv = findViewById(R.id.tagline_tv);
        TextView voteAverageTv = findViewById(R.id.vote_average_tv);
        TextView voteCountTv = findViewById(R.id.vote_count_tv);

        ImageView posterIv = findViewById(R.id.poster_iv);
        ImageView backdropIv = findViewById(R.id.backdrop_iv);

        if (movieDetails.isAdult())
            adultTv.setText(R.string.yes);
        else
            adultTv.setText(R.string.no);

        fillTextViewWithValueOrDataUnavailable(budgetTv, String.valueOf(movieDetails.getBudget()));
        fillTextViewWithValueOrDataUnavailable(homepageTv, movieDetails.getHomepage());
        fillTextViewWithValueOrDataUnavailable(originalLanguageTv, movieDetails.getOriginalLanguage());
        fillTextViewWithValueOrDataUnavailable(originalTitleTv, movieDetails.getOriginalTitle());
        fillTextViewWithValueOrDataUnavailable(overviewTv, movieDetails.getOverview());
        fillTextViewWithValueOrDataUnavailable(popularityTv, String.valueOf(movieDetails.getPopularity()));
        fillTextViewWithValueOrDataUnavailable(releaseDateTv, movieDetails.getReleaseDate());
        fillTextViewWithValueOrDataUnavailable(revenueTv, String.valueOf(movieDetails.getRevenue()));
        fillTextViewWithValueOrDataUnavailable(runtimeTv, String.valueOf(movieDetails.getRuntime()));
        fillTextViewWithValueOrDataUnavailable(statusTv, movieDetails.getStatus());
        fillTextViewWithValueOrDataUnavailable(taglineTv, movieDetails.getTagline());
        fillTextViewWithValueOrDataUnavailable(voteAverageTv, String.valueOf(movieDetails.getVoteAverage()));
        fillTextViewWithValueOrDataUnavailable(voteCountTv, String.valueOf(movieDetails.getVoteCount()));

        fillTextViewWithListData(genresTv, movieDetails.getGenres());
        fillTextViewWithListData(productionCompaniesTv, movieDetails.getProductionCompanies());
        fillTextViewWithListData(productionCountriesTv, movieDetails.getProductionCountries());
        fillTextViewWithListData(spokenLanguagesTv, movieDetails.getSpokenLanguages());

        setTitle(movieDetails.getTitle());

        Uri moviePosterUri = NetworkUtils.buildMovieImageUri(movieDetails.getPosterPath());
        Picasso.with(DetailActivity.this)
                .load(moviePosterUri)
                .into(posterIv);

        Uri movieBackdropUri = NetworkUtils.buildMovieImageUri(movieDetails.getBackdropPath());
        Picasso.with(DetailActivity.this)
                .load(movieBackdropUri)
                .into(backdropIv);
    }

    private void fillTextViewWithValueOrDataUnavailable(TextView textView, String value) {
        if (value == null || value.isEmpty()) {
            textView.setText(R.string.data_unavailable);
            return;
        }

        textView.setText(value);
    }

    private <T extends IUIAppendableObject> void fillTextViewWithListData(TextView textView, List<T> data) {
        if (data == null || data.isEmpty()) {
            textView.setText(R.string.data_unavailable);
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            String value = data.get(i).getUIAppendableValue();
            stringBuilder.append(value);

            if (i < data.size() - 1)
                stringBuilder.append("\n");
        }

        String valueToDisplay = stringBuilder.toString();
        fillTextViewWithValueOrDataUnavailable(textView, valueToDisplay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            int movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, DEFAULT_MOVIE_ID);
            Bundle loaderBundle = new Bundle();
            loaderBundle.putInt(LOADER_MOVIE_ID, movieId);

            mLoadingIndicator.setVisibility(View.VISIBLE);
            getSupportLoaderManager().restartLoader(MOVIE_DETAILS_LOADER_ID, loaderBundle, this);
            return true;
        }

        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}