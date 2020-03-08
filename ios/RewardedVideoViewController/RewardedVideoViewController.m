#import "RewardedVideoViewController.h"

#define kRewardedVideoSiteID          104808
#define kRewardedVideoPageID          1010691
#define kRewardedVideoFormatID        12167

@interface RewardedVideoViewController () <SASRewardedVideoManagerDelegate>

@property SASRewardedVideoManager *rewardedVideoManager;

@end

@implementation RewardedVideoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self createRewardedVideoManager];
}

- (void) createRewardedVideoManager {
    // Create a placement
    SASAdPlacement *placement = [SASAdPlacement adPlacementWithSiteId:kRewardedVideoSiteID
                                                               pageId:kRewardedVideoPageID
                                                             formatId:kRewardedVideoFormatID];
    
    // You can also use a test placement during development (a placement that will always deliver an ad from a given format).
    // DON'T FORGET TO REVERT TO THE ACTUAL PLACEMENT BEFORE SHIPPING THE APP!
    
    // SASAdPlacement *placement = [SASAdPlacement adPlacementWithTestAd:SASAdPlacementTestRewardedVideoWithEndCard];
    
    // Initialize the rewarded video manager with a placement
    self.rewardedVideoManager = [[SASRewardedVideoManager alloc] initWithPlacement:placement delegate:self];
}

#pragma mark - View controller actions

- (IBAction)loadRewardedVideoAd:(id)sender {
    // Disable show button during load phase
    self.showRewardedVideoAdButton.enabled = NO;
    
    // Load a rewarded video
    [self.rewardedVideoManager load];
}

- (IBAction)showRewardedVideoAd:(id)sender {
    // Check if the rewarded video is ready
    if (self.rewardedVideoManager.adStatus == SASAdStatusReady) {
        [self.rewardedVideoManager showFromViewController:self];
    } else if (self.rewardedVideoManager.adStatus == SASAdStatusExpired) {
        // If not, one of the reason could be that it's expired
        NSLog(@"RewardedVideo has expired and cannot be shown anymore.");
    }
}

#pragma mark - SASRewardedVideoManagerDelegate methods

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didLoadAd:(SASAd *)ad {
    NSLog(@"RewardedVideo has been loaded and is ready to be shown");
    // Enable show button
    self.showRewardedVideoAdButton.enabled = YES;
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didAppearFromViewController:(UIViewController *)controller {
    // Rewarded video is shown so we disable the show button
    self.showRewardedVideoAdButton.enabled = NO;
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didFailToLoadWithError:(NSError *)error {
    NSLog(@"RewardedVideo did fail to load with error: %@", [error localizedDescription]);
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didFailToShowWithError:(NSError *)error {
    NSLog(@"RewardedVideo did fail to show with error: %@", [error localizedDescription]);
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager  didSendVideoEvent:(SASVideoEvent)videoEvent {
     NSLog(@"RewardedVideo did send video event: %li", (long)videoEvent);
}

- (void)rewardedVideoManager:(SASRewardedVideoManager *)manager didCollectReward:(SASReward *)reward {
    NSLog(@"RewardedVideo did collect reward for currency %@ with amount %ld", reward.currency, (long)[reward.amount integerValue]);
    
    // Here you should reward your user with the amount of the given currency.
}

@end