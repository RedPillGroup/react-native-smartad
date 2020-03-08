//
//  SCSVASTErrorRemoteLoggerProtocol.h
//  SCSCoreKit
//
//  Created by glaubier on 26/08/2019.
//  Copyright © 2019 Smart AdServer. All rights reserved.
//

NS_ASSUME_NONNULL_BEGIN

/**
 Public interface handling the VAST Error logging.
 */
@protocol SCSVASTErrorRemoteLoggerProtocol <NSObject>

/**
 Will trigger a log of the given error.
 
 @param name The name of the error.
 @param description The description of the error.
 @param VASTCode The VAST Code of the error.
 @param smartCode The Smart Code of the error.
 @param VASTResponse The VAST response associated with the error.
 @param optionalInfo Optional info about the error.
 */
- (void)logErrorWithName:(NSString *)name
             description:(NSString *)description
                VASTCode:(NSInteger)VASTCode
               smartCode:(NSInteger)smartCode
            VASTResponse:(nullable NSString *)VASTResponse
            optionalInfo:(nullable NSDictionary *)optionalInfo;

@end

NS_ASSUME_NONNULL_END
