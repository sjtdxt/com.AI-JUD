package Support;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class ExportChecklist {

	XSSFWorkbook workbook;
	Sheet sheet1, sheet2;
	Row row;
	Cell cell;
	String filename="";


	public String generateChecklistDoc(String arr) throws IOException {

		workbook = new XSSFWorkbook();

		fetch(workbook, arr);

		  export(workbook);
		  
		  return filename;

	}

	public void fetch(XSSFWorkbook workbook, String arr) {
		JSONObject obj = null;
		JSONArray nameArr = null;
		JSONArray valArr = null;
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(arr);
		JSONArray jsonArr = jsonObject.getJSONArray("data");
		String[] columns = { "ID", "Category", "Checklist", "Self Review",
				"Peer Review", "Comments" };
		int rowi, coli;
		
		CellStyle styleGray = workbook.createCellStyle();
		styleGray.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
		styleGray.setFillForegroundColor(IndexedColors.GREY_25_PERCENT
				.getIndex());
		styleGray.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styleGray.setBorderLeft(CellStyle.BORDER_THIN);
		styleGray.setBorderTop(CellStyle.BORDER_THIN);
		styleGray.setBorderRight(CellStyle.BORDER_THIN);
		styleGray.setBorderBottom(CellStyle.BORDER_THIN);
		styleGray.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		
		CellStyle styleHoliday = workbook.createCellStyle();
		styleHoliday.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
		styleHoliday.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styleHoliday.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
		styleHoliday.setBorderLeft(CellStyle.BORDER_THIN);
		styleHoliday.setBorderTop(CellStyle.BORDER_THIN);
		styleHoliday.setBorderRight(CellStyle.BORDER_THIN);
		styleHoliday.setBorderBottom(CellStyle.BORDER_THIN);
		styleHoliday.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		sheet1 = workbook.createSheet("Details");
		sheet2 = workbook.createSheet("Checklist Data");

		sheet2.setColumnWidth(1, 30 * 256);
		sheet2.setColumnWidth(2, 250 * 256);
		sheet2.setColumnWidth(3, 20 * 256);
		sheet2.setColumnWidth(4, 20 * 256);
		sheet2.setColumnWidth(5, 20 * 256);

		row = sheet2.createRow(0);
		coli = 0;
		while (coli < 6) {
			cell = row.createCell(coli);
			cell.setCellValue(columns[coli]);
			cell.setCellStyle(styleGray);
			coli++;
		}

		for (int i = 0; i < ((JSONArray) jsonArr).size(); i++) {
			obj = jsonArr.getJSONObject(i);
			if (obj.containsKey("dev")) {
				
				filename = obj.getString("tech") + "_" + obj.getString("type") + "_" + obj.getString("dev") + "_" + obj.getString("rel") + "_" + obj.getString("func") + "_" + obj.getString("comp");

				nameArr = obj.names();
				valArr = obj.toJSONArray(nameArr);

				sheet1.setColumnWidth(3, 25 * 256);
				sheet1.setColumnWidth(4, 25 * 256);

				rowi = 6;
				for (int idx = 0; idx < obj.size(); idx++) {
					row = sheet1.createRow(rowi++);

					cell = row.createCell(3);

					switch (nameArr.getString(idx)) {
					case "comp":
						cell.setCellValue("Component name");
						cell.setCellStyle(styleHoliday);
						break;

					case "dev":
						cell.setCellValue("Developer name");
						cell.setCellStyle(styleHoliday);
						break;

					case "func":
						cell.setCellValue("Functionality");
						cell.setCellStyle(styleHoliday);
						break;

					case "rel":
						cell.setCellValue("Release");
						cell.setCellStyle(styleHoliday);
						break;

					case "reviewer":
						cell.setCellValue("Reviewer name");
						cell.setCellStyle(styleHoliday);
						break;
						
					case "tech":
						cell.setCellValue("Technology");
						cell.setCellStyle(styleHoliday);
						break;
						
					case "type":
						cell.setCellValue("Checklist Type");
						cell.setCellStyle(styleHoliday);
						break;
					}

					cell = row.createCell(4);
					cell.setCellValue(valArr.getString(idx));
					cell.setCellStyle(styleHoliday);

				}
			} else {
				row = sheet2.createRow(i);
				for (int id = 0; id < obj.size(); id++) {
					cell = row.createCell(id);

					switch (sheet2.getRow(0).getCell(id).getStringCellValue()) {
					case "ID":
						cell.setCellValue(obj.getString("id"));
						break;

					case "Category":
						cell.setCellValue(obj.getString("category"));
						break;

					case "Checklist":
						cell.setCellValue(obj.getString("checklist"));
						break;

					case "Self Review":
						cell.setCellValue(obj.getString("selfReview"));
						break;

					case "Peer Review":
						cell.setCellValue(obj.getString("peerreview"));
						break;

					case "Comments":
						cell.setCellValue(obj.getString("comments"));
						break;

					}

				}
			}

		}
		
		sheet1.protectSheet("p@$$w0rd" + sheet1.getSheetName());
		sheet2.protectSheet("p@$$w0rd" + sheet2.getSheetName());
	}

	public XSSFWorkbook export(XSSFWorkbook workbook1) throws IOException {
		File f 	= new File(filename);
		FileOutputStream out1 = new FileOutputStream("Y:/" + filename + ".xlsx");
		workbook1.write(out1);
		return workbook1;

//		Desktop dk = null;
//		dk = Desktop.getDesktop();
//		try {
//			dk.open(new File(filename));
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
	}

}
