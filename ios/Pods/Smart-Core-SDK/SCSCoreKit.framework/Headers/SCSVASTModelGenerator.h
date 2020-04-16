//
//  SCSVASTModelGenerator.h
//  SCSCoreKit
//
//  Created by Thomas Geley on 22/03/2017.
//  Copyright © 2017 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@class SCSURLSession, SCSVASTParserResponse, SCSVASTError, SCSVASTURL;

@interface SCSVASTModelGenerator : NSObject

/**
 Initialize a new instance of SCSVASTModelGenerator with a session manager.
 
 @param sessionManager The session manager used to retrieve VAST files.
 
 @return An initialized instance of SCSVASTModelGenerator.
 */
- (instancetype)initWithSessionManager:(nullable SCSURLSession *)sessionManager;

/**
 Initialize a new instance of SCSVASTModelGenerator with a session manager.
 
 @param sessionManager The session manager used to retrieve VAST files.
 @param isWrapper YES if the XML corresponds to a wrapper ad, NO otherwise (this info is only used for remote logging). 
 
 @return An initialized instance of SCSVASTModelGenerator.
 */
- (instancetype)initWithSessionManager:(nullable SCSURLSession *)sessionManager isWrapper:(BOOL)isWrapper NS_DESIGNATED_INITIALIZER;

/**
 Generate a VAST Model from XML datas or after downloading content of an URL.
 
 @param datas The inputed XML datas if any.
 @param url The URL from where the XML Datas should be downloaded.
 @param timeout The amount of time to perform the (download +) transformation of the XML datas into a VAST Model.
 @return A SCSVASTParserResponse.
 */
- (SCSVASTParserResponse *)generateVASTModelFromXML:(nullable NSData *)datas url:(nullable NSURL *)url timeout:(NSTimeInterval)timeout __deprecated;

/**
 Generate a VAST Model from XML datas or after downloading content of an URL.
 
 @param datas The inputed XML datas if any.
 @param url The URL from where the XML Datas should be downloaded.
 @param timeout The amount of time to perform the (download +) transformation of the XML datas into a VAST Model.
 @param errorHandler A block called when an error is encountered if any (blocking or not). The last encountered error will also be available in the response for retro-compatibility reasons.
 @return A SCSVASTParserResponse.
 */
- (SCSVASTParserResponse *)generateVASTModelFromXML:(nullable NSData *)datas url:(nullable NSURL *)url timeout:(NSTimeInterval)timeout errorHandler:(void(^)(SCSVASTError *, NSMutableArray <SCSVASTURL *> *, NSString * _Nullable))errorHandler;

- (instancetype)init NS_UNAVAILABLE;

@end

NS_ASSUME_NONNULL_END
