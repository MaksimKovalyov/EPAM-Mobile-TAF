//  FrankCommandRoute.m
//  Frank
//
//  Created by phodgson on 5/30/10.
//  Copyright 2010 ThoughtWorks. See NOTICE file for details.

#import "FrankCommandRoute.h"
#import "RoutingHTTPConnection.h"
#import "SFLogger.h"
#import "JSON.h"

@implementation FrankCommandRoute

- (id) init
{
	self = [super init];
	if (self != nil) {
		_commandDict = [[NSMutableDictionary alloc] init];
	}
	return self;
}

- (void) dealloc
{
	[_commandDict release];
	[super dealloc];
}

-(void) registerCommand: (id<FrankCommand>)command withName:(NSString *)commandName {
	[_commandDict setObject:command forKey:commandName];
}

#pragma mark Route implementation

- (id<FrankCommand>) commandForPath: (NSArray *)path{

	// parse command name as run_script:
	// http(s)://localhost:37265/run_script?{JSON_rest} or 
	// http(s)://localhost:37265/run_script
	NSArray *partsURL = [[path objectAtIndex:0] componentsSeparatedByString: @"?"];
	
	if( 1 != [partsURL count] )
		return [_commandDict objectForKey:[partsURL objectAtIndex:0]];
	
	return [_commandDict objectForKey:[path objectAtIndex:0]];
}

// Redesign for FAIL responses case.
- (NSObject<HTTPResponse> *) handleRequestForPath: (NSArray *)path withConnection:(RoutingHTTPConnection *)connection{
	
	NSString *incomingRequest = [path objectAtIndex:0];
	id<FrankCommand> command = [self commandForPath:path];
	SFLog(@"[Request Processing] Incoming request: %@", incomingRequest);
	SFLog(@"[Request Processing] Registered command: %@", [(id)command class]);
	
	if( nil == command ){
		SFLog(@"[RequestProcessing] The command <%@> is not registered. Please, check the incoming request.", incomingRequest);
		
		NSString *response = nil;
		
		NSMutableDictionary *rawResponse = [NSMutableDictionary dictionary];	
		NSString *reason = [NSString stringWithFormat:@"The command <%@> is not registered by FrankCommandRoute. Please, check the command name or add the command to server route.", incomingRequest];
		[rawResponse setObject:reason forKey:@"reason"];
		[rawResponse setObject:@"FAIL" forKey:@"test_command"];
		
		response = [rawResponse JSONRepresentation];
		
		NSData *browseData = [response dataUsingEncoding:NSUTF8StringEncoding];
		return [[[HTTPDataResponse alloc] initWithData:browseData] autorelease];
	}
	
	NSString *response = nil;
	NSRange range = [[path objectAtIndex:0] rangeOfString:@"?" options:NSCaseInsensitiveSearch];
	if( range.location != NSNotFound ) {
		//SFLog(@" json: %@", [[[path objectAtIndex:0] componentsSeparatedByString: @"?"] objectAtIndex:1]);
		response = [command handleCommandWithRequestBody:[[[path objectAtIndex:0] componentsSeparatedByString: @"?"] objectAtIndex:1]];
		//SFLog( @"returning:\n%@", response );
	}else{
	    //SFLog(@" json: %@", parts);
		response = [command handleCommandWithRequestBody:connection.postDataAsString];//connection.postDataAsString];
		//SFLog( @"returning:\n%@", response );
	}	
	
	NSData *browseData = [response dataUsingEncoding:NSUTF8StringEncoding];
	return [[[HTTPDataResponse alloc] initWithData:browseData] autorelease];
}

- (BOOL) canHandlePostForPath: (NSArray *)path{
	return( nil != [self commandForPath:path] );
}

@end