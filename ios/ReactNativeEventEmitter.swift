//
//  ReactNativeEventEmitter.swift
//  IrisLibrary
//
//  Created by Thiago Brandt on 18/05/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation

open class ReactNativeEventEmitter: RCTEventEmitter {
    
    override init(){
        super.init()
        IrisEventEmitter.instance.register(self)
    }
    @objc
    open override func supportedEvents() -> [String] {
        return IrisEventEmitter.instance.allEvents
    }
}
