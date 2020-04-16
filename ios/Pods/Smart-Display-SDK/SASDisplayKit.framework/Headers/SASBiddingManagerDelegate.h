//
//  SASBiddingManagerDelegate.h
//  SASDisplayKit
//
//  Created by Loïc GIRON DIT METAZ on 17/06/2019.
//  Copyright © 2019 Smart AdServer. All rights reserved.
//

#import "Foundation/Foundation.h"
#import "SASBiddingAdResponse.h"

NS_ASSUME_NONNULL_BEGIN

@class SASBiddingManager;

/**
 SASBiddingManager delegate protocol.
 
 Implement this protocol to be notified when an ad bidding call is successful or failed and
 to retrieve the ad response if any.
 */
@protocol SASBiddingManagerDelegate <NSObject>

/**
 Notifies the delegate that a valid ad response has been retrieved.
 
 @param biddingManager The instance of SASBiddingManager that called this delegate method.
 @param biddingAdResponse The ad response that have been retrieved by the manager.
 */
- (void)biddingManager:(SASBiddingManager *)biddingManager didLoadAdResponse:(SASBiddingAdResponse *)biddingAdResponse;

/**
 Notifies the delegate that an error occurred during ad response loading.
 
 @param biddingManager The instance of SASBiddingManager that called this delegate method.
 @param error The error that occurred during the ad bidding call.
 */
- (void)biddingManager:(SASBiddingManager *)biddingManager didFailToLoadWithError:(NSError *)error;

@end

NS_ASSUME_NONNULL_END
