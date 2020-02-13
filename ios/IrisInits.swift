//
//  IrisInits.swift
//  IrisLibrary
//
//  Created by Thiago Rodrigo Da Costa Brandt on 30/01/20.
//  Copyright © 2020 Facebook. All rights reserved.
//
import IrisSDKStatic

@objc(RCTIris)
class RCTIris: NSObject {
  @objc(initNotifications:)
   class func initNotifications(_ launchOptions: [AnyHashable: Any]?) {
      let notify = IrisNotify()
      notify.initWithCallbacks(launchOptions)
      IrisNotify.promptForPushNotifications { accepted in
        print("User accepted notifications: \(accepted)")
      }
  }
  @objc(geolocationService:)
   class func geolocationService(_ launchOptions: [AnyHashable: Any]?) {
    let geofence = IrisGeotrigger(launchOptions)
    geofence.start()
  }
  
  @objc(sendTag::)
  class func sendTag(key: String, value: String) {
    IrisNotify.sendTag(key, value: value)
  }
  
  @objc
  static func requiresMainQueueSetup() -> Bool {
    return true
  }
  
}
  
