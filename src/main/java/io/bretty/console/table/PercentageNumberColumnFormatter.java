/*
 * Copyright (c) 2015 SUN XIMENG (Nathaniel). All rights reserved.
 */

package io.bretty.console.table;

class PercentageNumberColumnFormatter extends NumberColumnFormatter {

	protected PercentageNumberColumnFormatter(Alignment al, int width,
			Precision p) {
		super(al, width, p);
	}

	@Override
	public String format(Number n) {
		double value = n.doubleValue() * 100;;
		return this.formatString(this.precision.format(value) + "%", this.width);
	}

}
