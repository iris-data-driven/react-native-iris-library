//
//  IrisLibrary.h
//  IrisLibrary
//
//  Created by Thiago Brandt on 04/05/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
//#import <IrisSDKStatic/IrisSDKStatic.h>


@interface IrisLibraryEvent : RCTEventEmitter <RCTBridgeModule>//, IrisNotificationOpenedDelegate, IrisNotificationReceivedDelegate>

- (void)irisEventReceived:(NSString *)eventName body:(NSDictionary *)body;

@end

