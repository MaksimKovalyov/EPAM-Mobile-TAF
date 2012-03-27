//
//  Filter.m
//  Frank
//
//  Created by Viktar on 7/6/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import "TAFFilter.h"

@implementation TAFFilter

static TAFFilter *sharedSingleton_ = nil;

+ (TAFFilter *) sharedInstance {
	if (sharedSingleton_ == nil) {
		sharedSingleton_ = [[TAFFilter alloc] init]; 
	}
	
	return sharedSingleton_;
}

- (void) dealloc
{
	[sharedSingleton_ release];
	[super dealloc];
}

- (NSString *) replaceHEXCodesFrom: (NSString *)url{
	NSString *result = [url stringByReplacingOccurrencesOfString:@"%7B" withString:@"{"];
	result = [result stringByReplacingOccurrencesOfString:@"%7D" withString:@"}"];
	result = [result stringByReplacingOccurrencesOfString:@"%22" withString:@"\""];
	result = [result stringByReplacingOccurrencesOfString:@"%20" withString:@" "];
	result = [result stringByReplacingOccurrencesOfString:@"%3E" withString:@">"];
    result = [result stringByReplacingOccurrencesOfString:@"%3C" withString:@"<"];
    result = [result stringByReplacingOccurrencesOfString:@"%5B" withString:@"["];
    result = [result stringByReplacingOccurrencesOfString:@"%5D" withString:@"]"];
	
	return result;
}

@end