/*
 * Copyright (c) 2015 SUN XIMENG (Nathaniel). All rights reserved.
 */

package io.bretty.console.table;

import java.text.DateFormat;
import java.util.Date;

class DateTimeColumnFormatter extends ColumnFormatter<Date> {

	private DateFormat df;

	public DateTimeColumnFormatter(Alignment al, int width, DateFormat df) {
		super(al, width);
		this.df = df;
	}

	@Override
	public String format(Date d) {
		return this.formatString(df.format(d), this.width);
	}

}
