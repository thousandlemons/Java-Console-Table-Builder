/*
 * Copyright (c) 2015 SUN XIMENG (Nathaniel). All rights reserved.
 */

package io.bretty.console.table;

class CurrencyNumberColumnFormatter extends NumberColumnFormatter {

	private String symbol;

	protected CurrencyNumberColumnFormatter(Alignment al, int width,
			Precision p, String symbol) {
		super(al, width, p);
		this.symbol = symbol;
	}

	@Override
	public String format(Number n) {
		double value = n.doubleValue();
		boolean negative = Double.compare(value, 0) < 0;
		if (negative)
			value = -value;
		String abs = this.symbol;
		if (this.symbol.length() > 1)
			abs += " ";
		abs += this.precision.format(value);
		if (negative)
			abs = "-" + abs;
		return this.formatString(abs, this.width);
	}

}
