## Installation

### Maven

```xml
<dependency>
  <groupId>io.bretty</groupId>
  <artifactId>console-table-builder</artifactId>
  <version>1.2</version>
</dependency>
```

### Downloads

* Download package [`console-table-builder-1.2.jar`](https://github.com/nathanielove/Java-Console-Table-Builder/blob/master/console-table-builder-1.2.jar?raw=true)
* Download package, source and javadoc from: [Artifect Directory on Maven Central Repository](https://repo1.maven.org/maven2/io/bretty/console-table-builder/1.2/)

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

## Quick Start

### Alignment
There are three alignment types availabe in the enum `Alignment`,

* ```LEFT```
* ```CENTER```
* ```RIGHT```

### Convert `Object[][]` to Stylish `Table`
If you already have a ```Object[][] data```, you can print it into the console as a formatted table instantly with a specifed ```Alignment``` and a ```width``` for **ALL** columns in this table:

```java
Table table = Table.of(data, Alignment.LEFT, 10); // 10-character wide for each column
System.out.println(table); // NOTICE: table.toString() is called implicitly
```

## More on Table Formats

### Precision

The enum `Precision` comprises decimal number precision formats from ```ZERO``` to ```NINE```. 

Each ```Number``` object processed by `Precision` will be rounded up to the specified precision. For example, applying ```Precision.TWO``` to the constant ```Math.PI``` will give us ```3.14``` as a result.

### ColumnFormatter
```ColumnFormatter<T>``` specifies how all the cells in a certain column should be formatted. It is an abstract class that is designed to be subclassed. 

A concrete implementation of a subclass has at least two fields, ```alignment``` and ```width```. A subclass also must implement a ```String format(T t)``` method, which can convert an object of type ```T``` to a string, which will be later used as a cell in the column.

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

Here is a list of all built-in `ColumnFormatter` implementations:

| Column Type | Factory Method | 
| ---| ---|
| Currency | `ColumnFormatter.currency(...)` |
| Number | `ColumnFormatter.number(...)` | 
| Percentage | `ColumnFormatter.percentage(...)`|
| Datetime | `ColumnFormatter.dateTime(...)`|
| Text | `ColumnFormatter.text(...)`|


## Build Tables in Fully Customized Format

### Convert `T[][]` to a Stylish `Table`

The following example shows, with given

*  ```Double[][] data```: table contents, and
* ```String[] headers```: table headers, 

how to print a table with the same format for each column:

* Aligned to the right
* 8-char wide
* 3-digit precision after the decimal point

```java
ColumnFormatter<Number> cf = ColumnFormatter.number(Alignment.RIGHT, 8, Precision.THREE);
Table table = Table.of(headers, data, cf);
System.out.println(table); //NOTICE: table.toString() is called implicitly
```
In the case that your table doesn't need headers, the ```headers``` in the paramaters can be simply omitted like this:

```java
Table table = Table.of(data, cf);
```
Such a table will not have headers in the first row.

### Build a `Table` Column By Column
If you want each column in your table to be formatted in a different fashion, this is your solution. 

The following example shows how to print a stylish table with given

* ```String[] names``` for the first column
* ```Integer[] ages``` for the second,
* ```Double[] rates``` (in percentage format) for the third

```java
// define a formatter for each column
ColumnFormatter<String> nameFormatter = ColumnFormatter.text(Alignment.LEFT, 10);
ColumnFormatter<Number> ageFormatter = ColumnFormatter.number(Alignment.RIGHT, 3, Precision.ZERO);
ColumnFormatter<Number> rateFormatter = ColumnFormatter.percentage(Alignment.RIGHT, 6, Precision.ONE);

// create a builder with the first column
// "Name" serves as the header for this column
Table.Builder builder = new Table.Builder("Name", names, nameFormatter);

// add other columns
builder.addColumn("Age", ages, ageFormatter);
builder.addColumn("Rate", rates, rateFormatter);

// build the table and print it
Table table = builder.build();
System.out.println(table); // NOTICE: table.toString() is called implicitly
```


