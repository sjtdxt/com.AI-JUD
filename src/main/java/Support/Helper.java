/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Support;

import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Sujeet
 */
public class Helper {
	Connection connection;

	public Table getTable(String s) throws IOException {

		Table table = DatabaseBuilder.open(new File("D:\\Admin.accdb"))
				.getTable(s);

		return table;

	}

	public int getRowCount(String s) throws IOException {
		int c = 0;
		Table table = DatabaseBuilder.open(new File("D:\\Admin.accdb"))
				.getTable(s);
		for (@SuppressWarnings("unused")
		Row row : table)
			c++;

		return c;
	}

	public List<String> getData(String table, String col) throws IOException {
		List<String> list = new ArrayList<String>();
		Table tableNew = DatabaseBuilder.open(new File("D:\\Admin.accdb"))
				.getTable(table);

		for (Row row : tableNew) {
			list.add((String) row.get(col));
		}

		return list;
	}

	public void convertDate(java.util.Date uDate) {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		System.out.println("utilDate:" + utilDate);
		System.out.println("sqlDate:" + sqlDate);
	}

	public Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//		 connection=DriverManager.getConnection("jdbc:ucanaccess://C:/SSD/Admin.accdb");
//		connection = DriverManager
//				.getConnection("jdbc:ucanaccess://Z:/InfoSwiss/Tools & Assets/Tracker/Backup/2016/Admin.accdb");
		 connection=DriverManager.getConnection("jdbc:ucanaccess://C:/ALM/Data.accdb");
		System.out.print("");
		return connection;
	}

	public int getMaxMonthDay(int m,int y){
		Calendar cal=Calendar.getInstance();
    	cal.set(Calendar.MONTH,m-1);
    	cal.set(Calendar.YEAR,y);
    	cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
    	return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public String getEmpName(String Id) throws ClassNotFoundException,
			SQLException {
		String str = "";
		Statement st = new Helper().getConnection().createStatement();
		ResultSet rs = st
				.executeQuery("select * from List_Employee where Enterprise_ID='"
						+ Id + "'");
		while (rs.next()) {
			str = rs.getString("Name");
		}

		return str;

	}

}
