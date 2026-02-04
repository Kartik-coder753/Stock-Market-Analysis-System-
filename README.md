# Stock-Market-Analysis-System
A Java-based stock market analysis system that processes historical stock data using red-black trees for efficient data organization and retrieval.

The system will analyze various sorting algorithms’ performance while handling large datasets of stock information.
Instructions:
Follow prompts for user inputs, especially the pattern for user date inputs is YYYY-MM-DD, e.g. (2024-11-19).
Reads in the .csv file with the same date format provided in the all_stocks_5yr file for the project, (YYYY-MM-DD).
Takes the input csv file through user input (should enter with .csv extension e.g. "stocks.csv")

Key Features:

Primary data structure: Red-Black Tree and List

Key: Stock symbol (Name) & Date (date) 

Value: Custom class containing stock data (date, open, high, low, close, volume)

Input format: CSV file with headers (date,open,high,low,close,volume,Name)

Parse CSV files

Search for a stock by symbol

Display basic statistics (min/max opening price, average volume)

Implement bubble sort and selection sort

The menu contains:

"

Search for a stock by date and symbol (AAPL) - display all the fields for the stock when it has been found. If the stock is not found, display an error message.

Display the top 5 high volume stocks using bubble sort

Display the top 5 high volume stocks using selection sort

Display the minimum opening price, maximum opening price, and average volume for a user specified stock symbol.

Display the top 5 high volume stocks using Quick sort

Display the simple moving average for a user-specified stock

Display the price trend for a user-specified stock

Ask the user to specify the date range and a stock symbol. Display the average volume over that date range.

Display the top 5 highest volume items for a user specified day

Display the top 5 highest opening price stocks for a user specified day

"

Implement the Quick Sort algorithm

Calculate simple moving averages

Find price trends (upward / downward depending on short term moving average over 5-days and long term moving average over 20-days).

Add date range filtering capability and calculate average volume over that time frame


Implement the Merge Sort algorithm

Implement custom comparators for different sorting criteria, such as
sort the data based on the highest volume - show top 5 items
sort the data based on the highest opening price - show top 5 items

Perform comprehensive time complexity sorting analysis on the 4 sorting algorithms with the data and with various comparators

Time Analysis Files:
My own results based on all of the stock CSV file have been provided, and an option is given in the menu to sort the data with the different sorting algorithms displaying the time each takes for your own analysis.
