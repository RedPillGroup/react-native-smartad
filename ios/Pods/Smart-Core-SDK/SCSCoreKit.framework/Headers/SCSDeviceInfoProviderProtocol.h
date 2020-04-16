//
//  SCSDeviceInfoProviderProtocol.h
//  SCSCoreKit
//
//  Created by Thomas Geley on 23/03/2017.
//  Copyright © 2017 Smart AdServer. All rights reserved.
//
#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@protocol SCSLocationProviderDelegate;

/**
 Protocol that must be implemented by all classes capable of retrieving informations for SCSDeviceInfo class.
 */
@protocol SCSDeviceInfoProviderProtocol <NSObject>

/**
 Returns the device platform/modelName.
 
 @return The device platform/modelName.
 */
- (NSString *)currentPlatform;

/**
 Returns the device system version.
 
 @return The device system version.
 */
- (NSString *)currentSystem;

/**
 Returns whether or not the device can play 360° videos.
 
 @return Whether or not the device can play 360° videos.
 */
- (BOOL)deviceCanPlay360Videos;

/**
 Return the user agent of the phone webview.
 
 @return The user agent of the webview.
 */
- (NSString *)webviewUserAgent;

/**
 Return the current local IP address of the device (Wi-Fi / en0 interface).
 
 @return The current local IP address of the device (Wi-Fi / en0 interface).
 */
- (NSString *)IPAddress;

/**
 Return YES if advertising tracking is enabled in the system settings, NO otherwise.
 
 @return YES if advertising tracking is enabled in the system settings, NO otherwise.
 */
- (BOOL)advertisingTrackingEnabled;

/**
 Return the device system locale.
 
 @return The device system locale.
 */
- (NSString *)locale;

@end

NS_ASSUME_NONNULL_END
