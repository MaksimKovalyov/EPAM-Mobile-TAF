//
//  Parser.m
//  Frank
//
//  Created by Viktar on 7/6/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import "TAFParser.h"


@implementation TAFParser

static TAFParser *sharedSingleton_ = nil;

+ (TAFParser *) sharedInstance {
	if (sharedSingleton_ == nil) {
		sharedSingleton_ = [[TAFParser alloc] init]; 
	}
	
	return sharedSingleton_;
}

- (void) dealloc
{
	[sharedSingleton_ release];
	[super dealloc];
}

- (void) log: (NSArray *)array notice: (NSString *)notice_{
	for (int i=0; i < [array count]; i++) {
		SFLog(@" %@: %@", notice_, [array objectAtIndex:i]);
	}
}

- (NSArray *) parseXPath: (NSString *)xpath {
	NSArray *res = [xpath componentsSeparatedByString:@"->"];
	
	[self log:res notice:@"parts"];
	
	return res;
}

- (NSArray *) parse: (NSString *)xpathNode {
	NSArray *res = [xpathNode componentsSeparatedByString:@"="];
	NSMutableArray *pathParts = nil;
	
	if ([res count] > 1){		
		NSString *classname_  = [res objectAtIndex:0];
		
		pathParts = [NSMutableArray arrayWithArray:[[res objectAtIndex:1] componentsSeparatedByString:@":"]];
		[pathParts addObject:classname_];
		
		[self log:pathParts notice:@"all elements"];
	}else {
		pathParts = [NSMutableArray array];
		[pathParts addObject:xpathNode];
		SFLog(@" all elements: %@", xpathNode);
	}
	
	return [NSArray arrayWithArray:pathParts];
}



@end
