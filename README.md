## Installation

### Maven

```xml
<dependency>
  <groupId>io.bretty</groupId>
  <artifactId>console-table-builder</artifactId>
  <version>1.1</version>
</dependency>
```

### Downloads

* Download package [`console-table-builder-1.1.jar`](https://github.com/nathanielove/Java-Console-Table-Builder/blob/master/console-table-builder-1.1.jar?raw=true)
* Download package, source and javadoc from: [Artifect Directory on Maven Central Repository](https://repo1.maven.org/maven2/io/bretty/console-table-builder/1.1/)

## Introduction
This library makes it easy to build stylish *<b>Excel-like tables</b>*, which can be printed into a single ```String``` with all formats preserved.<p>
The core features are:
<ul>
  <li><b>Quickly build</b> a table with uniform format for each column.</li>
  <li>Build tables that have <b>different formats column by column</b>.</li>
  <li>Built-in formatters including <b>numbers, percentages, currency, date, time</b>, etc.</li>
  <li>Built-in <b>alignment</b> formats for each column as LEFT, CENTER or RIGHT.</li>
  <li>Built-in <b>precision</b> formats for numbers</li>
</ul>
## User Guide
### Alignment
There are three alignment types availabe in the enum `Alignment`,

* ```Alignment.LEFT```
* ```Alignment.CENTER```
* ```Alignment.RIGHT```

### Instant `Table` from `Object[][]`
If you already have a ```Object[][] data```, you can print it into the console as a formatted table instantly with a specifed ```Alignment``` and a ```width``` for all the columns in this table. Here we go, 

```java
Table table = Table.of(data, Alignment.LEFT, 10); //10-character wide for each column
System.out.println(table); //NOTICE: table.toString() is called implicitly
```

### Precision
There are built-in precision formats from ```Precision.ZERO``` to ```Precision.NINE```. Each ```Number``` applied to will be rounded up to the specified precision. For example, applying ```Precision.TWO``` to the constant ```Math.PI``` will get ```3.14``` as a result.

### ColumnFormatter
```ColumnFormatter<T>``` specifies how all the cells in a certain column should be formatted. It is an abstract class that is designed to be subclassed. 

A concrete implementation of a subclass has at least two fields, ```alignment``` and ```width```. It also must have a ```String format(T t)``` method, which can read in a certain object of type ```T``` and output a formatted string, so that this string can be further printed as a cell in the column.

There are several existing implementation of this class. For example, to get a ```ColumnFormatter<Number>``` object to format numbers as

<ul>
  <li>with "USD" as prefix</li>
  <li>aligned to the right within each cell</b>.</li>
  <li>each column is 8-character wide</li>
  <li>each number has 2-digit precision</li>
</ul>

simply write: 

```java
ColumnFormatter<Number> usdollar = ColumnFormatter.currency(Alignment.RIGHT, 8, Precision.TWO, "USD");
```

Each element in the column associated with ```usdollar``` will be formatted as the example below.

```
 USD 15.00
USD 457.20
 USD 37.50
```
Please note that the ```"USD"``` in the parameter list can be replaced by any string, which will serve as a prefix in the formatted strings. If this string has only one character, it is most likely to be something like ```"$"```. In that case, there will be no space between this prefix string and the number. For example, if we get a ```ColumnFormatter<Number>``` using the one-character string ```"$"```,

```java
ColumnFormatter<Number> usdollar = ColumnFormatter.currency(Alignment.RIGHT, 8, Precision.TWO, "$");
```

the same values in the above example will be formatted as:

```
 $15.00
$457.20
 $37.50
```

*<b>For the more details about other built-in ```ColumnFormatter``` implementations, including ```dateTime```, ```percentage```, etc., please refer to the javadoc.</b>*

### Quickly Build a `Table` from `T[][]`


The following example shows how to print a ```Double[][] data``` (table contents) together with a ```String[] headers``` (table headers), in the console as a table, and all the columns have exactly the same format - aligned to the right,  8-char wide, and with 3-digit precision.

```java
ColumnFormatter<Number> cf = ColumnFormatter.number(Alignment.RIGHT, 8, Precision.THREE);
Table table = Table.of(headers, data, cf);
System.out.println(table); //NOTICE: table.toString() is called implicitly
```
In the case that your table doesn't need headers, the ```headers``` in the paramaters can be simply omitted.

```java
Table table = Table.of(data, cf);
```
Such a table will not have headers in the first row.

### Build a `Table` Column By Column
If you want each column in your table to be formatted in different fashion, this is your solution. 

Suppose that now we have a ```String[] names``` for the first column, ```Integer[] ages``` for the second, and ```Double[] rates``` (in percentage format) for the third, to build a table column by column, simply write the followings:

```java
//define the formatter for each column
ColumnFormatter<String> nameFormatter = ColumnFormatter.text(Alignment.LEFT, 10);
ColumnFormatter<Number> ageFormatter = ColumnFormatter.number(Alignment.RIGHT, 3, Precision.ZERO);
ColumnFormatter<Number> rateFormatter = ColumnFormatter.percentage(Alignment.RIGHT, 6, Precision.ONE);

//create a builder with the first column
//"Name" serves as the header for this column
Table.Builder builder = new Table.Builder("Name", names, nameFormatter);

//add other columns
builder.addColumn("Age", ages, ageFormatter);
builder.addColumn("Rate", rates, rateFormatter);

//build the table and print it
Table table = builder.build();
System.out.println(table); //NOTICE: table.toString() is called implicitly
```


