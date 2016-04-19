#import <Cordova/CDV.h>

@interface CDVNetworkInterface : CDVPlugin

- (void) getIPAddress:(CDVInvokedUrlCommand*)command;
- (void) getNetworkInfo:(CDVInvokedUrlCommand*)command;
- (void) getMacAddress:(CDVInvokedUrlCommand*)command;
- (void) getConnectedSSID:(CDVInvokedUrlCommand*)command;
- (void) getConnectedBSSID:(CDVInvokedUrlCommand*)command;

@end
