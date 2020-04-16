//
//  SASBiddingAdResponse.h
//  SASDisplayKit
//
//  Created by Loïc GIRON DIT METAZ on 13/06/2019.
//  Copyright © 2019 Smart AdServer. All rights reserved.
//

#import "SASAdPlacement.h"
#import "SASBiddingAdPrice.h"
#import "SASBiddingAdFormatType.h"

NS_ASSUME_NONNULL_BEGIN

/**
 Class representing the response of a bidding call.
 */
@interface SASBiddingAdResponse : NSObject <NSCopying, NSCoding>

/// The ad placement of the bidding ad.
@property (nonatomic, readonly) SASAdPlacement *adPlacement;

/// The format type of the bidding ad.
@property (nonatomic, readonly) SASBiddingAdFormatType biddingAdFormatType;

/// The price of the bidding ad.
@property (nonatomic, readonly) SASBiddingAdPrice *biddingAdPrice;

/// NO if the bidding ad response has not be used before, YES otherwise.
///
/// @note A bidding ad response can only be used once. Trying to load an ad using
/// a consumed response will trigger an error immediately.
@property (nonatomic, readonly) BOOL isConsumed;

- (instancetype)init NS_UNAVAILABLE;

@end

NS_ASSUME_NONNULL_END
