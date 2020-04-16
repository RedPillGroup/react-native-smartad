//
//  SCSRemoteLog.h
//  SCSCoreKit
//
//  Created by Thomas Geley on 18/09/2017.
//  Copyright © 2017 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SCSLogMeasureNode.h"
#import "SCSLogErrorNode.h"
#import "SCSLogNode.h"

NS_ASSUME_NONNULL_BEGIN

/// The level for the Log object
typedef NS_ENUM(NSInteger, SCSRemoteLogLevel) {
    SCSRemoteLogLevelDebug = 0,
    SCSRemoteLogLevelInfo = 1,
    SCSRemoteLogLevelWarning = 2,
    SCSRemoteLogLevelError = 3,
    
    /// Level NONE means no log will be sent.
    SCSRemoteLogLevelNone = 4
};

/**
 Represent an object to be posted by the SCSRemoteLogger.
 Logic processing and enrichment occurs in SCSRemoteLogger subclasses from clients SDKs.
 */
@interface SCSRemoteLog : NSObject

/// The timestamp of the Log - Timezone UTC. Format:"YYYY-MM-DD'T'HH:MM:ss.SSS'ZZZ".
@property (nonatomic, readonly) NSString *timestamp;

/// The message associated with the Log.
@property (nullable, nonatomic, readonly) NSString *message;

/// The category (source) of the Log.
@property (nonatomic, readonly) NSString *category;

/// The host of the base url.
@property (nullable, nonatomic, readonly) NSString *host;

/// Integer used as a bool value representing either the Base URL is secured or not.
@property (nonatomic, readonly) NSNumber *secured;

/// The level of the Log.
@property (nonatomic, readonly) SCSRemoteLogLevel level;

/// The sampling rate of the Log.
@property (nonatomic, readonly) NSUInteger samplingRate;

/// The type of the Log.
@property (nullable, nonatomic, readonly) NSString *type;

/// The nodes associated with the Log.
@property (nullable, nonatomic, readonly) NSArray<SCSLogNode *> *nodes;

- (instancetype)init NS_UNAVAILABLE;

@end

NS_ASSUME_NONNULL_END
