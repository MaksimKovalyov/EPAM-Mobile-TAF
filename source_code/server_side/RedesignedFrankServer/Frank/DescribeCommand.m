//  DescribeCommand.m
//  Frank
//
//  Created by Viktar on 11/12/11.
//  Copyright (c) 2011 EPAM Systems. All rights reserved.

#import "DescribeCommand.h"
#import "UIServiceAssistant.h"
#import "Constants.h"
#import "SFLogger.h"
#import "DumpCommand.h"

@implementation DescribeCommand

@synthesize commandResult, proxy;

- (void) execute:(NSDictionary *)params{	

    SFLog(@"[DescribeCommand] It starts to execute...");
    NSString *classNameChain = [params objectForKey:@"ClassNameChain"];

    UIServiceAssistant *uiAssistant = [[UIServiceAssistant alloc] init];
	
	//[uiAssistant setCommand:GET_ELEMENT_VALUE];
	[uiAssistant setParams:classNameChain];

    DumpCommand *dc = [[DumpCommand alloc] init];
    
	[self setProxy:[dc filter:[uiAssistant describeElement]]];
	[self setCommandResult: @"Yes"];

    [uiAssistant release];
    [dc release];
}

/*- (NSString *)getCommandResult{
 return commandResult;
 }*/

@end