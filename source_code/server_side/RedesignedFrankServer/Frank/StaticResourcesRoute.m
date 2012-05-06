//  StaticResourcesRoute.m
//  Frank
//
//  Created by phodgson on 5/30/10.
//  Copyright 2010 ThoughtWorks. See NOTICE file for details.

#import "StaticResourcesRoute.h"

@implementation StaticResourcesRoute

- (id) initWithStaticResourceSubDir:(NSString *)resourceSubdir
{
	self = [super init];
	if (self != nil) {
		_staticResourceDirectoryPath = [[[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:resourceSubdir]retain];
	}
	return self;
}

- (void) dealloc
{
	[_staticResourceDirectoryPath release];
	[httpResponse release];
	[super dealloc];
}


#pragma mark Route implementation

- (NSObject<HTTPResponse> *) handleRequestForPath: (NSArray *)path withConnection:(RoutingHTTPConnection *)connection{
	NSString *fullPathToRequestedResource = [_staticResourceDirectoryPath stringByAppendingPathComponent: [NSString pathWithComponents:path]];
	SFLog( @"Checking for static file at %@", fullPathToRequestedResource );
	BOOL isDir = YES;
	if( [[NSFileManager defaultManager] fileExistsAtPath:fullPathToRequestedResource isDirectory:&isDir] && !isDir ){
		httpResponse = [[HTTPFileResponse alloc] initWithFilePath:fullPathToRequestedResource];
		return httpResponse;
	}
	else
		return nil;
}

- (BOOL) canHandlePostForPath: (NSArray *)path{
	return NO;
}

@end