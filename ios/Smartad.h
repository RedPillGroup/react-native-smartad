#import <UIKit/UIKit.h>
#import <SASDisplayKit/SASDisplayKit.h>

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

NS_ASSUME_NONNULL_BEGIN

/*
 The purpose of this view controller is to display a rewarded video.
 
 A rewarded video works like an interstitial but is optimized to display video
 that will yield a reward when watched until the end.
 */
@interface Smartad : RCTEventEmitter <RCTBridgeModule, ISRewardedVideoDelegate>
// @interface Smartad : UIViewController

@property (weak, nonatomic) IBOutlet UIButton *showRewardedVideoAdButton;

@end

NS_ASSUME_NONNULL_END