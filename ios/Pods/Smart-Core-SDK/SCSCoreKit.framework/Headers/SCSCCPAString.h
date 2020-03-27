//
//  SCSCCPAString.h
//  SCSCoreKit
//
//  Created by Loïc GIRON DIT METAZ on 20/02/2020.
//  Copyright © 2020 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/// The version of the CCPA consent string.
typedef NS_ENUM(NSInteger, SCSCCPAStringVersion) {
    
    /// Unknown string version
    SCSCCPAStringVersionUnknown = -1,
    
    /// CCPA string version 1
    SCSCCPAStringVersionOne = 1
    
};

/**
 Class representing a CCPA consent string.
 */
@interface SCSCCPAString : NSObject

/// The consent string. It will be URL encoded or empty if invalid.
@property (nonatomic, readonly) NSString *CCPAString;

/// YES if the RAW consent string is valid, NO otherwise.
@property (nonatomic, readonly) BOOL isValid;

/// The version of the CCPA consent string.
@property (nonatomic, readonly) SCSCCPAStringVersion version;

/**
 Initialize a new instance of the CCPA consent string.
 
 @param rawCCPAString The RAW consent string that will be handled by the SCSCCPAString instance.
 @return An initialized instance of the CCPA consent string.
 */
- (instancetype)initWithCCPAString:(NSString *)rawCCPAString;

@end

NS_ASSUME_NONNULL_END
