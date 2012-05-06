//
//  SetElementValue.m
//  Frank
//
//  Created by Viktar on 7/6/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import "SetElementValue.h"
#import "UIServiceAssistant.h"
#import "SFLogger.h"
#import "Constants.h"

@implementation SetElementValue

@synthesize commandResult;

- (void) execute:(NSDictionary *)params{
	SFLog(@"[SetElementValueCommand] It starts to execute...");
	UIServiceAssistant *uiAssistant = [[UIServiceAssistant alloc] init];
	
    NSString *classNameChain = [params objectForKey:@"ClassNameChain"];
	NSString *className      = [params objectForKey:@"ClassName"];
	NSString *attributeName  = [params objectForKey:@"AttributeName"];
	NSString *attributeValue = [params objectForKey:@"AttributeValue"];
	NSString *value          = [params objectForKey:@"Value"];
	NSString *index          = [params objectForKey:@"ElementIndex"];

/*	
    FakeData for the test:  
	NSString *className      = @"UITextField";
	NSString *attributeName  = @"placeholder";
	NSString *attributeValue = @"Enter Username";
	NSString *value          = @"555";
	NSString *index          = @"0";
*/	
	
	[uiAssistant setCommand:SET_ELEMENT_VALUE];
	[uiAssistant setParams:className attrName:attributeName attrValue:attributeValue index:index value:value];
    [uiAssistant setParams:classNameChain value:value];
	[uiAssistant comply];
//	[uiAssistant complyWithCommand:SET_ELEMENT_VALUE];
    [self setCommandResult:[uiAssistant commandResult]];
	
	[uiAssistant release];		
}

@end
