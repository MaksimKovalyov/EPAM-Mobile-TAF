//
//  UIElement.h
//  Frank
//
//  Created by Viktar on 10/10/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "objc/runtime.h"
#import <CoreGraphics/CGGeometry.h>

@interface UIElement : UIView {

	id sourceObject;
    NSString* xpath;
	
}

@property (nonatomic, retain) id sourceObject;
@property (nonatomic, retain) NSString* xpath;

-(id) initWithObject: (id) _sourceObject;

-(void) setText: (NSString *) _text;
-(void) becomeActive;
-(void) flash;
//-(void) scrollDown;

-(void) scrollLeft;
-(void) scrollRight;
-(void) scrollDown;
-(void) scrollUp;

//- (void)simulateScroll:(float)point_x_gap andSecond:(float)point_y_gap;

-(void) actWith: (NSString *) _action;

//-(NSString *) getValueBy: (NSString *) _attributeName;
-(NSString *) getPropertyWith: (NSString *) _attributeName;

+ (id) callFrom:(id)target method:(SEL)_selector;


// getters for custom attributes
- (NSString *)HTMLSource;
- (NSString *)contentPosition;
- (int)numberOfChilds;
- (NSString *)systemFont;
- (NSString *)className;

+(NSDictionary *)describe:(id)object;
-(NSDictionary *)JSONProxy;

+ (UIScrollView *)scrollViewTouch:(UIView*)view withTouches:(NSSet*)set andEvent:(UIEvent*)event_;

-(void)wait:(double)seconds;

@end
