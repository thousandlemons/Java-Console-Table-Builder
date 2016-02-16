/*
 * Copyright (c) 2015 SUN XIMENG (Nathaniel). All rights reserved.
 */

package io.bretty.console.table;

class NumberColumnFormatter extends ColumnFormatter<Number> {

	protected Precision precision;

	protected NumberColumnFormatter(Alignment al, int width, Precision p) {
		super(al, width);
		this.precision = p;
	}

	/*protected double cast(Object n) {
		return ((Number) n).doubleValue();
	}*/

	@Override
	public String format(Number n) {
		double value = n.doubleValue();
		return this.formatString(this.precision.format(value), this.width);
	}

}
