//
//  SCSRemoteLogger.h
//  SCSCoreKit
//
//  Created by Thomas Geley on 18/09/2017.
//  Copyright © 2017 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SCSRemoteLog.h"
#import "SCSLogNode.h"
#import "SCSLocation.h"
#import "SCSIdentity.h"

NS_ASSUME_NONNULL_BEGIN

/// The channel type of the current ad.
typedef NS_ENUM(NSInteger, SCSRemoteLogSDKImplementationType) {
    SCSRemoteLogSDKImplementationTypeUnknown = -1,
    SCSRemoteLogSDKImplementationTypePrimary = 0,  // The current SDK is implemented as a Primary SDK
    SCSRemoteLogSDKImplementationTypeSecondary = 1 // The current SDK is implemented as a Secondary SDK
};

/**
 This class is used to log specific informations such as KPIs or errors.
 */
@interface SCSRemoteLogger : NSObject

#pragma mark - Readonly properties

/// URL where the logs should be posted.
@property (nonatomic, readonly) NSURL *endPointURL;

/// The minimum log level.
@property (nonatomic, readonly) SCSRemoteLogLevel minimumLogLevel;

/// Custom headers to be sent with each POST requests.
@property (nullable, nonatomic, readonly) NSArray <NSDictionary *> *customHeaders;

/// Sampling rate for Debug Level.
@property (nonatomic, readonly) NSUInteger debugSamplingRate;

/// Sampling rate for Info Level.
@property (nonatomic, readonly) NSUInteger infoSamplingRate;

/// Sampling rate for Warning Level.
@property (nonatomic, readonly) NSUInteger warningSamplingRate;

/// Sampling rate for Error Level.
@property (nonatomic, readonly) NSUInteger errorSamplingRate;

#pragma mark - Logger manipulation

/**
 Initialize a SCSRemoteLogger
 
 @param clientCategory The client SDK Category.
 
 @return an Initialized instance of SCSRemoteLogger.
 */
- (instancetype)initWithClientSDKCategory:(NSString *)clientCategory __deprecated;

/**
 Initialize a SCSRemoteLogger
 
 @param defaultEndPoint The end point URL that will be used by default until a remote config is received.
 @param clientCategory The client SDK Category.
 
 @return an Initialized instance of SCSRemoteLogger.
 */
- (instancetype)initWithDefaultEndPoint:(NSURL *)defaultEndPoint clientSDKCategory:(NSString *)clientCategory;

/**
 Configure the logger with a dictionary received from Smart servers.
 
 @param dictionary The configuration dictionary.
 */
- (void)configureWithDictionary:(NSDictionary *)dictionary;

/**
 Enqueue a SCSRemoteLog object to be posted.
 
 @param log The SCSRemoteLog to be posted.
 @param nodes An array of SCSLogNode to enrich the log with various informations pulled from the SDK.
 */
- (void)log:(SCSRemoteLog *)log enrichedWithNodes:(nullable NSArray<SCSLogNode *> *)nodes;

/**
 Enqueue a SCSRemoteLog object to be posted. This method is deprecated, please use the ones taking an array of SCSLogNode instead.
 
 @param log The SCSRemoteLog to be posted.
 @param dictionaries An array of Dictionaries to enrich the log with various informations pulled from the SDK.
 */
- (void)log:(SCSRemoteLog *)log enrichedWithDictionaries:(nullable NSArray<NSDictionary *> *)dictionaries __deprecated;

#pragma mark - SCSRemoteLog generation

/**
 Convenient method to initialize a SCSRemoteLog object with no type nor metric.
 If no SCSRemoteLog object is returned, it means the validation criteria to post the log (such as sampling level) were not met.
 
 @param message The message to be associated with the Log.
 @param level The level of the Log.
 
 @return An Initialized instance of a SCSRemoteLog object.
 */
- (nullable SCSRemoteLog *)generateLogWithMessage:(nullable NSString *)message level:(SCSRemoteLogLevel)level;

/**
 Convenient method to initialize a SCSRemoteLog object with no metric.
 If no SCSRemoteLog object is returned, it means the validation criteria to post the log (such as sampling level) were not met.
 
 @param message The message to be associated with the Log.
 @param level The level of the Log.
 @param type The type of the Log.
 
 @return An Initialized instance of a SCSRemoteLog object.
 */
- (nullable SCSRemoteLog *)generateLogWithMessage:(nullable NSString *)message level:(SCSRemoteLogLevel)level type:(nullable NSString *)type;

/**
 Convenient method to initialize a SCSRemoteLog object.
 If no SCSRemoteLog object is returned, it means the validation criteria to post the log (such as sampling level) were not met.
 
 @param message The message to be associated with the Log.
 @param level The level of the Log.
 @param type The type of the Log.
 @param metricType The metricType associated with the Log.
 @param metricValue The metricValue associated with the Log.
 
 @return An Initialized instance of a SCSRemoteLog object.
 */
- (nullable SCSRemoteLog *)generateLogWithMessage:(nullable NSString *)message level:(SCSRemoteLogLevel)level type:(nullable NSString *)type metricType:(nullable NSString *)metricType metricValue:(nullable NSString *)metricValue __deprecated;

/**
 Convenient method to initialize a SCSRemoteLog object.
 If no SCSRemoteLog object is returned, it means the validation criteria to post the log (such as sampling level) were not met.
 
 @param message The message to be associated with the Log.
 @param level The level of the Log.
 @param type The type of the Log.
 @param baseURLString The current baseURL.
 @param nodes The nodes of the log.

 @return An Initialized instance of a SCSRemoteLog object.
 */
- (nullable SCSRemoteLog *)generateLogWithMessage:(nullable NSString *)message level:(SCSRemoteLogLevel)level type:(nullable NSString *)type baseURLString:(nullable NSString *)baseURLString nodes:(nullable NSArray<SCSLogNode *> *)nodes;

- (instancetype)init NS_UNAVAILABLE;

@end

NS_ASSUME_NONNULL_END
