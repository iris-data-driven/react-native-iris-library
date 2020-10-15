'use strict';

import { NativeModules, NativeEventEmitter, Platform } from 'react-native';
import invariant from 'invariant';

const RNIrisLibrary = NativeModules.IrisLibrary;
const eventIris = Platform.OS === 'android' ? RNIrisLibrary : NativeModules.IrisEventEmitter ;

const IRIS_REMOTE_NOTIFICATION_OPENED = 'Iris-remoteNotificationOpened';
const IRIS_REMOTE_NOTIFICATION_RECEIVED = 'Iris-remoteNotificationReceived';

const _eventBroadcastNames = [
    IRIS_REMOTE_NOTIFICATION_OPENED,
    IRIS_REMOTE_NOTIFICATION_RECEIVED,  
];

const NOTIFICATION_OPENED_EVENT = "opened";
const NOTIFICATION_RECEIVED_EVENT = "received";

const _eventNames = [
    NOTIFICATION_OPENED_EVENT,
    NOTIFICATION_RECEIVED_EVENT,
];

var irisEventEmitter;
var _eventTypeHandler = new Map();
var _notificationCache = new Map();
var _listeners = [];

if (RNIrisLibrary != null) {
    irisEventEmitter = new NativeEventEmitter(eventIris);

    for(var i = 0; i < _eventBroadcastNames.length; i++) {
        var eventBroadcastName = _eventBroadcastNames[i];
        var eventName = _eventNames[i];

        _listeners[eventName] = handleEventBroadcast(eventName, eventBroadcastName)
    }
}
function handleEventBroadcast(type, broadcast) {
    
    return irisEventEmitter.addListener(
        broadcast, (notification) => {
            // Check if we have added listener for this type yet
            // Cache the result first if we have not.
            var handler = _eventTypeHandler.get(type);

            if (handler) {
                handler(notification);
            } else {
                _notificationCache.set(type, notification);
            }
        }
    );
    
}
function checkIfInitialized() {
    return RNIrisLibrary != null;
}


export default class IrisLibrary {

    static addCustomer(cpf, phone, email) {
        if (!checkIfInitialized()) return;
        
        RNIrisLibrary.addCustomer(cpf, phone, email, null);
    }

    static create(user) {
        if (!checkIfInitialized()) return;

        RNIrisLibrary.create(user);
    }

    static getNotificationList(callback) {
        if (!checkIfInitialized()) return;

        invariant(
            typeof callback === 'function',
            'Must provide a valid callback'
        );
        
        RNIrisLibrary.getNotificationList(callback);
    }

    static updateNotification(notification) {
        if (!checkIfInitialized()) return;

        RNIrisLibrary.updateNotification(notification);
    }

    static deleteNotification(notification) {
        if (!checkIfInitialized()) return;

        RNIrisLibrary.deleteNotification(notification);
    }

    static deleteAllNotifications() {
        if (!checkIfInitialized()) return;

        RNIrisLibrary.deleteAllNotifications();
    }

    static addEventListener(type, handler) {
        if (!checkIfInitialized()) return;
        console.log("EVENT: ", type, "FUNCTION: ", handler)

        invariant(
            type === NOTIFICATION_RECEIVED_EVENT ||
            type === NOTIFICATION_OPENED_EVENT,
            'IrisLibrary only supports received/opened events'
        );
        
        _eventTypeHandler.set(type, handler);
        
        if (type === NOTIFICATION_OPENED_EVENT) {
            RNIrisLibrary.initNotificationOpenedHandlerParams();
        }

        var cache = _notificationCache.get(type);
        if (handler && cache) {
            handler(cache);
            _notificationCache.delete(type);
        }
    }

    static removeEventListener(type) {
        if (!checkIfInitialized()) return;

        invariant(
            type === NOTIFICATION_RECEIVED_EVENT ||
            type === NOTIFICATION_OPENED_EVENT,
            'IrisLibrary only supports opened events'
        );

        _eventTypeHandler.delete(type);
    }
    static clearListeners() {
        if (!checkIfInitialized()) return;

        for(var i = 0; i < _eventNames.length; i++) {
            _listeners[_eventNames].remove();
        }
    }
    
    static getPermissionSubscriptionState(callback) {
        if (!checkIfInitialized()) return;

        invariant(
            typeof callback === 'function',
            'Must provide a valid callback'
        );
        RNIrisLibrary.getPermissionSubscriptionState(callback);
    }

    static sendTag(key, value) {
        if (!checkIfInitialized()) return;

        if (typeof value === "boolean") {
            value = value.toString();
        }

        RNIrisLibrary.sendTag(key, value);
    }

    static init() {
        if (!checkIfInitialized()) return;
        RNIrisLibrary.initIris();
    }

}
