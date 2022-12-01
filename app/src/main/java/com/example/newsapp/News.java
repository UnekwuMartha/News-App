package com.example.newsapp;

public class News {
    private final String title;
    private final String author;
    private final String section;
    private final String date;
    private final String url;

    public News(String mTitle, String mAuthor, String mSection, String mDate, String mUrl) {
        super();
        title = mTitle;
        author = mAuthor;
        section = mSection;
        date = mDate;
        url = mUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }
}