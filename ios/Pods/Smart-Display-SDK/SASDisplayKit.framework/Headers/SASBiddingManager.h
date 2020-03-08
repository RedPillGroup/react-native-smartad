//
//  SASBiddingManager.h
//  SASDisplayKit
//
//  Created by Loïc GIRON DIT METAZ on 17/06/2019.
//  Copyright © 2019 Smart AdServer. All rights reserved.
//

#import "SASAdPlacement.h"
#import "SASBiddingManagerDelegate.h"

NS_ASSUME_NONNULL_BEGIN

/**
 Class used to load an ad bidding response.
 
 An ad bidding response contains a price that you can compare with other ad networks responses. You can
 render the ad corresponding to a bidding response using a SASInterstitialManager, a SASRewardedVideoManager
 or a SASBannerView.
 */
@interface SASBiddingManager : NSObject

/// The ad placement on which the ad should be retrieved by the manager.
@property (nonatomic, readonly) SASAdPlacement *adPlacement;

/// The ad format that should be retrieved by the manager.
@property (nonatomic, readonly) SASBiddingAdFormatType biddingAdFormatType;

/// A object implementing the SASBiddingManagerDelegate protocol.
@property (nonatomic, weak, nullable) id<SASBiddingManagerDelegate> delegate;

/**
 Initialize a new instance of SASBiddingManager.
 
 @param adPlacement The ad placement on which the ad should be retrieved by the manager.
 @param biddingAdFormatType The ad format that should be retrieved by the manager.
 @param currency The currency that is requested for the bidding call (must be a 3 letters string compliant with ISO 4217).
 @param delegate A object implementing the SASBiddingManagerDelegate protocol.
 @return An initialized instance of SASBiddingManager.
 */
- (instancetype)initWithAdPlacement:(SASAdPlacement *)adPlacement
                biddingAdFormatType:(SASBiddingAdFormatType)biddingAdFormatType
                           currency:(NSString *)currency
                           delegate:(id<SASBiddingManagerDelegate>)delegate;

/**
 Load a bidding ad using the bidding manager.
 
 @warning You cannot load multiple ads at the same time, any attempt to do so will be ignored.
 */
- (void)load;

- (instancetype)init NS_UNAVAILABLE;

@end

NS_ASSUME_NONNULL_END
