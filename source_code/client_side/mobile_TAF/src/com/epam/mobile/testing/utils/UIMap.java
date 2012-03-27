package com.epam.mobile.testing.utils;

public class UIMap {

	public static class FirstLoginScreen{
		public static final String view               = "UIView=atIndex:1";
		
		public static final String login_button       = "UIButtonLabel=atIndex:1";
		public static final String publiclogin_button = "UIButtonLabel=atIndex:2";
	}
	
	public static class LoginScreen{
		
		public static final String label_publiclogin = "UILabel=accessibilityLabel:public login";
		public static final String label_username    = "";
		public static final String label_password    = "";
		
		public static final String textfield_username = "UITextField=placeholder:Enter Username";
		public static final String textfield_password = "UITextField=placeholder:Enter Password";
		
		public static final String button_sign_in     = "UIButton=accessibilityLabel:button sign in";
	}
	
	public static class HomeScreen implements UISearch{
		
		public static final String label_project        = "UILabel=accessibilityLabel:MARKETBOARD";
		public static final String project_somesection  = "MarketBoardPickerView=atIndex:1";
		public static final String label_some1          = "RatingView=atIndex:1";
		public static final String label_some2          = "InfinitePickerViewCell=atIndex:2";	
		public static final String segment_logout       = "UISegment=accessibilityLabel:logout";
	}
	
	public static class AlertDialog implements UIAlertDialog{	
	}
		
	public interface UISearch{
		public static final String view_searchresult   = "UITableView=atIndex:1";
		public static final String cell_searchresult   = "UITableView=atIndex:2";
	}
	
	public interface UIAlertDialog{
		
		public static final String button_cancel        = "UIButtonLabel=accessibilityLabel:Cancel";
		public static final String button_yes           = "UIButtonLabel=accessibilityLabel:Yes";
		public static final String button_no            = "UIButtonLabel=accessibilityLabel:No";
		public static final String textmessage_alert    = "UIAlertView=atIndex:1->UILabel=index:2";
		public static final String alert_continuebutton = "UIAlertButton=accessibilityLabel:Continue";
	}
}