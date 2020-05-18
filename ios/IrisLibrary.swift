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
    let launchOptions: [AnyHashable: Any] = [:]
    let notify = IrisNotify()
      notify.initWithCallbacks(launchOptions)
      IrisNotify.promptForPushNotifications { accepted in
        print("User accepted notifications: \(accepted)")
      }
      print("Notification Service initialized")
  }
  @objc
  func initGeolocation() -> Void {
    DispatchQueue.main.async {
      let launchOptions: [AnyHashable: Any] = [:]
      let geofence = IrisGeotrigger(launchOptions)
      geofence.start()
      print("Geolocation Service initialized")
    }
  }
  
  @objc
  func sendTag(_ key: String, value: String) -> Void {
    IrisNotify.sendTag(key, value: value)
    print("Tag sended to service")
  }
  
  @objc
    func addCustomer() -> Void {
    
    }
    
    
  @objc
  static func requiresMainQueueSetup() -> Bool {
    return false
  }
  
}
  
