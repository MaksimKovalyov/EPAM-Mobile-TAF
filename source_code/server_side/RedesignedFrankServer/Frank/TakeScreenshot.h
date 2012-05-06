//
//  TakeScreenshot.h
//  Frank
//
//  Created by Viktar on 10/13/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import <Command.h>
#import "FrankCommandRoute.h"

@interface TakeScreenshot : NSObject<Command, FrankCommand> {
	
	NSString* commandResult;
	NSString* height;
	NSString* width_;
	NSString* format;
	NSString* orientation_;
	NSString* image_;
	
}

@property (nonatomic, retain) NSString* commandResult;
@property (nonatomic, retain) NSString* height;
@property (nonatomic, retain) NSString* width_;
@property (nonatomic, retain) NSString* format;
@property (nonatomic, retain) NSString* orientation_;
@property (nonatomic, retain) NSString* image_;

@end
