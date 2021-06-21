
#if __has_include(<React/RCTViewManager.h>)
#import <React/RCTViewManager.h>
#else
#import "RCTViewManager.h"
#endif

#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#else
#import "RCTBridgeModule.h"
#endif

#import <UIKit/UIKit.h>

#import <SASDisplayKit/SASDisplayKit.h>

@interface RNBannerSmartAd : RCTViewManager <SASBannerViewDelegate>

@end
