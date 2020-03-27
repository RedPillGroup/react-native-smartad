//
//  SASBiddingAdPrice.h
//  SASDisplayKit
//
//  Created by Loïc GIRON DIT METAZ on 13/06/2019.
//  Copyright © 2019 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/**
 Class representing the price of a bidding ad response.
 */
@interface SASBiddingAdPrice : NSObject <NSCopying, NSCoding>

/// The price cpm of the bidding ad.
@property (nonatomic, readonly) double cpm;

/// The currency of the bidding ad.
@property (nonatomic, readonly) NSString *currency;

- (instancetype)init NS_UNAVAILABLE;

@end

NS_ASSUME_NONNULL_END
