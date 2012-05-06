//
//  DumpCommand.m
//  Frank
//
//  Created by phodgson on 5/30/10.
//  Copyright 2010 ThoughtWorks. See NOTICE file for details.
//

#import "DumpCommand.h"

/*
 * The source code below should be redesigned.
 Conceptual redesign: add JSONProxy for object, add categories(or abstraction) for all elements and use instead CustomType [object JSONProxy] method.
    
 Refactore: add all methods to filter object
 
 */

NSString *findMatchByRegex(NSString* str, NSString *regex){
	NSString *findRes = nil;
	NSError *error;
	NSRegularExpression *regExpession = [NSRegularExpression regularExpressionWithPattern:regex options:0 error:&error];
	if( regExpession == nil ) SFLog( @" step 2 er: Error making regex: %@", error );
	else {
		NSTextCheckingResult *match = [regExpession firstMatchInString:str options:0 range:NSMakeRange(0, [str length])];
		// warn fix
		NSRange range = [match rangeAtIndex:1];		
		findRes = [str substringWithRange:range];
	}

	//	SFLog(@" res match: %@", findRes);
	
	return findRes;
}

NSString *filterJSON( NSString *str){
	NSString *regexReplaceSemicolon = @"\\((.+?)\\);";
	//NSString *regexReplaceFigureColons = @"\\{\\{(.+?)\\}\\}";
	NSString *findRes = nil;
	NSError *error;
	NSRegularExpression *regex = [NSRegularExpression regularExpressionWithPattern:regexReplaceSemicolon
																		   options:0
																			 error:&error];
	//NSRegularExpression *regexFC = [NSRegularExpression regularExpressionWithPattern:regexReplaceSemicolon
	//																	   options:0
	//																		 error:&error];
	
	if( regex == nil ) 
		SFLog( @" Error making regex: %@", error );
//	else {
//		SFLog(@" regex: %@", regex);
//	}
	else {
		NSTextCheckingResult *result = [regex firstMatchInString:str options:0 range:NSMakeRange(0, [str length])];
		NSRange range = [result rangeAtIndex:1];
		findRes = [str substringWithRange:range];
	}
	findRes = [findRes stringByReplacingOccurrencesOfString:@";" withString:@","];
	findRes = [NSString stringWithFormat:@"%@%@%@", @"[", findRes, @"];"];
	
	NSString *modifiedString = [regex stringByReplacingMatchesInString:str
															   options:0
																 range:NSMakeRange(0, [str length])
														  withTemplate:findRes];
		
	return modifiedString;
}

// redesign: add all regex into Parser, Filter or other abstraction
NSMutableDictionary *parseScreen(NSString *responder)
{
	NSMutableDictionary *res = [NSMutableDictionary dictionary];
	
	NSString *test = responder;
	//NSString *regexClass = @"<(.+?):";
	//NSString *regexBaseClass = @"baseClass = (.+?);";
	//NSString *regexFrame = @"frame = (.+?);";
	//NSString *regexClipsToBounds = @"clipsToBounds = (.+?);";
	//NSString *regexOpaque = @"opaque = (.+?);";
	//NSString *regexUserInteraction = @"userInteractionEnabled = (.+?);";
	//NSString *regexLayer = @"layer = (.+?);";
	NSString *regexPairObjAndKey = @"(.+?)(; |>)";
	//NSString *regexReplaceSemiColon = @";";
	NSString *findRes = nil;
	NSString *objKey = nil;
	NSString *obj = nil;
	NSString *obj1 = nil;
	NSString *obj2 = nil;
	NSString *regexClassName = @"<(.+?):";
	//NSString *regexClassValue = @": (.+?)$";
	NSString *regexObjName = @"(.+?) =";
	//NSString *regexObjValue = @"= (.+?)$";
	NSString *regexSizeName = @"(.+?) =";
	NSString *regexSizeValueP1 = @"= (.{6}?)";
	NSString *regexSizeValueP2 = @"x (.{6}?)";
	NSString *regexLayerName = @"= <(.+?):";
	//NSString *regexLayerValue = @": (.+?)$";
	//NSString *regexBoundName = @"(.+?):";
	NSString *regexClassValueP1 = @"\\{\\{(.+?)\\}";
	NSString *regexClassValueP2 = @", \\{(.+?)\\}\\}$";
	
	
	NSError *error;
	
	// to do: filter that clear all data for JSON converting
	//test = filterJSON(test);
	
//	SFLog(@" step 1.");
	NSRegularExpression *testRegex = [NSRegularExpression regularExpressionWithPattern:regexPairObjAndKey options:0 error:&error];
//	SFLog(@" step 2.");
	if( testRegex == nil ) SFLog( @" step 2 er: Error making regex: %@", error );
	
	NSArray *matches = [testRegex matchesInString:test options:0 range:NSMakeRange(0, [test length])];
	for (NSTextCheckingResult *match in matches) {
		
		//NSTextCheckingResult *result = [testRegex firstMatchInString:test options:0 range:NSMakeRange(0, [test length])];
		NSRange range = [match rangeAtIndex:1];
		findRes = [test substringWithRange:range];
//		SFLog(@" step 41: match: %@", findRes);
		// add class element
		if ([[findRes substringToIndex:1] isEqualToString:@"<"]) {
			objKey = @"className";//findMatchByRegex(findRes, regexClassValue);
			obj = findMatchByRegex(findRes, regexClassName);
//			SFLog(@" case 1: %@", objKey);
//			SFLog(@" case 1: %@", obj);
		}
		
		// add layer element
		range = [findRes rangeOfString:@"mode =" options:NSCaseInsensitiveSearch];
		if( range.location != NSNotFound ) {
			obj = findMatchByRegex(findRes, regexLayerName);
			objKey = @"mode";//findMatchByRegex(findRes, regexLayerName);
//			SFLog(@" case 2: %@", objKey);
//			SFLog(@" case 2: %@", obj);
		}
		
		// add 'key = value' pair element
		range = [findRes rangeOfString:@"size =" options:NSCaseInsensitiveSearch];
		if( range.location != NSNotFound ) {
			obj2 = findMatchByRegex(findRes, regexSizeValueP2);
			obj1 = findMatchByRegex(findRes, regexSizeValueP1);
			objKey = findMatchByRegex(findRes, regexSizeName);
			
			obj = [NSString stringWithFormat:@"%@%@%@%@%@", @"[", obj1, @",", obj2, @"]"];
//			SFLog(@" case 3: %@", objKey);
//			SFLog(@" case 3: %@", obj);
		}
		
		range = [findRes rangeOfString:@"bounds =" options:NSCaseInsensitiveSearch];
		if( range.location != NSNotFound ) {
			obj2 = findMatchByRegex(findRes, regexClassValueP2);
			obj1 = findMatchByRegex(findRes, regexClassValueP1);
			objKey = findMatchByRegex(findRes, regexObjName);
			obj = [NSString stringWithFormat:@"%@%@%@%@%@%@", @"[[", obj1, @"],", @"[", obj2, @"]]"];
	//		SFLog(@" case 4: %@", objKey);
	//		SFLog(@" case 4: %@", obj);
		}
		
		if ((objKey != nil) && (obj != nil)){
			[res setObject:obj forKey:objKey];
		}
	}
	
//	SFLog(@" res finall: %@", res);
	
	return res;
}

NSMutableDictionary *parseResponder(NSString *responder)
{
	NSMutableDictionary *res = [NSMutableDictionary dictionary];
	
	NSString *test = responder;
	//NSString *regexClass = @"<(.+?):";
	//NSString *regexBaseClass = @"baseClass = (.+?);";
	//NSString *regexFrame = @"frame = (.+?);";
	//NSString *regexClipsToBounds = @"clipsToBounds = (.+?);";
	//NSString *regexOpaque = @"opaque = (.+?);";
	//NSString *regexUserInteraction = @"userInteractionEnabled = (.+?);";
	//NSString *regexLayer = @"layer = (.+?);";
	NSString *regexPairObjAndKey = @"(.+?)(; |>)";
	//NSString *regexReplaceSemiColon = @";";
	NSString *findRes = nil;
	NSString *objKey = nil;
	NSString *obj = nil;
	NSString *regexClassName = @"<(.+?):";
	//NSString *regexClassValue = @": (.+?)$";
	NSString *regexObjName = @"(.+?) =";
	NSString *regexObjValue = @"= (.+?)$";
	NSString *regexLayerName = @"= <(.+?):";
	NSString *regexLayerValue = @": (.+?)$";
	
	NSError *error;
	
	test = filterJSON(test);
	
//	SFLog(@" step 1.");
	NSRegularExpression *testRegex = [NSRegularExpression regularExpressionWithPattern:regexPairObjAndKey options:0 error:&error];
//	SFLog(@" step 2.");
	if( testRegex == nil ) SFLog( @" step 2 er: Error making regex: %@", error );
	
	NSArray *matches = [testRegex matchesInString:test options:0 range:NSMakeRange(0, [test length])];
	for (NSTextCheckingResult *match in matches) {
	
	//NSTextCheckingResult *result = [testRegex firstMatchInString:test options:0 range:NSMakeRange(0, [test length])];
	NSRange range = [match rangeAtIndex:1];
	findRes = [test substringWithRange:range];
//	SFLog(@" step 41: match: %@", findRes);
		// add class element
		if ([[findRes substringToIndex:1] isEqualToString:@"<"]) {
			objKey = @"className";//findMatchByRegex(findRes, regexClassValue);
			obj = findMatchByRegex(findRes, regexClassName);
		}
		
		// add layer element
		range = [findRes rangeOfString:@"layer =" options:NSCaseInsensitiveSearch];
		if( range.location != NSNotFound ) {
			obj = findMatchByRegex(findRes, regexLayerValue);
			objKey = findMatchByRegex(findRes, regexLayerName);
		}
		
		// add 'key = value' pair element
		range = [findRes rangeOfString:@"<" options:NSCaseInsensitiveSearch];
		if( range.location == NSNotFound ) {
			obj = findMatchByRegex(findRes, regexObjValue);
			objKey = findMatchByRegex(findRes, regexObjName);
		}
		
		if ((objKey != nil) && (obj != nil)){
			[res setObject:obj forKey:objKey];
		}
	}
	
	return res;
}

NSMutableDictionary *parseLayer(NSString *rect)
{
	NSMutableDictionary *res = [NSMutableDictionary dictionary];
	
	NSString *test = rect;
	//NSString *regexPairObjAndKey = @"(.+?)(; |>)";
	//NSString *regexReplaceSemiColon = @";";
	//NSString *findRes = nil;
	NSString *objKey = nil;
	NSString *obj = nil;
	NSString *regexLayerName = @"<(.+?):";
	NSString *regexLayerValue = @": (.+?)>$";
	
	//NSError *error;
		
	objKey = findMatchByRegex(test, regexLayerName);
	obj = findMatchByRegex(test, regexLayerValue);
	
//	SFLog(@" test obj: %@", objKey);
//	SFLog(@" test obj: %@", obj);
	
	if ((objKey != nil) && (obj != nil)){
		[res setObject:obj forKey:objKey];
	}
//	SFLog(@" res final: %@", res);
	
	return res;
}

NSMutableDictionary *parseNSRect(NSString *rect)
{
	NSMutableDictionary *res = [NSMutableDictionary dictionary];
	
	NSString *test = rect;
	//NSString *regexPairObjAndKey = @"(.+?)(; |>)";
	//NSString *regexReplaceSemiColon = @";";
	//NSString *findRes = nil;
	NSString *objKey = nil;
	NSString *obj1 = nil;
	NSString *obj2 = nil;
	NSString *regexClassName = @"(.+?):";
	NSString *regexClassValueP1 = @"\\{\\{(.+?)\\}";
	NSString *regexClassValueP2 = @", \\{(.+?)\\}\\}$";
	
	objKey = findMatchByRegex(test, regexClassName);
	obj1 = findMatchByRegex(test, regexClassValueP1);
	obj2 = findMatchByRegex(test, regexClassValueP2);
//	SFLog(@" test obj: %@", objKey);
//	SFLog(@" test obj: %@", obj1);

//	SFLog(@" test obj: %@", obj2);
	NSString *obj = [NSString stringWithFormat:@"%@%@%@%@%@%@", @"[[", obj1, @"],", @"[", obj2, @"]]"];
    //NSString *coords = [NSString stringWithFormat:@"%@%@%@", @"[", obj1, @"]"];
    //NSString *size = [NSString stringWithFormat:@"%@%@%@", @"[", obj2, @"]"];
	if ((objKey != nil) && (obj != nil)){
		[res setObject:obj forKey:objKey];
        //[res setObject:coords forKey:@"coordinates"];
        //[res setObject:size forKey:@"size"];
	}
	
	return res;
}

NSMutableDictionary *parseUIWindow(NSString *str)
{
	NSMutableDictionary *res = [NSMutableDictionary dictionary];
	NSString *findRes = nil;
	NSString *test = str;
	NSString *regexStr = @"baseClass = (.+?);";
	NSError *error;
	
//	SFLog(@" step 1.");
	NSRegularExpression *testRegex = [NSRegularExpression regularExpressionWithPattern:regexStr options:0 error:&error];
//	SFLog(@" step 2.");
	if( testRegex == nil ) SFLog( @" step 2 er: Error making regex: %@", error );
	else {
		NSTextCheckingResult *result = [testRegex firstMatchInString:test options:0 range:NSMakeRange(0, [test length])];
		NSRange range = [result rangeAtIndex:1];
		findRes = [test substringWithRange:range];
	}

//	SFLog(@" step 4: match: %@", findRes);
	
	NSString *objKey = @"baseClass";
	
	if ( findRes != nil ){
		[res setObject:findRes forKey:objKey];
	}
	
	return res;
}

NSMutableDictionary *parseUIFont(NSString *font)
{
	NSMutableDictionary *res = [NSMutableDictionary dictionary];
    NSMutableDictionary *font_attrs = [NSMutableDictionary dictionary];
	
	NSString *test = font;
	//NSString *regexPairObjAndKey = @"(.+?)(; |>)";
	//NSString *regexReplaceSemiColon = @";";
	//NSString *findRes = nil;
	NSString *objKey = nil;
	NSString *font_family = nil;
	NSString *font_weight = nil;
	NSString *font_style = nil;
	NSString *font_size = nil;
    
	NSString *regexClassName = @"<(.+?):";
	NSString *regexClassValueP1 = @"\"(.+?)\"";
	NSString *regexClassValueP2 = @"font-weight:(.+?);";
	NSString *regexClassValueP3 = @"font-style:(.+?);";
	NSString *regexClassValueP4 = @"font-size:(.+?)$";
    
	objKey = findMatchByRegex(test, regexClassName);
	font_family = findMatchByRegex(test, regexClassValueP1);
	font_weight = findMatchByRegex(test, regexClassValueP2);
    font_style = findMatchByRegex(test, regexClassValueP3);
	font_size = findMatchByRegex(test, regexClassValueP4);

    //	SFLog(@" test obj: %@", objKey);
    //	SFLog(@" test obj: %@", obj1);
    
    //	SFLog(@" test obj: %@", obj2);
	//NSString *obj = [NSString stringWithFormat:@"%@%@%@%@%@%@", @"[[", obj1, @"],", @"[", obj2, @"]]"];
    if (font_family != nil){
		[font_attrs setObject:font_family forKey:@"font-family"];
	}
    if (font_weight != nil){
		[font_attrs setObject:font_weight forKey:@"font-weight"];
	}
    if (font_style != nil){
		[font_attrs setObject:font_style forKey:@"font-style"];
	}
    if (font_size != nil){
		[font_attrs setObject:font_size forKey:@"font-size"];
	}
	if (objKey != nil){
		[res setObject:font_attrs forKey:objKey];
	}
	
	return res;
}


NSDictionary *convertToJSON ( id obj, id objKey )
{
	//NSMutableArray *subviewDescriptions = [NSMutableArray array];
	//NSMutableDictionary *description = [NSMutableDictionary dictionary];
	NSMutableDictionary *res = [NSMutableDictionary dictionary];
	
	NSString* desc = [obj description];

    if ([objKey isEqualToString:@"font"]) {
        //SFLog(@" font-fname: %@", [obj familyName]);
        //SFLog(@" classname: %@", [obj class]);
        //SFLog(@" font-size: %d", [obj pointSize]);
        //SFLog(@" style: %@", [obj proxyForJson]);
        //UIFont *f = nil; [f proxyForJson];
        //SFLog(@" width: %@", [obj familyName]);
        
        res = parseUIFont(desc);
    } else
	if ([objKey isEqualToString:@"_responderForEditing"]){ 
		[res setObject:@"COMPLEX_TYPE" forKey:objKey];
	} else
	if ([objKey isEqualToString:@"accessibilityFrame"]){ 
		res = parseNSRect(desc);
	} else
	if ([objKey isEqualToString:@"layer"]){ 
		res = parseLayer(desc);
	} else
	if ([objKey isEqualToString:@"screen"]){ 
		res = parseScreen(desc);
	} else {
		[res setObject:@"COMPLEX_TYPE" forKey:objKey];
	}
			
	return res;
}

BOOL objectIsPrimitive( id obj )
{
	return ( 
		   [obj isKindOfClass:[NSString class]]
		   || [obj isKindOfClass:[NSNumber class]]
		   || [obj isKindOfClass:[NSNull class]]
		   );		
}

NSMutableDictionary *filterNonprimitiveValuesFrom( NSDictionary *dictionary ) {
	NSMutableDictionary *filteredDictionary = [NSMutableDictionary dictionaryWithCapacity:[dictionary count]];
	
	for(id key in [dictionary allKeys]) {
		id value = [dictionary valueForKey:key];
		if( objectIsPrimitive( value ) )
			[filteredDictionary setObject:value forKey:key];
		else{			
			
			//[filteredDictionary setObject:convertToJSON(value) forKey:key];		
			// use it.
			[filteredDictionary setObject:convertToJSON(value, key) forKey:key];
			//[filteredDictionary setObject:@"valueNonPrimitive" forKey:"NonPrimitiveType"];	
		}
	}
	
	return filteredDictionary;
}

NSDictionary *customAttributesFor( UIView *view ) {
	NSMutableDictionary *customAttributes = [NSMutableDictionary dictionary];
	
	if( [view respondsToSelector:@selector(accessibilityLabel)] )
	{
		id value = [view accessibilityLabel];
		if( !value )
			value = [NSNull null];
		[customAttributes setObject:value forKey:@"accessibilityLabel"];
	}
	
	[customAttributes setObject:NSStringFromClass([view class]) forKey:@"class"];		
	
	return customAttributes;
}

NSDictionary *describeView( UIView *view ) {
	NSMutableArray *subviewDescriptions = [NSMutableArray array];
	for (UIView *subview in view.subviews) {
		[subviewDescriptions addObject: describeView(subview) ];
	}
	
	NSMutableDictionary *description = [NSMutableDictionary dictionaryWithDictionary:[UIElement describe:view]];
	description = filterNonprimitiveValuesFrom(description);

	[description setObject:subviewDescriptions forKey:@"subviews"];
	[description addEntriesFromDictionary:customAttributesFor(view)];
	
	return description;
}

@implementation DumpCommand

- (NSMutableDictionary *)filter:(NSDictionary *)dictionary {
	NSMutableDictionary *filteredDictionary = [NSMutableDictionary dictionaryWithCapacity:[dictionary count]];
	
	for(id key in [dictionary allKeys]) {
		id value = [dictionary valueForKey:key];
		if( objectIsPrimitive( value ) )
			[filteredDictionary setObject:value forKey:key];
		else{			
			
			//[filteredDictionary setObject:convertToJSON(value) forKey:key];		
			// use it.
			[filteredDictionary setObject:convertToJSON(value, key) forKey:key];
			//[filteredDictionary setObject:@"valueNonPrimitive" forKey:"NonPrimitiveType"];	
		}
	}
	
	return filteredDictionary;
}


- (NSString *)handleCommandWithRequestBody:(NSString *)requestBody {
	NSDictionary *dom = describeView( [[UIApplication sharedApplication] keyWindow] );
	
	SFLog(@" dom: %@", dom);
	//SFLog(@" dom JSON representation: %@", [dom JSONRepresentation]);	
	
	return [dom JSONRepresentation];
}

@end
