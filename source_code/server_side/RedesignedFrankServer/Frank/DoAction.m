//  DoAction.m
//  Frank
//
//  Created by Viktar on 7/7/11.
//  Copyright 2011 EPAM Systems. All rights reserved.

#import "DoAction.h"
#import "UIServiceAssistant.h"
#import "Constants.h"
#import "SFLogger.h"

@implementation DoAction

@synthesize commandResult;

- (void) execute:(NSDictionary *)params{
	SFLog(@"[DoActionCommand] It starts to execute...");
	
	NSString *className      = [params objectForKey:@"ClassName"];
    NSString *classNameChain = [params objectForKey:@"ClassNameChain"];
	NSString *attributeName  = [params objectForKey:@"AttributeName"];
	NSString *attributeValue = [params objectForKey:@"AttributeValue"];
	NSString *action         = [params objectForKey:@"Action"];
	NSString *index          = [params objectForKey:@"ElementIndex"];
	NSString *xpath          = [params objectForKey:@"XPath"];
    
	UIServiceAssistant *uiAssistant = [[UIServiceAssistant alloc] init];
	
	[uiAssistant setCommand:DO_ACTION];
	[uiAssistant setParams:className attrName:attributeName attrValue:attributeValue index:index action:action];
    [uiAssistant setClassNameChain:classNameChain];
    [uiAssistant setXpath:xpath];
	[uiAssistant comply];
	//	[uiAssistant complyWithCommand:DO_ACTION];
    //SFLog(@"before");
	[self setCommandResult:[uiAssistant commandResult]];
    //SFLog(@"after");
    
	[uiAssistant release];	
}

@end