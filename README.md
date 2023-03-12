# abda
abds is a sample movie listing app, built using
[Jetpack Compose](https://developer.android.com/jetpack/compose). The aim of this app is to
showcase the power and capabilities of Compose.

## Screenshots

<img src="screenshots/abda_ss.jpg" alt="Screenshot">

## Tools

### Pagination

The has been migrated to use the newly launched
[Pagination 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?authuser=2) library
to effectively load and display data whenever necessary.

### Retrofit

The app uses [OMDB API](https://www.omdbapi.com/) as its remote data source. it also uses Room 
database to store data locally and local caching in case of network failure.

###  Navigation

The app utilizes the new nav controller for compose to render different screens.

### State handling

A ViewModel acts as the primary state holder to perform business logic and update the
composable states.

- Flow, Live Data, Hilt, Glide are some of the other used tools.


## Features

 - The app contains two screens. The first one is the home screen where we can see the list of all
movies upon searching and view its details via a modal bottom sheet.

 - We can also filter the choices as per a particular type.

 - We also have the option to bookmark/favorite an item and save it locally. The second screen is
 the bookmark screen where we can see our bookmarked items. 