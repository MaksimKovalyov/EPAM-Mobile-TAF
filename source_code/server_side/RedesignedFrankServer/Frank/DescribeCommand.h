//
//  DescribeCommand.h
//  Frank
//
//  Created by Viktar on 11/12/11.
//  Copyright (c) 2011 EPAM Systems. All rights reserved.
//

#import "Command.h"

@interface DescribeCommand : NSObject<Command> {
    
	NSString* commandResult;
    NSDictionary* proxy;
    
}

@property (nonatomic, retain) NSString* commandResult;
@property (nonatomic, retain) NSDictionary* proxy;

@end
