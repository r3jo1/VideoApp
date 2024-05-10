# Video App (Tlece Bangladesh Ltd Assessment) 

This project was created to complete the assessment for Tlece Bangladesh Ltd. The task was to "Create a simple Android application using Java/Kotlin."

## Technologies Used
- Language - Kotlin
- UI - XML
- Database - SQLite
- Architecture - MVVM
- Dependency Injection - Koin
- Image Loading - Glide
- Network - Retrofit
- Other Libraries - SwipeRefreshLayout
- Firebase - Firebase Cloud Messaging


## What I have done
- I created a MVVM structure where I used LiveData and ViewModel to handle the data. I used Repository pattern to handle the data from the database and network.
- The provided gist link had the revision id with it, which will not allow future updates of the data to be fetched. So, I removed the revision id from the URL and fetched the data directly from the gist using Retrofit.
- After the initial JSON parsing, I saved the data in the SQLite database. I used SQLite to store the data locally. The periodic checkings are done using ViewModel and Handler.
- My plan was to use work manager to do the periodic checking but I couldn't implement it due to time constraints, it doesn't support less than 15 minutes periodic checking.
- For the UI, I planned to created a 2 column grid layout to show the data. But to show the title and description of the data, I used a single column layout. I used SwipeRefreshLayout to refresh the data. I also added some other UI elements like Live icon, view count, etc.
- I created a basic Video Player Activity to play the video. It can be opened by clicking on the video thumbnail.
- Firebase Cloud Messaging is implemented to show notifications. I used the Firebase console to send notifications. Custom icon is provided for the notification. I also added required POST NOTIFICATION permission request for Android 13 and above.

## Download APK
The Release APK is available in the repository. You can download it from the following link:
 [Download APK](https://github.com/ahmmedrejowan/VideoApp/raw/master/app/release/app-release.apk)

## Screenshots

|Home|Player|Notification|
|---|---|---|
|  ![Shot1](https://raw.githubusercontent.com/ahmmedrejowan/VideoApp/master/files/shot2.png)  |  ![Shot2](https://raw.githubusercontent.com/ahmmedrejowan/VideoApp/master/files/shot3.png) | ![Shot3](https://raw.githubusercontent.com/ahmmedrejowan/VideoApp/master/files/shot1.png) |
