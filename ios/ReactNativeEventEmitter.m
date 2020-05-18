//
//  ReactNativeEventEmitter.m
//  IrisLibrary
//
//  Created by Thiago Brandt on 05/05/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_MODULE(ReactNativeEventEmitter, RCTEventEmitter)
RCT_EXTERN_METHOD(supportedEvents)
@end

