
Stock Market Analysis System
A Java-based application that processes historical stock data using a Red-Black Tree for efficient storage and retrieval. The system provides a menu-driven interface to analyze stock performance, compute statistics, and compare the execution time of four sorting algorithms (Bubble, Selection, Quick, Merge) on large datasets.

Features
Efficient Data Storage
Utilizes a custom Red-Black Tree implementation with keys formed by concatenating stock symbol and date (symbol_date). Each node stores a StockData object containing:

Date, Open, High, Low, Close, Volume, Symbol.

CSV File Import
Reads stock data from a CSV file with the following columns (header required):

text
date,open,high,low,close,volume,Name
Dates must be in YYYY-MM-DD format for correct string comparison and search operations.

Interactive Menu
The program presents a console menu with 12 options:

Search for a stock by symbol and date (exact match).
Top 5 high-volume stocks using Bubble Sort (with execution time).
Top 5 high-volume stocks using Selection Sort (with execution time).
Top 5 high-volume stocks using Quick Sort (with execution time).
Top 5 high-volume stocks using Merge Sort (with execution time).
Min / Max opening price and average volume for a given stock symbol.
Simple Moving Average (SMA) for a user-specified stock and window size (in days).
Price trend (Upward/Downward) based on 5-day vs. 20-day moving averages.
Average volume for a stock over a user-defined date range.
Top 5 highest opening price stocks on a specific date.
Top 5 highest volume stocks on a specific date.
Exit the program.
Sorting Algorithm Performance
Options 2–5 measure and display the time taken (in milliseconds) to sort the entire dataset by volume using each algorithm. This allows you to compare the efficiency of Bubble, Selection, Quick, and Merge Sort on real stock data.

Data Structures
Red-Black Tree
The primary data structure for storing and retrieving stock records.

Key: symbol + "_" + date (e.g., AAL_2013-02-08).

Value: StockData object containing all fields.
Insertion and search operations are O(log n) in the average and worst case, ensuring good performance even with large datasets.

List
After loading data, an in-order traversal of the tree populates an ArrayList<StockData> used for sorting and analysis.

Sorting Algorithms Implemented
Algorithm	Method Name	Complexity (avg)	Stable
Bubble Sort	bubbleSort()	O(n²)	Yes
Selection Sort	selectionSort()	O(n²)	No
Quick Sort	quickSort()	O(n log n)	No
Merge Sort	mergeSort()	O(n log n)	Yes
Each sort uses a Comparator to order by descending volume. Execution time is captured with System.currentTimeMillis() before and after sorting.

How to Use
Prerequisites
Java Development Kit (JDK) 8 or higher.

A CSV file containing stock data with the format described below.

Compilation
bash
javac StockAnalysis.java
Running the Program
bash
java StockAnalysis
Instructions
When prompted, enter the path to your CSV file (e.g., stocks.csv).

The program will load the data and display the main menu.

Choose an option by typing the corresponding number and pressing Enter.

Follow additional prompts (symbol, date, window size, etc.) as required.

Execution times for sorting algorithms are shown after each relevant option.

Input File Format
The CSV file must contain a header row.

Columns: date,open,high,low,close,volume,Name

Date format: YYYY-MM-DD (e.g., 2013-02-08).
Note: The sample file stocks.csv uses M/D/YYYY; please convert dates to YYYY-MM-DD before use, or modify the code to parse the existing format.

Missing numeric fields are handled (default to 0), but rows with an empty symbol are skipped.

Example
After loading a properly formatted CSV, you might see:

text
Stock Market Analysis System Menu
1. Search for a stock by symbol and date
2. Display top 5 high-volume stocks (Bubble Sort)
...
Please enter your choice:
Sample Output (Option 2 - Bubble Sort)
text
The top 5 high-volume stocks (using Bubble Sort):
Date: 2013-02-14, Open: $14.94, High: $14.96, Low: $13.16, Close: $13.99, Volume: 31879900, Symbol: AAL
Date: 2013-02-15, Open: $13.93, High: $14.61, Low: $13.93, Close: $14.5, Volume: 15628000, Symbol: AAL
Date: 2013-02-20, Open: $14.17, High: $14.26, Low: $13.15, Close: $13.33, Volume: 14725200, Symbol: AAL
Date: 2013-03-06, Open: $14.52, High: $14.68, Low: $14.25, Close: $14.57, Volume: 13243200, Symbol: AAL
Date: 2013-02-21, Open: $13.62, High: $13.95, Low: $12.9, Close: $13.37, Volume: 11922100, Symbol: AAL
Bubble Sort time: 2 milliseconds
Notes
The Red-Black tree insertion includes a fix-up method to maintain balance.

Sorting is performed on a copy of the original list to preserve the original order for subsequent operations.

Date comparisons are done lexicographically (string comparison) and therefore require the YYYY-MM-DD format to work correctly.

The program exits gracefully when option 12 is selected.

Possible Enhancements
Add support for different date formats (e.g., using SimpleDateFormat).

Implement additional comparators (e.g., by opening price, closing price).

Export sorted results to a file.

Use multithreading for sorting performance tests.

This project was developed as part of a data structures and algorithms course to demonstrate the use of Red-Black trees and sorting algorithm analysis.


