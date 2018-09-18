package com.zhuhaibing.baoko.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
	
	/**读取excel数据原理：通过sheet（表单）获取到行（row）,然后再通过行获取到列（cell）
	 * @param path
	 * @param startRow
	 * @param endRow
	 * @param startCell
	 * @param endCell
	 * @return
	 */
	public static Object[][] read(String path  ,int startRow,int endRow,int startCell,int endCell){
		System.out.println(path);
		try {
			//创建一个工作簿对象
			Workbook workbook=WorkbookFactory.create(ExcelUtil.class.getResourceAsStream(path));
			
			//获取要操作的sheet表单
			Sheet sheet=workbook.getSheetAt(0);
			//创建保存数据的数组
			Object[][] datas=new Object[endRow-startRow+1][endCell-startCell+1];
			//通过循环拿出咱们想要的数据
			for(int i=startRow;i<=endRow;i++) {
				//拿出一行
				Row row=sheet.getRow(i-1);
				//取出此行的所有列
				for (int j = startCell; j <= endCell; j++) {
					//取出一列
					Cell cell=row.getCell(j-1,MissingCellPolicy.CREATE_NULL_AS_BLANK);//获取到空列不报空指针异常
					cell.setCellType(CellType.STRING);//设置 获取的到的值是String
					//取出列的值
					String value=cell.getStringCellValue();
					//把数据存到二维数组
					datas[i-startRow][j-startCell]=value;
				}
			}
			return datas;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		Object[][] datas= read("/baidonglogin.xlsx",1,2,1,3);
		for(Object[] objects:datas) {
			for(Object object:objects) {
				System.out.println("["+object+"]");
			}
			System.out.println("===========");
		}
	}
}
