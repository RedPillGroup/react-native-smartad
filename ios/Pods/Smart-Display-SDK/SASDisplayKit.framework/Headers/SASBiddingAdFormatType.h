//
//  SASBiddingAdFormatType.h
//  SASDisplayKit
//
//  Created by Loïc GIRON DIT METAZ on 28/06/2019.
//  Copyright © 2019 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/// Valid ad format type for bidding ads.
typedef NS_ENUM(NSInteger, SASBiddingAdFormatType) {
    
    /// Banner type.
    SASBiddingAdFormatTypeBanner           = 0,
    
    /// Interstitial type.
    SASBiddingAdFormatTypeInterstitial     = 1,
    
    /// Rewarded video type.
    SASBiddingAdFormatTypeRewardedVideo    = 2
    
};

NS_ASSUME_NONNULL_END
