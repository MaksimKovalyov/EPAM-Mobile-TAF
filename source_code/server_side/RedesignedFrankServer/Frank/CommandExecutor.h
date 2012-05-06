//
//  CommandExecutor.h
//  Frank
//
//  Created by Viktar on 7/6/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import "Command.h"
#import "SetElementValue.h"
#import "GetElementValue.h"
#import "DoAction.h"
#import "DescribeCommand.h"
#import "TakeScreenshot.h"

@interface CommandExecutor : NSObject{
	NSString *request;
	id<Command> command;
}

@property (nonatomic, retain) NSString* request;
@property (nonatomic, retain) id<Command> command;

//- (id) init;
+ (CommandExecutor *) sharedInstance;
- (id<Command>) createCommand:(NSString *) commandType;
- (id<Command>) createCommand:(NSString *) commandType request:(NSString *) request_;

@end
