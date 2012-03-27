//
//  FlashCommand.m
//  Frank
//
//  Created by Viktar on 6/8/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import "FlashCommand.h"
#import "JSON.h"

// It should be removed. Flash is added to flash action via DoAction operation.

@implementation FlashCommand

-(void) performCommand {
	
}

- (NSString *)handleCommandWithRequestBody:(NSString *)requestBody {
	
	[self performCommand];
	
	succes = YES;
	NSString *boolString = (succes ? @"true" : @"false");
	NSDictionary *response = [NSDictionary dictionaryWithObject:boolString
														 forKey:@"test_command"];
	return [response JSONRepresentation];	
}

@end