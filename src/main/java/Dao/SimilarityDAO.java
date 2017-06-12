package Dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Entity.CaseSteps;
import Entity.JudData;

public class SimilarityDAO {

	public ArrayList<Object> similarResult(String Case) {
		List<Object> al = new ArrayList<Object>();
		JudData jd;
		List<Object> validCases = new ArrayList<Object>();

		try {
			FileInputStream csv = new FileInputStream(
					new File("C://Users//sujeet.suresh.dixit//Desktop//AI-JUD//Files//SM.xlsx"));
			Workbook workbook = null;
			try {
				workbook = new XSSFWorkbook(csv);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow;
			Cell currentCell;

			while (iterator.hasNext()) {

				currentRow = iterator.next();

				if (null == currentRow.getCell(0)) {

				} else {
					if ((currentRow.getCell(0)).getStringCellValue().equals(Case)) {
						Iterator<Cell> cellIterator = currentRow.iterator();

						while (cellIterator.hasNext()) {

							currentCell = cellIterator.next();
							if (currentCell.getCellType() == 1) {

							} else if (currentCell.getCellType() == 0) {
								float val = (float) currentCell.getNumericCellValue();
								if (val != 1) {
									if (val >= 0.3) {
										validCases.add(datatypeSheet.getRow(0).getCell(currentCell.getColumnIndex())
												.getStringCellValue());

									}
								}
							}

						}
						System.out.println();
					}

				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			FileInputStream csv2 = new FileInputStream(
					new File("C://Users//sujeet.suresh.dixit//Desktop//AI-JUD//Files//JD.xlsx"));
			Workbook workbook = null;
			try {
				workbook = new XSSFWorkbook(csv2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow;

			while (iterator.hasNext()) {

				currentRow = iterator.next();

				for (Object casee : validCases) {

					if (null == currentRow.getCell(2)) {

					} else {
						if ((currentRow.getCell(2)).getStringCellValue().equals(casee.toString().split(".txt")[0])) {

							jd = new JudData();
							jd.setLabel(currentRow.getCell(2).getStringCellValue());
							jd.setOutcome(currentRow.getCell(0).getStringCellValue());
							jd.setSummary(currentRow.getCell(1).getStringCellValue());

							al.add(jd);
						}

					}

				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return (ArrayList<Object>) al;

	}

	public ArrayList<Object> generate(String Case) {
		int p3_4 = 0, p4_5 = 0, p_5 = 0;
		List<Object> al = new ArrayList<Object>();
		try {
			FileInputStream csv = new FileInputStream(
					new File("C://Users//sujeet.suresh.dixit//Desktop//AI-JUD//Files//SM.xlsx"));
			Workbook workbook = null;
			try {
				workbook = new XSSFWorkbook(csv);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow;
			Cell currentCell;

			while (iterator.hasNext()) {

				currentRow = iterator.next();

				if (null == currentRow.getCell(0)) {

				} else {
					if ((currentRow.getCell(0)).getStringCellValue().equals(Case)) {
						Iterator<Cell> cellIterator = currentRow.iterator();

						while (cellIterator.hasNext()) {

							currentCell = cellIterator.next();
							if (currentCell.getCellType() == 1) {

							} else if (currentCell.getCellType() == 0) {
								float val = (float) currentCell.getNumericCellValue();
								if (val != 1) {
									if (val > 0.3 && val <= 0.4) {
										p3_4++;
									} else if (val > 0.4 && val <= 0.5) {
										p4_5++;
									} else if (val > 0.5) {
										p_5++;
									}
								}
							}

						}
						System.out.println();
					}

				}

			}
			System.out.println(p3_4 + " " + p4_5 + " " + p_5);
			al.add(p3_4);
			al.add(p4_5);
			al.add(p_5);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return (ArrayList<Object>) al;
	}

	public ArrayList<Object> getBasic(String Case) {

		int newCase, totalCase, indi = 0, notindi = 0;
		List<Object> al = new ArrayList<Object>();
		File folder = new File("C://Users//sujeet.suresh.dixit//Desktop//AI-JUD//JudData//NewCases");
		File[] listOfFiles = folder.listFiles();

		newCase = listOfFiles.length;

		folder = new File("C://Users//sujeet.suresh.dixit//Desktop//AI-JUD//JudData//Cases");
		listOfFiles = folder.listFiles();

		totalCase = listOfFiles.length;

		try {
			FileInputStream csv2 = new FileInputStream(
					new File("C://Users//sujeet.suresh.dixit//Desktop//AI-JUD//Files//JD.xlsx"));
			Workbook workbook = null;
			try {
				workbook = new XSSFWorkbook(csv2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow;

			while (iterator.hasNext()) {

				currentRow = iterator.next();

				if (null == currentRow.getCell(0)) {

				} else {
					if ((currentRow.getCell(0)).getStringCellValue().equalsIgnoreCase("Indicted")) {

						indi++;

					} else if ((currentRow.getCell(0)).getStringCellValue().equalsIgnoreCase("Not Indicted")){
						notindi++;
					}

				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		al.add(new Integer(newCase));
		al.add(new Integer(totalCase));
		al.add(new Integer(indi));
		al.add(new Integer(notindi));

		return (ArrayList<Object>) al;

	}

	public String getCaseDetails(String Case) throws IOException {
		String lines = "";
		FileReader fr;
		BufferedReader br;

		try {
			File folder = new File("C://Users//sujeet.suresh.dixit//Desktop//AI-JUD//JudData//Cases");
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
				if (file.getName().contains(Case)) {
					fr = new FileReader(
							"C://Users//sujeet.suresh.dixit//Desktop//AI-JUD//JudData//Cases//" + file.getName());
					br = new BufferedReader(fr);
					Scanner scan = new Scanner(br);
			while(scan.hasNext()){
				lines+=scan.nextLine();
			}

				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return lines;
	}

	public ArrayList<Object> getTop3(String Case) {

		List<Object> al = new ArrayList<Object>();
		Map<Float, String> mp = new HashMap<Float, String>();
		Map<Float, String> newMap = null;

		try {
			FileInputStream csv = new FileInputStream(
					new File("C://Users//sujeet.suresh.dixit//Desktop//AI-JUD//Files//SM.xlsx"));
			Workbook workbook = null;
			try {
				workbook = new XSSFWorkbook(csv);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow;
			Cell currentCell;

			while (iterator.hasNext()) {

				currentRow = iterator.next();

				if (null == currentRow.getCell(0)) {

				} else {
					if ((currentRow.getCell(0)).getStringCellValue().equals(Case)) {
						Iterator<Cell> cellIterator = currentRow.iterator();

						while (cellIterator.hasNext()) {

							currentCell = cellIterator.next();
							if (currentCell.getCellType() == 1) {

							} else if (currentCell.getCellType() == 0) {
								float val = (float) currentCell.getNumericCellValue();
								if (val != 1) {
									mp.put(val, datatypeSheet.getRow(0).getCell(currentCell.getColumnIndex())
											.getStringCellValue());
								}
							}

						}
						System.out.println();
					}

				}

			}

			MyComparator comp = new MyComparator(mp);

			newMap = new TreeMap(comp);
			newMap.putAll(mp);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<String> lst = new ArrayList<String>();
		Set set2 = newMap.entrySet();
		Iterator iterator2 = set2.iterator();
		Map.Entry me2;
		for (int i = 0; i < 3; i++) {
			me2 = (Map.Entry) iterator2.next();
			lst.add((String) me2.getValue());
		}

		try {
			FileInputStream csv2 = new FileInputStream(
					new File("C://Users//sujeet.suresh.dixit//Desktop//AI-JUD//JudData//Judicial//Steps.xlsx"));
			Workbook workbook = null;
			try {
				workbook = new XSSFWorkbook(csv2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator;
			Row currentRow;

			for (String as : lst) {
				String set = "";

				iterator = datatypeSheet.iterator();
				CaseSteps cs = new CaseSteps();
				while (iterator.hasNext()) {

					currentRow = iterator.next();

					if (null == currentRow.getCell(0)) {

					} else {
						if ((currentRow.getCell(0)).getStringCellValue()
								.equalsIgnoreCase(as.toString().split(".txt")[0])) {

							while ((currentRow.getCell(0)).getStringCellValue()
									.equalsIgnoreCase(as.toString().split(".txt")[0])) {
								cs.setName(currentRow.getCell(0).getStringCellValue());
								set += currentRow.getCell(1).getStringCellValue();

								currentRow = iterator.next();
								if ((currentRow.getCell(0)).getStringCellValue()
										.equalsIgnoreCase(as.toString().split(".txt")[0])) {
									set += "%_%";
								}
							}
							cs.setSteps(set);
							al.add(cs);
						}

					}
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return (ArrayList<Object>) al;

	}

}

class MyComparator implements Comparator {
	Map map;

	public MyComparator(Map map) {
		this.map = map;
	}

	@Override
	public int compare(Object o1, Object o2) {
		return (o2.toString()).compareTo(o1.toString());
	}
}