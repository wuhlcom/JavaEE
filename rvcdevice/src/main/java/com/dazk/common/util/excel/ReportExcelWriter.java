package com.dazk.common.util.excel;

import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



/**
 * 报表有关的导出方法写在此类中
 * @author xb
 */
public class ReportExcelWriter {

	public int MONTH = Calendar.getInstance().get(Calendar.MONTH) + 1;
	
	public int YEAR = Calendar.getInstance().get(Calendar.YEAR);
	
	public boolean FLAG = false;

	public boolean process() {
		return FLAG;
	}
	
	protected void refreshTime(){
		MONTH = Calendar.getInstance().get(Calendar.MONTH) + 1;
		YEAR = Calendar.getInstance().get(Calendar.YEAR);
	}
	
	
	/**
	 * 报表数据导出处理方法
	 * 
	 * @param path
	 * @param list
	 * @param excelName
	 * @param resp
	 * 注意：path为模板所在路径,method为处理报表数据的方法
	 * @return
	 */
	public <T> void questionExceptionOut(Sheet sheet, List<T> list,CellStyle style, Workbook wb) {
		// 表格内容
//		for (int i = 0; i < list.size(); i++) {
//			Row row = sheet.createRow(i + 1);
//			row.setHeightInPoints(18);
//			QuestionInfoException questionInfoException = (QuestionInfoException) list.get(i);
//
//			Cell cell0 = row.createCell(0);
//			cell0.setCellStyle(style);
//			cell0.setCellValue(questionInfoException.getQuestionCode());
//
//			Cell cell1 = row.createCell(1);
//			cell1.setCellStyle(style);
//			cell1.setCellValue(questionInfoException.getQuestionTitle());
//
//			Cell cell2 = row.createCell(2);
//			cell2.setCellStyle(style);
//			cell2.setCellValue(questionInfoException.getQuestionDes());
//			
//			Cell cell3 = row.createCell(3);
//			cell3.setCellStyle(style);
//			cell3.setCellValue(questionInfoException.getQuestionKey());
//			
//			Cell cell4 = row.createCell(4);
//			cell4.setCellStyle(style);
//			cell4.setCellValue(questionInfoException.getQuestionStatus());
//			
//			Cell cell5 = row.createCell(5);
//			cell5.setCellStyle(style);
//			cell5.setCellValue(questionInfoException.getRemark());
//			
//			Cell cell6 = row.createCell(6);
//			cell6.setCellStyle(style);
//			cell6.setCellValue(questionInfoException.getSpare1());
//			
//			Cell cell7 = row.createCell(7);
//			cell7.setCellStyle(style);
//			cell7.setCellValue(questionInfoException.getSpare2());
//			
//			Cell cell8 = row.createCell(8);
//			cell8.setCellStyle(style);
//			cell8.setCellValue(questionInfoException.getSpare3());
//			
//			Cell cell9 = row.createCell(9);
//			cell9.setCellStyle(style);
//			cell9.setCellValue(questionInfoException.getSpare4());
//			
//			Cell cell10 = row.createCell(10);
//			cell10.setCellStyle(style);
//			cell10.setCellValue(questionInfoException.getBatchid());
//			
//			Cell cell11 = row.createCell(11);
//			cell11.setCellStyle(style);
//			cell11.setCellValue(questionInfoException.getException());
//			
//		}
	}
	
//	public <T> void answerExceptionOut(Sheet sheet, List<T> list,CellStyle style, Workbook wb) {
//		// 表格内容
//		for (int i = 0; i < list.size(); i++) {
//			Row row = sheet.createRow(i + 1);
//			row.setHeightInPoints(18);
//			AnwserInfoException anwserInfoException = (AnwserInfoException) list.get(i);
//
//			Cell cell0 = row.createCell(0);
//			cell0.setCellStyle(style);
//			cell0.setCellValue(anwserInfoException.getAnwserCode());
//
//			Cell cell1 = row.createCell(1);
//			cell1.setCellStyle(style);
//			cell1.setCellValue(anwserInfoException.getQuestionCode());
//
//			Cell cell2 = row.createCell(2);
//			cell2.setCellStyle(style);
//			cell2.setCellValue(anwserInfoException.getAnwserMode());
//			
//			Cell cell3 = row.createCell(3);
//			cell3.setCellStyle(style);
//			cell3.setCellValue(anwserInfoException.getAnwserDes());
//			
//			Cell cell4 = row.createCell(4);
//			cell4.setCellStyle(style);
//			cell4.setCellValue(anwserInfoException.getAnwserStatus());
//			
//			Cell cell5 = row.createCell(5);
//			cell5.setCellStyle(style);
//			cell5.setCellValue(anwserInfoException.getRemark());
//			
//			Cell cell6 = row.createCell(6);
//			cell6.setCellStyle(style);
//			cell6.setCellValue(anwserInfoException.getSpare1());
//			
//			Cell cell7 = row.createCell(7);
//			cell7.setCellStyle(style);
//			cell7.setCellValue(anwserInfoException.getSpare2());
//			
//			Cell cell8 = row.createCell(8);
//			cell8.setCellStyle(style);
//			cell8.setCellValue(anwserInfoException.getSpare3());
//			
//			Cell cell9 = row.createCell(9);
//			cell9.setCellStyle(style);
//			cell9.setCellValue(anwserInfoException.getSpare4());
//			
//			Cell cell10 = row.createCell(10);
//			cell10.setCellStyle(style);
//			cell10.setCellValue(anwserInfoException.getBatchid());
//			
//			Cell cell11 = row.createCell(11);
//			cell11.setCellStyle(style);
//			cell11.setCellValue(anwserInfoException.getException());
//			
//		}
//	}
//
//	
//	public <T> void relationExceptionOut(Sheet sheet, List<T> list,CellStyle style, Workbook wb) {
//		// 表格内容
//		for (int i = 0; i < list.size(); i++) {
//			Row row = sheet.createRow(i + 1);
//			row.setHeightInPoints(18);
//			QuestionSortRelationException relationException = (QuestionSortRelationException) list.get(i);
//
//			Cell cell0 = row.createCell(0);
//			cell0.setCellStyle(style);
//			cell0.setCellValue(relationException.getSortCode());
//
//			Cell cell1 = row.createCell(1);
//			cell1.setCellStyle(style);
//			cell1.setCellValue(relationException.getQuestionCode());
//
//			Cell cell2 = row.createCell(2);
//			cell2.setCellStyle(style);
//			cell2.setCellValue(relationException.getRelationStatus());
//			
//			Cell cell3 = row.createCell(3);
//			cell3.setCellStyle(style);
//			cell3.setCellValue(relationException.getRemark());
//			
//			Cell cell4 = row.createCell(4);
//			cell4.setCellStyle(style);
//			cell4.setCellValue(relationException.getSpare1());
//			
//			Cell cell5 = row.createCell(5);
//			cell5.setCellStyle(style);
//			cell5.setCellValue(relationException.getSpare2());
//			
//			Cell cell6 = row.createCell(6);
//			cell6.setCellStyle(style);
//			cell6.setCellValue(relationException.getSpare3());
//			
//			Cell cell7 = row.createCell(7);
//			cell7.setCellStyle(style);
//			cell7.setCellValue(relationException.getSpare4());
//			
//			Cell cell8 = row.createCell(8);
//			cell8.setCellStyle(style);
//			cell8.setCellValue(relationException.getBatchid());
//			
//			Cell cell9 = row.createCell(9);
//			cell9.setCellStyle(style);
//			cell9.setCellValue(relationException.getException());
//			
//		}
//	}
	


}

