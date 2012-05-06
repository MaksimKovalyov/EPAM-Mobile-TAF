//
//  RunScript.h
//  Frank
//
//  Created by Viktar on 6/8/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <FrankCommandRoute.h>
#import "JSON.h"

@interface RunScript : NSObject <FrankCommand> {
	
	BOOL succes;
	NSString *value;
}

@end
