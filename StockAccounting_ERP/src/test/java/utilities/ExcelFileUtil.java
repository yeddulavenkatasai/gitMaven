package utilities;

import java.awt.image.IndexColorModel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil 
{
      Workbook wb;
 //constructor for reading excel path
public ExcelFileUtil(String Excelpath) throws Throwable
{
	  FileInputStream fi = new FileInputStream(Excelpath);
	  wb = WorkbookFactory.create(fi);
}
 //count no of Rows in sheet
public int rowcount(String sheetname)
{
	  return wb.getSheet(sheetname).getLastRowNum();
}
 //get cell data
public String getcelldata(String sheetname,int row,int column)
{
      String data = "";
	  if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
	  {
		 int celldata = (int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
		 data = String.valueOf(celldata);
	  }else {
		data = wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
	}
	return data;	
}
 //method fo writing data
public void setcelldata(String sheetname,int row ,int column,String status,String writeExcel) throws Throwable
{
 //get sheet from wb
	 Sheet ws = wb.getSheet(sheetname);
 //get row from sheet
	 Row rowNum = ws.getRow(row);
 //get cell from row
	 Cell cell = rowNum.createCell(column);
 //write status
	 cell.setCellValue(status);
  if (status.equalsIgnoreCase("Pass"))
  {
	  CellStyle style = wb.createCellStyle();
	  Font font = wb.createFont();
	//colour text with green
	  font.setColor(IndexedColors.GREEN.getIndex());
	  font.setBold(true);
	  font.setBoldweight(font.BOLDWEIGHT_BOLD);
	  style.setFont(font);
	  ws.getRow(row).getCell(column).setCellStyle(style);
  }
  else if (status.equalsIgnoreCase("Fail"))
  {
	  CellStyle style = wb.createCellStyle();
	  Font font = wb.createFont();
	//colour text with red 
	  font.setColor(IndexedColors.RED.getIndex());
	  font.setBold(true);
	  font.setBoldweight(font.BOLDWEIGHT_BOLD);
	  style.setFont(font);
	  ws.getRow(row).getCell(column).setCellStyle(style);
  }
  else if (status.equalsIgnoreCase("Block")) 
  {
	  CellStyle style = wb.createCellStyle();
	  Font font = wb.createFont();
	//colour text with yellow
	  font.setColor(IndexedColors.YELLOW.getIndex());
	  font.setBold(true);
	  font.setBoldweight(font.BOLDWEIGHT_BOLD);
	  style.setFont(font);
	  ws.getRow(row).getCell(column).setCellStyle(style);
  }
      FileOutputStream fo = new FileOutputStream(writeExcel);
      wb.write(fo);	 
}
}














