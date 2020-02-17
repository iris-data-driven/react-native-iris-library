# react-native-iris-library

## Getting started

`$ yarn add react-native-iris-library`

### Mostly automatic installation

`$ react-native link react-native-iris-library`
### iOS integration
`$ cd ios & pod install`
Add a .swift empty file and click "Create Bridging Header" from Xcode popup
Build and run.

Knowed issues:
* Error with *higher minimum deployment target* => set ios: 10.0 in Podfile and `pod install` again.
* Error *duplicated interface* => Pods > Development Pods > IrisLibrary > delete Pods folder inside IrisLibrary module.
* Error *Unable to resolve module scheduler* => clean cache from Metro Bundler using yarn.

### Android integration
Open the build.gradle file from project
add `classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61"` inside "Dependencies"
add `maven {url 'https://iris-mobile-repo.s3.amazonaws.com/android/'}`

## Usage
Add notification configuration from native docs:
- iOS - https://iris-mobile-repo.s3.amazonaws.com/documtantions/ios.html
- Android - https://iris-mobile-repo.s3.amazonaws.com/documtantions/android.html

```javascript
import IrisLibrary from 'react-native-iris-library';

// TODO: What to do with the module?
IrisLibrary;
```
