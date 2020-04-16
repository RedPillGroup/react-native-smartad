//
//  SCSVASTCreative.h
//  SCSCoreKit
//
//  Created by Thomas Geley on 20/03/2017.
//  Copyright © 2017 Smart AdServer. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SCSVASTError.h"

NS_ASSUME_NONNULL_BEGIN

@class SCSVASTTrackingEvent, SCSVASTURL;

@interface SCSVASTCreative : NSObject

/// An array of all the tracking events for this creative.
@property (nonatomic, readonly) NSMutableArray <SCSVASTTrackingEvent *> *trackingEvents;

/// The clickURL to open when the creative is clickedThrough
@property (nullable, nonatomic, readonly) SCSVASTURL *clickThrough;

/// An array of all the URLs to call when the creative is clicked.
@property (nonatomic, readonly) NSMutableArray <SCSVASTURL *> *clickTracking;

/**
 Initializer from a dictionary.
 
 @param dictionary A dictionary from the parsed XML.
 @return An initialized instance of SCSVASTCreative or nil if the dictionary was invalid.
 */
- (nullable instancetype)initWithDictionary:(NSDictionary *)dictionary __deprecated;

/**
 Initializer from a dictionary.

 @param dictionary A dictionary from the parsed XML.
 @param error A reference to an error that will be set if the VAST creative cannot be initialized.
 @param errorHandler A block called when an error is encountered if any (blocking or not). If the error prevent the model from being initialized, it will also be set in the 'error' reference.
 @param isWrapper YES if the creative to be parsed is from a wrapper ad, NO otherwise.
 @return An initialized instance of SCSVASTCreative or nil if the dictionary was invalid.
*/
- (nullable instancetype)initWithDictionary:(NSDictionary *)dictionary error:(NSError * _Nullable * _Nullable)error errorHandler:(void(^)(SCSVASTError *, NSMutableArray <SCSVASTURL *> *))errorHandler isWrapper:(BOOL)isWrapper NS_DESIGNATED_INITIALIZER;

/**
 Adds tracking events to this creative.
 
 @param events An array of tracking events.
 */
- (void)addTrackingEvents:(NSArray <SCSVASTTrackingEvent *> *)events;

/**
 Indicates whether or not this creative is valid. This method will be surcharged in subclasses.
 
 @param forWrapper Indicates if the Ad owning this creative is a wrapper ad.
 @return Whether or not this creative is valid.
 */
- (BOOL)isValidForWrapper:(BOOL)forWrapper;

- (instancetype)init NS_UNAVAILABLE;

@end

NS_ASSUME_NONNULL_END
