//
//  SCSLogNode.h
//  SCSCoreKit
//
//  Created by glaubier on 25/03/2019.
//  Copyright © 2019 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/**
 Abstract class representing a node of a SCSRemoteLog.
 */
@interface SCSLogNode : NSObject

/**
 Return the node's name.
 */
- (nonnull NSString *)name;

/**
 Return a dictionary representing the JSON of the node.
 */
- (nullable NSDictionary *)jsonDictionary;

@end

NS_ASSUME_NONNULL_END
