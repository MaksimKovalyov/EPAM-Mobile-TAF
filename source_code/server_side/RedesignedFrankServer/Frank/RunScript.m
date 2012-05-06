//  RunScript.m
//  Frank
//
//  Created by Viktar on 6/8/11.
//  Copyright 2011 EPAM Systems. All rights reserved.

#import "RunScript.h"
#import "TAFFilter.h"
#import "CommandExecutor.h"
#import "Constants.h"
#import "UIServiceAssistant.h"
#import "SFLogger.h"
#import "UIElement.h"
#import "UIXPath.h"
#import "TAFUIEngine.h"

@implementation RunScript

- (NSDictionary *) getImageProperties:(TakeScreenshot *)command {
	NSMutableDictionary *properties = [NSMutableDictionary dictionary];
    NSMutableDictionary *image = [NSMutableDictionary dictionary];
    
	[properties setObject:[command image_] forKey:@"data"];
	[properties setObject:[command height] forKey:@"height"];
	[properties setObject:[command width_] forKey:@"width"];
	[properties setObject:[command orientation_] forKey:@"orientation"];
	[properties setObject:[command format] forKey:@"format"];
	//[properties setObject:[command commandResult] forKey:@"isImageCaptured"];
	[properties setObject:@"iPad" forKey:@"device"];
    
    [image setObject:properties forKey:@"image"];
    
	return image;
}

- (NSDictionary *)perform: (NSString *)operation with: (NSDictionary *)params {
    id<Command> cmd = [[CommandExecutor sharedInstance] createCommand:operation];
	[cmd execute:params];
	
    NSDictionary *resultValue = [NSDictionary dictionaryWithObject:@"none" forKey:@"value"];
	
	// generate related to operation response, replace to self-generated response inside commands
	if ([operation isEqualToString:GET_ELEMENT_VALUE]) {
		resultValue = [NSDictionary dictionaryWithObject:[(GetElementValue*)cmd commandResult] forKey:@"value"];
	}
    if ([operation isEqualToString:TAKE_SCREENSHOT]) {
	    resultValue = [NSDictionary dictionaryWithDictionary:[self getImageProperties: (TakeScreenshot*)cmd]];
    }
    if ([operation isEqualToString:DESCRIBE]) {
	    resultValue = [NSDictionary dictionaryWithObject:[(DescribeCommand*)cmd proxy] forKey:@"proxy"];;
    }
    
    
	SFLog(@"[ServerPart] Request has processed.");	

    return resultValue;
}

+(BOOL)isValidAttribute:(NSString *)attrName{
    BOOL result = NO;
    
    NSSet *attributes_scheme = [[NSSet alloc] initWithObjects:@"accessibilityLabel", 
                                @"accessibilityValue",
                                @"index", 
                                @"atIndex", 
                                @"text", 
                                @"font", 
                                @"className", 
                                @"placeholder",
                                @"HTMLSource",
                                @"systemFont",
                                @"isSelected",
                                @"isTappable",
                                @"isEnabled",
                                @"isHighlighted",
                                @"accessibilityElementsHidden",
                                @"textAlignment",
                                @"numberOfLines",
                                @"accessibilityFrame",
                                @"contentPosition",
                                @"numberOfChilds",
                                @"numberOfSections",
                                @"numberOfRawsInSection",
                                nil];
    if ((attrName == nil)||([attrName isEqualToString:@""])) {
        result = YES;
    }else
        if ([attributes_scheme containsObject:attrName]) {
            result = YES;
        }
    
    [attributes_scheme release];
    
    return result;
}

+(BOOL)isValidOperation:(NSString *)opName{
    BOOL result = NO;
    
    NSSet *operations_scheme = [[NSSet alloc] initWithObjects:GET_ELEMENT, 
                                GET_ELEMENT_VALUE, 
                                SET_ELEMENT_VALUE, 
                                TAKE_SCREENSHOT,
                                DO_ACTION,
                                DESCRIBE,
                                PING_SERVER,
                                nil];
    if ([operations_scheme containsObject:opName]) {
        result = YES;
    }
    
    [operations_scheme release];
    
    return result;
}

+(BOOL)isValidClassNameChain:(NSString *)chain{
    BOOL result = YES;
    NSString *attrName = nil;
    UIXPath *xpath = [[UIXPath alloc] initWith:chain];
    int nodesLength = [xpath.nodes count];
    for (int i=0; i<nodesLength; ++i) {
        attrName = [TAFUIEngine findAttrNameIn:[[xpath nodes] objectAtIndex:i]];
    
        if (![RunScript isValidAttribute:attrName]) {
            [xpath release];
            
            return NO;
        }
    }
    
    [xpath release];
    
    return result;
}

- (BOOL)validate:(NSString *)incomingRequest{
    BOOL result = YES;
    NSDictionary *requestCommand = [incomingRequest JSONValue];
	NSString *operation = [requestCommand objectForKey:@"Operation"];
	NSDictionary *params = [requestCommand objectForKey:@"Params"];
    NSString *attributeName  = [params objectForKey:@"AttributeName"];
    NSString *chain  = [params objectForKey:@"ClassNameChain"];

    
    if (![RunScript isValidOperation:operation]){
        result = NO;
        SFLog(@"[ServerPart][Validation] Operation fail. ");
    }
    if (![RunScript isValidAttribute:attributeName]){
        result = NO;
        SFLog(@"[ServerPart][Validation] Attributes fail. ");
    }
    if (![RunScript isValidClassNameChain:chain]){
        result = NO;
        SFLog(@"[ServerPart][Validation] Classname Chain fail. ");
    }

    
    SFLog(@"[ServerPart] Validation result: %@", (result ? @"true" : @"false"));
    
    return result;
}

- (NSString *)handleCommandWithRequestBody:(NSString *)requestBody {
		
	NSString *filteredRequest = [[TAFFilter sharedInstance] replaceHEXCodesFrom:requestBody];//filterRequest(requestBody); 
	NSDictionary *resultValue = nil;
	NSDictionary *requestCommand = [filteredRequest JSONValue];
	NSString *operation = [requestCommand objectForKey:@"Operation"];
	NSDictionary *params = [requestCommand objectForKey:@"Params"];
	
	SFLog(@"[ServerPart] Command name: %@", [self class]);
	SFLog(@"[ServerPart] Command JSON rest: %@", requestCommand);
	SFLog(@"[ServerPart] Command operation: %@", operation);
	
    SFLog(@"[ServerPart] Validation of operation and params starts...");
	
	if ([self validate:filteredRequest]) {
        resultValue = [self perform:operation with:params];
    }else{
        NSString *error = @"The command is not valid. Please, see details info: ";
        resultValue = [NSDictionary dictionaryWithObject:error forKey:@"value"];
    }
    
	
	succes = YES;
	NSString *boolString = (succes ? @"true" : @"false");
	
	NSMutableDictionary *ress = [NSMutableDictionary dictionary];
	
	[ress setObject:resultValue forKey:@"result"];
	[ress setObject:boolString forKey:@"status"];
    [ress setObject:operation forKey:@"command"];

	SFLog(@"[ServerPart] Server sends response: %@", ress);
	
	return [ress JSONRepresentation];	
}

@end