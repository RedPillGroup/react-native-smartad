//
//  SCSVASTErrors.h
//  SCSCoreKit
//
//  Created by Thomas Geley on 20/03/2017.
//  Copyright © 2017 Smart AdServer. All rights reserved.
//

#pragma mark - Domain

#define SCSVAST_ERROR_DOMAIN                                    @"SCSVASTErrorDomain"

#pragma mark - Manager Errors

#define SCSVASTManagerError_Parsing                             @"Parsing failed."
#define SCSVASTManagerErrorCode_Parsing                         10000

#define SCSVASTManagerError_Timeout                             @"VASTManager Timeout."
#define SCSVASTManagerErrorCode_Timeout                         10001

#define SCSVASTManagerError_NoInput                             @"No XML Input."
#define SCSVASTManagerErrorCode_NoInput                         10002

#define SCSVASTManagerError_UnableToDownloadXML                 @"Unable to download XML."
#define SCSVASTManagerErrorCode_UnableToDownloadXML             10003

#define SCSVASTManagerError_NoModel                             @"Unable to create VAST Model."
#define SCSVASTManagerErrorCode_NoModel                         10004

#define SCSVASTManagerError_NoMoreAds                           @"No more ads available."
#define SCSVASTManagerErrorCode_NoMoreAds                       10005

#define SCSVASTManagerError_PassbackResolutionIncomplete        @"Passback resolution incomplete."
#define SCSVASTManagerErrorCode_PassbackResolutionIncomplete    10006

#define SCSVASTManagerError_NoMorePassback                      @"No more Passback."
#define SCSVASTManagerErrorCode_NoMorePassback                  10007

#define SCSVASTManagerError_NoAd                                @"No ad available."
#define SCSVASTManagerErrorCode_NoAd                            10008
