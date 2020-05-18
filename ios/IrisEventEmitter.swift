//
//  IrisEventEmitter.swift
//  IrisLibrary
//
//  Created by Thiago Brandt on 05/05/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation
@objc(IrisEventEmitter)
class IrisEventEmitter: NSObject {
    public static var instance = IrisEventEmitter()
    private static var eventEmitter: ReactNativeEventEmitter!
    private override init() {}
    
    func register(_ eventEmitter: ReactNativeEventEmitter) {
        IrisEventEmitter.eventEmitter = eventEmitter
    }
    @objc
    func dispatch(name: String, body: Any?) {
        IrisEventEmitter.eventEmitter.sendEvent(withName: name, body: body)
    }
    lazy var allEvents: [String] = {
        var allEventNames: [String] = []
        allEventNames.append("didReceivedDeepLink")
        
        return allEventNames
    }()
}

