//
//  IrisInits.swift
//  IrisLibrary
//
//  Created by Thiago Rodrigo Da Costa Brandt on 30/01/20.
//  Copyright © 2020 Facebook. All rights reserved.
//
import IrisSDKStatic

@objc(IrisLibrary)
class IrisLibrary: NSObject {
  @objc
  func initNotifications() -> Void {
    DispatchQueue.main.async {
        let launchOptions: [AnyHashable: Any] = [:]
        let notify = IrisNotify()
        notify.delegate = UIApplication.shared.delegate as? PushDeepLinkingDelegate
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
        IrisEnv.default.set(.homolog)
        let newUser = try? JSONSerialization.data(withJSONObject: user, options: .prettyPrinted)
        if let newUserData  = newUser {
            IrisNotify.create(user: newUserData)
            print("Received object from JS")
        } else {
                print("Cannot convert Dictionary to Data")
            }
    }
   @objc
    func getNotificationList(callback: ([NSDictionary]) -> Void) {
        var arrayDict = [NSDictionary]()
        let notificationList = IrisNotify.getNotifications()
        for notification in notificationList {
            var dict = [NSDictionary]()
            dict["title"] = notification.title
            dict["subtitle"] = notification.subtitle
            dict["body"] = notification.body
            dict["launchURL"] = notification.launchURL
            dict["notificationOpened"] = notification.read
            dict["imageURL"] = notification.att
            dict["notificationID"] = notification.notificationID
            arrayDict.append(dict)
        }
            completion(arrayDict)
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
        return IrisNotification(notificationID: dict["notificationID"],
                                title: dict["title"],
                                subtitle: dict["subtitle"],
                                body: dict["body"],
                                launchURL: dict["launchURL"],
                                read: dict["notificationOpened"],
                                rawPayload: ["id" : dict["imageURL"]])
    }
}


