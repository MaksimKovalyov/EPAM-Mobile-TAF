//
//  DelegateCommand.h
//  ReutersInsider
//
//  Created by Viktar on 4/27/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <FrankCommandRoute.h>
#import "JSON.h"
#import "SFLogger.h"

@interface MemoryWarningCommand : NSObject <FrankCommand> {

	BOOL succes;
	
}

@end
