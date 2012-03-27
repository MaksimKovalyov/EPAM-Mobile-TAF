//
//  StaticResourcesRoute.h
//  Frank
//
//  Created by phodgson on 5/30/10.
//  Copyright 2010 ThoughtWorks. See NOTICE file for details.
//

#import <Foundation/Foundation.h>
#import "RequestRouter.h"
#import "SFLogger.h"

@interface StaticResourcesRoute : NSObject<Route> {
	
	NSString *_staticResourceDirectoryPath;
	NSObject<HTTPResponse> *httpResponse;

}

- (id) initWithStaticResourceSubDir:(NSString *)resourceSubdir;

@end
