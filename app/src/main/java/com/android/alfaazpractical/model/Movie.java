package com.android.alfaazpractical.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity
public class Movie implements Serializable {

    @PrimaryKey
    private int id;
    @SerializedName("popularity")
    @Expose
    @ColumnInfo(name = "popularity")
    private Double popularity;
    @SerializedName("vote_count")
    @Expose
    @ColumnInfo(name = "vote_count")
    private Integer voteCount;
    @SerializedName("release_date")
    @Expose
    @ColumnInfo(name = "release_date")
    private String releaseDate;
    @SerializedName("adult")
    @Expose
    @ColumnInfo(name = "adult")
    private Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;
    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    private String title;
    @SerializedName("original_language")
    @Expose
    @ColumnInfo(name = "original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    @ColumnInfo(name = "original_title")
    private String originalTitle;
    @SerializedName("poster_path")
    @Expose
    @ColumnInfo(name = "poster_path")
    private String posterPath;
    @SerializedName("overview")
    @Expose
    @ColumnInfo(name = "overview")
    private String overview;
    @SerializedName("video")
    @Expose
    @ColumnInfo(name = "video")
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

}
