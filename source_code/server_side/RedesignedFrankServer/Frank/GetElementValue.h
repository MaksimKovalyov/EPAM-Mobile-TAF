//
//  GetElementValue.h
//  Frank
//
//  Created by Viktar on 7/7/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import "Command.h"

@interface GetElementValue : NSObject<Command> {

	NSString* commandResult;

}

@property (nonatomic, retain) NSString* commandResult;

@end
