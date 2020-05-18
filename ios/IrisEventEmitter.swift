//
//  IrisEventEmitter.swift
//  IrisLibrary
//
//  Created by Thiago Brandt on 05/05/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation

class IrisEventEmitter {
    public static var instance = IrisEventEmitter()
    private static var eventEmitter: ReactNativeEventEmitter!
    private init() {}
    
    func register(_ eventEmitter: ReactNativeEventEmitter) {
        IrisEventEmitter.eventEmitter = eventEmitter
    }
    func dispatch(name: String, body: Any?) {
        IrisEventEmitter.eventEmitter.sendEvent(withName: name, body: body)
    }
    lazy var allEvents: [String] = {
        var allEventNames: [String] = []
        allEventNames.append("didReceivedDeepLink")
        
        return allEventNames
    }()
}

