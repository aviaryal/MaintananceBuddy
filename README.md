# MaintananceBuddy
An app where resident can request maintenance to the apartments office

## Features
* Realtime chatting with the manger
* Add request for maintenance 
* Upload pictures and video for request
* View the maintenance history
* Get new notifiaciotn 
* Maintain Account

## Getting Started

###Prerequisites

Android Studio Version 4.1
Minimum SDK version 19

Additonal Dependencies (on build.gradle) are below:

 // Import the BoM for the Firebase platform
    implementation platform('com.google.firebase:firebase-bom:26.0.0')
    // Declare the dependency for the Firebase Authentication library
    implementation 'com.google.firebase:firebase-auth-ktx'
    //Firebase database
    implementation 'com.google.firebase:firebase-database'
    // Add the dependency for the Firebase SDK for Google Analytics
    implementation 'com.google.firebase:firebase-analytics-ktx'
    implementation 'com.google.firebase:firebase-firestore-ktx'
    implementation 'com.google.firebase:firebase-storage:16.0.1'
    //Mulitdex error
    implementation 'com.android.support:multidex:1.0.3'
    //dependi for buttom navigation bar
    //Group adapter

    def groupie_version = "2.8.1"
    implementation "com.xwray:groupie:$groupie_version"
    implementation "com.xwray:groupie-kotlin-android-extensions:$groupie_version"
    implementation "com.xwray:groupie-viewbinding:$groupie_version"

    //implementation 'com.xwray:groupie:2.1.0'
    //Recycle view
    def recyclerview_version = "1.0.0"
    implementation "androidx.recyclerview:recyclerview:$recyclerview_version"
    implementation "androidx.recyclerview:recyclerview-selection:1.1.0-rc03"

    //picasso
    def picasso_version = "2.71828"
    implementation "com.squareup.picasso:picasso:$picasso_version"
    //circular screen
    implementation 'de.hdodenhof:circleimageview:3.1.0'

Also following should be enabled.
 multiDexEnabled true

 Building from source
 Clone the repository
 https://github.com/aviaryal/MaintananceBuddy.git
 *Open Android studio and select new project from file menu.
 *Select the import from git on the options
 * Wait for the Gradle sync to finish.Install additonal dependencies if asked to install
 * Hit the build buttom or run buttom to run the app in the emulator.
 
