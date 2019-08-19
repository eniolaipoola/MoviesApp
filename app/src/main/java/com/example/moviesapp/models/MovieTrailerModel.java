package com.example.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieTrailerModel {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("results")
    @Expose
    private List<TrailerResult> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TrailerResult> getResults() {
        return results;
    }

    public void setResults(List<TrailerResult> results) {
        this.results = results;
    }

    public class TrailerResult {

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("iso_639_1")
        @Expose
        private String iso_639_1;

        @SerializedName("iso_3166_1")
        @Expose
        private String iso_3166_1;

        @SerializedName("key")
        @Expose
        private String key;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("site")
        @Expose
        private String site;

        @SerializedName("size")
        @Expose
        private String size;

        @SerializedName("type")
        @Expose
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIso_639_1() {
            return iso_639_1;
        }

        public void setIso_639_1(String iso_639_1) {
            this.iso_639_1 = iso_639_1;
        }

        public String getIso_3166_1() {
            return iso_3166_1;
        }

        public void setIso_3166_1(String iso_3166_1) {
            this.iso_3166_1 = iso_3166_1;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
