//
//  SCSVASTPixelManager.h
//  SCSCoreKit
//
//  Created by Loïc GIRON DIT METAZ on 06/08/2019.
//  Copyright © 2019 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@class SCSPixelManager, SCSVASTURL;

/**
 Manage VAST related pixels
 */
@interface SCSVASTPixelManager : NSObject

/**
 Initialize a new instance of SCSVASTPixelManager.
 
 @param pixelManager The pixel manager instance that will be used to call all pixels.
 @return An initialized instance of SCSVASTPixelManager.
 */
- (instancetype)initWithPixelManager:(SCSPixelManager *)pixelManager;

/**
 Call an array of error pixels corresponding to a given VAST error.
 
 If pixel URL contain an 'ERRORCODE' macro, this macro will be automatically replaced by
 the VAST error code.
 
 @param pixels An array of VAST URLs.
 @param errorCode The VAST error code.
 */
- (void)callVASTErrorPixels:(NSArray<SCSVASTURL *> *)pixels withErrorCode:(NSInteger)errorCode;

@end

NS_ASSUME_NONNULL_END
