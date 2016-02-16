/*
 * Copyright (c) 2015 SUN XIMENG (Nathaniel). All rights reserved.
 */

package io.bretty.console.table;

/**
 * The {@code Precision} enum represents the number of places after 
 * the decimal point in a decimal number.
 * @author nathaniel
 *
 */

public enum Precision {
	
	ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(
			8), NINE(9);


	private int precision;

	private Precision(int p) {
		this.precision = p;
	}

	protected String format(double n) {
		String f = "%." + this.precision + "f";
		return String.format(f, n);
	}


}
