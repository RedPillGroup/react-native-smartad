//
//  SCSLogSDKNode.h
//  SCSCoreKit
//
//  Created by glaubier on 25/03/2019.
//  Copyright © 2019 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SCSLogNode.h"
#import "SCSIdentity.h"
#import <UIKit/UIKit.h>
#import "SCSDeviceInfo.h"
#import "SCSLocation.h"
#import "SCSAppInfo.h"
#import <AdSupport/AdSupport.h>
#import "SCSRemoteLogger.h"
#import "SCSTCFString.h"

NS_ASSUME_NONNULL_BEGIN

@interface SCSLogSDKNode : SCSLogNode

- (instancetype)init NS_UNAVAILABLE;

/**
 Initialize the SDK node with all needed information.
 
 @param sdkName             The name of the SDK.
 @param sdkVersion          The version of the SDK.
 @param sdkVersionID        The version ID of the SDK.
 @param appName             The application name.
 @param appBundleID         The application bundle identifier.
 @param platform            The current platform name.
 @param systemVersion       The current system version.
 @param identityType        The SCSIdentityType of the current SCSIdentity instance.
 @param adTrackingEnabled   Either the Advertising Tracking is enabled or not.
 @param gdprConsentString   The current gdprConsentString (if any).
 @param reachabilityStatus  The current reachability status. Is nullable.
 
 @return an Initialized instance of SCSLogSDKNode.
 */
- (instancetype)initWithSDKName:(NSString *)sdkName
                     sdkVersion:(NSString *)sdkVersion
                   sdkVersionID:(NSUInteger)sdkVersionID
                        appName:(NSString *)appName
                    appBundleID:(NSString *)appBundleID
                       platform:(NSString *)platform
                  systemVersion:(NSString *)systemVersion
                   identityType:(SCSIdentityType)identityType
              adTrackingEnabled:(BOOL)adTrackingEnabled
              gdprConsentString:(nullable NSString *)gdprConsentString
             reachabilityStatus:(nullable NSString *)reachabilityStatus __deprecated;

/**
 Initialize the SDK node with all needed information.
 
 @param sdkName             The name of the SDK.
 @param sdkVersion          The version of the SDK.
 @param sdkVersionID        The version ID of the SDK.
 @param implementationType  The current implementation type of the SDK.
 @param appName             The application name.
 @param appBundleID         The application bundle identifier.
 @param platform            The current platform name.
 @param systemVersion       The current system version.
 @param identityType        The SCSIdentityType of the current SCSIdentity instance.
 @param adTrackingEnabled   Either the Advertising Tracking is enabled or not.
 @param gdprConsentString   The current gdprConsentString (if any).
 @param reachabilityStatus  The current reachability status. Is nullable.
 
 @return an Initialized instance of SCSLogSDKNode.
 */
- (instancetype)initWithSDKName:(NSString *)sdkName
                     sdkVersion:(NSString *)sdkVersion
                   sdkVersionID:(NSUInteger)sdkVersionID
             implementationType:(SCSRemoteLogSDKImplementationType)implementationType
                        appName:(NSString *)appName
                    appBundleID:(NSString *)appBundleID
                       platform:(NSString *)platform
                  systemVersion:(NSString *)systemVersion
                   identityType:(SCSIdentityType)identityType
              adTrackingEnabled:(BOOL)adTrackingEnabled
              gdprConsentString:(nullable NSString *)gdprConsentString
             reachabilityStatus:(nullable NSString *)reachabilityStatus __deprecated;

/**
 Initialize the SDK node with all needed information.
 
 @param sdkName            The name of the SDK.
 @param sdkVersion         The version of the SDK.
 @param sdkVersionID       The version ID of the SDK.
 @param implementationType The current implementation type of the SDK.
 @param appName            The application name.
 @param appBundleID        The application bundle identifier.
 @param platform           The current platform name.
 @param systemVersion      The current system version.
 @param identityType       The SCSIdentityType of the current SCSIdentity instance.
 @param adTrackingEnabled  Either the Advertising Tracking is enabled or not.
 @param TCFString          The current TCF String.
 @param isTCFStringValid   The validity of the given TCFString, either YES or NO.
 @param TCFStringVersion   The TCF Version of the given gdprConsentString.
 @param reachabilityStatus The current reachability status. Is nullable.
 
 @return an Initialized instance of SCSLogSDKNode.
 */
- (instancetype)initWithSDKName:(NSString *)sdkName
                     sdkVersion:(NSString *)sdkVersion
                   sdkVersionID:(NSUInteger)sdkVersionID
             implementationType:(SCSRemoteLogSDKImplementationType)implementationType
                        appName:(NSString *)appName
                    appBundleID:(NSString *)appBundleID
                       platform:(NSString *)platform
                  systemVersion:(NSString *)systemVersion
                   identityType:(SCSIdentityType)identityType
              adTrackingEnabled:(BOOL)adTrackingEnabled
                      TCFString:(NSString *)TCFString
               isTCFStringValid:(BOOL)isTCFStringValid
               TCFStringVersion:(SCSTCFStringTCFVersion)TCFStringVersion
             reachabilityStatus:(nullable NSString *)reachabilityStatus NS_DESIGNATED_INITIALIZER;

@end

NS_ASSUME_NONNULL_END
