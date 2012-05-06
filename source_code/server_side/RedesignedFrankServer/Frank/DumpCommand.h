//  DumpCommand.h
//  Frank
//
//  Created by phodgson on 5/30/10.
//  Copyright 2010 ThoughtWorks. See NOTICE file for details.

#import <Foundation/Foundation.h>
#import "JSON.h"
#import "objc/runtime.h"
#import "UIElement.h"
#import "FrankCommandRoute.h"
#import "SFLogger.h"

@interface DumpCommand : NSObject<FrankCommand> {

}

-(NSMutableDictionary *)filter:(NSDictionary *)dictionary;

@end