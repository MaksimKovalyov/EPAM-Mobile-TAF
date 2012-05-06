//  SetElementValue.h
//  Frank
//
//  Created by Viktar on 7/6/11.
//  Copyright 2011 EPAM Systems. All rights reserved.

#import "Command.h"

@interface SetElementValue : NSObject<Command> {
	NSString* commandResult;
}

@property (nonatomic, retain) NSString* commandResult;

@end