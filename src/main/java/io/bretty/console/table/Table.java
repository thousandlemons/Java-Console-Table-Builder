/*
 * Copyright (c) 2015 SUN XIMENG (Nathaniel). All rights reserved.
 */

package io.bretty.console.table;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Table} class represents an table to be formatted into a 
 * single {@code String}, which can be further printed into the console.
 * @author nathaniel
 *
 */
public final class Table {
	
	public static final class Builder{
		private static final int DEFAULT_COLUMN_NUMBER = 15;
		
		private List<Column> columns = new ArrayList<Column>(DEFAULT_COLUMN_NUMBER);
		private int totalWidth = 0;
		
		/**
		 * The only constructor must start with a column
		 * @param header A string as the header of the column
		 * @param data A generic array of objects
		 * @param formatter  A formatter for the specific type T. See class ColumnFormatter
		 */
		public <T> Builder(String header, T[] data, ColumnFormatter<T> formatter){
			this.addColumn(header, data, formatter);
		}
		
		/**
		 * Add a column to the ColumnBuilder object
		 * @param header A string as the header of the column
		 * @param data A generic array of objects
		 * @param formatter A formatter for the specific type T. See ColumnFormatter.
		 * @return The builder itself
		 */
		public <T> Builder addColumn(String header, T[] data, ColumnFormatter<T> formatter){
			Column c = new Column(header, data, formatter);
			this.columns.add(c);
			this.totalWidth += c.width();
			return this;
		}
		
		/**
		 * Build a table from the content of the builder
		 * @return A new Table object
		 */
		public Table build(){
			int columnCount = this.columns.size();
			int rowCount = 1;
			for(Column c : this.columns){
				int current = c.size();
				if(current > rowCount){
					rowCount = current;
				}
			}
			Table t = new Table();
			t.table = new String[rowCount][columnCount];
			for(int i = 0; i < rowCount; ++i){
				for(int j = 0; j < columnCount; ++j){
					t.table[i][j] = this.columns.get(j).get(i);
				}
			}
			t.charCount = rowCount * (this.totalWidth + columnCount + 2);
			return t;
		}
		
	}
	
	/**
	 * Quickly build a table from given data
	 * @param headers A String array as the headers for the entire table
	 * @param data A 2D array of objects of Type T
	 * @param f A single formatter of Type T for all the columns in the table
	 * @return
	 */
	public static <T> Table of(String[] headers, T[][] data, ColumnFormatter<T> f){
		if (data.length == 0 || data[0].length != headers.length);
		Table t = new Table();
		t.table = new String[data.length + 1][data[0].length];
		ColumnFormatter<String> tcf = ColumnFormatter.text(f);
		for(int i = 0; i < headers.length; ++i){
			t.table[0][i] = tcf.format(headers[i]);
		}
		for(int i = 1; i <= data.length; ++i){
			for(int j = 0; j < data[0].length; ++j){
				t.table[i][j] = f.format(data[i-1][j]);
			}
		}
		t.charCount = data.length * (data[0].length * (f.width + 1)) + 2;
		return t;
	}

	/**
	 * Quickly build a table
	 * @param data A 2D String array as the content of the table
	 * @param al See enum Alignment
	 * @param width An integer as the width of each column in the table
	 * @return
	 */
	public static Table of(String[][] data, Alignment al, int width){
		TextColumnFormatter tf = new TextColumnFormatter(al, width);
		Table t = new Table();
		t.table = new String[data.length][data[0].length];
		for(int i = 0; i < data.length; ++i){
			for(int j = 0; j < data[0].length; ++j){
				t.table[i][j] = tf.format(data[i][j]);
			}
		}
		t.charCount = data.length * (data[0].length * (width + 1)) + 2;
		return t;
	}
	
	/**
	 * Quickly build a table
	 * @param data A 2D array of Type T as the content of the table
	 * @param f A single formatter of Type T for all the columns in this table
	 * @return
	 */
	public static <T> Table of(T[][] data, ColumnFormatter<T> f){
		Table t = new Table();
		t.table = new String[data.length][data[0].length];
		for(int i = 0; i < data.length; ++i){
			for(int j = 0; j < data[0].length; ++j){
				t.table[i][j] = f.format(data[i][j]);
			}
		}
		t.charCount = data.length * (data[0].length * (f.width + 1)) + 2;
		return t;
	}

	
	private String[][] table;
	
	private int charCount;
	
	private String s = null;
	
	private static final char VERTICAL_SEPARATOR = '|';
	
	private Table() {
		
	}
	
	/**
	 * Convert the table in full format to a String
	 */
	@Override
	public String toString(){
		if(this.s == null){
			StringBuilder sb = new StringBuilder(this.charCount);
			for(int i = 0; i < this.table.length; ++i){
				sb.append(VERTICAL_SEPARATOR);
				for(int j = 0; j < this.table[0].length; ++j){
					sb.append(this.table[i][j]);
					sb.append(VERTICAL_SEPARATOR);
				}
				sb.append('\n');
			}
			sb.deleteCharAt(sb.length() - 1);
			this.s = sb.toString();
		}
		
		return this.s;
	}
	

}
