//  Command.h
//  Frank
//
//  Created by Viktar on 7/6/11.
//  Copyright 2011 EPAM Systems. All rights reserved.

#import <Foundation/Foundation.h>

@protocol Command<NSObject>

@required

- (void) execute:(NSDictionary *)params;

@end