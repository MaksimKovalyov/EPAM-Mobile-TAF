//
//  GetElement.h
//  Frank
//
//  Created by Viktar on 11/28/11.
//  Copyright (c) 2011 EPAM Systems. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Command.h"

@interface GetElement : NSObject<Command> {
    
	NSDictionary* commandResult;
    
}

@property (nonatomic, retain) NSDictionary* commandResult;

@end
