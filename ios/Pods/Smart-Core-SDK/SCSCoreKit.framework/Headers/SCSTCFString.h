//
//  SCSTCFString.h
//  SCSCoreKit
//
//  Created by glaubier on 03/02/2020.
//  Copyright © 2020 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/// The TCF Version of the current consent string.
typedef NS_ENUM(NSInteger, SCSTCFStringTCFVersion) {
    
    /// TCF string version 1
    SCSTCFStringTCFVersionOne = 1
    
};

/**
 Class representing a IAB TCF consent string.
 */
@interface SCSTCFString : NSObject

/**
 Initialize a new instance of the TCF consent string.
 
 @param rawTCFString The RAW consent string that will be handled by the SCSTCFString instance.
 @return An initialized instance of the TCF consent string.
 */
- (instancetype)initWithTCFString:(NSString *)rawTCFString;

/// The URL consent string. It will be URL encoded or empty if invalid.
@property (nonatomic, strong, readonly) NSString *TCFString;

/// YES if the RAW consent string is valid, NO otherwise.
@property (nonatomic, assign, readonly) BOOL isValid;

/// The TCF version of the consent string.
@property (nonatomic, assign, readonly) SCSTCFStringTCFVersion version;

@end

NS_ASSUME_NONNULL_END
