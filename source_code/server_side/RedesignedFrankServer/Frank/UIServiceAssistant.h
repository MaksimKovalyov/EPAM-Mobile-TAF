//  UIServiceAssistant.h
//  Frank
//
//  Created by Viktar on 7/7/11.
//  Copyright 2011 EPAM Systems. All rights reserved.

@interface UIServiceAssistant : NSObject {
	NSString *command;
	
	SEL       selector_;
	NSArray  *arguments_;
	NSString *argument_;
	
	NSString *className;
	NSString *classNameChain;
	NSString *attributeName;
	NSString *attributeValue;
	NSString *value;
	NSString *action;
	NSString *elementIndex;
	NSString *commandResult;
}

@property (nonatomic, retain) NSString* command;
@property (nonatomic, retain) NSString* className;
@property (nonatomic, retain) NSString* classNameChain;
@property (nonatomic, retain) NSString* attributeName;
@property (nonatomic, retain) NSString* attributeValue;
@property (nonatomic, retain) NSString* value;
@property (nonatomic, retain) NSString* action;
@property (nonatomic, retain) NSString* elementIndex;
@property (nonatomic, retain) NSString* commandResult;
@property (nonatomic, retain) NSString* xpath;

-(id)init;

+(NSString *) takeScreenshot;
-(NSDictionary *) describeElement;

-(void) setCommand: (NSString *)command_;
-(void) setParams: (NSString *)className_ attrName: (NSString *)attributeName_ attrValue: (NSString *)attributeValue_ index: (NSString *)index_ value: (NSString *)value_;
-(void) setParams: (NSString *)className_ attrName: (NSString *)attributeName_ attrValue: (NSString *)attributeValue_ index: (NSString *)index_ action: (NSString *)action_;
-(void) setParams: (NSString *)classNameChain_ attrName: (NSString *)attributeName_ index: (NSString *)index_;
-(void) setParams: (NSString *)classNameChain_;
-(void) setXPath: (NSString *)xpath_;
-(void) setParams: (NSString *)classNameChain_ value: (NSString *)value_;

-(void) comply;
-(void) complyWithCommand: (NSString *)command_;
//-(NSString *) getCommandResult;

- (id) applyToTarget:(id)target selector:(NSString *)sel attributes:(NSArray *)atr;
- (id) applyToTarget:(id)target selector:(NSString *)sel attribute:(NSString *)atr;

@end