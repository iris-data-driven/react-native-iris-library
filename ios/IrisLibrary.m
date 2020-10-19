#import <React/RCTBridgeModule.h>
#import "IrisLibrary.h"


@interface RCT_EXTERN_MODULE(IrisLibrary, NSObject)
RCT_EXTERN_METHOD(initIris)
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

@interface RCT_EXTERN_MODULE(IrisEventEmitter, RCTEventEmitter)

RCT_EXTERN_METHOD(supportedEvents)

@end