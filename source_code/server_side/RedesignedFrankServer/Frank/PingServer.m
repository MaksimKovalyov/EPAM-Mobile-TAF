//  PingServer.m
//  Frank
//
//  Created by Viktar on 12/27/11.
//  Copyright (c) 2011 EPAM Systems. All rights reserved.

#import "PingServer.h"

@implementation PingServer

- (NSString *)handleCommandWithRequestBody:(NSString *)requestBody {
	
	SFLog(@"Ping Server command starts.");
    
    succes = YES;
	NSString *boolString = (succes ? @"PASS" : @"FAIL");	
	NSMutableDictionary *ress = [NSMutableDictionary dictionary];
	[ress setObject:boolString forKey:@"test_command"];
	
	return [ress JSONRepresentation];
	
}

@end