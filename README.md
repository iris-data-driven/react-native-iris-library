# react-native-iris-library
Working with Xcode 11.7 and Android Studio 4.0.1

### 0. Before installation

Configure the irisConfig.json (android) and Iris.plist (iOS) files with the service keys provided by the Iris CS Team
Add notification configuration from native docs:

- iOS     - https://iris-mobile-repo.s3.amazonaws.com/documentation/ios.html
ALERT: If you're integration with React Native, skip the Carthage configuration.

- Android - https://iris-mobile-repo.s3.amazonaws.com/documentation/android.html


## 1. Getting started

`$ yarn add react-native-iris-library`

### 2. Mostly automatic installation
### 3. If you use autolink, skip to 5th step
`$ react-native link react-native-iris-library`

### 4. iOS integration 
Add to your podfile (in case you already had use of one of the frameworks, you don't have to add it)
```
pod 'OneSignal'
```

`$ cd ios & pod install`
### 5. Swift integration
Add a .swift empty file (Xcode > File > New > File > .swift) and click "Create Bridging Header" from Xcode popup
Build and run.

Knowed issues:
* Error with *higher minimum deployment target* => set ios: 10.0 in Podfile and `pod install` again.
* Error *duplicated interface* => Pods > Development Pods > IrisLibrary > delete Pods folder inside IrisLibrary module.
* Error *Unable to resolve module scheduler* => clean cache from Metro Bundler using yarn.

### 1. Android integration
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
    implementation 'com.google.firebase:firebase-core:17.2.2'
    implementation 'com.google.firebase:firebase-messaging:20.1.0'
    implementation 'com.squareup.retrofit2:retrofit:2.6.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.0.1'
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
            url 'http://iris-mobile-repo.s3.amazonaws.com/android/release/'
            content{
                includeGroup "com.somosiris"
            }
        }
    }
}
```


```javascript
import IrisLibrary from 'react-native-iris-library';

// TODO: What to do with the module?
Using inside RN code:

//To init Notification Service inside componentDidMount
IrisLibrary.init();

//To send the tag id4all
IrisLibrary.sendTag("KEY", "VALUE")

//To create a new User for CDP Database
IrisLibrary.create(newUser)

newUser has to be an Object type:
 - *At least 1* of keys (email, phone, document)  is required
 - Key *id_source* is required
 - If you send some *address*, *city* and *uf* are required 
Object:
{
    "name" : "John Bean",
    "gender" : "M",
    "birthday" : "1990-10-10",
    "document" : "02426147016",
    "email" : "name@mail.com",
    "phone" : 55991234567,
    "id_source" : "myid_12356",
    "opt_in_email" : true,
    "opt_in_sms" : true,
    "addresses" : [
        {
          "street" : "Avenida Ipiranga",
          "city" : "São Paulo",
          "country" : "Brasil",
          "uf" : "SP",
          "nickname" : "Endereço Profissional",
          "complement" : "Andar 12",
          "zipcode" : "90040620",
          "neighborhood" : "Partenon",
          "number" : "9000"
        }
    ]
}

More info at: https://bulk.prod.api.somosiris.com/docs/#operation/insertUsers

//In order to receive push notification, use:
IrisLibrary.addCustomer(DOCUMENT_VALUE, PHONE_VALUE, MAIL_VALUE, "")


```

- Deep linking:
You can use the Additional Data json provided with the notification payload, to deep link the opened notification to some view in your app.

```javascript
  componentDidMount = () => {
    IrisLibrary.init();
    IrisLibrary.addEventListener('opened', this.onOpened);

  }
  componetnWillUnmount = () => {
    IrisLibrary.removeEventListener('opened', this.onOpened);
  }

  onOpened = (openResult) => {
    console.log('Deep Link', openResult.notification.payload.additionalData);
    //Use the additionalData to redirect the user to some specific view in the app
  }

```


- Notification Center:
We have a Notification Center handler. When the device receives a Push Notification, the notification data is stored locally in the device and it's acessible within this methods below:

- To get the notification list (if exists):
```javascript
IrisLibrary.getNotificationList( notificationList => {
               //handle array of notification objects
            });
```

- To delete all notifications:
```javascript
IrisLibrary.deleteAllNotifications();
```

- To update one notification:
```javascript
IrisLibrary.updateNotification(notificationObject);
```

- To delete one notificaiton:
```javascript
IrisLibrary.deleteNotification(notificationObject);
```

Alternatively, we have a component *NotificationCenter*, it consists in a View component with close and cleanAll button, and a list of notifications items, you can use it, edit it ou build your own component. Call it from a modal View:

```javascript
<View>
    <Modal 
    animationType="slide"
    transparent={true}
    visible={notificationModalVisible}
    onRequestClose={() => { 
        this.setNotificationModalVisible(false);
    }}>
        <View style={styles.modal}>
            <NotificationCenter onCreateClicked={this.setNotificationModalVisible}/>
        </View>
    </Modal>
</View>
```

If you can't call the component from the module, just copy da code in your project.

If something get wrong, please open an issue or contact 
