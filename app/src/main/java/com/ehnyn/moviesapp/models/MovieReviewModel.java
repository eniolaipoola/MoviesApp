package com.ehnyn.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieReviewModel {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("page")
    @Expose
    private String page;

    @SerializedName("results")
    @Expose
    private List<MovieReviewResult> reviewResult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<MovieReviewResult> getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(List<MovieReviewResult> reviewResult) {
        this.reviewResult = reviewResult;
    }

    public class MovieReviewResult {

        @SerializedName("author")
        @Expose
        private  String author;

        @SerializedName("content")
        @Expose
        private String content;

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("url")
        @Expose
        private String url;

        @SerializedName("total_pages")
        @Expose
        private String totalPages;

        @SerializedName("total_results")
        @Expose
        private String totalResults;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(String totalPages) {
            this.totalPages = totalPages;
        }

        public String getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(String totalResults) {
            this.totalResults = totalResults;
        }
    }
}
