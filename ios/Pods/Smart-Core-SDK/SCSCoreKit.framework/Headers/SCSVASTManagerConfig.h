//
//  SCSVASTManagerConfig.h
//  SCSCoreKit
//
//  Created by Loïc GIRON DIT METAZ on 17/02/2020.
//  Copyright © 2020 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

#define kSCSVASTManagerConfigDefaultRequestTimeout                                  5.0
#define kSCSVASTManagerConfigDefaultMaximumWrapper                                  5
#define kSCSVASTManagerConfigDefaultHandleWrappersAdpods                            NO
#define kSCSVASTManagerConfigDefaultStrictWrapperFirstLevelImpressionValidation     NO

/**
 Configuration used by the VAST manager to parse the XML file.
 */
@interface SCSVASTManagerConfig : NSObject

/// The request timeout.
@property (nonatomic, assign) NSTimeInterval requestTimeout;

/// The maximum wrapper's depth during parsing.
@property (nonatomic, assign) NSInteger maximumWrappers;

/// YES to handle ad pod retrieved in wrappers, NO otherwise.
@property (nonatomic, assign) BOOL handleWrappersAdpods;

/// YES to enable strict first level impression validation for wrapper ads (inline ads
/// are always valid even if they have no impression), NO to disable it.
///
/// @note The VAST specification states that all first level ads must have an impression so
/// invalid ads should be rejected with the relevant VAST error. This configuration option
/// allows to disable this strict validation for cases where the impression is not needed
/// (for instance in the Display SDK where there will always be a top level impression
/// in the JSON response).
@property (nonatomic, assign) BOOL strictWrapperFirstLevelImpressionValidation;

- (instancetype)init;

@end

NS_ASSUME_NONNULL_END
