//  UITree.m
//  Frank
//
//  Created by Viktar on 10/11/11.
//  Copyright 2011 EPAM Systems. All rights reserved.

#import "UITree.h"
#import "UIElement.h"
#import "SFLogger.h"

@implementation UITree

@synthesize currentUILayer;

-(id)initWith: (NSString *)className{

	self = [super init];

	[self setCurrentUILayer:[NSMutableArray array]];
	[self collectUILayerBy:className];
	
	if (self != nil){
		SFLog(@"[LOG][INIT] %@ is initialised.", [self description]);
	}
	
	return self;
}

-(void) addOneMoreObject:(id)_obj forKey:(NSString *)_key{
	[[self currentUILayer] addObject:[[[UIElement alloc] initWithObject:_obj] autorelease]];
	SFLog(@" Object is added. Info: %@", _obj);
	SFLog(@" Count: %d", [[self currentUILayer] count]);
	
}

-(void)collectNextLayer:(id)_object layerCutCriteria:(NSString *)cName{
	NSArray *nextUILayer = [_object subviews];
	int _length = [nextUILayer count];
	
	for (int i = 0; i < _length; i++) {
		id tmpObject = [nextUILayer objectAtIndex:i];
		
		if ([tmpObject isKindOfClass:NSClassFromString(cName)]) {
			[self addOneMoreObject:tmpObject forKey:cName];
		}
		
		if ([[tmpObject subviews] count] > 0) {
			[self collectNextLayer:tmpObject layerCutCriteria:cName];
		}
	}
}

-(void)collectUILayerBy:(NSString *) _className{
	SFLog(@"[UILayerCollection] Collecting starts...");
	
	UIWindow *window = [[UIApplication sharedApplication] keyWindow];
	NSArray *subviews = [window subviews];
	int length = [subviews count];
	
	for (int i=0; i < length; i++) {
		id object = [subviews objectAtIndex:i];
		if ([object isKindOfClass:NSClassFromString(_className)]) {
			[self addOneMoreObject:object forKey:_className];
		}
		
		if ([[object subviews] count] > 0) {
			[self collectNextLayer:object layerCutCriteria:_className];
		}
	}
}

@end