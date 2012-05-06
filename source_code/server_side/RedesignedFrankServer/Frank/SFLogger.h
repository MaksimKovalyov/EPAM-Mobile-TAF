//
//  SFLog.h
//  Frank
//
//  Created by Viktar on 10/13/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <objc/objc.h>
#import <objc/runtime.h>

/*
 1: ON
 0: OFF
 */
#define LOG 0

@interface SFLogger : NSObject {
	
}

void SFLog(NSString *format,...);
void SFError(NSString *format,...);
+ (void) log:(NSArray *)array notice:(NSString *)notice_;
	
@end
