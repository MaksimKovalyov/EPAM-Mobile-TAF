//
//  UIXPath.h
//  Frank
//
//  Created by Viktar on 10/10/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface UIXPath : NSObject {

	NSString* xpath;	
	NSMutableArray* nodes;
}

@property (nonatomic, retain) NSString* xpath;
@property (nonatomic, retain) NSMutableArray* nodes;

-(id)initWith:(NSString *)_xpath;

-(void)parseNodes;
//-(NSString *)xpath;
//-(void)setXpath:(NSString *)_xpath;

@end
