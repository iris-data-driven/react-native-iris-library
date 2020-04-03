# react-native-iris-library

## Getting started

`$ yarn add react-native-iris-library`

### Mostly automatic installation

`$ react-native link react-native-iris-library`
### iOS integration
Add to your podfile (in case you already had use of one of the frameworks, you don't have to add it)
```
pod 'PlotPlugin','2.6.1
pod 'OneSignal', '~>2.11.0'
```

`$ cd ios & pod install`
Add a .swift empty file (Xcode > File > New > File > .swift) and click "Create Bridging Header" from Xcode popup
Build and run.

Knowed issues:
* Error with *higher minimum deployment target* => set ios: 10.0 in Podfile and `pod install` again.
* Error *duplicated interface* => Pods > Development Pods > IrisLibrary > delete Pods folder inside IrisLibrary module.
* Error *Unable to resolve module scheduler* => clean cache from Metro Bundler using yarn.

### Android integration
Open the build.gradle file from app
```
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-kapt'

defaultConfig {
...
manifestPlaceholders = [
                onesignal_app_id               : '',
                onesignal_google_project_number: 'REMOTE'
        ]
...
}

dependencies {
...
    implementation project(':react-native-iris-library')
    implementation fileTree(dir: "libs", include: ["*.jar"])
    implementation "com.facebook.react:react-native:+"  // From node_modules

    implementation 'com.google.code.gson:gson:2.8.6'
    implementation ('com.onesignal:OneSignal:3.10.3') {
        exclude group: 'com.google.android.gms'
        exclude group: 'com.google.firebase'
    }
    implementation 'com.plotprojects:plot-android:3.10.0'
    implementation 'com.google.firebase:firebase-core:17.2.2'
    implementation 'com.google.firebase:firebase-messaging:20.1.0'

    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3'
    implementation 'com.squareup.retrofit2:retrofit:2.6.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.0.1'
    implementation 'com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2'
    implementation 'com.android.support:multidex:1.0.3'
...
}
```
Open the build.gradle file from project
add 
```
dependencies {
...

        classpath 'com.android.tools.build:gradle:3.5.3'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61"
        classpath 'com.google.gms:google-services:4.3.3'

...
}
allprojects {
    repositories {
...
        maven {
            url 'https://maven-repo.plotprojects.com/'
        }
        maven {
            url 'http://iris-mobile-repo.s3.amazonaws.com/android/release/'
            content{
                includeGroup "com.somosiris"
            }
        }
    }
}
```


## Usage
Add notification configuration from native docs:
- iOS     - https://iris-mobile-repo.s3.amazonaws.com/documentation/ios.html
if your integration with React Native, skip the Carthage configuration.
- Android - https://iris-mobile-repo.s3.amazonaws.com/documentation/android.html

```javascript
import NativeModules from 'react';

// TODO: What to do with the module?
Using inside RN code:

//To init Notification Service
NativeModules.IrisLibrary.initNotifications()

//To init Geolocation Service
NativeModules.IrisLibrary.initGeolocation()

//To send the tag id4all
NativeModules.IrisLibrary.sendTag("KEY", "VALUE") 
```
