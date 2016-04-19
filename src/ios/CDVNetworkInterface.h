#import <Cordova/CDV.h>

@interface CDVNetworkInterface : CDVPlugin

- (void) getIPAddress:(CDVInvokedUrlCommand*)command;
- (void) getNetworkInfo:(CDVInvokedUrlCommand*)command;
- (void) getMacAddress:(CDVInvokedUrlCommand*)command;

@end
