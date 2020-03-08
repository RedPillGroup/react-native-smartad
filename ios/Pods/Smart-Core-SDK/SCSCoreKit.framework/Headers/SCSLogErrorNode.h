//
//  SCSLogErrorNode.h
//  SCSCoreKit
//
//  Created by glaubier on 25/03/2019.
//  Copyright © 2019 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SCSLogNode.h"

NS_ASSUME_NONNULL_BEGIN

@interface SCSLogErrorNode : SCSLogNode

- (instancetype)init NS_UNAVAILABLE;

/**
 Initialize the error node with all needed information.
 
 @param message    The error message to log.
 @param adResponse The adResponse associated with the log, if any.
 @param timeout    The timeout setting associated with the log, if any.
 
 @return A fully initialized instance of SCSLogErrorNode.
 */
- (instancetype) initWithErrorMessage:(NSString *)message
                           adResponse:(nullable NSString *)adResponse
                   timeoutSettingTime:(NSTimeInterval)timeout;

@end

NS_ASSUME_NONNULL_END
