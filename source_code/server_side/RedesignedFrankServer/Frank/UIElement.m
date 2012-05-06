//  UIElement.m
//  Frank
//
//  Created by Viktar on 10/10/11.
//  Copyright 2011 EPAM Systems. All rights reserved.

#import "UIElement.h"
#import "SFLogger.h"
#import <Foundation/Foundation.h>

@interface UIView (TouchViewFix)

- (void)handleTap:(UITapGestureRecognizer*)recognizer;

@end

@interface UIScrollView (UITouchScrollViewFix)
    
- (BOOL)touchesShouldBegin:(NSSet *)touches withEvent:(UIEvent *)event inContentView:(UIView *)view;

@end

@implementation UIScrollView (PrivateCase)

- (void) touchesEnded: (NSSet *) touches withEvent: (UIEvent *) event 
{
    SFLog(@" have touched....");
    [super touchesEnded: touches withEvent: event];
}

/*
- (UIView *)hitTest:(CGPoint)point withEvent:(UIEvent *)event {
    
    UIView *result = nil;
    for (UIView *child in self.subviews)
        if ([child pointInside:point withEvent:event])
            if ((result = [child hitTest:point withEvent:event]) != nil)
                break;
    
    return result;
}
*/

@end

@implementation NSObject (MapKitUISpecHack) 
- (id)_mapkit_hasPanoramaID { 
	return nil; 
} 

@end


// strange issue with specific crash (NSObject category is not enough)
@implementation UIView (MapKitUISpecHack) 
- (id)_mapkit_hasPanoramaID { 
    return nil; 
}

@end



// UISpec implementation.
@interface UITouch (Synthesize)

- (id)initInView:(UIView *)view;

@end

@implementation UITouch (Synthesize)
           
//
// initInView:phase:
//
// Creats a UITouch, centered on the specified view, in the view's window.
// Sets the phase as specified.
//

/*
 - (id)initInViewScroll:(UIView *)view
 {
 self = [super init];
 if (self != nil)
 {
 CGRect frameInWindow;
 if ([view isKindOfClass:[UIWindow class]])
 {
 frameInWindow = view.frame;
 }
 else
 {
 frameInWindow =
 [view.window convertRect:view.frame fromView:view.superview];
 }
 
 _tapCount = 1;
 _locationInWindow =
 CGPointMake(
 frameInWindow.origin.x + 0.5 * frameInWindow.size.width,
 frameInWindow.origin.y + 0.5 * frameInWindow.size.height);
 
 CGPoint _prev_loc =
 CGPointMake(
 frameInWindow.origin.x + 0.5 * frameInWindow.size.width,
 frameInWindow.origin.y + 0.4 * frameInWindow.size.height);
 
	
 _previousLocationInWindow = _locationInWindow;
 
 UIView *target = [view.window hitTest:_locationInWindow withEvent:nil];
 
 _window = [view.window retain];
 _view = [target retain];
 _phase = UITouchPhaseBegan;
 _touchFlags._firstTouchForView = 1;
 _touchFlags._isTap = 1;
 _timestamp = [NSDate timeIntervalSinceReferenceDate];
 }
 return self;
 }
 */      


- (id)initInView:(UIView *)view
{
	self = [super init];
	if (self != nil)
	{
		CGRect frameInWindow;
		if ([view isKindOfClass:[UIWindow class]])
		{
			frameInWindow = view.frame;
		}
		else
		{
			frameInWindow =
			[view.window convertRect:view.frame fromView:view.superview];
		}
		
		_tapCount = 1;
		_locationInWindow =
		CGPointMake(
					frameInWindow.origin.x + 0.5 * frameInWindow.size.width,
					frameInWindow.origin.y + 0.5 * frameInWindow.size.height);
		_previousLocationInWindow = _locationInWindow;
		
		UIView *target = [view.window hitTest:_locationInWindow withEvent:nil];
		
		_window = [view.window retain];
		_view = [target retain];
        
        SFLog(@" init in view: %@", _view);
        
		_phase = UITouchPhaseBegan;
		_touchFlags._firstTouchForView = 1;
		_touchFlags._isTap = 1;
		_timestamp = [NSDate timeIntervalSinceReferenceDate];
	}
    
//    [[self view] ];
    
	return self;
}

//
// setPhase:
//
// Setter to allow access to the _phase member.
//
- (void)setPhase:(UITouchPhase)phase
{
	_phase = phase;
	_timestamp = [NSDate timeIntervalSinceReferenceDate];
}

//
// setPhase:
//
// Setter to allow access to the _locationInWindow member.
//
- (void)setLocationInWindow:(CGPoint)location
{
	_previousLocationInWindow = _locationInWindow;
	_locationInWindow = location;
	_timestamp = [NSDate timeIntervalSinceReferenceDate];
}

@end

//
// GSEvent is an undeclared object. We don't need to use it ourselves but some
// Apple APIs (UIScrollView in particular) require the x and y fields to be present.
//
@interface GSEventProxy : NSObject
{
@public
    //int ignored1[5];
	float x;
	float y;
	//int ignored2[24];
    
	unsigned int flags;
	unsigned int type;
	unsigned int ignored1;
	float x1;
	float y1;
	float x2;
	float y2;
	unsigned int ignored2[10];
	unsigned int ignored3[7];
	float sizeX;
	float sizeY;
	float x3;
	float y3;
	unsigned int ignored4[3];
}
@end
@implementation GSEventProxy
@end

//
// PublicEvent
//
// A dummy class used to gain access to UIEvent's private member variables.
// If UIEvent changes at all, this will break.
//
@interface PublicEvent : NSObject
{
@public
    GSEventProxy           *_event;
    NSTimeInterval          _timestamp;
    NSMutableSet           *_touches;
    CFMutableDictionaryRef  _keyedTouches;
}
@end

@implementation PublicEvent
@end

@interface UIEvent (Creation)

- (id)_initWithEvent:(GSEventProxy *)fp8 touches:(id)fp12;

@end

//
// UIEvent (Synthesize)
//
// A category to allow creation of a touch event.
//
@implementation UIEvent (Synthesize)
             
/*
 - (id)initWithTouchScroll:(UITouch *)touch
 {
 CGPoint location = [touch locationInView:touch.window];
 GSEventProxy *gsEventProxy = [[GSEventProxy alloc] init];
 gsEventProxy->x1 = location.x;
 gsEventProxy->y1 = location.y;
 gsEventProxy->x2 = location.x;
 gsEventProxy->y2 = location.y;
 gsEventProxy->x3 = location.x;
 gsEventProxy->y3 = location.y;
 gsEventProxy->sizeX = 1.0;
 gsEventProxy->sizeY = 1.0;
 gsEventProxy->flags = ([touch phase] == UITouchPhaseEnded) ? 0x1010180 : 0x3010180;
 gsEventProxy->type = 3001;	
 
 
 if ([touch phase] == UITouchPhaseEnded){
 gsEventProxy->x1 = location.x;
 gsEventProxy->y1 = 0.4*location.y;
 gsEventProxy->x2 = location.x;
 gsEventProxy->y2 = 0.4*location.y;
 gsEventProxy->x3 = location.x;
 gsEventProxy->y3 = 0.4*location.y;		
 }
 
 //
 // On SDK versions 3.0 and greater, we need to reallocate as a
 // UITouchesEvent.
 //
 Class touchesEventClass = objc_getClass("UITouchesEvent");
 if (touchesEventClass && ![[self class] isEqual:touchesEventClass])
 {
 [self release];
 self = [touchesEventClass alloc];
 }
 
 self = [self _initWithEvent:gsEventProxy touches:[NSSet setWithObject:touch]];
 if (self != nil)
 {
 }
 return self;
 }
 */
        
- (id)initWithTouch:(UITouch *)touch
{
	CGPoint location = [touch locationInView:touch.window];
	GSEventProxy *gsEventProxy = [[[GSEventProxy alloc] init] autorelease];
	gsEventProxy->x1 = location.x;
	gsEventProxy->y1 = location.y;
	gsEventProxy->x2 = location.x;
	gsEventProxy->y2 = location.y;
	gsEventProxy->x3 = location.x;
	gsEventProxy->y3 = location.y;
	gsEventProxy->sizeX = 1.0;
	gsEventProxy->sizeY = 1.0;
	gsEventProxy->flags = ([touch phase] == UITouchPhaseEnded) ? 0x1010180 : 0x3010180;
	gsEventProxy->type = 3001;	
	
	//
	// On SDK versions 3.0 and greater, we need to reallocate as a
	// UITouchesEvent.
	//
	Class touchesEventClass = objc_getClass("UITouchesEvent");
	if (touchesEventClass && ![[self class] isEqual:touchesEventClass])
	{
		[self release];
		self = [touchesEventClass alloc];
	}
	
    self = [self _initWithEvent:gsEventProxy touches:[NSSet setWithObject:touch]];

	return self;
}

@end
     

// proxy object for native objects
@implementation UIElement

@synthesize sourceObject, xpath;

+ (UIScrollView*)findFirstScrollView:(UIView*)scrollView{
    UIScrollView* sv = nil;
    
    SFLog(@"[LOG] potential scroll view: %@", scrollView);
    
    if (scrollView == nil) {
        return nil;
    }
    if ([scrollView isKindOfClass:[UIScrollView class]]){
        sv = (UIScrollView*)scrollView;
    }else{
        sv = [UIElement findFirstScrollView:[scrollView superview]];
    }
        
    return sv;
}

+ (UIScrollView *)scrollViewTouch:(UIView*)view withTouches:(NSSet*)set andEvent:(UIEvent*)event_{
    SFLog(@"[LOG] view class, the first in chain: %@", view);
    
    // update: it was added touhesEnded. 
    UIScrollView *v = [UIElement findFirstScrollView:view];
    if (v != nil) {
        BOOL bb = [v touchesShouldBegin:set withEvent:event_ inContentView:[view superview]];
  
        //SFLog(@"[LOG] view class that have been touched: %@", v);
        //SFLog(@"[LOG] result of touch begins phase: %@", (bb ? @"true" : @"false"));
    }else{
        SFLog(@"[LOG] view class that have been touched is nil.");    
    }
    
    return v;
}

+ (void)scrollViewTouch:(UIView*)view withTouches:(NSSet*)set andEndEvent:(UIEvent*)event_ inView:(UIView*)sv{
    SFLog(@"[LOG] view class, the first in chain: %@", view);
    
    //UIScrollView *v = [UIElement findFirstScrollView:view];
    if (sv != nil) {
        //[v setTouchedView:[view superview]];
        [sv touchesEnded:set withEvent:event_];
      
    }else{
        SFLog(@"[LOG] view class that have been touched is nil.");    
    }
}


-(id) initWithObject: (id) _sourceObject{
	self = [super init];
	
	// issue: how to init object of my own class correctly from the object of another class? 
	// just wrap with additional functionality
	if (self != nil) {
	//	self = _sourceObject;
		[self setSourceObject:_sourceObject];
	}
	
	// dynamically add all properties of method and 
	// declare it using interface. 
	// (similar to proxy object light incorporation from UIKit native object into UIElement proxy object)
	// ...?
	
	// or make container for source object

	
	return self;
}

-(NSDictionary *)JSONProxy{
    return [UIElement describe:[self sourceObject]];
}

+(NSDictionary *)describe:(id)object {
	//SFLog(@"describing object %@", object);
	NSMutableDictionary *properties = [NSMutableDictionary dictionary];
	Class clazz = [object class];
	do {
		//SFLog(@"describing class %@", NSStringFromClass(clazz));
		unsigned i;
		id objValue;
		int intValue;
		long longValue;
		char *charPtrValue;
		char charValue;
		short shortValue;
		float floatValue;
		double doubleValue;
		
		unsigned int propertyCount = 0;
		objc_property_t *propertyList = class_copyPropertyList(clazz, &propertyCount);
		//SFLog(@"property count = %d", propertyCount);
		for (i=0; i<propertyCount; i++) {
			
			objc_property_t *thisProperty = propertyList + i;
			const char* propertyName = property_getName(*thisProperty);
			const char* propertyAttributes = property_getAttributes(*thisProperty);
			
			NSString *key = [NSString stringWithFormat:@"%s", propertyName];
			NSString *keyAttributes = [NSString stringWithFormat:@"%s", propertyAttributes];
			//SFLog(@"key = %@", key);
			//SFLog(@"keyAttributes = %@", keyAttributes);
			
			NSArray *attributes = [keyAttributes componentsSeparatedByString:@","];
			if ([[[attributes lastObject] substringToIndex:1] isEqualToString:@"G"]) {
				key = [[attributes lastObject] substringFromIndex:1];
			}
			
			SEL selector = NSSelectorFromString(key);
			if ([object respondsToSelector:selector]) {
				NSMethodSignature *sig = [object methodSignatureForSelector:selector];
				//SFLog(@"sig = %@", sig);
				NSInvocation *invocation = [NSInvocation invocationWithMethodSignature:sig];
				[invocation setSelector:selector];
				//SFLog(@"invocation selector = %@", NSStringFromSelector([invocation selector]));
				[invocation setTarget:object];
				
                // refactore
				@try {
					[invocation invoke];
				}
				@catch (NSException *exception) {
					SFLog(@"UIQuery.describe caught %@: %@", [exception name], [exception reason]);
					continue;
				}
				
				const char* type = [[invocation methodSignature] methodReturnType];
				NSString *returnType = [NSString stringWithFormat:@"%s", type];
				const char* trimmedType = [[returnType substringToIndex:1] cStringUsingEncoding:NSASCIIStringEncoding];
				//SFLog(@"return type = %@", returnType);
				switch(*trimmedType) {
					case '@':
						[invocation getReturnValue:(void **)&objValue];
						if (objValue == nil) {
							[properties setObject:[NSString stringWithFormat:@"%@", objValue] forKey:key];
						} else {
							[properties setObject:objValue forKey:key];
						}
						break;
					case 'i':
						[invocation getReturnValue:(void **)&intValue];
						[properties setObject:[NSString stringWithFormat:@"%i", intValue] forKey:key];
						break;
					case 's':
						[invocation getReturnValue:(void **)&shortValue];
						[properties setObject:[NSString stringWithFormat:@"%ud", shortValue] forKey:key];
						break;
					case 'd':
						[invocation getReturnValue:(void **)&doubleValue];
						[properties setObject:[NSString stringWithFormat:@"%lf", doubleValue] forKey:key];
						break;
					case 'f':
						[invocation getReturnValue:(void **)&floatValue];
						[properties setObject:[NSString stringWithFormat:@"%f", floatValue] forKey:key];
						break;
					case 'l':
						[invocation getReturnValue:(void **)&longValue];
						[properties setObject:[NSString stringWithFormat:@"%ld", longValue] forKey:key];
						break;
					case '*':
						[invocation getReturnValue:(void **)&charPtrValue];
						[properties setObject:[NSString stringWithFormat:@"%s", charPtrValue] forKey:key];
						break;
					case 'c':
						[invocation getReturnValue:(void **)&charValue];
						[properties setObject:[NSString stringWithFormat:@"%d", charValue] forKey:key];
						break;
					case '{': {
						unsigned int length = [[invocation methodSignature] methodReturnLength];
						void *buffer = (void *)malloc(length);
						[invocation getReturnValue:buffer];
						NSValue *value = [[[NSValue alloc] initWithBytes:buffer objCType:type] autorelease];
						[properties setObject:value forKey:key];
						break;
					}
				}
			}
		}
	} while ((clazz = class_getSuperclass(clazz)) != nil);
    return properties;
}

-(void)touch{
	
//    [[[UITouch alloc] initInView:[self sourceObject]] autorelease];
//    [UITouch touchInView:[self sourceObject]];
    
    UITouch *touch_ = [[UITouch alloc] initInView:[self sourceObject]];
	UIEvent *eventDown = [[NSClassFromString(@"UITouchesEvent") alloc] initWithTouch:touch_];
	NSSet *touches = [[NSMutableSet alloc] initWithObjects:&touch_ count:1];
	
    //[UIElement scrollViewTouch:[self sourceObject] withTouches:touches andEvent:eventDown];
    [UIElement scrollViewTouch:[self sourceObject] withTouches:touches andEvent:eventDown];       
	
    [touch_.view touchesBegan:touches withEvent:eventDown];
	
	UIEvent *eventUp = [[NSClassFromString(@"UITouchesEvent") alloc] initWithTouch:touch_];
	[touch_ setPhase:UITouchPhaseEnded];
	
    //[UIElement scrollViewTouch:[self sourceObject] withTouches:touches andEndEvent:eventUp];       
	[touch_.view touchesEnded:touches withEvent:eventUp];
	//[touch_.view touchesEnded:touches withEvent:eventDown];
	
	[eventDown release];
	[eventUp release];
	[touches release];
	[touch_ release];
    
	[self wait:.5];	
}

-(id)dynamicallyCall:(NSString*) methodName{
    id result = nil;
    // new refactored code:
    SEL _selector = NSSelectorFromString(methodName);
    
    id real_object_target = [self sourceObject];
    id proxy_object_target = self;
    
    // check real action
    // check custom action: HTMLSource, systemFont, other specific for testing attributes
    if ([real_object_target respondsToSelector:_selector]){
        result = [UIElement callFrom:real_object_target method:_selector];
    }
    else{
        if ([proxy_object_target respondsToSelector:_selector]){
            result = [UIElement callFrom:proxy_object_target method:_selector];
        }else{
            SFLog(@"The method %@ is not supported for the element.", methodName);
        }
    }
    
    return result;
}


// refactore method
-(void) actWith: (NSString *) _action{
    SFLog(@"[LOG] action: %@ ", _action);

    // temp usage extra action:
    [self flash];
    
    [self dynamicallyCall:_action];
    
 }

/*
- (void)simulateScroll:(float)point_x_gap andSecond:(float)point_y_gap{
    float x_center_p1 = [(UIView*)[self sourceObject] accessibilityFrame].origin.x + [(UIView*)[self sourceObject] accessibilityFrame].size.width*0.5;
    float y_center_p1 = [(UIView*)[self sourceObject] accessibilityFrame].origin.y + [(UIView*)[self sourceObject] accessibilityFrame].size.height*0.5;

    float x_center_p2 = x_center_p1 + point_x_gap;
    float y_center_p2 = y_center_p1 + point_y_gap;
    
    
    
    SFLog(@" 1st gap: %d, 2nd gap: %d", point_x_gap, point_y_gap);
    
    //CGPoint tapPoint = CGPointZero;
    // Mid point
    // tapPoint = CGPointCenteredInRect([(UIView*)[self sourceObject] accessibilityFrame]);
    
    
    CGPoint startPoint = CGPointMake(x_center_p1, y_center_p1);
    CGPoint endPoint = CGPointMake(x_center_p2, y_center_p2);

    SFLog(@"_____________ start 1 point: %f, end point: %f", x_center_p1, y_center_p1);
    SFLog(@"_____________ start 2 point: %f, end point: %f", x_center_p2, y_center_p2);
    
    [(UIView*)[self sourceObject] dragFromPoint:startPoint toPoint:endPoint];
}
*/

/*
// experimental code:
- (NSString *)HTMLClick:(NSString*)xpath_{
    UIWebView *webView = [self sourceObject];
  
    xpath = [xpath stringByReplacingOccurrencesOfString:@"-" withString:@"/"];
    
    NSString* elementXPathClick = [NSString stringWithFormat:@"simulateClick(document.evaluate( \"//%@\",                                         document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue)", xpath];
    
   // NSString* elementClickByXPath = [NSString stringWithFormat:@"simulateClick(%@);", elementXPathValue];
    
   // NSString* elementClick = [NSString stringWithFormat:@"simulateClick(window.document.getElementById(\"%@\"));", xpath];
    
    NSString* element = [NSString stringWithFormat:@"document.evaluate( \"//%@\",                                         document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.innerHTML", xpath];
    
    NSString* injectJavaScriptClickSimulator = [NSString stringWithString:@"                                                              function simulateClick(target, options) {var event = target.ownerDocument.createEvent('MouseEvents');                                                    options = options || ['click', true, true, target.ownerDocument.defaultView, 1, 0, 0, 0, 0, false, false, false, false, 0, null                                                                          ];        event.initMouseEvent.apply(event, options);                                                                                                                                                                                        target.dispatchEvent(event);            }                           "];
    
    NSString* elementChangeColor = [NSString stringWithFormat:@"document.evaluate( \"//%@\",                                         document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.style.backgroundColor = 'yellow'", xpath];
    
  //  NSString* elementChangeColor = [NSString stringWithFormat:@"window.jQuery(\"%@\").animate({ \"color\" : \"#0ef\" }, 500);", xpath];
    
    //$("#abc").animate({ "color" : "#0ef" }, 500);
    
    //NSString* elementHidden = [NSString stringWithFormat:@"window.document.getElementById(\"%@\").style.display = 'none'", xpath];
    //NSString* elementjQueryClick = [NSString stringWithFormat:@"window.jQuery(\"%@\").click()", xpath];
    
    //NSString* elementAlert = [NSString stringWithFormat:@"window.alert(typeof window.$ + typeof window.jQuery);", xpath];
    
//    NSString* elementAlert = [NSString stringWithFormat:@"alert(typeof window.simulateClick == 'function');", xpath];
    
    [webView stringByEvaluatingJavaScriptFromString:elementChangeColor];
//    [webView stringByEvaluatingJavaScriptFromString:elementHidden];
    
    SFLog(@" element: %@", element);
    
    SFLog(@" element: %@", [webView stringByEvaluatingJavaScriptFromString:element]);
    
    SFLog(@" element click function: %@", injectJavaScriptClickSimulator);
    SFLog(@" element click function: %@", elementXPathClick);

    [webView stringByEvaluatingJavaScriptFromString:injectJavaScriptClickSimulator];
    [webView stringByEvaluatingJavaScriptFromString:elementXPathClick];
    
   // [webView stringByEvaluatingJavaScriptFromString:elementAlert];
 
   // [webView stringByEvaluatingJavaScriptFromString:elementAlert];
    
    
    //[webView stringByEvaluatingJavaScriptFromString:elementClick];
    
    ////@"document.body.innerHTML"]; 
    return [webView stringByEvaluatingJavaScriptFromString:element];
}
*/

- (NSString *)HTMLAllSource{
    UIWebView *webView = [self sourceObject];
    return [webView stringByEvaluatingJavaScriptFromString:@"document.documentElement.innerHTML"];
}

// stub method
- (NSString *)HTMLSource{
    UIWebView *webView = [self sourceObject];
    return [webView stringByEvaluatingJavaScriptFromString:@"document.getElementById(\"marketDataTabs\").innerHTML"];
}


// redesign scroll actions
// add parameterized method
-(void)scroll:(CGPoint)offsetPoint {
    UIScrollView *scroller = (UIScrollView *)[self sourceObject];
	[scroller setContentOffset:offsetPoint animated:YES];
}

-(void)scrollToTop {
    [self scroll:CGPointMake(0, 0)];
}

-(void)scrollToBottom {
	UIScrollView *scroller = (UIScrollView *)[self sourceObject];
    CGFloat scrollerSize = scroller.contentSize.height;
    CGFloat offset = scroller.frame.size.height;
	[self scroll:CGPointMake(0,scrollerSize - offset)];
}

-(void)scrollHorizontally:(int)offset{
    [self scroll:CGPointMake(offset,0)];
}

-(void)scrollVertically:(int)offset{
    [self scroll:CGPointMake(0,offset)];
}

-(void) scrollLeft {
    UIScrollView *scroller = (UIScrollView *)[self sourceObject];
    [self scrollHorizontally:scroller.frame.origin.x];// - offset];
}

-(void) scrollRight {
    UIScrollView *scroller = (UIScrollView *)[self sourceObject];
    CGFloat offset = scroller.frame.size.width;
	[self scrollHorizontally:scroller.frame.origin.x + offset];
}

-(void) scrollRightShort {
    UIScrollView *scroller = (UIScrollView *)[self sourceObject];
    CGFloat offset = scroller.frame.size.width;
	[self scrollHorizontally:scroller.frame.origin.x + 0.5*offset];
}

-(void) scrollUp {
    UIScrollView *scroller = (UIScrollView *)[self sourceObject];
    CGFloat offset = scroller.frame.size.height;
    [self scrollVertically:scroller.frame.origin.y + offset];
}

-(void) scrollUpShort {
    UIScrollView *scroller = (UIScrollView *)[self sourceObject];
    CGFloat offset = scroller.frame.size.height;
    [self scrollVertically:scroller.frame.origin.y + 0.5*offset];
}

-(void)scrollDown{
    UIScrollView *scroller = (UIScrollView *)[self sourceObject];
    //CGFloat offset = scroller.frame.size.height;
    [self scrollVertically:scroller.frame.origin.y]; //- offset];
}

-(void) setText: (NSString *) _text{
	// unresolved issue.
	[[self sourceObject] setText:_text];
}

-(void) becomeActive{
	[[self sourceObject] performSelector:@selector(becomeFirstResponder) withObject:nil afterDelay:0.0];
}

- (void)flash{
	UIView *view = (UIView *)[self sourceObject];
	view.alpha = 0.3;
	UIView *v1 = view;//[view parent];
	UIColor *tempColor = [v1.backgroundColor retain];
	
	for (int i=0; i<7; i++) {
		v1.backgroundColor = [UIColor yellowColor];
		//view.hidden = YES;
		CFRunLoopRunInMode(kCFRunLoopDefaultMode, .08, false);
		v1.backgroundColor = [UIColor blueColor];
		//view.hidden = NO;
		CFRunLoopRunInMode(kCFRunLoopDefaultMode, .08, false);
	}
	view.alpha = 1.0;
	v1.backgroundColor = tempColor;
	[tempColor release];
}

-(void)wait:(double)seconds {
	CFRunLoopRunInMode(kCFRunLoopDefaultMode, seconds, false);
}

// dynamically call validated method
// only case for simple methods without args, for example: [self text];
+ (id) callFrom:(id)target method:(SEL)_selector{

    // prepare params before invocation
	NSMethodSignature *signature = [target methodSignatureForSelector:_selector];
	
    // set up invocation and invoke the method
    NSInvocation *invocation = [NSInvocation invocationWithMethodSignature:signature];
	[invocation setTarget:target];	
	[invocation setSelector:_selector];	
	[invocation invoke];
	
    // set up return value
	const char *returnType = signature.methodReturnType;	
	id returnValue = nil;
    
    // get return value
    // case: nil object
	if( !strcmp(returnType, @encode(void)) )
		returnValue =  nil;
    
    // case: id object
	if( !strcmp(returnType, @encode(id)) ) // retval is an objective c object
		[invocation getReturnValue:&returnValue];
    
    // case: BOOL object
    if( !strcmp(returnType, @encode(BOOL)) ){ // retval is an BOOL object
		BOOL retVal;
        [invocation getReturnValue:&retVal];
        returnValue = retVal ? @"1" : @"0";
    }
    
    // case: int object
    if( !strcmp(returnType, @encode(int)) ){ // retval is an objective c object
		int retVal;
        [invocation getReturnValue:&retVal];
        returnValue = [NSString stringWithFormat:@"%d", retVal];
    }
	
	SFLog(@" return value: %@", returnValue);   
    
	return returnValue;
}


// refactore method to many params method and all returns value
- (id) applyTo:(id)target with:(NSString *)_sel and:(NSString *)_arg{

    SFLog(@" invoke method: sel=%@", _sel);

    
	// refromat construction report
    //	SFLog(@" target: %@ ", target);
    //	SFLog(@" sel: %@ ", _sel);
    //	SFLog(@" arg: %@ ", _arg);
	
	SEL _selector = NSSelectorFromString(_sel);
	
    if (![target respondsToSelector:_selector]){
        SFLog(@"The object=%@ is not respond to selector=%@.", [target class], _sel);
        return nil;
    }
        
    
	NSMethodSignature *signature = [target methodSignatureForSelector:_selector];
//	NSUInteger requiredNumberOfArguments = signature.numberOfArguments - 2; // Indices 0 and 1 indicate the hidden arguments self and _cmd, respectively
	
//	SFLog(@" required args: %i ", requiredNumberOfArguments);
	
	//	if( requiredNumberOfArguments != [arguments_ count] )
	//		[NSException raise:@"wrong number of arguments" 
	//			format:@"%@ takes %i arguments, but %i were supplied", NSStringFromSelector(selector_), requiredNumberOfArguments, [arguments_ count] ];
	
	NSInvocation *invocation = [NSInvocation invocationWithMethodSignature:signature];
	[invocation setTarget:target];
	
	[invocation setSelector:_selector];
	
	//NSInteger index = 2; // Indices 0 and 1 indicate the hidden arguments self and _cmd, respectively
	//	for( id arg in arguments_ ) {
	//		[invocation setArgument:&arg atIndex:index++];
	//		SFLog(@" arg: %@ ", arg);
	//	}
	
	//SFLog(@" before set arg ");
	//[invocation setArgument:&_arg atIndex:index];
	
//	SFLog(@" before invoke target ");
	[invocation invoke];//WithTarget:target];
	
//	SFLog(@" before ret val ");
	const char *returnType = signature.methodReturnType;
	//SFLog(@" ret type: %@", returnType);
	
	id returnValue = nil;
	if( !strcmp(returnType, @encode(void)) )
		returnValue =  nil;
	if( !strcmp(returnType, @encode(id)) ) // retval is an objective c object
		[invocation getReturnValue:&returnValue];
    if( !strcmp(returnType, @encode(BOOL)) ){ // retval is an objective c object
		BOOL retVal;
        [invocation getReturnValue:&retVal];
        NSString* rv = retVal ? @"1" : @"0";
         SFLog(@" val: %@", rv);
        
        return rv;
    }
    
    if( !strcmp(returnType, @encode(int)) ){ // retval is an objective c object
		int retVal;
        [invocation getReturnValue:&retVal];
        //NSString* rv = [retVal ];
        SFLog(@" val int: %@", retVal);
        //UITextAlignment* ta = [target textAlignment];
        //SFLog(@" val int 2: ", ta);
        //return retVal;
    }

	
	SFLog(@" ret val: %@", returnValue);   
    
	return returnValue;
}

// [REFACTORED] @Viktar
// 2 strategies: 
// 1) JSONProxy - get all attributes by one command (common time of command is not long)
// 2) dynamically construct the concrete attribute (if attribute is unexist in real object then add it
// to UIElement class, also all unreal attributes should be added into JSON via [UIElement JSONProxy] method)
-(NSString *) getPropertyWith: (NSString *) _attributeName{
    id resultValue = nil;
    resultValue = [self dynamicallyCall:_attributeName];
    
    SFLog(@"[Searching property] the attribute name: %@, the actual attribute value ", _attributeName, resultValue);
	
	return resultValue;
}

// getters for custom attributes:
// ........................................................................................
- (NSString *)contentPosition{
   return [NSString stringWithFormat:@"x=%f, y=%f", [(UIScrollView*)[self sourceObject] contentOffset].x , [(UIScrollView*)[self sourceObject] contentOffset].y];
}

- (int)numberOfChilds{
    return [[[self sourceObject] subviews] count];
}

// stub method
- (NSString *)systemFont{
    // stub implementation:
    return [[self sourceObject] stringByEvaluatingJavaScriptFromString:@"document.styleSheets[0].rules[1].style.fontSize"];
}

- (NSString *)className{
    return NSStringFromClass([[self sourceObject] class]);
}


-(void)dealloc {
	[super dealloc];
}

/*

- (void)scrollImplementedByDraggabilityToRight{
    
    SFLog(@" ... mid3 ...");
    
    // Start at the top and recurse down
    CGRect frame = [self.window convertRect:[[self sourceObject] accessibilityFrame] fromView:[self sourceObject]];
    
    UIView *hitView = nil;
    CGPoint tapPointLeft = CGPointZero;
    CGPoint tapPointRight = CGPointZero;
    CGPoint tapPointMid = CGPointZero;
    
    // Mid point
    tapPointMid = CGPointCenteredInRect(frame);
    hitView = [self.window hitTest:tapPointMid withEvent:nil];
    
    
    // Top left
    //tapPointLeft = CGPointMake(frame.origin.x + 1.0f, frame.origin.y + 1.0f);
    tapPointLeft = CGPointMake(frame.origin.x + 0.2*frame.size.width, frame.origin.y + 0.1*frame.size.height);
    hitView = [self.window hitTest:tapPointLeft withEvent:nil];
    
    // Top right
    //tapPointRight = CGPointMake(frame.origin.x + 1.0f + frame.size.width - 1.0f, frame.origin.y + 1.0f);
    tapPointRight = CGPointMake(frame.origin.x + 0.2*frame.size.width, frame.origin.y + 0.3*frame.size.height);
    hitView = [self.window hitTest:tapPointRight withEvent:nil];

    [self dragFromPoint:tapPointLeft toPoint:tapPointRight];
}
*/ 

@end