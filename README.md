Popular Movies App

This is a project from Udacity Android Developer Nanodegree Program


Project Summary

Most of us can relate to kicking back on the couch and enjoying a movie with friends and family.
This project is an app that allow users to discover the most popular starredMovies playing.

The app upon launch, present the user with a grid arrangement of movie posters. It allows the user
to change sort order via a setting. The sort order can be by most popular, top rated, or by starred.
It allows the user to tap on a movie poster and transition to a details screen with additional information such as:
original title, movie poster image thumbnail, A plot synopsis (called overview in the api),user rating
(called vote_average in the api) and release date.

On the Details page, the user can star a movie as a favourite. All starred movies can be fetched on the home page by
sorting by starred via settings.

###Screenshots
![](https://user-images.githubusercontent.com/19291341/64474138-4ec75100-d168-11e9-81ae-97a5a1336422.jpg)

![](https://user-images.githubusercontent.com/19291341/64474136-4ec75100-d168-11e9-862d-75676f678453.jpg)

Details Page
![](https://user-images.githubusercontent.com/19291341/64474137-4ec75100-d168-11e9-8c50-3a2c1c7e2787.jpg)


The starredMovies are received from an API http://api.themoviedb.org/3/. To use this API, you need to sign up with
themoviesdb and generate an API_KEY which would be used for all api calls made in the application.

To ensure proper functioning of this app, generate the API key and place it in the APPConstant.java file,
API_PRIVATE_KEY variable.








