package com.ibrahim.flickerbrowser;

class Photo {

    private String mTitle;
    private String mAuthor;
    private String mAutherId;
    private String mLink;
    private String mTags;
    private String mImage;

    public Photo(String title, String author, String autherId, String link, String tags, String image) {
        mTitle = title;
        mAuthor = author;
        mAutherId = autherId;
        mLink = link;
        mTags = tags;
        mImage = image;
    }


    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getAutherId() {
        return mAutherId;
    }

    public String getLink() {
        return mLink;
    }

    public String getTags() {
        return mTags;
    }

    public String getImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAutherId='" + mAutherId + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }
}
