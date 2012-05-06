//
//  DelegateCommand.m
//  ReutersInsider
//
//  Created by Viktar on 4/27/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import "MemoryWarningCommand.h"

/*
 *  Also add real memory warning simulation.
 */
@implementation MemoryWarningCommand

-(IBAction) performFakeMemoryWarning {
	#ifdef DEBUG_BUILD
    SEL memoryWarningSel = @selector(_performMemoryWarning);
	succes = [[UIApplication sharedApplication] respondsToSelector:memoryWarningSel];
    if (succes) {
		[[UIApplication sharedApplication] performSelector:memoryWarningSel];
    }else {
		SFLog(@"%@",@"UIApplication doesn't responds to selector: performMemoryWarning");
    }
	#else
        SFLog(@"%@",@"Warning: performFakeMemoryWarning called on a non debug build"); 
	#endif
}

- (NSString *)handleCommandWithRequestBody:(NSString *)requestBody {
	
	SFLog(@"Simulate Memory Warning command starts.");
		
	[self performFakeMemoryWarning];
	
	NSString *boolString = (succes ? @"true" : @"false");	
	NSMutableDictionary *ress = [NSMutableDictionary dictionary];
	[ress setObject:boolString forKey:@"memory_warning"];
	
	return [ress JSONRepresentation];
	
}


@end