//
//  IrisInits.swift
//  IrisLibrary
//
//  Created by Thiago Rodrigo Da Costa Brandt on 30/01/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

import IrisSDK

@objc
class Iris: NSObject {
  @objc
   class func initNotifications(_ launchOptions: [AnyHashable: Any]) {
      let notify = IrisNotify()
      notify.initWithCallbacks(launchOptions)
      IrisNotify.promptForPushNotifications { accepted in
        print("User accepted notifications: \(accepted)")
      }
  }
 @objc
  class func geolocationService(_ launchOptions: [AnyHashable: Any]) {
    let geofence = IrisGeotrigger(launchOptions)
    geofence.start()
  }
  
  
}
  