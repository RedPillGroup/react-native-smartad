//
//  SCSVASTErrorRemoteLoggerProtocol.h
//  SCSCoreKit
//
//  Created by glaubier on 26/08/2019.
//  Copyright © 2019 Smart AdServer. All rights reserved.
//

#import "SCSVASTError.h"

NS_ASSUME_NONNULL_BEGIN

/**
 Public interface handling the VAST Error logging.
 */
@protocol SCSVASTErrorRemoteLoggerProtocol <NSObject>

/**
 Will trigger a log of the given error.
 
 @param VASTError    A VAST error instance (that contains all relevant info and error codes).
 @param VASTResponse The VAST response associated with the error.
 @param optionalInfo Optional info about the error.
 */
- (void)logVASTError:(SCSVASTError *)VASTError
        VASTResponse:(nullable NSString *)VASTResponse
        optionalInfo:(nullable NSDictionary *)optionalInfo;

@end

NS_ASSUME_NONNULL_END
