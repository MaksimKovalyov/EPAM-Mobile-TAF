//
//  TestCommand.m
//  Frank
//
//  Created by Viktar on 5/17/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import "TestCommand.h"

// It should be removed. Just command that is used for testing new methods.
@implementation TestCommand

-(NSString *) performTestCommand {
	
	NSString *res = @"test";
	
	return res;
}


- (NSString *)handleCommandWithRequestBody:(NSString *)requestBody {
	
	//SFLog(@"Test command starts: %@", requestBody);
	
	NSString *filteredRequest = [[TAFFilter sharedInstance] replaceHEXCodesFrom:requestBody];//filterRequest(requestBody); 
	SFLog(@"filtered request: %@ ", filteredRequest);
	
	NSString *resultValue = [self performTestCommand];
	
	succes = YES;
	NSString *boolString = (succes ? @"true" : @"false");
	
	NSMutableDictionary *ress = [NSMutableDictionary dictionary];
	
	[ress setObject:resultValue forKey:@"data"];
	[ress setObject:boolString forKey:@"test_command"];
			
	return [ress JSONRepresentation];	
}

- (void) printChildsOf: (NSArray *)view level: (int) l{
	
	int count = [view count];
	SFLog(@" count: %i", count);
	SFLog(@" level: %i", l);
	
	for (int i=0; i < count; i++) {
		SFLog(@"level: %i", l);
		SFLog(@"index: %i", i);
		SFLog(@" view: %@", [view objectAtIndex:i]);
    }
}

- (void) printChildsOf: (UITableView *)view {
	
	NSArray *visibleCells = [view visibleCells];
	int count = [visibleCells count];
	SFLog(@" raws: %i", count);
	SFLog(@" table view: %@", view);
	
	for (int i=0; i < count; i++) {
		
		SFLog(@"index: %i", i);
		SFLog(@" view cell: %@", [[visibleCells objectAtIndex:i] description]);
		
	}
}


@end