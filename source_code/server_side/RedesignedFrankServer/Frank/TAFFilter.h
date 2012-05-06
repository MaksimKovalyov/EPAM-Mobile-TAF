//  Filter.h
//  Frank
//
//  Created by Viktar on 7/6/11.
//  Copyright 2011 EPAM Systems. All rights reserved.

#import <Foundation/Foundation.h>

@interface TAFFilter : NSObject {
	
}

+ (TAFFilter *) sharedInstance;
- (NSString *) replaceHEXCodesFrom: (NSString *)url;

@end