#import "RNBannerSmartAd.h"
#import <React/RCTUtils.h>
#import <React/RCTUIManager.h>
#import <React/RCTRootView.h>
#import <React/RCTRootViewDelegate.h>

@interface RNBannerSmartAdManager : RCTViewManager
@property RNBannerSmartAd *childBanner;
@end
@implementation RNBannerSmartAdManager

#pragma mark - View controller lifecycle
RCT_EXPORT_MODULE()
- (UIView *)view
{
  _childBanner = [[RNBannerSmartAd alloc] init];
  return _childBanner;
}
RCT_EXPORT_VIEW_PROPERTY(onBannerLoad, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(siteId, NSInteger);
RCT_EXPORT_VIEW_PROPERTY(pageId, NSInteger);
RCT_EXPORT_VIEW_PROPERTY(formatId, NSInteger);
RCT_EXPORT_METHOD(loadBanner:(nonnull NSNumber *)reactTag) {
  [_childBanner bannerLoadPlacement];
}
RCT_EXPORT_METHOD(reloadBanner:(nonnull NSNumber *)reactTag) {
  [_childBanner bannerReload];
}
@end

@interface RNBannerSmartAd ()

@end

@implementation RNBannerSmartAd

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    self.banner = [[SASBannerView alloc] initWithFrame:CGRectMake(0, 0, CGRectGetWidth(self.frame), 50) loader:SASLoaderActivityIndicatorStyleWhite];
    self.banner.delegate = self;
  [self addSubview:self.banner];
    return self;
}


#pragma mark - SASBannerView delegate

- (void) bannerLoadPlacement {
  [[SASConfiguration sharedInstance] configureWithSiteId:_siteId];
  SASAdPlacement *placement = [SASAdPlacement adPlacementWithSiteId:_siteId pageId:_pageId formatId:_formatId];
  dispatch_async(dispatch_get_main_queue(), ^{
    [_banner loadWithPlacement:placement];
  });
}
- (void) bannerReload {
  SASAdPlacement *placement = [SASAdPlacement adPlacementWithSiteId:_siteId pageId:_pageId formatId:_formatId];
  dispatch_async(dispatch_get_main_queue(), ^{
    [_banner loadWithPlacement:placement];
  });
}
- (void)bannerViewDidLoad:(SASBannerView *)bannerView {
    NSLog(@"BannerSmart has been loaded");
  if (_onBannerLoad){
    _onBannerLoad(@{@"data":@(YES)});
  }
}

- (void)bannerView:(SASBannerView *)bannerView didFailToLoadWithError:(NSError *)error {
    NSLog(@"BannerSmart has failed to load with error: %@", [error localizedDescription]);
}
@end
