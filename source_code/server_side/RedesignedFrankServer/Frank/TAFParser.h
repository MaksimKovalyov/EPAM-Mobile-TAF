//  Parser.h
//  Frank
//
//  Created by Viktar on 7/6/11.
//  Copyright 2011 EPAM Systems. All rights reserved.

#import <Foundation/Foundation.h>
#import "SFLogger.h"

@interface TAFParser : NSObject {

}

+ (TAFParser *) sharedInstance;
- (NSArray *) parseXPath: (NSString *)xpath;
- (NSArray *) parse: (NSString *)xpathNode;

@end