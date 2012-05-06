//
//  TakeScreenshot.m
//  Frank
//
//  Created by Viktar on 10/13/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import "TakeScreenshot.h"
#import "UIServiceAssistant.h"
#import "Constants.h"
#import "SFLogger.h"
#import "JSON.h"

@implementation TakeScreenshot

@synthesize commandResult, height, width_, image_, orientation_, format;

- (NSString *) orientationString
{
	switch ([[UIDevice currentDevice] orientation])
	{
		case UIDeviceOrientationUnknown: return @"Unknown";
		case UIDeviceOrientationPortrait: return @"Portrait";
		case UIDeviceOrientationPortraitUpsideDown: return @"Portrait Upside Down";
		case UIDeviceOrientationLandscapeLeft: return @"Landscape Left";
		case UIDeviceOrientationLandscapeRight: return @"Landscape Right";
		case UIDeviceOrientationFaceUp: return @"Face Up";
		case UIDeviceOrientationFaceDown: return @"Face Down";
		default: break;
	}
	return nil;
}

- (void) execute:(NSDictionary *)params{
	SFLog(@"[TakeScreenshotCommand] It starts to execute...");
		
	[self setImage_:[UIServiceAssistant takeScreenshot]];
	[self setCommandResult: @"Yes"];

	// calculate height and width
	CGRect screen = [[UIScreen mainScreen] bounds];
	CGFloat _width = CGRectGetWidth(screen);
	CGFloat _height = CGRectGetHeight(screen);
	NSString *h = [NSString stringWithFormat: @"%.2f", _height];
	NSString *w = [NSString stringWithFormat: @"%.2f", _width];
	
	[self setHeight: h];
	[self setWidth_: w];
	[self setFormat: @"png"];	
	[self setOrientation_: [self orientationString]];	
}

- (NSDictionary *) getImageProperties{
	NSMutableDictionary *properties = [NSMutableDictionary dictionary];
    NSMutableDictionary *image = [NSMutableDictionary dictionary];
    NSMutableDictionary *device = [NSMutableDictionary dictionary];

    NSDictionary *stub = nil;
    
    [self execute:stub];
    
	[properties setObject:[self image_] forKey:@"data"];
	[properties setObject:[self height] forKey:@"height"];
	[properties setObject:[self width_] forKey:@"width"];
	[device setObject:[self orientation_] forKey:@"orientation"];
	[properties setObject:[self format] forKey:@"format"];
	//[properties setObject:[command commandResult] forKey:@"isImageCaptured"];
	[device setObject:@"iPad" forKey:@"name"];
    
    [image setObject:properties forKey:@"image"];
    [image setObject:device forKey:@"device"];
    
	return image;
}

- (NSString *)handleCommandWithRequestBody:(NSString *)requestBody {
	
    //	SFLog(@"Test command starts: %@", requestBody);
	
	//NSDictionary *requestCommand = [requestBody JSONValue];
	//NSDictionary *operationDict = [requestCommand objectForKey:@"operation"];
	
    //	SFLog(@" req command: %@", requestCommand);
    //	SFLog(@" op Dict: %@ ", operationDict);
	
    NSMutableDictionary *ress = [NSMutableDictionary dictionary];
	
	[ress setObject:[self getImageProperties] forKey:@"result"];
	[ress setObject:@"true" forKey:@"status"];
    [ress setObject:@"take_screenshot" forKey:@"command"];
    
	SFLog(@"[ServerPart] Server sends response: %@", ress);

	return [ress JSONRepresentation];	
}


@end