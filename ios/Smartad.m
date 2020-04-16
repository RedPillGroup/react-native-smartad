#import "Smartad.h"

NSString *const kSmartAdRewardedVideoNotReady = @"smartAdRewardedVideoNotReady";
NSString *const kSmartAdRewardedVideoAdLoaded = @"smartAdRewardedVideoAdLoaded";
NSString *const kSmartAdRewardedVideoAdFailedToLoad = @"smartAdRewardedVideoAdFailedToLoad";
NSString *const kSmartAdRewardedVideoAdShown = @"smartAdRewardedVideoAdShown";
NSString *const kSmartAdVideoAdFailedToShow = @"smartAdVideoAdFailedToShow";
NSString *const kSmartAdRewardedVideoAdClosed = @"smartAdRewardedVideoAdClosed";
NSString *const kSmartAdRewardReceived = @"smartAdRewardReceived";
NSString *const kSmartAdRewardNotReceived = @"smartAdRewardNotReceived";
NSString *const kSmartAdRewardedVideoAdClicked = @"smartAdRewardedVideoAdClicked";
NSString *const kSmartAdRewardedVideoEvent = @"smartAdRewardedVideoEvent";
NSString *const kSmartAdRewardedVideoEndCardDisplayed = @"smartAdRewardedVideoEndCardDisplayed";

#define kBaseURL @"https://mobile.smartadserver.com"

@interface Smartad () <SASRewardedVideoManagerDelegate>

@property SASRewardedVideoManager *rewardedVideoManager;

@end

@implementation Smartad {
    RCTResponseSenderBlock _requestRewardedVideoCallback;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

- (NSArray<NSString *> *)supportedEvents {
    return @[
        @"kSmartAdRewardedVideoNotReady"
        @"kSmartAdRewardedVideoAdLoaded"
        @"kSmartAdRewardedVideoAdFailedToLoad"
        @"kSmartAdRewardedVideoAdShown"
        @"kSmartAdVideoAdFailedToShow"
        @"kSmartAdRewardedVideoAdClosed"
        @"kSmartAdRewardReceived"
        @"kSmartAdRewardNotReceived"
        @"kSmartAdRewardedVideoAdClicked"
        @"kSmartAdRewardedVideoEvent"
        @"kSmartAdRewardedVideoEndCardDisplayed" ];
}

RCT_EXPORT_METHOD(initializeRewardedVideo:(nonnull NSInteger *)kRewardedVideoSiteID kRewardedVideoPageID:(nonnull NSString *)kRewardedVideoPageID kRewardedVideoFormatID:(nonnull NSInteger *)kRewardedVideoFormatID kRewardedVideoKeywordTargeting:(nullable NSString *)kRewardedVideoKeywordTargeting)
{
    [[SASConfiguration sharedInstance] configureWithSiteId:kRewardedVideoSiteID baseURL:kBaseURL];
    
    SASAdPlacement *placement = [SASAdPlacement
        adPlacementWithSiteId:kRewardedVideoSiteID
                       pageId:kRewardedVideoPageID
                     formatId:kRewardedVideoFormatID
             keywordTargeting:kRewardedVideoKeywordTargeting
    ];
    self.rewardedVideoManager = [[SASRewardedVideoManager alloc] initWithPlacement:placement delegate:self];
}

RCT_EXPORT_METHOD(loadRewardedVideoAd)
{
    if (self.rewardedVideoManager != nil) {
        [self.rewardedVideoManager load];
    } else {
        [self sendEventWithName:kSmartAdRewardedVideoAdFailedToLoad body:nil];
    }
}

RCT_EXPORT_METHOD(showRewardedVideo)
{
    if (self.rewardedVideoManager != nil && self.rewardedVideoManager.adStatus == SASAdStatusReady) {
        [self.rewardedVideoManager showFromViewController:self];
    } else if (self.rewardedVideoManager.adStatus == SASAdStatusExpired) {
        NSLog(@"RewardedVideo has expired and cannot be shown anymore.");
        [self sendEventWithName:kSmartAdRewardedVideoNotReady body:nil];
    } else {
        [self sendEventWithName:kSmartAdRewardedVideoNotReady body:nil];
    }
}


- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didLoadAd: (SASAd *)ad {
    NSLog(@"RewardedVideo has been loaded and is ready to be shown");
    [self sendEventWithName:kSmartAdRewardedVideoAdLoaded body:nil];
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didFailToLoadWithError: (NSError *)error {
    NSLog(@"RewardedVideo did fail to load with error: %@", [error localizedDescription]);
    [self sendEventWithName:kSmartAdRewardedVideoAdFailedToLoad body:nil];
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didAppearFromViewController: (UIViewController *)controller {
    [self sendEventWithName:kSmartAdRewardedVideoAdShown body:nil];
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didFailToShowWithError: (NSError *)error {
    NSLog(@"RewardedVideo did fail to show with error: %@", [error localizedDescription]);
    [self sendEventWithName:kSmartAdVideoAdFailedToShow body:nil];
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didDisappearFromViewController: (UIViewController *)controller {
    [self sendEventWithName:kSmartAdRewardedVideoAdClosed body:nil];
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didCollectReward: (SASReward *)reward {
    if (reward != nil) {
        NSLog(@"RewardedVideo did collect reward for currency %@ with amount %ld", reward.currency, (long)[reward.amount integerValue]);
        [self sendEventWithName:kSmartAdRewardReceived body:@{@"amount":reward.amount,  @"currency":reward.currency}];
        
    } else {
        [self sendEventWithName:kSmartAdRewardNotReceived body:nil];
    }
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager shouldHandleURL: (NSURL *)URL {
    [self sendEventWithName:kSmartAdRewardedVideoAdClicked body:nil];

}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didSendVideoEvent: (SASVideoEvent *)videoEvent {
    NSLog(@"RewardedVideo did send video event: %li", (long)videoEvent);
    [self sendEventWithName:kSmartAdRewardedVideoEvent body:nil];
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didLoadEndCardFromViewController: (UIViewController *)controller {
    [self sendEventWithName:kSmartAdRewardedVideoEndCardDisplayed body:nil];
}

@end
