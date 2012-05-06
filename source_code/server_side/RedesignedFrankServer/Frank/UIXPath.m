//  UIXPath.m
//  Frank
//
//  Created by Viktar on 10/10/11.
//  Copyright 2011 EPAM Systems. All rights reserved.

#import "UIXPath.h"
#import "SFLogger.h"
#import "TAFParser.h"

@implementation UIXPath

@synthesize xpath;
@synthesize nodes;


-(id)initWith:(NSString *)_xpath{
	
	self = [super init];
	
	if (self != nil){
		[self setXpath:_xpath];
		[self parseNodes];
		
		SFLog(@"[INIT] %@ is initialised.", self);
		SFLog(@"[INIT] XPath: %@ ", [self xpath]);	
	}else {
		SFError(@"[INIT] UIXPath is not initialised.");
	}
	
	return self;
}

-(void)parseNodes{
	NSMutableArray *xpathNodes = [NSMutableArray array];
	
	NSArray *xpathUnparsedNodes = [[TAFParser sharedInstance] parseXPath:[self xpath]];
	int length = [xpathUnparsedNodes count];
	for (int i = 0; i < length; i++) {
		[xpathNodes addObject:[[TAFParser sharedInstance] parse:[xpathUnparsedNodes objectAtIndex:i]]];
	}
	
	[self setNodes:xpathNodes];
	
	[SFLogger log:[self nodes] notice:@"XPathNodes: "];
}

@end