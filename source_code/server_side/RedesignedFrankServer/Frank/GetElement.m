//  GetElement.m
//  Frank
//
//  Created by Viktar on 11/28/11.
//  Copyright (c) 2011 EPAM Systems. All rights reserved.

#import "GetElement.h"
#import "UIServiceAssistant.h"
#import "Constants.h"
#import "SFLogger.h"

@implementation GetElement

@synthesize commandResult;

- (void) execute:(NSDictionary *)params{	
	SFLog(@" get element value command.");
	
	NSString *classNameChain = [params objectForKey:@"ClassNameChain"];
//    NSString *className      = [params objectForKey:@"ClassName"];
	NSString *attributeName  = [params objectForKey:@"AttributeName"];
 //   NSString *attributeValue = [params objectForKey:@"AttributeValue"];
	NSString *index          = [params objectForKey:@"ElementIndex"];
	
	UIServiceAssistant *uiAssistant = [[UIServiceAssistant alloc] init];
	
	[uiAssistant setCommand:GET_ELEMENT];
	[uiAssistant setParams:classNameChain attrName:attributeName index:index];
	//[uiAssistant comply];
	//	[uiAssistant complyWithCommand:GET_ELEMENT_VALUE];
	
	[self setCommandResult:[uiAssistant describeElement]];
	SFLog(@"[GetElementValueCommand] result: %@", commandResult);
	
	[uiAssistant release];
}

@end