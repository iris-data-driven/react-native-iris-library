#import <React/RCTBridgeModule.h>
#import "IrisLibrary.h"


@interface RCT_EXTERN_MODULE(IrisLibrary, NSObject)
RCT_EXTERN_METHOD(initIris)
// RCT_EXTERN_METHOD(initGeolocation)
RCT_EXTERN_METHOD(sendTag:(NSString *)key value:(NSString *)value)
RCT_EXTERN_METHOD(setHomolog)
RCT_EXTERN_METHOD(addCustomer:(nullable NSString *)phone cpf:(nullable NSString *)cpf email:(nullable NSString *)email source:(nullable NSString *)source)
RCT_EXTERN_METHOD(create:(NSDictionary * )user)
RCT_EXTERN_METHOD(deleteAllNotifications)
RCT_EXTERN_METHOD(updateNotification:(NSDictionary *)notification)
RCT_EXTERN_METHOD(deleteNotification:(NSDictionary *)notification)
RCT_EXTERN_METHOD(getNotificationList:(RCTResponseSenderBlock)callback)
RCT_EXPORT_METHOD(initNotificationOpenedHandlerParams) {
    // Not implemented in iOS
}
@end

@implementation IrisLibraryEvent
{
    bool hasListeners;
}

RCT_EXPORT_MODULE();

+ (id)allocWithZone:(NSZone *)zone {
    static IrisLibraryEvent *sharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [super allocWithZone:zone];
    });
    return sharedInstance;
}
-(void)startObserving {
    hasListeners = YES;
    // Set up any upstream listeners or background tasks as necessary
}

// Will be called when this module's last listener is removed, or on dealloc.
-(void)stopObserving {
    hasListeners = NO;
    // Remove upstream listeners, stop unnecessary background tasks
}
- (NSArray<NSString *> *)supportedEvents
{
  return @[@"DeepLinkingReceived", @"Iris-remoteNotificationOpened", @"Iris-remoteNotificationReceived"];
}

- (void)irisEventReceived:(NSString *)eventName body:(NSDictionary *)body
{
  if(hasListeners){
        [self sendEventWithName:eventName body:body];
    }
}




/*
 AppDelegate.h
 
 #import <IrisSDKStatic/IrisSDKStatic-Swift.h>

 @interface AppDelegate : UIResponder <UIApplicationDelegate, RCTBridgeDelegate, PushDeepLinkingDelegate>
 
 
 AppDelegate.m
 #import <IrisLibrary.h>
 
 - (void)send:(NSDictionary * _Nonnull)dict {
 IrisLibraryEvent *notification =  [IrisLibraryEvent allocWithZone:nil];
 [notification irisEventReceived:dict];
 }
 */

@end

