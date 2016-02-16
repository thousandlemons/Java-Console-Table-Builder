/*
 * Copyright (c) 2015 SUN XIMENG (Nathaniel). All rights reserved.
 */

package io.bretty.console.table;

class TextColumnFormatter extends ColumnFormatter<String> {

	protected TextColumnFormatter() {
	}

	protected TextColumnFormatter(Alignment al, int width) {
		super(al, width);
	}

	@Override
	public String format(String s) {
		return this.formatString(s, this.width);
	}

}
