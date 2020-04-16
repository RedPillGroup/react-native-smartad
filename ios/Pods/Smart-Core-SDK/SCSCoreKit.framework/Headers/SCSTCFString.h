//
//  SCSTCFString.h
//  SCSCoreKit
//
//  Created by glaubier on 03/02/2020.
//  Copyright © 2020 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/// The version of the IAB TCF consent string.
typedef NS_ENUM(NSInteger, SCSTCFStringTCFVersion) {
    
    /// Unknown string version
    SCSTCFStringTCFVersionUnknown = -1,
    
    /// TCF string version 1
    SCSTCFStringTCFVersionOne = 1,
    
    /// TCF string version 2
    SCSTCFStringTCFVersionTwo = 2
    
};

/**
 Class representing a IAB TCF consent string.
 */
@interface SCSTCFString : NSObject

/// The consent string. It will be URL encoded or empty if invalid.
@property (nonatomic, readonly) NSString *TCFString;

/// YES if the RAW consent string is valid, NO otherwise.
@property (nonatomic, readonly) BOOL isValid;

/// The version of the TCF consent string.
@property (nonatomic, readonly) SCSTCFStringTCFVersion version;

/**
 Initialize a new instance of the TCF consent string.
 
 @param rawTCFString The RAW consent string that will be handled by the SCSTCFString instance.
 @return An initialized instance of the TCF consent string.
 */
- (instancetype)initWithTCFString:(NSString *)rawTCFString;

@end

NS_ASSUME_NONNULL_END
