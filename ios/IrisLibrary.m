#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(IrisLibrary, NSObject)
RCT_EXTERN_METHOD(initNotifications:(NSDictionary * _Nullable)launchOptions)
RCT_EXTERN_METHOD(initGeolocation:(NSDictionary * _Nullable)launchOptions)
RCT_EXTERN_METHOD(sendTag:(NSString *)key value:(NSString *)value)
@end

