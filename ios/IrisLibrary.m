#import <React/RCTBridgeModule.h>
#import "IrisLibrary.h"


@interface RCT_EXTERN_MODULE(IrisLibrary, NSObject)
RCT_EXTERN_METHOD(initNotifications)
RCT_EXTERN_METHOD(initGeolocation)
RCT_EXTERN_METHOD(sendTag:(NSString *)key value:(NSString *)value)
RCT_EXTERN_METHOD(setHomolog)
RCT_EXTERN_METHOD(addCustomer:(nullable NSString *)phone cpf:(nullable NSString *)cpf email:(nullable NSString *)email source:(nullable NSString *)source)
@end

@implementation IrisLibraryEvent

RCT_EXPORT_MODULE();

+ (id)allocWithZone:(NSZone *)zone {
    static IrisLibraryEvent *sharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [super allocWithZone:zone];
    });
    return sharedInstance;
}

- (NSArray<NSString *> *)supportedEvents
{
  return @[@"DeepLinkingReceived"];
}

- (void)irisEventReceived:(NSDictionary *)dictionary
{
  [self sendEventWithName:@"DeepLinkingReceived" body:dictionary];
}

@end


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
 
