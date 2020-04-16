//
//  SCSVASTParser.h
//  SCSCoreKit
//
//  Created by Thomas Geley on 22/03/2017.
//  Copyright © 2017 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SCSVASTError.h"

NS_ASSUME_NONNULL_BEGIN

@class SCSVASTParserResponse, SCSVASTURL;

/**
 This class transforms XML Datas into a VASTModel or return an error.
 */
@interface SCSVASTParser : NSObject

- (instancetype)init NS_UNAVAILABLE;

/**
 Creates a VAST Model from XML datas.
 
 @param datas The XML datas to parse.
 @return An SCSVASTParserResponse containing a (nullable) model and a (nullable) set of errors.
 */
+ (SCSVASTParserResponse *)parseXMLAndGenerateVASTObjectModel:(nullable NSData *)datas __deprecated;

/**
 Creates a VAST Model from XML datas.
 
 This method can also call a delegate implementing SCSVASTErrorRemoteLoggerProtocol if any error occurs during parsing.
 
 @param datas The XML datas to parse.
 @param isWrapper YES if the XML corresponds to a wrapper ad, NO otherwise (this info is only used for remote logging).
 @param errorHandler A block called when an error is encountered if any (blocking or not). The last encountered error will also be available in the response for retro-compatibility reasons.
 @return An SCSVASTParserResponse containing a (nullable) model and a (nullable) set of errors.
 */
+ (SCSVASTParserResponse *)parseXMLAndGenerateVASTObjectModel:(nullable NSData *)datas isWrapper:(BOOL)isWrapper errorHandler:(void(^)(SCSVASTError *, NSMutableArray <SCSVASTURL *> *, NSString * _Nullable))errorHandler;

@end

NS_ASSUME_NONNULL_END
