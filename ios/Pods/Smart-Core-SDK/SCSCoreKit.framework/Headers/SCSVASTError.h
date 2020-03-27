//
//  SCSVASTError.h
//  SCSCoreKit
//
//  Created by Loïc GIRON DIT METAZ on 05/09/2019.
//  Copyright © 2019 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

#define SCSVASTErrorNoErrorCode     -1
#define SCSVASTErrorUserInfoKey     @"VASTError"

/**
 Hold all the properties of a VAST error.
 
 Several errors can occur during VAST retrieval, parsing, wrapper resolution, …
 
 These errors have a different code depending if they are logged in public API (in which
 case they follow the IAB specification) or if they are logged in Smart technical backend.
 
 To avoid any mistake with these error codes, all usable errors are already defined as static
 methods of this class.
 */
@interface SCSVASTError : NSObject <NSCopying>

#pragma mark - Error properties

/// The name of the error.
@property (nonatomic, readonly) NSString *errorName;

/// The description of the error.
@property (nonatomic, readonly) NSString *errorDescription;

/// The VAST error code (compliant with IAB specification).
@property (nonatomic, readonly) NSInteger VASTCode;

/// The Smart internal error code.
@property (nonatomic, readonly) NSInteger smartCode;

/// The underlying NSError that represent the VAST error.
///
/// This error only contains the VAST code and the description
@property (nonatomic, readonly) NSError *error;

#pragma mark - Standard VAST Error Code

// These static methods allows to retrieve all valid VAST errors with there underlying
// Smart error code and description.
//
// All errors are described here:
// https://smartadserver.atlassian.net/wiki/x/ywF_T

// --------------------------------------- 100 ---------------------------------------

// 100

+ (SCSVASTError *)XML_PARSING_ERROR;

+ (SCSVASTError *)XML_PARSING_ERROR_WRAPPER;

// 101

+ (SCSVASTError *)VAST_VALIDATION_ERROR_MISSING_VERSION;

+ (SCSVASTError *)VAST_VALIDATION_ERROR_MISSING_IMPRESSION;

+ (SCSVASTError *)VAST_VALIDATION_ERROR_MISSING_INLINE_AND_WRAPPER;

+ (SCSVASTError *)VAST_VALIDATION_ERROR_MISSING_VASTADTAGURI;

+ (SCSVASTError *)VAST_VALIDATION_ERROR_MISSING_CREATIVES;

+ (SCSVASTError *)VAST_VALIDATION_ERROR_LINEAR_MISSING_MEDIAFILES;

+ (SCSVASTError *)VAST_VALIDATION_ERROR_NONLINEAR_MISSING_ATTRIBUTES;

+ (SCSVASTError *)VAST_VALIDATION_ERROR_NONLINEAR_MISSING_RESOURCE;

// 102

+ (SCSVASTError *)VAST_VERSION_ERROR_NOT_SUPPORTED;

+ (SCSVASTError *)VAST_VERSION_ERROR_NOT_SUPPORTED_WRAPPER;

// --------------------------------------- 200 ---------------------------------------

// 201

+ (SCSVASTError *)VAST_CREATIVE_ERROR_MISMATCHED_AD_LINEARITY;

// 202

+ (SCSVASTError *)VAST_CREATIVE_ERROR_MISMATCHED_AD_DURATION;

// --------------------------------------- 300 ---------------------------------------

// 300

+ (SCSVASTError *)VAST_WRAPPER_ERROR;

// 301

+ (SCSVASTError *)VAST_WRAPPER_ERROR_FETCH;

+ (SCSVASTError *)VAST_WRAPPER_ERROR_FETCH_CORS;

+ (SCSVASTError *)VAST_WRAPPER_ERROR_FETCH_TIMEOUT;

// 302

+ (SCSVASTError *)VAST_WRAPPER_ERROR_LIMIT_REACHED;

// 303

+ (SCSVASTError *)VAST_WRAPPER_ERROR_NOT_VAST_RESPONSE;

// 304

+ (SCSVASTError *)VAST_WRAPPER_ERROR_INLINE_TIMEOUT;

// --------------------------------------- 400 ---------------------------------------

// 400

+ (SCSVASTError *)VAST_LINEAR_ERROR_GENERAL;

// 401

+ (SCSVASTError *)VAST_LINEAR_ERROR_MEDIA_UNABLE_TO_LOAD;

// 402

+ (SCSVASTError *)VAST_LINEAR_ERROR_MEDIA_FETCH_TIMEOUT;

// 403

+ (SCSVASTError *)VAST_LINEAR_ERROR_MEDIA_NO_VALID_FORMAT;

// 405

+ (SCSVASTError *)VAST_LINEAR_ERROR_MEDIA_UNABLE_TO_DISPLAY;

// 408

+ (SCSVASTError *)VAST_LINEAR_ERROR_CONDITIONAL_AD_REJECTED;

// 409

+ (SCSVASTError *)VAST_LINEAR_ERROR_INTERACTIVE_FILE_NOT_EXECUTED;

// --------------------------------------- 500 ---------------------------------------

// 501

+ (SCSVASTError *)VAST_NONLINEAR_ERROR_MISMATCHED_DIMENSIONS;

// 502

+ (SCSVASTError *)VAST_NONLINEAR_ERROR_FETCH;

// 503

+ (SCSVASTError *)VAST_NONLINEAR_ERROR_NO_VALID_FORMAT;

// --------------------------------------- 600 ---------------------------------------

// 600

+ (SCSVASTError *)VAST_COMPANION_ERROR_GENERAL;

// 603

+ (SCSVASTError *)VAST_COMPANION_ERROR_FETCH;

// 604

+ (SCSVASTError *)VAST_COMPANION_ERROR_NO_VALID_FORMAT;

// --------------------------------------- 900 ---------------------------------------

// 900

+ (SCSVASTError *)VAST_UNDEFINED_ERROR;

// 901

+ (SCSVASTError *)VAST_VPAID_ERROR;

+ (SCSVASTError *)VAST_VPAID_ERROR_FETCH;

+ (SCSVASTError *)VAST_VPAID_ERROR_PLAYBACK;

// -------------------------------------- SMART --------------------------------------

+ (SCSVASTError *)VAST_VALIDATION_ERROR_MISSING_ADSYSTEM;

+ (SCSVASTError *)VAST_VALIDATION_ERROR_MISSING_ADTITLE;

+ (SCSVASTError *)VAST_VALIDATION_ERROR_LINEAR_MISSING_DURATION;

+ (SCSVASTError *)SMART_VAST_ERROR_ADCALL_FETCH;

+ (SCSVASTError *)SMART_VAST_ERROR_ADCALL_FETCH_TIMEOUT;

+ (SCSVASTError *)SMART_VAST_ERROR_TOTAL_TIMEOUT;

#pragma mark - Initializers

- (instancetype)init NS_UNAVAILABLE;

@end

NS_ASSUME_NONNULL_END
