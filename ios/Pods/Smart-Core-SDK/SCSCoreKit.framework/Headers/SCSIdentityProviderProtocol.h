//
//  SCSIdentityProviderProtocol.h
//  SCSCoreKit
//
//  Created by Thomas Geley on 23/03/2017.
//  Copyright © 2017 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>

@class SCSTransientID, SCSTCFString, SCSCCPAString;

NS_ASSUME_NONNULL_BEGIN

/**
 Protocol that must be implemented by all classes capable of retrieving ID for the SCSIdentity class.
 */
@protocol SCSIdentityProviderProtocol <NSObject>

/**
 Returns the advertising ID (the result can be empty or invalid depending of the OS version and whether
 the user has disabled the tracking in the Settings).
 
 @return The advertising ID or an invalid /empty ID.
 */
- (NSString *)advertisingID;

/**
 Retrieve the current transient ID and the last generation date (they can be nil if generateNewTransientID
 has never been called on the device).
 
 @return A SCSTransientID containing the transient ID and the last generation date if available, nil otherwise.
 */
- (nullable SCSTransientID *)retrieveTransientID;

/**
 Generate a new transient ID and returns it immediately. The transient ID generated is also saved in user settings and
 can be retrieved by calling retrieveTransientID later.
 
 @return The newly generated transient ID.
 */
- (NSString *)generateNewTransientID;

/**
 Returns the base64url encoded GDPR consent string stored in NSUserDefaults by any IAB certified CMP.
 
 @return The base64url encoded consent string.
 */
- (nullable NSString *)gdprConsentString;

/**
 Returns a SCSTCFString instance representing the GDPR consent string stored in NSUserDefaults by any IAB certified CMP.
 
 @return The SCSTCFString instance representing the GDPR TCF string.
 */
- (nullable SCSTCFString *)TCFString;

/**
Returns a SCSCCPAString instance representing the CCPA consent string stored in NSUserDefaults by any IAB certified CMP.

@return The SCSCCPAString instance representing the CCPA string.
*/
- (nullable SCSCCPAString *)CCPAString;

@end

NS_ASSUME_NONNULL_END
