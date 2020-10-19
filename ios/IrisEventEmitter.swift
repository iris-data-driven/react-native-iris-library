//
//  EventEmitter.swift
//  IrisLibrary
//
//  Created by Thiago Brandt on 09/10/20.
//

@objc(IrisEventEmitter)
class IrisEventEmitter: RCTEventEmitter {

    public static var shared: IrisEventEmitter?
    override init() {
        super.init()
        IrisEventEmitter.shared = self
    }
    override func supportedEvents() -> [String]! {
        let allEventNames: [String] = ["Iris-remoteNotificationOpened",
                                       "Iris-remoteNotificationReceived"]

        return allEventNames
    }
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }
}

