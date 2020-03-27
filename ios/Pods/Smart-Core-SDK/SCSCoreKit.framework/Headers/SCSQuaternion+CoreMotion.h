//
//  SCSQuaternion+CoreMotion.h
//  SCSCoreKit
//
//  Created by Loïc GIRON DIT METAZ on 08/04/2019.
//  Copyright © 2019 Smart AdServer. All rights reserved.
//

#import "SCSQuaternion.h"
#import <CoreMotion/CoreMotion.h>

NS_ASSUME_NONNULL_BEGIN

/**
 Quaternion category to allow initialization from a CoreMotion quaternion.
 */
@interface SCSQuaternion (CoreMotion)

/**
 Initialize the quaternion using a CMQuaternion (from the CoreMotion framework).
 
 @param quaternion A valid CMQuaternion instance.
 */
- (instancetype)initFromCMQuaternion:(CMQuaternion)quaternion;

@end

NS_ASSUME_NONNULL_END
