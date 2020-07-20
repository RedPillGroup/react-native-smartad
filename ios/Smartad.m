#import "Smartad.h"
#import "RCTUtils.h"

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
NSString *const kSmartAdVignette = @"kSmartAdVignette";
NSString *const kSmartAdCustomAdvertiser = @"kSmartAdCustomAdvertiser";

#define kBaseURL @"https://mobile.smartadserver.com"

@interface Smartad () <SASRewardedVideoManagerDelegate>

@property SASRewardedVideoManager *rewardedVideoManager;
@property (nonatomic, strong) SASNativeAd *nativeAd;
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
        kSmartAdRewardedVideoNotReady,
        kSmartAdRewardedVideoAdLoaded,
        kSmartAdRewardedVideoAdFailedToLoad,
        kSmartAdRewardedVideoAdShown,
        kSmartAdVideoAdFailedToShow,
        kSmartAdRewardedVideoAdClosed,
        kSmartAdRewardReceived,
        kSmartAdRewardNotReceived,
        kSmartAdRewardedVideoAdClicked,
        kSmartAdRewardedVideoEvent,
        kSmartAdRewardedVideoEndCardDisplayed,
        kSmartAdVignette,
        kSmartAdCustomAdvertiser ];
}

RCT_EXPORT_METHOD(initializeRewardedVideo:(nonnull NSInteger *)kRewardedVideoSiteID kRewardedVideoPageID:(nonnull NSInteger *)kRewardedVideoPageID kRewardedVideoFormatID:(nonnull NSInteger *)kRewardedVideoFormatID kRewardedVideoKeywordTargeting:(nullable NSString *)kRewardedVideoKeywordTargeting)
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

RCT_EXPORT_METHOD(loadRewardedVideoAd:(nullable NSString *)securedTransactionToken)
{
    if (self.rewardedVideoManager != nil) {
        [self.rewardedVideoManager loadWithSecuredTransactionToken:securedTransactionToken];
    } else {
        [self sendEventWithName:kSmartAdRewardedVideoAdFailedToLoad body:nil];
    }
}

RCT_EXPORT_METHOD(showRewardedVideo)
{
    if (self.rewardedVideoManager != nil && self.rewardedVideoManager.adStatus == SASAdStatusReady) {
        
        UIViewController* vc = RCTPresentedViewController();
        [self.rewardedVideoManager showFromViewController:vc];
    } else if (self.rewardedVideoManager.adStatus == SASAdStatusExpired) {
        NSLog(@"RewardedVideo has expired and cannot be shown anymore.");
        [self sendEventWithName:kSmartAdRewardedVideoNotReady body:nil];
    } else {
        [self sendEventWithName:kSmartAdRewardedVideoNotReady body:nil];
    }
}

RCT_EXPORT_METHOD(onDestroy)
{}

RCT_EXPORT_METHOD(reset)
{}


- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didFailToLoadWithError: (NSError *)error {
    NSLog(@"RewardedVideo did fail to load with error: %@", [error localizedDescription]);
    [self sendEventWithName:kSmartAdRewardedVideoAdFailedToLoad body:nil];
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didAppearFromViewController: (UIViewController *)controller {
    NSLog(@"RewardedVideo did appear");
    [self sendEventWithName:kSmartAdRewardedVideoAdShown body:nil];
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didFailToShowWithError: (NSError *)error {
    NSLog(@"RewardedVideo did fail to show with error: %@", [error localizedDescription]);
    [self sendEventWithName:kSmartAdVideoAdFailedToShow body:nil];
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didDisappearFromViewController: (UIViewController *)controller {
    NSLog(@"RewardedVideo did disappear");
    [self sendEventWithName:kSmartAdRewardedVideoAdClosed body:nil];
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didCollectReward: (SASReward *)reward {
    NSLog(@"RewardedVideo did collect reward");
    if (reward != nil) {
        NSLog(@"RewardedVideo did collect reward for currency %@ with amount %ld", reward.currency, (long)[reward.amount integerValue]);
        [self sendEventWithName:kSmartAdRewardReceived body:@{@"amount":reward.amount,  @"currency":reward.currency, @"securedToken":reward.securedTransactionToken}];
    } else {
        [self sendEventWithName:kSmartAdRewardNotReceived body:nil];
    }
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager shouldHandleURL: (NSURL *)URL {
    NSLog(@"RewardedVideo should Handle url");
    [self sendEventWithName:kSmartAdRewardedVideoAdClicked body:nil];
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didSendVideoEvent: (SASVideoEvent *)videoEvent {
    NSLog(@"RewardedVideo did send video event: %li", (long)videoEvent);
    [self sendEventWithName:kSmartAdRewardedVideoEvent body:nil];
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didLoadEndCardFromViewController: (UIViewController *)controller {
    NSLog(@"RewardedVideo did load end card");
    [self sendEventWithName:kSmartAdRewardedVideoEndCardDisplayed body:nil];
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager willPresentModalViewFromViewController: (UIViewController *)controller {
    NSLog(@"RewardedVideo will present modal");
    
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager willDismissModalViewFromViewController: (UIViewController *)controller {
    NSLog(@"RewardedVideo will modal view ");
    
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didLoadAd:(SASAd *)ad {
    NSLog(@"RewardedVideo has been loaded and is ready to be shown");

    // Find Ad vignette
    SASNativeVideoAd *castedAd = (SASNativeVideoAd *)ad;
    if (castedAd.posterImageUrl) {
        NSDictionary *extraParameters = ad.extraParameters;
        NSLog(@"Vignette is found at: %@", [castedAd.posterImageUrl absoluteString]);
        [self sendEventWithName:kSmartAdVignette body:@{@"url":[castedAd.posterImageUrl absoluteString], @"extraparams":extraParameters}];
    }

    [self sendEventWithName:kSmartAdRewardedVideoAdLoaded body:nil];
}

@end
