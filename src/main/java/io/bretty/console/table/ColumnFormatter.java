/*
 * Copyright (c) 2015 SUN XIMENG (Nathaniel). All rights reserved.
 */

package io.bretty.console.table;

import java.text.DateFormat;
import java.util.Date;

/**
 * The {@code ColumnFormatter} class represents a certain format of
 * an logical column in a table. For example, the format can be percentage,
 * currency, plain text, ect.
 * @author Nathaniel
 *
 * @param <T>
 */

public abstract class ColumnFormatter<T> {
	
	/**
	 * Get a built-in ColumnFormatter for currency values.
	 * @param al See enum Alignment
	 * @param width An integer as the width of the column
	 * @param p See enum Precision
	 * @param symbol A string as the currency symbol.
	 * If the symbol length is greater than 1, there will be a space between the symbol and the number.
	 * @return A ColumnFormatter object
	 */
	public static ColumnFormatter<Number> currency(Alignment al, int width,
			Precision p, String symbol) {
		return new CurrencyNumberColumnFormatter(al, width, p, symbol);
	}

	/**
	 * Get a built-in ColumnFormatter for date and time
	 * @param al See enum Alignment
	 * @param width An integer as the width of the column
	 * @param df See java.text.DateFormat
	 * @return A ColumnFormatter for datetime objects
	 */
	public static ColumnFormatter<Date> dateTime(Alignment al, int width,
			DateFormat df) {
		return new DateTimeColumnFormatter(al, width, df);
	}

	/**
	 * Format a string using the Alignment and width of an existing ColumnFormatter for any type
	 * @param cf An existing ColumnFormatter of any type
	 * @param s The input String to be formatted
	 * @param <T> the class of the objects in the column
	 * @return The result String
	 */
	public static <T> String formatText(ColumnFormatter<T> cf, String s){
		textFormatter.al = cf.al;
		textFormatter.width = cf.width;
		return textFormatter.format(s);
	}

	/**
	 * Get a built-in ColumnFormatter for plain numbers
	 * @param al See enum Alignment
	 * @param width An integer as the width of the column
	 * @param p See enum Precision
	 * @return A ColumnFormatter for Number objects
	 */
	public static ColumnFormatter<Number> number(Alignment al, int width, Precision p) {
		return new NumberColumnFormatter(al, width, p);
	}

	/**
	 * Get a built-in ColumnFormatter for percentage values
	 * @param al See enum Alignment
	 * @param width An integer as the width of the column
	 * @param p See enum Precision
	 * @return a ColumnFormatter for percentage numbers
	 */
	public static ColumnFormatter<Number> percentage(Alignment al, int width,
			Precision p) {
		return new PercentageNumberColumnFormatter(al, width, p);
	}

	/**
	 * Get a built-in ColumnFormatter for plain texts
	 * @param al See enum Alignment
	 * @param width An integer as the width of the column
	 * @return a ColumnFormatter for String objects
	 */
	public static ColumnFormatter<String> text(Alignment al, int width) {
		return new TextColumnFormatter(al, width);
	}
	
	/**
	 * Get a ColumnFormatter object using the Alignment and width of an existing ColumnFormatter of any type
	 * @param cf An existing ColumnFormatter
	 * @param <T> the class of the column objects
	 * @return a ColumnFormatter for String objects
	 */
	public static <T> ColumnFormatter<String> text(ColumnFormatter<T> cf){
		return new TextColumnFormatter(cf.al, cf.width);
	}
	
	private static ColumnFormatter<String> textFormatter = new TextColumnFormatter();

	protected Alignment al;

	protected int width;

	protected ColumnFormatter() {

	}

	protected ColumnFormatter(Alignment al, int width) {
		this.al = al;
		this.width = width;
	}

	/**
	 * Format an object of T type into a String
	 * @param t An object of T type
	 * @return A formatted String
	 */
	public abstract String format(T t);

	protected String formatString(String s, int width) {
		return this.al.format(s, width, ' ');
	}

	/**
	 * 
	 * @return the Alignment of this ColumnFormatter
	 */
	public final Alignment getAlignment() {
		return this.al;
	}

	/**
	 * 
	 * @return the width of the ColumnFormatter
	 */
	public final int getWidth() {
		return this.width;
	}

}
