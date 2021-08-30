#import "RNBannerSmartAd.h"
#import "RCTUtils.h"
#import <React/RCTUIManager.h>

#define kBannerSiteID          348776
#define kBannerPageID          1224025
#define kBannerFormatID        102663
#define ROOT_VIEW_CONTROLLER (UIApplication.sharedApplication.keyWindow.rootViewController)

@interface RNBannerSmartAd ()
    @property SASBannerView *banner;
    @property (nonatomic, strong) UIView *safeAreaBackground;
    @property (nonatomic, copy) RCTBubblingEventBlock onBannerLoad;
@end
@implementation RNBannerSmartAd

#pragma mark - View controller lifecycle

RCT_EXPORT_MODULE()

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}
// Invoke all exported methods from main queue
- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

- (instancetype)init
{
    self = [super init];
    NSLog(@"BannerSmart init view");
    [[SASConfiguration sharedInstance] configureWithSiteId:kBannerSiteID];
//    self.safeAreaBackground = [[UIView alloc] init];
//    [ROOT_VIEW_CONTROLLER.view addSubview: self.safeAreaBackground];
    self.banner = [[SASBannerView alloc] initWithFrame:CGRectMake(0, 0, CGRectGetWidth(self.view.frame), 50) loader:SASLoaderActivityIndicatorStyleWhite];
    self.banner.delegate = self;
    return self;
}

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"OnBannerAdLoadedEvent"];
}

RCT_EXPORT_METHOD(loadBanner:(nonnull NSNumber *)reactTag) {
    NSLog(@"BannerSmart load banner");

    // Create a placement
    SASAdPlacement *placement = [SASAdPlacement adPlacementWithSiteId:kBannerSiteID pageId:kBannerPageID formatId:kBannerFormatID];

//    NSLog(@"BanneSmart siteId is %zd", self.siteId);
//    NSLog(@"BanneSmart pageId is %ld", (long)self.pageId);
    
    NSLog(@"BannerSmart load placement");
    [self.bridge.uiManager addUIBlock:^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
        UIView *view = viewRegistry[reactTag];
        [view addSubview:self.banner];
        dispatch_async(dispatch_get_main_queue(), ^{
            [self.banner loadWithPlacement:placement];
        });
    }];
    
    
    
}

#pragma mark - SASBannerView delegate



//RCT_EXPORT_VIEW_PROPERTY(onBannerLoad, RCTBubblingEventBlock)
- (void)bannerViewDidLoad:(SASBannerView *)bannerView {
    NSLog(@"BannerSmart has been loaded");
//    bannerView.onBannerLoad(@{@"data":@(YES)});
//    [self bannerView.sendEventWithName:@"OnBannerAdLoadedEvent" body:nil];
}

- (void)bannerView:(SASBannerView *)bannerView didFailToLoadWithError:(NSError *)error {
    NSLog(@"BannerSmart has failed to load with error: %@", [error localizedDescription]);
}

// - (void)setOnBannerLoad:(RCTBubblingEventBlock)onBannerLoad
// {
//     NSLog(@"BannerSmaart onBanner Load");
// }
//RCT_EXPORT_VIEW_PROPERTY(siteId, NSInteger);
// RCT_EXPORT_VIEW_PROPERTY(pageId, NSInteger);
//- (void)setSiteId:(NSInteger)siteId
//{
////    self.siteId = siteId;
//    NSLog(@"BannersiteId is %zd", siteId);
//}
//
//- (void)setPageId:(NSInteger)pageId
//{
//    self.pageId = pageId;
//    NSLog(@"BannerpageId is %zd", pageId);
//}
//
//- (void)setFormatId:(NSInteger)formatId
//{
//    self.formatId = formatId;
//    NSLog(@"BannerformatId is %zd", formatId);
//}

//- (void)sendReactNativeEventWithName:(NSString *)name body:(NSDictionary<NSString *, id> *)body
//{
//    [self sendEventWithName: name body: body];
//}

@end
