//
//  IrisInits.swift
//  IrisLibrary
//
//  Created by Thiago Rodrigo Da Costa Brandt on 30/01/20.
//  Copyright © 2020 Facebook. All rights reserved.
//
import IrisSDKStatic
import OneSignal

@objc(IrisLibrary)
class IrisLibrary: NSObject, IrisNotificationOpenedDelegate, IrisNotificationReceivedDelegate {
    func notificationOpened(_ payload: OSNotificationOpenedResult) {
        guard let notification = objToJson(payload.stringify()) else {return}
        IrisEventEmitter.shared?.sendEvent(withName: "Iris-remoteNotificationOpened", body: notification)
    }
    func objToJson(_ notificationString: String) -> Any? {
        guard let jsonData = notificationString.data(using: .utf8),
            let json = try? JSONSerialization.jsonObject(with: jsonData, options: .mutableContainers) else {return nil}
        return json
    }
    
    func notificationReceived(_ payload: OSNotification) {
        guard let notification = objToJson(payload.stringify()) else {return}
        IrisEventEmitter.shared?.sendEvent(withName: "Iris-remoteNotificationReceived", body: notification)
    }
    
    func send(_ dict: [AnyHashable : Any]) {
        IrisEventEmitter.shared?.sendEvent(withName: "DeepLinkingReceived", body: dict)
    }
    
    @objc
    func initIris() -> Void {
        initNotifications()
    }
    
    func initNotifications() -> Void {
        DispatchQueue.main.async {
            let launchOptions: [AnyHashable: Any] = [:]
            let notify = IrisNotify()
            notify.openedDelegate = self
            notify.receivedDelegate = self
            notify.initWithCallbacks(launchOptions)
            IrisNotify.promptForPushNotifications { accepted in
                print("User accepted notifications: \(accepted)")
            }
            print("Notification Service initialized")
        }
    }
    
    @objc
    func sendTag(_ key: String, value: String) -> Void {
        IrisNotify.sendTag(key, value: value)
        print("Tag sended to service")
    }
    
    @objc
    func addCustomer(_ phone: String, cpf: String, email: String, source: String) -> Void {
        IrisNotify.addCustomer(phone: phone, cpf: cpf, email: email, source: source)
    }
    @objc
    func setHomolog() -> Void {
        IrisEnv.default.set(.homolog)
    }
    @objc
    func create(_ user: NSDictionary) -> Void {
        let newUser = try? JSONSerialization.data(withJSONObject: user, options: .prettyPrinted)
        if let newUserData  = newUser {
            IrisNotify.create(user: newUserData)
            print("Received object from JS")
        } else {
            print("Cannot convert Dictionary to Data")
        }
    }
    @objc
    func getNotificationList(_ callback: RCTResponseSenderBlock) -> Void {
        var arrayDict = [NSDictionary]()
        let notificationList = IrisNotify.getNotifications()
        if notificationList != [] {
            for notification in notificationList {
                let dict = NSMutableDictionary()
                dict["title"] = notification.title
                dict["subtitle"] = notification.subtitle
                dict["body"] = notification.body
                dict["launchURL"] = notification.launchURL
                dict["notificationOpened"] = notification.read
                dict["imageURL"] = notification.att
                dict["notificationID"] = notification.notificationID
                arrayDict.append(dict)
            }
            callback([arrayDict])
        } else {
            print("No notifications to return")
            return
        }
    }
    @objc
    func deleteAllNotifications() -> Void {
        IrisNotify.deleteAllNotifications()
    }
    @objc
    func updateNotification(_ notification: NSDictionary) -> Void {
        let newNotification = toIrisNotification(notification)
        IrisNotify.updateNotification(newNotification)
    }
    @objc
    func deleteNotification(_ notification: NSDictionary) -> Void {
        let newNotification = toIrisNotification(notification)
        IrisNotify.deleteNotification(newNotification)
    }
    
    
    @objc
    static func requiresMainQueueSetup() -> Bool {
        return false
    }
    
    
    func toIrisNotification(_ dict: NSDictionary) -> IrisNotification {
        return IrisNotification(notificationID: dict["notificationID"] as? String  ?? "",
                                title: dict["title"] as? String ?? "",
                                subtitle: dict["subtitle"] as? String ?? "",
                                body: dict["body"] as? String ?? "",
                                launchURL: dict["launchURL"] as? String ?? "",
                                read: dict["notificationOpened"] as? Bool ?? true,
                                rawPayload: ["id" : dict["imageURL"] as? String ?? ""])
    }
}


