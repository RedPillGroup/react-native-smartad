#import "RNBannerSmartAd.h"
#import "RCTUtils.h"

#if __has_include(<React/RCTBridge.h>)
#import <React/RCTBridge.h>
#import <React/RCTUIManager.h>
#import <React/RCTEventDispatcher.h>
#else
#import "RCTBridge.h"
#import "RCTUIManager.h"
#import "RCTEventDispatcher.h"
#endif

#define kBannerSiteID          348776
#define kBannerPageID          1224025
#define kBannerFormatID        102663

@interface RNBannerSmartAd () <SASBannerViewDelegate>
    @property SASBannerView *banner;
@end
@implementation RNBannerSmartAd

#pragma mark - View controller lifecycle

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(loadBanner:(nonnull NSNumber *)reactTag) {
    NSLog(@"BannerSmart call created");
    [[SASConfiguration sharedInstance] configureWithSiteId:kBannerSiteID];
    self.banner = [[SASBannerView alloc] initWithFrame:CGRectMake(0, 0, CGRectGetWidth(self.view.frame), 50) loader:SASLoaderActivityIndicatorStyleWhite];

    // Create a placement
    SASAdPlacement *placement = [SASAdPlacement adPlacementWithSiteId:kBannerSiteID pageId:kBannerPageID formatId:kBannerFormatID];

   
    NSLog(@"BannerSmart load placement");
    [self.bridge.uiManager addUIBlock:^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, SASBannerView *> *viewRegistry) {
        SASBannerView *view = viewRegistry[reactTag];
        [view addSubview:self.banner];
        [self.banner loadWithPlacement:placement];
    }];
}

#pragma mark - SASBannerView delegate




- (void)bannerViewDidLoad:(SASBannerView *)bannerView {
    NSLog(@"BannerSmart has been loaded");
}

- (void)bannerView:(SASBannerView *)bannerView didFailToLoadWithError:(NSError *)error {
    NSLog(@"BannerSmart has failed to load with error: %@", [error localizedDescription]);
}

//- (void)setSiteId:(NSInteger)siteId
//{
//    self.siteId = siteId;
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
@end
