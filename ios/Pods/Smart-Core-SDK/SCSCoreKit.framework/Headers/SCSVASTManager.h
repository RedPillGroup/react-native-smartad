//
//  SCSVASTManager.h
//  SCSCoreKit
//
//  Created by Thomas Geley on 22/03/2017.
//  Copyright © 2017 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SCSVASTManagerProtocol.h"
#import "SCSVASTErrorRemoteLoggerProtocol.h"

NS_ASSUME_NONNULL_BEGIN

@protocol SCSVASTManagerDelegate, SCSVASTAdAdapterProtocol;
@class SCSURLSession, SCSPixelManager, SCSVASTManagerResponse, SCSVASTPixelManager, SCSVASTManagerConfig;

/**
 Default implementation of the SCSCoreKit's VAST Manager.
 */
@interface SCSVASTManager : NSObject <SCSVASTManagerProtocol>

/// The delegate of the VASTManager instance
@property (nullable, nonatomic, weak) id <SCSVASTManagerDelegate> delegate;

/// The adapter used to transform VASTAds into consumable ads.
@property (nullable, nonatomic, weak) id <SCSVASTAdAdapterProtocol> adapter;

- (instancetype)init NS_UNAVAILABLE;

/**
 Public initializer
 
 @param delegate The Manager's delegate.
 @param adapter The adapter to transform VAST Ads in the relevant video ad for the given SDK.
 @param sessionManager The Session manager for distant calls. Used only for Unit testing.
 @param config The VAST manager configuration.
 
 @return An initialized instance of SCSVASTManager.
 */
- (instancetype)initWithDelegate:(nullable id <SCSVASTManagerDelegate>)delegate
                         adapter:(nullable id <SCSVASTAdAdapterProtocol>)adapter
                  sessionManager:(nullable SCSURLSession *)sessionManager //The session manager is only passed for unit tests…
                          config:(SCSVASTManagerConfig *)config NS_DESIGNATED_INITIALIZER;

/**
 Public initializer
 
 @param delegate The Manager's delegate.
 @param adapter The adapter to transform VAST Ads in the relevant video ad for the given SDK.
 @param sessionManager The Session manager for distant calls. Used only for Unit testing.
 @param requestTimeout The timeout for requests (wrapper resolution).
 @param maximumWrappers The maximum number of wrappers that can be resolved in a wrapper chain.
 @param handleWrappersAdpods Indicates whether or not the manager should "insert" wrapper adpods in the ad store.
 
 @return An initialized instance of SCSVASTManager.
 */
- (instancetype)initWithDelegate:(nullable id <SCSVASTManagerDelegate>)delegate
                         adapter:(nullable id <SCSVASTAdAdapterProtocol>)adapter
                  sessionManager:(nullable SCSURLSession *)sessionManager //The session manager is only passed for unit tests…
                  requestTimeout:(NSTimeInterval)requestTimeout
                 maximumWrappers:(NSInteger)maximumWrappers
            handleWrappersAdpods:(BOOL)handleWrappersAdpods NS_DESIGNATED_INITIALIZER __deprecated;

/**
 Public initializer
 
 @param delegate The Manager's delegate.
 @param adapter The adapter to transform VAST Ads in the relevant video ad for the given SDK.
 @param sessionManager The Session manager for distant calls. Used only for Unit testing.
 @param pixelManager The pixel manager instance to call error pixels.
 @param requestTimeout The timeout for requests (wrapper resolution).
 @param maximumWrappers The maximum number of wrappers that can be resolved in a wrapper chain.
 @param handleWrappersAdpods Indicates whether or not the manager should "insert" wrapper adpods in the ad store.
 
 @return An initialized instance of SCSVASTManager.
 */
- (instancetype)initWithDelegate:(nullable id <SCSVASTManagerDelegate>)delegate
                         adapter:(nullable id <SCSVASTAdAdapterProtocol>)adapter
                  sessionManager:(nullable SCSURLSession *)sessionManager //The session manager is only passed for unit tests…
                    pixelManager:(nullable SCSPixelManager *)pixelManager //The pixel manager is only passed for unit tests…
                  requestTimeout:(NSTimeInterval)requestTimeout
                 maximumWrappers:(NSInteger)maximumWrappers
            handleWrappersAdpods:(BOOL)handleWrappersAdpods NS_DESIGNATED_INITIALIZER __deprecated;

@end

NS_ASSUME_NONNULL_END
