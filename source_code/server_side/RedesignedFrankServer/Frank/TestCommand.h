//  TestCommand.h
//  Frank
//
//  Created by Viktar on 5/17/11.
//  Copyright 2011 EPAM Systems. All rights reserved.

#import <Foundation/Foundation.h>
#import <FrankCommandRoute.h>
#import <JSON.h>
#import "CommandExecutor.h"
#import "Command.h"
#import "TAFFilter.h"
#import <objc/runtime.h>
#import "SFLogger.h"

@interface TestCommand : NSObject <FrankCommand> {

	BOOL succes;
	
}

- (void) printChildsOf: (NSArray *)view level: (int) l;
- (void) printChildsOf: (UITableView *)view;

@end