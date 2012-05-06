//  CommandExecutor.m
//  Frank
//
//  Created by Viktar on 7/6/11.
//  Copyright 2011 EPAM Systems. All rights reserved.

#import "CommandExecutor.h"
#import "Constants.h"
#import "SFLogger.h"

@implementation CommandExecutor

@synthesize request, command;

static CommandExecutor *sharedSingleton_ = nil;

+ (CommandExecutor *) sharedInstance {
	if (sharedSingleton_ == nil) {
		sharedSingleton_ = [[CommandExecutor alloc] init]; 
	}
	
	return sharedSingleton_;
}

- (void) dealloc
{
	[sharedSingleton_ release];
	[command release];
	[super dealloc];
}

- (id<Command>) createCommand:(NSString *) commandType request:(NSString *) request_{
	self.request = request_;
	SFLog(@"[CommandExecutor] It is created command: %@", commandType);
	
	return [self createCommand:commandType];
}

- (id<Command>) createCommand:(NSString *) commandType{
	
	if ([commandType isEqualToString:GET_ELEMENT_VALUE]) {
		command = [[GetElementValue alloc] init]; 
		return command;
	}
	if ([commandType isEqualToString:SET_ELEMENT_VALUE]) {
		command = [[SetElementValue alloc] init];
		return command;
	}
	if ([commandType isEqualToString:DO_ACTION]) {
		command = [[DoAction alloc] init];
		return command;
	}
	if ([commandType isEqualToString:TAKE_SCREENSHOT]) {
		command = [[TakeScreenshot alloc] init];
		return command;
	}
    if ([commandType isEqualToString:DESCRIBE]) {
		command = [[DescribeCommand alloc] init];
		return command;
	}

	
	return nil;
}

@end