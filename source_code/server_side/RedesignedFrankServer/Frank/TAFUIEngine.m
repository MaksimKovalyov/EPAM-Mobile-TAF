//
//  UIEngine.m
//  Frank
//
//  Created by Viktar on 10/10/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import "TAFUIEngine.h"
#import "TAFParser.h"
#import "UITree.h"
#import "SFLogger.h"

// Possible misunderstanding with 2 different methods 
//     +(UIElement *) findBy: classname atIndex: index{} method 
// with another one
//     +(UIElement *) findBy: index parent: element{} method
//
// The first method returns the element by index in scope of elements defined classname.
// The second method returns the element by index in hierarchy of the UITree with defined parent.
// The first method operates with index as collector index of all elements in the UITree.
// The second method operates with index as index in relation parent-childs and it is an index of child.
// Clarify later the misunderstanding index methods.

@implementation TAFUIEngine

+(UIElement *)findBy:(NSString *)_className andAttribute:(NSString *)_attributeName value:(NSString *)_attributeValue atIndex:(int)_index{
	UIElement *element = nil;
	
	return element;	
}

+(UIElement *)findBy:(NSString *)_className atIndex:(int)_index{
	UIElement *element = nil;
	_index--;

	UITree *tree = [[UITree alloc] initWith:_className];
	
	NSArray *uiLayer = [tree currentUILayer];
	SFLog(@" count of original object: %d", [[tree currentUILayer] count]);	
	SFLog(@" count of original object: %d", _index);	
	
    if ([uiLayer count] > 0) {
        element = [uiLayer objectAtIndex:_index];
    }
	//SFLog(@" element is found: %@", element);	
    
    [tree release];
    
	return element;	
}

+(UIElement *)getParentOf:(UIElement *)_element{
    
    SFLog(@" current root: %@", [_element sourceObject]);
    SFLog(@" parent of root: %@", [[_element sourceObject] superview]);
    
	return [[[UIElement alloc] initWithObject:[[_element sourceObject] superview]] autorelease];
}

+(UIElement *)findSingleNext: (UIElement *)_root{
	SFLog(@"Find next. Root object: %@", [_root sourceObject]);
	return [[[UIElement alloc] initWithObject:[[[_root sourceObject] subviews] objectAtIndex:0]] autorelease];
}

+(UIElement *)findBy:(NSString *)_className andAttribute:(NSString *)_attributeName value:(NSString *)_attributeValue{
	
    UIElement *element = nil;
	UITree *tree = [[UITree alloc] initWith:_className];
	
	NSArray *uiLayer = [tree currentUILayer];
	SFLog(@" count of original object: %d", [[tree currentUILayer] count]);
	int layerLength = [uiLayer count];
	for (int i=0; i < layerLength; i++) {
		UIElement *object = [uiLayer objectAtIndex:i];
		SFLog(@" It starts inspection of object %@", [object sourceObject]);
		
		SFLog(@"[Searching property] attribute name: %@ ", _attributeName);
		SFLog(@"[Searching property] attribute expected value: %@ ", _attributeValue);
        if ([_attributeName isEqualToString:@"atIndex"]) {
            element = [TAFUIEngine findBy:_className atIndex:[_attributeValue intValue]];
        }
            
		if ([[object getPropertyWith:_attributeName] isEqualToString:_attributeValue]) {
			element = object;
			SFLog(@" Object has been found. Info: %@", [element sourceObject]);
		}
	}
	
	[tree release];
	
	return element;
}

// It is a big question for providing mix strategy
// parent + index or other of this nature
// refactore for diffs strategies
+(UIElement *)findBy:(NSString *)_className andAttribute:(NSString *)_attributeName value:(NSString *)_attributeValue with:(UIElement *)_currentRoot{
	UIElement *element = nil;
	BOOL primaryMethod = YES;
	
	// Take in action different methods to search UI:
	// 1) by parent node relation;
	// 2) by index in UIKit structure(child node relation);
	// 3) by class name, attribute name and attribute value(entity initial ties);
	//  other... 
	
	// search implementation based on parent node relation;
	if ([_className isEqualToString:@"parent"]) {
		element = [TAFUIEngine getParentOf:_currentRoot];
        _currentRoot = element;
        SFLog(@" parent command: %@", [element sourceObject]);
		primaryMethod = NO;
	}
	
	// search implementation based on child node relation;
	if ([_attributeName isEqualToString:@"index"]) {
		element = [TAFUIEngine findBy:[_attributeValue intValue] parent:_currentRoot];
        _currentRoot = element;
		primaryMethod = NO;
	}
	
	// search implementation based on collection relation in the whole UITree;
	if ([_attributeName isEqualToString:@"atIndex"]) {
		element = [TAFUIEngine findBy:_className atIndex:[_attributeValue intValue]];
        _currentRoot = element;
		primaryMethod = NO;
	}
    
    if ([_className isEqualToString:@"next"]){
        element = [TAFUIEngine findSingleNext:_currentRoot];
        _currentRoot = element;
		primaryMethod = NO;
    }
	
	// Element next - ? Is it useful? For example, UITableView=aName:aValue->next->next->next->UILabel
	// next replaces single UI elements and we can unspecify description or at this point we have potential problem?
	// use next and don't worry strategy
	// Also, is it useful strategy next with depth level? For example, UITableView=aName:aValue->next=depth:3->UILabel.
	// search implementation based on throwing via single element in hierarchy;
	// Example: parent_element -> single_element_withClassname_in_hierarchy -> next_elements
	if ((_attributeName == nil) && (_attributeValue == nil)) {
        if (_currentRoot == nil) {
            element = [TAFUIEngine findBy:_className atIndex:0];
            primaryMethod = NO;
        }
	}

   // SFLog(@" _element_: %@", [element sourceObject]);
	// search implementation based on entity initial ties. it is primary method for search UI elements.
	if (primaryMethod == YES) {
		element = [TAFUIEngine findBy:_className andAttribute:_attributeName value:_attributeValue];	
	}
//    SFLog(@" _element_: %@", [element sourceObject]);

	SFLog(@"Element is found. Info: %@", [element sourceObject]);
	
	return element;
}

// redesign as for method with root
+(UIElement *)process:(NSArray *)_node{
	UIElement *element = nil;
	int nodeLength = [_node count];
	NSString *aName = nil;
	NSString *aValue = nil;
	NSString *cName = nil;
	
	if (nodeLength > 0) {
		if (nodeLength == 1) {
			cName = [_node objectAtIndex:0];
		}else {
			aName  = [_node objectAtIndex:0];
			aValue = [_node objectAtIndex:1];
			cName  = [_node objectAtIndex:2];
		}
		
		if ([aName isEqualToString:@"atIndex"]) {
            SFLog(@" aValue: %@", aValue);
            //SFLog
			element = [TAFUIEngine findBy:cName atIndex:[aValue intValue]];
		}else {
			element = [TAFUIEngine findBy:cName andAttribute:aName value:aValue];
		}
	}else {
		SFLog(@"[LOG][ERROR] XPath node is empty.");
	}
	
	return element;
}

+(NSString *)findAttrNameIn:(NSArray *)_node{
//	UIElement *element = nil;
	int nodeLength = [_node count];
	NSString *aName = nil;
	//NSString *aValue = nil;
	//NSString *cName = nil;
	
	if (nodeLength > 0) {
		if (nodeLength == 1) {
			//cName = [_node objectAtIndex:0];
			
			//element = [UIEngine findBy:cName andAttribute:aName value:aValue with:_root];
		}else {
			aName  = [_node objectAtIndex:0];
			//aValue = [_node objectAtIndex:1];
			//cName  = [_node objectAtIndex:2];
            
			//element = [UIEngine findBy:cName andAttribute:aName value:aValue with:_root];
		}
		
		//element = [UIEngine findBy:cName andAttribute:aName value:aValue with:_root];
	}else {
		SFLog(@"[LOG][ERROR] XPath node is empty.");
	}
	
	return aName;
}


+(UIElement *)process:(NSArray *)_node with:(UIElement *)_root{
	UIElement *element = nil;
	int nodeLength = [_node count];
	NSString *aName = nil;
	NSString *aValue = nil;
	NSString *cName = nil;
	
	if (nodeLength > 0) {
		if (nodeLength == 1) {
			cName = [_node objectAtIndex:0];
			
			//element = [UIEngine findBy:cName andAttribute:aName value:aValue with:_root];
		}else {
			aName  = [_node objectAtIndex:0];
			aValue = [_node objectAtIndex:1];
			cName  = [_node objectAtIndex:2];

			//element = [UIEngine findBy:cName andAttribute:aName value:aValue with:_root];
		}
		
		element = [TAFUIEngine findBy:cName andAttribute:aName value:aValue with:_root];
	}else {
		SFLog(@"[LOG][ERROR] XPath node is empty.");
	}
	
	return element;
}

+(UIElement *)findBy:(int)_index parent:(UIElement *)_element{
	SFLog(@"[UIEngine] FindBy index method starts...");
    int count = [[[_element sourceObject] subviews] count];
    SFLog(@" object at index: %@", [_element sourceObject]);
    SFLog(@" object subviews count: %d", count);
  
    if (_index < count)
        return [[[UIElement alloc] initWithObject:[[[_element sourceObject] subviews] objectAtIndex:_index]] autorelease];
    else
        return nil;
}

+(UIElement *)findBy:(UIXPath *)_xpath{
	SFLog(@"[UIEngine] FindBy xpath method starts...");
	
	int nodesLength = [_xpath.nodes count];
	UIElement *rootPath = nil;
	UIElement *tmpRootPath = nil;
	
	if (nodesLength > 0) {
		rootPath = [TAFUIEngine process:[_xpath.nodes objectAtIndex:0]];
	}
	
	for (int i = 1; i < nodesLength; i++) {
		tmpRootPath = rootPath;
		rootPath = [TAFUIEngine process:[_xpath.nodes objectAtIndex:i] with:tmpRootPath];
	}
	
	SFLog(@"[UIEngine] UI element %@ is found.", rootPath);
	
	return rootPath;
}

@end
