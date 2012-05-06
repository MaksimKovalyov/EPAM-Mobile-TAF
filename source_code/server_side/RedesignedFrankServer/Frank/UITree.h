//  UITree.h
//  Frank
//
//  Created by Viktar on 10/11/11.
//  Copyright 2011 EPAM Systems. All rights reserved.

#import <Foundation/Foundation.h>

@interface UITree : NSObject {

	NSMutableArray *currentUILayer;
	
}

@property (nonatomic, retain) NSMutableArray* currentUILayer;

// make snapshot of the current UI state that cut into layer by className criteria
-(id)initWith: (NSString *)className;
-(void)collectUILayerBy:(NSString *) _className;

@end