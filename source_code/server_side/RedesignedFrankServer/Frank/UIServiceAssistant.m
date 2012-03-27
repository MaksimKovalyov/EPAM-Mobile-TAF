//
//  UIServiceAssistant.m
//  Frank
//
//  Created by Viktar on 7/7/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import "UIServiceAssistant.h"
#import "Constants.h"
#import "TAFParser.h"
#import "RunScript.h"
#import "SFLogger.h"
#import <AVFoundation/AVFoundation.h>
#import <Foundation/Foundation.h>
#import "TAFUIEngine.h"
#import "UIElement.h"
#import "UIXPath.h"

@interface NSData (MBBase64)
//+ (id)dataWithBase64EncodedString:(NSString *)string;     //  Padding '=' characters are optional. Whitespace is ignored.
- (NSString *)base64Encoding;
@end

@interface UIImage (ScreenImage)

- (UIImage *)imageRotatedByDegrees:(CGFloat)degrees;

CGFloat DegreesToRadians(CGFloat degrees);
CGFloat RadiansToDegrees(CGFloat radians);

@end

//CGImageRef UIGetScreenImage();

@implementation UIImage (ScreenImage)

static inline double radians (double degrees) {return degrees * M_PI/180;}

CGFloat DegreesToRadians(CGFloat degrees) {return degrees * M_PI / 180;};
CGFloat RadiansToDegrees(CGFloat radians) {return radians * 180/M_PI;};

- (UIImage *)imageRotatedByDegrees:(CGFloat)degrees 
{   
	// calculate the size of the rotated view's containing box for our drawing space
	UIView *rotatedViewBox = [[UIView alloc] initWithFrame:CGRectMake(0,0,self.size.width, self.size.height)];
	CGAffineTransform t = CGAffineTransformMakeRotation(DegreesToRadians(degrees));
	rotatedViewBox.transform = t;
	CGSize rotatedSize = rotatedViewBox.frame.size;
	[rotatedViewBox release];
	
	// Create the bitmap context
	UIGraphicsBeginImageContext(rotatedSize);
	CGContextRef bitmap = UIGraphicsGetCurrentContext();
	
	// Move the origin to the middle of the image so we will rotate and scale around the center.
	CGContextTranslateCTM(bitmap, rotatedSize.width/2, rotatedSize.height/2);
	
	// Rotate the image context
	CGContextRotateCTM(bitmap, DegreesToRadians(degrees));
	
	// Now, draw the rotated/scaled image into the context
	CGContextScaleCTM(bitmap, 1.0, -1.0);
	CGContextDrawImage(bitmap, CGRectMake(-self.size.width / 2, -self.size.height / 2, self.size.width, self.size.height), [self CGImage]);
	
	UIImage *newImage = UIGraphicsGetImageFromCurrentImageContext();
	UIGraphicsEndImageContext();
	return newImage;
	
}

+ (NSString *)imagePNGRepresentation{
	UIWindow *screenWindow = [[UIApplication sharedApplication] keyWindow];
	
	UIGraphicsBeginImageContext(screenWindow.frame.size);
	CGContextRef c = UIGraphicsGetCurrentContext();
	//CGContextRotateCTM (c, radians(90));
	[screenWindow.layer renderInContext:c];
	
	UIImage *screenshot = UIGraphicsGetImageFromCurrentImageContext();
	UIGraphicsEndImageContext();
	
	// rotate image	
	//UIImage* rscreenshot = [screenshot imageRotatedByDegrees:-90.0];
	
	// Rotate the image context
	//CGContextRotateCTM(bitmap, DegreesToRadians(degrees));
	
	//NSData *screenshotPNG = UIImagePNGRepresentation(rscreenshot);
	NSData *screenshotPNG = UIImagePNGRepresentation(screenshot);
	
	NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
	NSString *documentsDirectory = [paths objectAtIndex:0];
	
	// date
	NSDate *cdate = [NSDate date];
	NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
	[dateFormatter setDateFormat:@"dd-MM-YY"];
	NSString *date = [dateFormatter stringFromDate:cdate];
	[dateFormatter release];
	// time
	NSDate *ctime = [NSDate date];
	NSDateFormatter *timeFormatter = [[NSDateFormatter alloc] init];
	[timeFormatter setDateFormat:@"HH-mm-ss"];
	NSString *time = [timeFormatter stringFromDate:ctime];
	[timeFormatter release];
	NSString *finalDate = [NSString stringWithFormat:@"screenshot-DATE-%@-TIME-%@.png", date, time]; 
	
	SFLog(@"[UIServiceAssistant] The image name: %@", finalDate);
	SFLog(@"[UIServiceAssistant] The image store location: %@", documentsDirectory);
	
	NSError *error = nil;
	[screenshotPNG writeToFile:[documentsDirectory stringByAppendingPathComponent:finalDate] options:NSAtomicWrite error:&error];

	NSString *base64String = [screenshotPNG base64Encoding];
	//[base64String writeToFile:[documentsDirectory stringByAppendingPathComponent:@"screenshot_base64_111.txt"] atomically:YES encoding:NSUTF8StringEncoding error:&error];	
	
	//SFLog(@"base64String: %@", base64String);
	
	return base64String;
}

@end

// Logic should be redesigned.
@implementation UIServiceAssistant

@synthesize command, className, classNameChain, 
			attributeName, attributeValue, value, action, 
			elementIndex, commandResult, xpath;

-(id)init{
	self = [super init];
	
	return self;
}

- (void) dealloc
{
	[super dealloc];
}

-(void) setCommand: (NSString *)command_{
	command = command_;
}

-(void) setParams: (NSString *)className_ attrName: (NSString *)attributeName_ attrValue: (NSString *)attributeValue_ index: (NSString *)index_ value: (NSString *)value_{
	className = className_;
	attributeName = attributeName_;
	attributeValue = attributeValue_;
	elementIndex = index_;
	value = value_;
}

-(void) setParams: (NSString *)classNameChain_ value: (NSString *)value_{
	classNameChain = classNameChain_;
    value = value_;
}

-(void) setParams: (NSString *)className_ attrName: (NSString *)attributeName_ attrValue: (NSString *)attributeValue_ index: (NSString *)index_ action: (NSString *)action_{
	className = className_;
	attributeName = attributeName_;
	attributeValue = attributeValue_;
	elementIndex = index_;
	action = action_;
}

-(void) setParams: (NSString *)classNameChain_ attrName: (NSString *)attributeName_ index: (NSString *)index_{
	classNameChain = classNameChain_;
	attributeName = attributeName_;
	elementIndex = index_;
}

-(void) setParams: (NSString *)classNameChain_{
	classNameChain = classNameChain_;
}

-(void) setXPath: (NSString *)xpath_{
	xpath = xpath_;
}

+(NSString *) takeScreenshot{
	SFLog(@"[UIServiceAssistant] Screenshot is processing...");
	NSString *screenshot = [UIImage imagePNGRepresentation];
	SFLog(@"[UIServiceAssistant] Screenshot is captured.");
	
	return screenshot;
}

-(void) complyWithSEVCommand{
	UIElement *element_ = nil;
	if ((classNameChain == nil)||([classNameChain isEqualToString:@""])) {
        element_ = [TAFUIEngine findBy:className andAttribute:attributeName value:attributeValue];
    }else{
        UIXPath *xpath_ = [[UIXPath alloc] initWith:classNameChain];
        element_ = [TAFUIEngine findBy:xpath_];
        [xpath_ release];
    }
	[element_ flash];	
	[element_ setText:value];
	[element_ becomeActive];
	
    if (element_ == nil) {
        [self setCommandResult:@"The element is not found."];
    }
	//[element_ release];
}

- (void) log: (NSArray *)array notice: (NSString *)notice_{
	for (int i=0; i < [array count]; i++) {
		SFLog(@" %@: %@ at index: %d", notice_, [array objectAtIndex:i], i);
	}
}

-(void) complyWithGEVCommand{
	SFLog(@"[GetElementValueCommand] It starts to execute...");

	UIXPath *xpath_ = [[UIXPath alloc] initWith:classNameChain];

	UIElement *rootPath = [TAFUIEngine findBy:xpath_];
	[rootPath flash];
	
	NSString *result = [rootPath getPropertyWith:attributeName];	
	SFLog(@"[GetElementValueCommand] result: %@", result);
	if (result == nil) {
        commandResult = @"The element value is not found.";
    }else{
        commandResult = result;
    }
    
    [xpath_ release];
}

-(NSDictionary *) describeElement{
	SFLog(@"[DescribeCommand] It starts to execute...");
    
	UIXPath *xpath_ = [[UIXPath alloc] initWith:classNameChain];
	UIElement *rootPath = [TAFUIEngine findBy:xpath_];
	[rootPath flash];
    
    [xpath_ release];
	
    return [rootPath JSONProxy];
}


/*
-(void) complyWithDescribeCommand{
	SFLog(@"[DescribeCommand] It starts to execute...");
	
	UIElement *element = [UIEngine findBy:className andAttribute:attributeName value:attributeValue];
	[element flash];
	SFLog(@"[DoActionCommand] The element %@ is flashed.", element);
	
	[element actWith:action];
	SFLog(@"[DoActionCommand] The action %@ is performed.", element);	
}*/

-(void) complyWithDACommand{
	SFLog(@"[DoActionCommand] It starts to execute...");
	UIElement *element = nil;
    SFLog(@"chain: %@", classNameChain);
    
    if ((classNameChain != nil)&&(![classNameChain isEqualToString:@"none"])&&(![classNameChain isEqualToString:@""])) {
        SFLog(@"chain: %@", classNameChain);
        UIXPath *xpath_ = [[UIXPath alloc] initWith:classNameChain];
        element = [TAFUIEngine findBy:xpath_];
        
        [xpath_ release];
        //classNameChain = nil;
        // hardcode, stub implementation
//        if ([classNameChain isEqualToString:@"UIWebView=atIndex:1"]){
//            NSString *systemFontSize = [[element sourceObject] stringByEvaluatingJavaScriptFromString:@"document.styleSheets[0].rules[1].style.fontSize"];
            //SFLog(@" element font size: %@", [[element sourceObject] font]);
//            SFLog(@" font size:  %@", systemFontSize);
//            [self setCommandResult:systemFontSize];
//        }
        
    }else{
        element = [TAFUIEngine findBy:className andAttribute:attributeName value:attributeValue];
    }
    
 //   SFLog(@" system font size: %d", [UIFont systemFontSize]);
    
//	[element flash];
//	SFLog(@"[DoActionCommand] The element %@ is flashed.", element);
	
    if (element == nil) {
        [self setCommandResult:@"The element is not found."];
    }else{
        [element setXpath:[self xpath]];
        [element actWith:action];
    }
    
	SFLog(@"[DoActionCommand] The action %@ is performed.", element);	
}

-(void) comply{
	SFLog(@"[UIServiceAssistant] The command %@ is processing...", command);
	if ([command isEqualToString:SET_ELEMENT_VALUE]) {
		[self complyWithSEVCommand];
	}
	if ([command isEqualToString:GET_ELEMENT_VALUE]) {
		[self complyWithGEVCommand];
	}
	if ([command isEqualToString:DO_ACTION]) {
		[self complyWithDACommand];
	}
}

-(void) complyWithCommand: (NSString *)command_{
	command = command_;
	[self comply];
}

- (id) applyToObject:(id)target{

	SFLog(@" target: %@ ", target);
//	SFLog(@" sel: %@ ", selector_);
	SFLog(@" arg: %@ ", argument_);
	
	NSMethodSignature *signature = [target methodSignatureForSelector:selector_];
	NSUInteger requiredNumberOfArguments = signature.numberOfArguments - 2; // Indices 0 and 1 indicate the hidden arguments self and _cmd, respectively
	
	SFLog(@" required args: %i ", requiredNumberOfArguments);
	
//	if( requiredNumberOfArguments != [arguments_ count] )
//		[NSException raise:@"wrong number of arguments" 
//			format:@"%@ takes %i arguments, but %i were supplied", NSStringFromSelector(selector_), requiredNumberOfArguments, [arguments_ count] ];

	NSInvocation *invocation = [NSInvocation invocationWithMethodSignature:signature];
	[invocation setTarget:target];
	
	[invocation setSelector:selector_];

	NSInteger index = 2; // Indices 0 and 1 indicate the hidden arguments self and _cmd, respectively
//	for( id arg in arguments_ ) {
//		[invocation setArgument:&arg atIndex:index++];
//		SFLog(@" arg: %@ ", arg);
//	}

	SFLog(@" before set arg ");
	[invocation setArgument:&argument_ atIndex:index];
	
	SFLog(@" before invoke target ");
	[invocation invoke];//WithTarget:target];
	
	SFLog(@" before ret val ");
	const char *returnType = signature.methodReturnType;
	//SFLog(@" ret type: %@", returnType);
	
	id returnValue = nil;
	if( !strcmp(returnType, @encode(void)) )
		returnValue =  nil;
	if( !strcmp(returnType, @encode(id)) ) // retval is an objective c object
		[invocation getReturnValue:&returnValue];
	
	//SFLog(@" ret val: %@", returnValue);
	
	return returnValue;
}

- (id) applyToTarget:(id)target selector:(NSString *)sel attributes:(NSArray *)atr {
	selector_ = NSSelectorFromString(sel);
	arguments_ = atr;
	
	return [self applyToObject:target];
}

- (id) applyToTarget:(id)target selector:(NSString *)sel attribute:(NSString *)atr {
	selector_ = NSSelectorFromString(sel);
	argument_ = atr;
	
	return [self applyToObject:target];
}

@end
