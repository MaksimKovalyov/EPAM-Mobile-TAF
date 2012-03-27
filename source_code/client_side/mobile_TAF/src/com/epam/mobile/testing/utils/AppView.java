package com.epam.mobile.testing.utils;


// The class should provide necessary specification of app view.
// It can be used for cheking all attributes, except class names.
// For example:
// fonts, elements order in the model (via Rect coordinates), key words and whitespaces 
// in labels naming, colors, styles, image attributes, other.
public class AppView {
	public News news = new News();
	
	public AppView() {
		//this.news = new News();
	} 
	
	public class News{
		
		public String font_size = "";
		
		public String increaseFontSize(){
			String result = font_size.replace("px", "");
			font_size = String.valueOf(Integer.parseInt(result) + 1);

			return font_size;
		}
	
		public String decreaseFontSize() {
			String result = font_size.replace("px", "");
			font_size = String.valueOf(Integer.parseInt(result) - 1);

			return font_size;
		}
		
		public String formatFontSize(){
			font_size = font_size.replace("px", "");

			return font_size;
		}
	}
	
}
