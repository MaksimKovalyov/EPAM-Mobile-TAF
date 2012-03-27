package com.epam.mobile.extensions.excel;

import com.extentech.ExtenXLS.*;
import com.extentech.formats.XLS.*;
import com.extentech.toolkit.Logger;

import java.io.*;

/* Copyright 2004 Extentech Inc.
EXTENTECH SOFTWARE LICENSE AGREEMENT

All Java classes and other files contained in the com.extentech package are protected
by copyright and are the sole property of Extentech Inc.

This software may be used only by Extentech customers and may not be copied, sold, distributed
or used for any other purpose without the prior written consent of Extentech.  Those
interested in licensing components, servlets, and utility classes separately from the Luminet
Server product should contact Extentech Inc. at sales@extentech.com.

You agree that you will not reverse-engineer, or decompile the compiled files distributed with
this program with the exception of open-source servlets and other files with which source code
is included in the distribution.

To the maximum extent permitted by law, Extentech Inc. disclaims all warranties regarding
this software, expressed or implied, including but not limited to warranties of merchantability
and fitness for a particular purpose. In no event shall Extentech be liable for special,
consequential, incidental or indirect damages arising out of the use or inability
to use this software even if Extentech Inc. is aware of the possibility of such
damages or known defects.

This software is provided AS IS.
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.

By using this software, you are agreeing to all of the terms of this license.
****************************************************/


/**
      @author John McMahon -- Copyright &copy;2006 <a href = "http://www.extentech.com">Extentech Inc.</a>
      @version 4.1

    This Class Demonstrates the basic functionality of of ExtenXLS.

 */
public class TestCellAdding{

    public static void main(String[] args){
        testadds t = new testadds();
		String s = "Test Successful.";
		if(args.length>0)s = args[0];
		t.testit(s);
    }
}

/** Test the creation of a new Workbook with 3 worksheets.
*/
class testadds{
	
	private String wdir = "C:/experiments/OpenXLSProject/work_dir/";
	int ROWHEIGHT   = 5;    
    int NUMADDS     = 10;    
    
    public void testit(String argstr){
        WorkBookHandle book = new WorkBookHandle();
        
//      IMPORTANT PERFORMANCE SETTINGS!!!
        book.setDupeStringMode(WorkBookHandle.SHAREDUPES);
        book.setStringEncodingMode(WorkBookHandle.STRING_ENCODING_COMPRESSED);
        
        WorkSheetHandle sheet = null;
        try{
            sheet = book.getWorkSheet("Sheet1");
        }catch (WorkSheetNotFoundException e){System.out.println("couldn't find worksheet" + e);}            
        System.out.println("Beginning Cell Adds");
        String addr = "";
        
        // add a Double check that it was set
        sheet.add(new Double(22250.321),"A1");
        try{
            CellHandle cellA3 = sheet.getCell("A1");
            System.out.println(cellA3.getStringVal());
        }catch(CellNotFoundException e){;}
        long ltimr = System.currentTimeMillis();
	    
        for(int i = 1;i<NUMADDS;i++){
            addr = "E"+String.valueOf(i);
            sheet.add(new Double(1297.2753 * i),addr);
           // try{sheet.getCell(addr).getRow().setHeight(2000);}catch(CellNotFoundException e){;}
        }
        System.out.print("Adding " + NUMADDS);
	    System.out.println(" Double values took: " + ((System.currentTimeMillis() - ltimr)) + " milliseconds.");

        String teststr = "ExtenXLS is used around the world by Global 1000 and Fortune 500 companies to provide dynamic Excel reporting in their Java web applications.";
        teststr += "Written entirely in Java, ExtenXLS frees you from platform dependencies, allowing you to give your users the information they need, in the world's most popular Spreadsheet format.";
        
        try{
            sheet = book.getWorkSheet("Sheet2");
        }catch (WorkSheetNotFoundException e){System.out.println("couldn't find worksheet" + e);}                                
        String t = "";
        
        // IMPORTANT PERFORMANCE SETTING!!!
        sheet.setFastCellAdds(true);
        
        
        ltimr = System.currentTimeMillis();
        for(int i = 1;i<NUMADDS;i++){
            addr = "B"+String.valueOf(i);
            t = teststr + String.valueOf(i);
            sheet.add(t,addr);
            //try{sheet.getCell(addr).getRow().setHeight(ROWHEIGHT);}catch(CellNotFoundException e){;}
        }

        System.out.print("Adding " + NUMADDS);
	    System.out.println(" Strings took: " + ((System.currentTimeMillis() - ltimr)) + " milliseconds.");

	    ltimr = System.currentTimeMillis();
	    this.testWrite(book);
        System.out.println("Done.");
        System.out.print("Writing " + book);
	    System.out.println(" took: " + ((System.currentTimeMillis() - ltimr)) + " milliseconds.");
 
    }

    
    public void testWrite(WorkBookHandle b){
        try{
      	    java.io.File f = new java.io.File(wdir + "testAddOutput.xls");
            FileOutputStream fos = new FileOutputStream(f);
            BufferedOutputStream bbout = new BufferedOutputStream(fos);
            b.writeBytes(bbout);
            bbout.flush();
		    fos.close();
      	} catch (java.io.IOException e){Logger.logInfo("IOException in Tester.  "+e);}  
    }  


}