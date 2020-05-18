#import <React/RCTBridgeModule.h>


@interface RCT_EXTERN_MODULE(IrisLibrary, NSObject)
RCT_EXTERN_METHOD(initNotifications)
RCT_EXTERN_METHOD(initGeolocation)
RCT_EXTERN_METHOD(sendTag:(NSString *)key value:(NSString *)value)
RCT_EXTERN_METHOD(addCustomer:(nullable NSString *)phone cpf:(nullable NSString *)cpf email:(nullable NSString *)email source:(nullable NSString *)source)
@end

