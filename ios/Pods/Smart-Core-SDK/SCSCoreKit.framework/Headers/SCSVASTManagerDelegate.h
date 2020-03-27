//
//  Header.h
//  SCSCoreKit
//
//  Created by Thomas Geley on 22/03/2017.
//  Copyright © 2017 Smart AdServer. All rights reserved.
//

NS_ASSUME_NONNULL_BEGIN

@class SCSVASTAd, SCSVASTError, SCSVASTURL;

@protocol SCSVASTManagerProtocol;

@protocol SCSVASTManagerDelegate <NSObject>

/**
 Called when the manager has properly parsed a VAST XML into a valid SCSVASTModel object.
 */
- (void)vastManagerIsReadyToProvideAds;

/**
 Called when the manager fails to parse a VAST XML into a valid SCSVASTModel.
 
 @param error The encountered error as an NSError.
 */
- (void)vastManagerDidFailWithError:(nullable NSError *)error;

/**
 Called when the manager encounters an error during the parsing process.
 
 This error is not necessarily blocking the whole parsing process, the VAST manager will still call
 either vastManagerIsReadyToProvideAds or vastManagerDidFailWithError at some point.
 
 You should use this method to call all pixels associated with the error and log it if necessary.
 
 @param manager An object conforming to the SCSVASTManagerProtocol.
 @param error The error that has been encountered.
 @param errorURLs The error pixels associated with this error (and that should be called by the delegate).
 @param VASTResponse The VAST response that has triggered the error if available, nil otherwise.
 */
- (void)vastManager:(id <SCSVASTManagerProtocol>)manager didEncounterError:(SCSVASTError *)error errorURLs:(NSArray <SCSVASTURL *> *)errorURLs VASTResponse:(nullable NSString *)VASTResponse;

/**
 Called when the manager starts to resolve a Wrapper Ad from the root VAST Model. (Wrapper Pixa)
 
 @param manager An object conforming to the SCSVASTManagerProtocol.
 @param ad The SCSVASTAd instance being resolved.
 */
- (void)vastManager:(id <SCSVASTManagerProtocol>)manager didStartToResolveWrapperAd:(SCSVASTAd *)ad;

/**
 Called when the manager resolving a wrapper finds no valid ad in the response. (VNoAd Pixa)
 
 @param manager An object conforming to the SCSVASTManagerProtocol.
 @param ad The SCSVASTAd instance being resolved.
 */
- (void)vastManager:(id <SCSVASTManagerProtocol>)manager didFailToResolveWrapperAd:(SCSVASTAd *)ad;

@end

NS_ASSUME_NONNULL_END
