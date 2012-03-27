//
//  UIEngine.h
//  Frank
//
//  Created by Viktar on 10/10/11.
//  Copyright 2011 EPAM Systems. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UIElement.h"
#import "UIXPath.h"

@interface TAFUIEngine : NSObject {

}

+(UIElement *)findBy:(NSString *)_className andAttribute:(NSString *)_attributeName value:(NSString *)_attributeValue;

+(UIElement *)findBy:(NSString *)_className andAttribute:(NSString *)_attributeName value:(NSString *)_attributeValue with:(UIElement *)_currentRoot;

+(UIElement *)findBy:(NSString *)_className andAttribute:(NSString *)_attributeName value:(NSString *)_attributeValue atIndex:(int)_index;

+(UIElement *)findBy:(int)_index parent:(UIElement *)_element;

+(UIElement *)findBy:(UIXPath *)_xpath;


+(NSString *)findAttrNameIn:(NSArray *)_node;

+(UIElement *)getParentOf:(UIElement *)_element;


+(UIElement *)process:(NSArray *)_node;

+(UIElement *)process:(NSArray *)_node with:(UIElement *)_root;

@end
