//  GetElementValue.m
//  Frank
//
//  Created by Viktar on 7/7/11.
//  Copyright 2011 EPAM Systems. All rights reserved.

#import "GetElementValue.h"
#import "UIServiceAssistant.h"
#import "Constants.h"
#import "SFLogger.h"

@implementation GetElementValue

@synthesize commandResult;

- (void) execute:(NSDictionary *)params{	
	SFLog(@" get element value command.");
	
	NSString *classNameChain = [params objectForKey:@"ClassNameChain"];
	NSString *attributeName  = [params objectForKey:@"AttributeName"];
	NSString *index          = [params objectForKey:@"ElementIndex"];
	
	UIServiceAssistant *uiAssistant = [[UIServiceAssistant alloc] init];
	
	[uiAssistant setCommand:GET_ELEMENT_VALUE];
	[uiAssistant setParams:classNameChain attrName:attributeName index:index];
	[uiAssistant comply];
	//	[uiAssistant complyWithCommand:GET_ELEMENT_VALUE];
	
	[self setCommandResult:[uiAssistant commandResult]];
	SFLog(@"[GetElementValueCommand] result: %@", commandResult);
	
	[uiAssistant release];
}

@end