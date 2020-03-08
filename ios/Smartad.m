#import "Smartad.h"
#import <SASDisplayKit/SASDisplayKit.h>

#define kSiteID 104808
#define kBaseURL @"https://mobile.smartadserver.com"

@implementation Smartad

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions)
{
  [[SASConfiguration sharedInstance] configureWithSiteId:kSiteID baseURL:kBaseURL];);
}


@end
