/*
 * Copyright (c) 2015 SUN XIMENG (Nathaniel). All rights reserved.
 */

package io.bretty.console.table;

final class Column {
	
	private int size;
	private String[] content;
	private String empty;

	protected <T> Column(String header, T[] data, ColumnFormatter<T> formatter) {
		this.size = data.length + 1;
		this.content = new String[this.size];
		ColumnFormatter<String> textFormatter = ColumnFormatter.text(formatter);
		textFormatter.al = Alignment.CENTER;
		this.content[0] = textFormatter.format(header);
		for(int i = 1; i < this.content.length; ++i){
			this.content[i] = formatter.format(data[i-1]);
		}
		this.empty = textFormatter.format("");
	}
	
	protected String get(int index){
		if(index < this.size){
			return this.content[index];
		}
		else{
			return this.empty;
		}
	}
	
	protected int size(){
		return this.size;
	}
	
	protected int width(){
		return this.content[0].length();
	}

}
