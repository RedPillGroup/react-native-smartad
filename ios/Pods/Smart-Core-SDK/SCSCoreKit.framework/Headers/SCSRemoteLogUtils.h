//
//  SCSRemoteLogUtils.h
//  SCSCoreKit
//
//  Created by glaubier on 03/05/2019.
//  Copyright © 2019 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SCSRemoteLog.h"
#import "SCSLogNode.h"

NS_ASSUME_NONNULL_BEGIN

@interface SCSRemoteLogUtils : NSObject

/**
 Create a JSON NSDictionary from the given log and the given nodes.
 
 @param log The log to use to create the JSON dictionary.
 @param nodes Possible additional nodes to put in the JSON dictionary.
 
 @return A NSDictionary representing the JSON of the log.
 */
+ (nullable NSDictionary *)generateJSONDictionaryWithLog:(SCSRemoteLog *)log nodes:(nullable NSArray<SCSLogNode *> *)nodes;

/**
 Create a JSON NSDictionary from the given log and the given dictionaries. Method created for legacy purpose, it has the old behavior of the RemoteLogger.
 
 @param log The log to use to create the JSON dictionary.
 @param dictionaries Possible additional dictionaries to put in the JSON dictionary.
 
 @return A NSDictionary representing the JSON of the log.
 */
+ (nullable NSDictionary *)generateJSONDictionaryWithLog:(SCSRemoteLog *)log dictionaries:(nullable NSArray<NSDictionary *> *)dictionaries __deprecated;

@end

NS_ASSUME_NONNULL_END
