package EsPoc.EarlySalary.Common.CommonAction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


import EsPoc.EarlySalary.Utilities.SuiteBase;


public class ExcelReader {
	
	
	
	
	public Object[][] getDataingrid(String Filelocation,String sheetname) throws IOException, InvalidFormatException 
	 {
		 Object[][]  data=null;
		   try
		   {
		   
		    FileInputStream fis=new FileInputStream(Filelocation);
			Workbook wb=WorkbookFactory.create(fis);
			Sheet sh=wb.getSheet(sheetname);
		    int rowcount=sh.getLastRowNum()+1;
		    int coln=sh.getRow(0).getLastCellNum();
		    int index=0;
		      data =new Object[rowcount-1][1];
		    Hashtable<String,String> table = null;
		    for (int i = 1; i <= rowcount-1; i++) {
		        table=new Hashtable<String,String>();
		               
		       for (int j = 0; j < coln-1 ; j++) {
						  String value = getexceldata(Filelocation,sheetname,i,j);
						  //System.out.println(" value"+value);
						  String key=getexceldata(Filelocation,sheetname,0,j);
						//  System.out.println(" key"+key);
						  table.put(key,value);					
				  }
				           
		        data[index][0]=table;
		        //System.out.println("Data from Sheet"+data[index][0]);
		        index++;
		    }
	
		   
		   }
		    catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		    //System.out.println(data); */
		    return data;
	
	 }
	
	
	
	
	public String getexceldata1(String Filelocation,String sheetname,int rowid,String colnm) throws Exception

	{
		String retval=null;
		int colid;
		try
		{
		FileInputStream fis=new FileInputStream(Filelocation);
		Workbook wb=WorkbookFactory.create(fis);
		Sheet sh=wb.getSheet(sheetname);
		Row r=sh.getRow(rowid);
		colid=getcellno(Filelocation,sheetname,colnm);
		Cell c=r.getCell(colid);
		retval=cellToString(c);	
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			//Setup.log.fatal(e.toString());
		}
		catch(IOException e)
		{
			e.printStackTrace();
			//Setup.log.fatal(e.toString());
		}
		catch(InvalidFormatException e)
		{
			e.printStackTrace();
			/////Setup.log.fatal(e.toString());
		}
			
		return retval; 
	}

	
	
	private int getcellno(String filelocation, String sheetname, String colnm) throws Exception
	{
		// TODO Auto-generated method stub
		int rowno=0;
	try
	{
		FileInputStream fis=new FileInputStream(filelocation);
		Workbook wb=WorkbookFactory.create(fis);
		Sheet sh=wb.getSheet(sheetname);
		int coln=sh.getRow(0).getLastCellNum();
		for(int j=0;j<=coln;j++)
		{
		  String coln_name= getexceldata(filelocation,sheetname,0,j);
		  if(coln_name.equalsIgnoreCase(colnm))
		  {
			  rowno=j;
			  break;
		  }	  
		}
	}
	catch(FileNotFoundException e)
	{
			e.printStackTrace();
			//Setup.log.fatal(e.toString());
	}
	catch(IOException e)
	{
			e.printStackTrace();
			//Setup.log.fatal(e.toString());
	}
	catch(InvalidFormatException e)
	{
			e.printStackTrace();
			//Setup.log.fatal(e.toString());
	}
		return rowno;
	}
	
	
	
	
	public static String cellToString(Cell cell)
	{
		int type;
		Object result = null;
		type = cell.getCellType();			
		switch (type)
		{
			case 0 :
				result = cell.getNumericCellValue();
				break;
				
			case 1 : 
				result = cell.getStringCellValue();
				break;
				
			case 3 : 	
			       result=cell.toString();
			       break;		
		}
		return result.toString();
	}

	
	
	public String getexceldata(String Filelocation,String sheetname,int rowid, int colid)

	{
		String retval=null;
		
		try
		{
		FileInputStream fis=new FileInputStream(Filelocation);
		Workbook wb=WorkbookFactory.create(fis);
		Sheet sh=wb.getSheet(sheetname);
		Row r=sh.getRow(rowid);
		Cell c=r.getCell(colid);
		retval=cellToString(c);	}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			////Setup.log.fatal(e.toString());
		}
		catch(IOException e)
		{
			e.printStackTrace();
			/////Setup.log.fatal(e.toString());
		}
		catch(InvalidFormatException e)
		{
			e.printStackTrace();
			//Setup.log.fatal(e.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return retval; 
	}
	
	
	
	/*public int get_row_count(String Filelocation,String sheetname)
	{
		int rowcount=0;
		try
		{
		FileInputStream fis=new FileInputStream(Filelocation);
		Workbook wb=WorkbookFactory.create(fis);
		Sheet sh=wb.getSheet(sheetname);
		rowcount=sh.getLastRowNum();
			
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			Setup.log.fatal(e.toString());
		}
		catch(IOException e)
		{
			e.printStackTrace();
			Setup.log.fatal(e.toString());
		}
		catch(InvalidFormatException e)
		{
			e.printStackTrace();
			Setup.log.fatal(e.toString());
		}
		
		return rowcount;
	}*/
	
	
	
	/*public void writeexceldata(String Filelocation,String sheetname,int rowid, int colid,String val, int CELL_TYPE_STRING)

	{
			
		try
		{
		FileInputStream fis=new FileInputStream(Filelocation);
		Workbook wb=WorkbookFactory.create(fis);
		Sheet sh=wb.getSheet(sheetname);
		Row r=sh.getRow(rowid);
		Cell c=r.createCell(colid);
		c.setCellType(CELL_TYPE_STRING);
		c.setCellValue(val);
		FileOutputStream fout=new FileOutputStream(Filelocation);
		wb.write(fout);
		fout.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			Setup.log.fatal(e.toString());
		}
		catch(IOException e)
		{
			e.printStackTrace();
			Setup.log.fatal(e.toString());
		}
		catch(InvalidFormatException e)
		{
			e.printStackTrace();
			Setup.log.fatal(e.toString());
		}
	}*/
	
	
	
//	public String[][] getDatainarray(String Filelocation,String sheetname) throws IOException, InvalidFormatException 
//	 {
//		    
//		   
//		    FileInputStream fis=new FileInputStream(Filelocation);
//			Workbook wb=WorkbookFactory.create(fis);
//			Sheet sh=wb.getSheet(sheetname);
//		    int rowcount=sh.getLastRowNum();
//		    int coln=sh.getRow(0).getLastCellNum();
//		    System.out.println("row count"+rowcount);
//		    System.out.println("row count"+coln);
//		    String[][] data=new String[rowcount][coln];
//		    //int r=0,c=0;
//		    
//		    	
//		    for (int i = 1 ,r=0; (i <= rowcount) && (r < rowcount); i++ , r++) {
//		          
//		       for (int j = 0 ,c=0;(j <= coln-1) && (c < coln) ; j++,c++) {
//		        		     	   
//						  data[r][c]= getexceldata(Filelocation,sheetname,i,j);
//						  System.out.println(data[r][c]+"");
//						   
//					}
//				   
//		      } 
//	
//		   
//	    
//		    return data;
//	
//	 }


}
