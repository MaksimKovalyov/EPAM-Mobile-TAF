//
//  FrankServer.m
//  Frank
//
//  Created by phodgson on 5/24/10.
//  Copyright 2010 ThoughtWorks. See NOTICE file for details.
//

#import "FrankServer.h"
#import "HTTPServer.h"
#import "RoutingHTTPConnection.h"
#import "StaticResourcesRoute.h"
#import "FrankCommandRoute.h"
#import "DumpCommand.h"
#import "MemoryWarningCommand.h"
#import "TestCommand.h"
#import "FlashCommand.h"
#import "RunScript.h"
#import "TakeScreenshot.h"
#import "SFLogger.h"
#import "PingServer.h"
#import <dlfcn.h>

@implementation FrankServer

+ (void)_enableAccessibility;
{
    NSAutoreleasePool *autoreleasePool = [[NSAutoreleasePool alloc] init];
    NSString *appSupportLocation = @"/System/Library/PrivateFrameworks/AppSupport.framework/AppSupport";
    
    NSDictionary *environment = [[NSProcessInfo processInfo] environment];
    NSString *simulatorRoot = [environment objectForKey:@"IPHONE_SIMULATOR_ROOT"];
    if (simulatorRoot) {
        appSupportLocation = [simulatorRoot stringByAppendingString:appSupportLocation];
    }
    
    void *appSupportLibrary = dlopen([appSupportLocation fileSystemRepresentation], RTLD_LAZY);
    
    CFStringRef (*copySharedResourcesPreferencesDomainForDomain)(CFStringRef domain) = dlsym(appSupportLibrary, "CPCopySharedResourcesPreferencesDomainForDomain");
    
    if (copySharedResourcesPreferencesDomainForDomain) {
        CFStringRef accessibilityDomain = copySharedResourcesPreferencesDomainForDomain(CFSTR("com.apple.Accessibility"));
        
        if (accessibilityDomain) {
            CFPreferencesSetValue(CFSTR("ApplicationAccessibilityEnabled"), kCFBooleanTrue, accessibilityDomain, kCFPreferencesAnyUser, kCFPreferencesAnyHost);
            CFRelease(accessibilityDomain);
        }
    }
    
    [autoreleasePool drain];
}

- (id) initWithDefaultBundle {
	return [self initWithStaticFrankBundleNamed: @"frank_static_resources"];
}

// static bundle is switched off
- (id) initWithStaticFrankBundleNamed:(NSString *)bundleName
{
   
               
	self = [super init];
    
    @try {
    
	if (self != nil) {
		//if( ![bundleName hasSuffix:@".bundle"] )
		//	bundleName = [bundleName stringByAppendingString:@".bundle"];
		
		
		FrankCommandRoute *frankCommandRoute = [[[FrankCommandRoute alloc] init] autorelease];
		[frankCommandRoute registerCommand:[[[DumpCommand alloc] init] autorelease] withName:@"dump"];
		
		// add new commands
//  	[frankCommandRoute registerCommand:[[[LogCommand alloc] init]autorelease] withName:@"log"];
//		[frankCommandRoute registerCommand:[[[AppCommand alloc] init] autorelease] withName:@"relogon"];
		[frankCommandRoute registerCommand:[[[MemoryWarningCommand alloc] init] autorelease] withName:@"memory_warning"];
		[frankCommandRoute registerCommand:[[[TestCommand alloc] init] autorelease] withName:@"test_command"];
		[frankCommandRoute registerCommand:[[[RunScript alloc] init] autorelease] withName:@"run_script"];
		[frankCommandRoute registerCommand:[[[TakeScreenshot alloc] init] autorelease] withName:@"take_screenshot"];
		[frankCommandRoute registerCommand:[[[FlashCommand alloc] init] autorelease] withName:@"flash"];
		[frankCommandRoute registerCommand:[[[PingServer alloc] init] autorelease] withName:@"ping_server"];
		
		[[RequestRouter singleton] registerRoute:frankCommandRoute];
		
		//StaticResourcesRoute *staticRoute = [[[StaticResourcesRoute alloc] initWithStaticResourceSubDir:bundleName] autorelease];
		//[[RequestRouter singleton] registerRoute:staticRoute];
		
		_httpServer = [[[HTTPServer alloc] init] retain];
		
		[_httpServer setName:@"Frank UISpec server"];
		[_httpServer setType:@"_http._tcp."];
		[_httpServer setConnectionClass:[RoutingHTTPConnection class]];
		[_httpServer setPort:FRANK_SERVER_PORT];
		
		SFLog(@"[INIT_PHASE] Server is started.. Info: %@", _httpServer);
        
        [FrankServer _enableAccessibility];
        SFLog(@"[INIT_PHASE] Accessibility label set on programmatically.");
        
	}
        
    }
    @catch (NSException * e) {
        SFLog(@" Exception: %@", e);
    }
    @finally {
        SFLog(@" Finally: ");
        
    }
    
		
	return self;
}

- (BOOL) startServer{
	NSError *error;
	if( ![_httpServer start:&error] ) {
		NSLog(@"Error starting HTTP Server:");// %@", error);
		return NO;
	}
	
	return YES;
}

- (void) dealloc
{
	[ [ NSNotificationCenter defaultCenter ] removeObserver: self ];
	
	[_httpServer release];
	[super dealloc];
	
}

@end
