# Popular Movies App
This is a project from Udacity Android Developer Nanodegree Program

### Project Summary
Most of us can relate to kicking back on the couch and enjoying a movie with friends and family.
This project is an app that allow users to discover the most popular starredMovies playing. The
app upon launch, presents the user with a grid arrangement of movie posters. It allows the user
to change sort order via a setting. The sort order are most popular, top rated, or starred.

It also allows the user to tap on a movie poster and transition to a details screen with additional information such as:
original title, movie poster image thumbnail, A plot synopsis (called overview in the api), user rating
(called vote_average in the api) and release date. On the Details page, the user can star a movie as a favourite.

### Features
- The application ensures all movies are displayed in a user friendly interface. It uses a variety of layout to
such as LinearLayout, ScrollView, RecyclerView, and ConstraintsLayout.
- All API calls are made using Retrofit

### Third Party API
The app fetches it's data from an API http://api.themoviedb.org/3/. To use this API, you need to sign up with
themoviesdb and generate an API_KEY which would be used for all api calls made in the application.

To ensure proper functioning of this app, generate the API key and place it in the APPConstant.java file,
API_PRIVATE_KEY variable.


### Images
1. Version 1.0
- Tablet View

<p align="center">
    <img src="/screenshots/screenshot_home_tab.png" alt=""/>
    <img src="/screenshots/screenshot_tab.png" alt=""/>
</p>

2. Version 1.1
- Mobile View
![HomePage](https://github.com/eniolaipoola/MoviesApp/assets/19291341/be3637a1-a9e7-4656-b078-eb55f0d1e1d0)
![Details Page](https://github.com/eniolaipoola/MoviesApp/assets/19291341/60f7d1d0-3252-42c5-b02e-067ddfba86d3)









