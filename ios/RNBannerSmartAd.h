
#if __has_include(<React/RCTViewManager.h>)
#import <React/RCTViewManager.h>
#else
#import "RCTViewManager.h"
#endif

#if __has_include(<React/RCTView.h>)
#import <React/RCTView.h>
#else
#import "RCTView.h"
#endif

#if __has_include(<React/RCTEventEmitter.h>)
#import <React/RCTEventEmitter.h>
#else
#import "RCTEventEmitter.h"
#endif

#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#else
#import "RCTBridgeModule.h"
#endif

#import <UIKit/UIKit.h>

#import <SASDisplayKit/SASDisplayKit.h>

@interface RNBannerSmartAd : RCTView <SASBannerViewDelegate>
@property SASBannerView *banner;
@property (nonatomic, copy) RCTBubblingEventBlock onBannerLoad;
@property (nonatomic, assign) NSInteger siteId;
@property (nonatomic, assign) NSInteger pageId;
@property (nonatomic, assign) NSInteger formatId;
- (void)bannerLoadPlacement;
- (void)bannerReload;
@end
