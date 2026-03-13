
// ID: 20760160
// Project 3:
// Description:
//Stock Market Analysis System. Develop a Java-based stock market analysis system that processes historical stock data using red-black trees for efficient data organization and retrieval. The system will analyze various sorting algorithms’ performance while handling large datasets of stock information.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StockAnalysis {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    //Class to store the data attributes of each unique stock
    static class StockData {
        String date;
        double open, high, low, close;
        long volume;
        String symbol;
        //Stores the data attributes provided
        StockData(String date, double open, double high, double low, double close, long volume, String symbol) {
            this.date = date;
            this.open = open;
            this.high = high;
            this.low = low;
            this.close = close;
            this.volume = volume;
            this.symbol = symbol;
        }
        //toString method to print out the data attributes of the stock
        @Override
        public String toString() {
            return "Date: " + date + ", Open: $" + open + ", High: $" + high + ", Low: $" + low +
                    ", Close: $" + close + ", Volume: " + volume + ", Symbol: " + symbol;
        }
    }
    //Implementation of RedBlackTree provided in class
    private class Node {
        String key;
        StockData data;
        Node left, right, parent;
        boolean color;

        Node(String key, StockData data) {
            this.key = key;
            this.data = data;
            this.color = RED;
        }
    }

    private Node root;
    //Method to insert new nodes into the RedBlackTree
    public void insert(String key, StockData data) {
        Node newNode = new Node(key, data);
        Node parent = null;
        Node current = root;

        while (current != null) {
            parent = current;
            if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else if (key.compareTo(current.key) > 0) {
                current = current.right;
            } else {
                return;
            }
        }

        newNode.parent = parent;
        if (parent == null) {
            root = newNode;
        } else if (key.compareTo(parent.key) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        fixInsert(newNode);
    }
    //Method to fix the RedBlackTree after new insertions have been added as to remain a valid RedBlackTree.
    private void fixInsert(Node node) {
        while (node != root && node.parent.color == RED) {
            if (node.parent == node.parent.parent.left) {
                Node uncle = node.parent.parent.right;
                if (uncle != null && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rightRotate(node.parent.parent);
                }
            } else {
                Node uncle = node.parent.parent.left;
                if (uncle != null && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    leftRotate(node.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }
    
    private void leftRotate(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;

        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.parent = node.parent;

        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }

        rightChild.left = node;
        node.parent = rightChild;
    }

    private void rightRotate(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;

        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.parent = node.parent;

        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.left) {
            node.parent.left = leftChild;
        } else {
            node.parent.right = leftChild;
        }

        leftChild.right = node;
        node.parent = leftChild;
    }
    //Method to search for a stock by symbol and date
    public StockData searchStock(String symbol, String date) {
        //key stores a unique key made by joining the symbol and date of the stock with a "_" character seperating them/
        String key = symbol + "_" + date;
        Node current = root;

        while (current != null) {
            if (key.equals(current.key)) {
                return current.data;
            } else if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }
    /** Reads .csv file in and stores the data attributes of the stocks
     * By splitting the data with "," the different data attributes are stored
     * Also checks if the inputs are empty, or row is invalid, (less than 7 fields or no symbol)
     */
    public void readFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            if (fields.length < 7) {
                System.err.println("Skipping invalid row (as there is a wrong number of columns): " + line);
                continue;
            }
            double open = 0.0;
            double high = 0.0;
            double low = 0.0;
            double close = 0.0;
            long volume = 0;
            String symbol = fields[6].trim();
            String date = fields[0];
            if (!fields[1].isEmpty()) {
            open = Double.parseDouble(fields[1]);}
            if (!fields[2].isEmpty()) {
            high = Double.parseDouble(fields[2]);}
            if (!fields[3].isEmpty()) {
            low = Double.parseDouble(fields[3]);}
            if (!fields[4].isEmpty()) {
            close = Double.parseDouble(fields[4]);}
            if (!fields[1].isEmpty()) {
            volume = Long.parseLong(fields[5]);}
            if (!fields[1].isEmpty()) {
            symbol = fields[6].trim();}
            if (symbol.isEmpty()) {
                System.err.println("Skipping this line with a missing symbol: "+ line);
                continue;
            }

            StockData data = new StockData(date, open, high, low, close, volume, symbol);
            insert(symbol + "_" + date, data);
        }
        reader.close();
    }
    /** Collects all the stock data and stores it in a list using inOrderTraversal */
    public List<StockData> storeData() {
        List<StockData> stockList = new ArrayList<>();
        inOrderTraversal(root, stockList);
        return stockList;
    }

    private void inOrderTraversal(Node node, List<StockData> list) {
        if (node != null) {
            inOrderTraversal(node.left, list);
            list.add(node.data);
            inOrderTraversal(node.right, list);
        }
    }
    // Implementation of bubblesort sorting algorithm
    private void bubbleSort(List<StockData> list, Comparator<StockData> comparator) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    StockData temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
    // Implementation of selectionSort sorting algorithm
    private void selectionSort(List<StockData> list, Comparator<StockData> comparator) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int smallIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(list.get(j), list.get(smallIndex)) < 0) {
                    smallIndex = j;
                }
            }
            StockData temp = list.get(i);
            list.set(i, list.get(smallIndex));
            list.set(smallIndex, temp);
        }
    }
    // Implementation of quickSort sorting algorithm
    private void quickSort(List<StockData> list, int low, int high, Comparator<StockData> comparator) {
        if (low < high) {
            int pi = partition(list, low, high, comparator);
            quickSort(list, low, pi - 1, comparator);
            quickSort(list, pi + 1, high, comparator);
        }
    }

    private int partition(List<StockData> list, int low, int high, Comparator<StockData> comparator) {
        StockData pivot = list.get(high);
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                StockData temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        StockData temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }

            // Merge Sort Implementation
            public void mergeSort(List<StockData> list, Comparator<StockData> comparator) {
                if (list.size() <= 1) {
                    return; 
                }
            
                int mid = list.size() / 2;
            
                List<StockData> left = new ArrayList<>(list.subList(0, mid));
                List<StockData> right = new ArrayList<>(list.subList(mid, list.size()));
            
                mergeSort(left, comparator);
                mergeSort(right, comparator);
            
                // Merge the sorted halves back together
                merge(list, left, right, comparator);
            }
            
            private void merge(List<StockData> list, List<StockData> left, List<StockData> right, Comparator<StockData> comparator) {
                int i = 0, j = 0, k = 0;
            
                while (i < left.size() && j < right.size()) {
                    if (comparator.compare(left.get(i), right.get(j)) <= 0) { // Use <= for stable sort
                        list.set(k++, left.get(i++));
                    } else {
                        list.set(k++, right.get(j++));
                    }
                }
            
                while (i < left.size()) {
                    list.set(k++, left.get(i++));
                }
            
                while (j < right.size()) {
                    list.set(k++, right.get(j++));
                }
            }
            
    //Main method to run the program and display the menu with all the features and implementations
    public static void main(String[] args) {
        StockAnalysis analysis = new StockAnalysis();
        Scanner scanner = new Scanner(System.in);
    
        try {
            //Read in CSV file
            System.out.print("Please enter the path to the CSV file: ");
            String filePath = scanner.nextLine();
            analysis.readFile(filePath);
            System.out.println("The Stock data was loaded successfully.");
    
            List<StockData> listOfStocks = analysis.storeData();
            //Menu to choose options to analyze the stocks
            while (true) {
                System.out.println("\nStock Market Analysis System Menu");
                System.out.println("Please type 1 to search for a stock by symbol and date");
                System.out.println("Please type 2 to display the top 5 high-volume stocks (using Bubble Sort)");
                System.out.println("Please type 3 to display the top 5 high-volume stocks (using Selection Sort)");
                System.out.println("Please type 4 to display the top 5 high-volume stocks (using Quick Sort)");
                System.out.println("Please tpye 5 to display the top 5 high-volume stocks (using Merge Sort)");
                System.out.println("Please type 6 to display the minimum and maximum opening price, and the average volume for a stock symbol");
                System.out.println("Please type 7 to display the simple moving average for a stock symbol");
                System.out.println("Please type 8 to display price trend for a stock symbol");
                System.out.println("Please type 9 to display average volume for a stock in a date range");
                System.out.println("Please type 10 to display the top 5 highest opening price stocks for a user-specified date");
                System.out.println("Please type 11 to display top 5 highest volume stocks for a user-specified date");
                System.out.println("Please type 12 to exit");
                System.out.print("Please enter your choice: ");
    
                int choice = scanner.nextInt();
                scanner.nextLine(); 
    
                switch (choice) {
                    case 1: // Search for a stock
                        System.out.print("Please enter the stock symbol: ");
                        String symbol = scanner.nextLine();
                        System.out.print("Please enter date in the following format: (YYYY-MM-DD): ");
                        String date = scanner.nextLine();
    
                        StockData result = analysis.searchStock(symbol, date);
                        if (result != null) {
                            System.out.println(result);
                        } else {
                            System.out.println("Error: The stock was not found.");
                        }
                        break;
                        /**Captures the start and end time to calculate duration of sorts for time analysis */
                    case 2: // Top 5 high-volume stocks (using Bubble Sort)
                        List<StockData> bubbleSortList = new ArrayList<>(listOfStocks);
                        long startTimeBubbleSort = System.currentTimeMillis(); // Captures start time
                        analysis.bubbleSort(bubbleSortList, new Comparator<StockData>() {
                            @Override
                            public int compare(StockData o1, StockData o2) {
                                return Long.compare(o2.volume, o1.volume); // Sorts by descending volume
                            }
                        });
                        
                        long endTimeBubbleSort = System.currentTimeMillis(); // Captures finish time
                        long durationBubbleSort = endTimeBubbleSort - startTimeBubbleSort; // Calculate duration
                        System.out.println("The top 5 high-volume stocks (using Bubble Sort):");
                        int count = 0;
                        for (StockData data : bubbleSortList) {
                            if (count >= 5) break;
                            System.out.println(data);
                            count++;
                        }
                        System.out.println("Bubble Sort time: " + durationBubbleSort + " milliseconds");
                        break;
    
                    case 3: // Top 5 high-volume stocks (using Selection Sort)
                        List<StockData> selectionSortList = new ArrayList<>(listOfStocks);
                        long startTimeSelectionSort = System.currentTimeMillis(); 
                        analysis.selectionSort(selectionSortList, new Comparator<StockData>() {
                            @Override
                            public int compare(StockData o1, StockData o2) {
                                return Long.compare(o2.volume, o1.volume); 
                            }
                        });
                        long endTimeSelectionSort = System.currentTimeMillis(); 
                        long durationSelectionSort = endTimeSelectionSort - startTimeSelectionSort; 
                        System.out.println("The top 5 high-volume stocks (using Selection Sort):");
                        count = 0;
                        for (StockData data : selectionSortList) {
                            if (count >= 5) break;
                            System.out.println(data);
                            count++;
                        }
                        System.out.println("Selection Sort time: " + durationSelectionSort + " milliseconds");
                        break;
    
                    case 4: // Top 5 high-volume stocks (using Quick Sort)
                        List<StockData> quickSortList = new ArrayList<>(listOfStocks);
                        long startTimeQuickSort = System.currentTimeMillis(); 
                        analysis.quickSort(quickSortList, 0, quickSortList.size() - 1, new Comparator<StockData>() {
                            @Override
                            public int compare(StockData o1, StockData o2) {
                                return Long.compare(o2.volume, o1.volume); 
                            }
                        });
                        long endTimeQuickSort = System.currentTimeMillis(); 
                        long durationQuickSort = endTimeQuickSort - startTimeQuickSort; 
                        System.out.println("The top 5 high-volume stocks (using Quick Sort):");
                        count = 0;
                        for (StockData data : quickSortList) {
                            if (count >= 5) break;
                            System.out.println(data);
                            count++;
                        }
                        System.out.println("Quick Sort time: " + durationQuickSort + " milliseconds");
                        break;
    
                    case 5: // Top 5 high-volume stocks (using Merge Sort)
                        List<StockData> mergeSortList = new ArrayList<>(listOfStocks);
                        long startTimeMergeSort = System.currentTimeMillis(); 
                        analysis.mergeSort(mergeSortList, new Comparator<StockData>() {
                            @Override
                            public int compare(StockData o1, StockData o2) {
                                return Long.compare(o2.volume, o1.volume);
                            }
                        });
                        long endTimeMergeSort = System.currentTimeMillis(); 
                        long durationMergeSort = endTimeMergeSort - startTimeMergeSort;
                        System.out.println("The top 5 high-volume stocks (using Merge Sort):");
                        count = 0;
                        for (StockData data : mergeSortList) {
                            if (count >= 5) break;
                            System.out.println(data);
                            count++;
                        }
                        System.out.println("Merge Sort time: " + durationMergeSort + " milliseconds");
                        break;
    
                    case 6: // Displays the minimum and maximum opening price, average volume
                        System.out.print("Please enter stock symbol: ");
                        String target = scanner.nextLine();
                        /**listOfSymbol stores the list of all symbols matching the entered symbol */
                        List<StockData> listOfSymbol = new ArrayList<>();
                        for (StockData data : listOfStocks) {
                            if (data.symbol.equalsIgnoreCase(target)) {
                                listOfSymbol.add(data);
                            }
                        }
    
                        if (listOfSymbol.isEmpty()) {
                            System.out.println("Error: No data found for the specified stock symbol.");
                        } else {
                            double minOpen = Double.MAX_VALUE;
                            double maxOpen = Double.MIN_VALUE;
                            double totalVolume = 0;
                            for (StockData data : listOfSymbol) {
                                if (data.open < minOpen) {
                                    minOpen = data.open;
                                }
                                if (data.open > maxOpen) {
                                    maxOpen = data.open;
                                }
                                totalVolume += data.volume;
                            }
                            double avgVolume = totalVolume / listOfSymbol.size();
                            System.out.printf("Statistics for %s: Min Price: $%.2f, Max Price: $%.2f, Avg Volume: %.0f\n",
                                    target, minOpen, maxOpen, avgVolume);
                        }
                        break;
    
                    case 7: // Simple moving average
                        System.out.print("Please enter stock symbol: ");
                        String inputSymbol = scanner.nextLine();
                        System.out.print("Please enter moving average window (in days): ");
                        int window = scanner.nextInt();
    
                        List<StockData> symbolData = new ArrayList<>();
                        for (StockData data : listOfStocks) {
                            if (data.symbol.equalsIgnoreCase(inputSymbol)) {
                                symbolData.add(data);
                            }
                        }
                        symbolData.sort(new Comparator<StockData>() {
                            @Override
                            public int compare(StockData o1, StockData o2) {
                                return o1.date.compareTo(o2.date);
                            }
                        });
                        //Checks to make sure there is enough data for the number of days to calculate the moving average
                        if (symbolData.size() < window) {
                            System.out.println("Error: Insufficient data for the specified window.");
                        } else {
                            System.out.println("Simple Moving Averages:");
                            for (int i = 0; i <= symbolData.size() - window; i++) {
                                double sum = 0;
                                for (int j = 0; j < window; j++) {
                                    sum += symbolData.get(i + j).close;
                                }
                                double average = sum / window;
                                System.out.printf("Ending date on %s: %.2f\n", symbolData.get(i + window - 1).date, average);
                            }
                        }
                        break;
    
                    case 8: // Price trend
                        System.out.print("Please enter stock symbol: ");
                        String trendSymbol = scanner.nextLine();
    
                        List<StockData> trendData = new ArrayList<>();
                        for (StockData data : listOfStocks) {
                            if (data.symbol.equalsIgnoreCase(trendSymbol)) {
                                trendData.add(data);
                            }
                        }
                        trendData.sort(new Comparator<StockData>() {
                            @Override
                            public int compare(StockData o1, StockData o2) {
                                return o1.date.compareTo(o2.date);
                            }
                        });
                        //Checks there is atleast data for last 20 days
                        if (trendData.size() < 20) {
                            System.out.println("Error: Insufficient data for trend analysis.");
                        } else {
                            System.out.println("Price Trend:");
                            for (int i = 20; i < trendData.size(); i++) {
                                double shortTermAvg = 0;
                                //Calculates data for last 5 days
                                for (int j = i - 5; j < i; j++) {
                                    shortTermAvg += trendData.get(j).close;
                                }
                                shortTermAvg /= 5;
    
                                double longTermAvg = 0;
                                for (int j = i - 20; j < i; j++) {
                                    longTermAvg += trendData.get(j).close;
                                }
                                longTermAvg /= 20;
    
                                String trend = shortTermAvg > longTermAvg ? "Upward" : "Downward";
                                System.out.printf("Date: %s, Trend: %s\n", trendData.get(i).date, trend);
                            }
                        }
                        break;
    
                    case 9: // Average volume for a stock in a date range
                        System.out.print("Enter stock symbol: ");
                        String volumeSymbol = scanner.nextLine();
                        System.out.print("Enter start date (YYYY-MM-DD): ");
                        String startDate = scanner.nextLine();
                        System.out.print("Enter end date (YYYY-MM-DD): ");
                        String endDate = scanner.nextLine();
    
                        List<StockData> volumeData = new ArrayList<>();
                        for (StockData data : listOfStocks) {
                            if (data.symbol.equalsIgnoreCase(volumeSymbol) &&
                                data.date.compareTo(startDate) >= 0 &&
                                data.date.compareTo(endDate) <= 0) {
                                volumeData.add(data);
                            }
                        }
    
                        if (volumeData.isEmpty()) {
                            System.out.println("No data available for the specified date range.");
                        } else {
                            double totalVolumeInRange = 0;
                            for (StockData data : volumeData) {
                                totalVolumeInRange += data.volume;
                            }
                            double avgVolumeRange = totalVolumeInRange / volumeData.size();
                            System.out.printf("Average volume for %s from %s to %s: %.0f\n",
                                    volumeSymbol, startDate, endDate, avgVolumeRange);
                        }
                        break;
    
                    case 10: // Display top 5 highest opening price stocks for a user-specified date
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        String userDate = scanner.nextLine();
    
                        List<StockData> priceSpecificList = new ArrayList<>();
                        for (StockData data : listOfStocks) {
                            if (data.date.equals(userDate)) {
                                priceSpecificList.add(data);
                            }
                        }
    
                        priceSpecificList.sort(new Comparator<StockData>() {
                            @Override
                            public int compare(StockData o1, StockData o2) {
                                return Double.compare(o2.open, o1.open); // Sort by descending opening price
                            }
                        });
    
                        System.out.println("Top 5 highest opening price stocks for " + userDate + ":");
                        int countDate = 0;
                        for (StockData data : priceSpecificList) {
                            if (countDate >= 5) break;
                            System.out.println(data);
                            countDate++;
                        }
                        break;
    
                    case 11: // Display top 5 highest volume stocks for a user-specified date
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        String volumeDate = scanner.nextLine();
    
                        List<StockData> volumeSpecificList = new ArrayList<>();
                        for (StockData data : listOfStocks) {
                            if (data.date.equals(volumeDate)) {
                                volumeSpecificList.add(data);
                            }
                        }
    
                        volumeSpecificList.sort(new Comparator<StockData>() {
                            @Override
                            public int compare(StockData o1, StockData o2) {
                                return Long.compare(o2.volume, o1.volume); // Sort by descending volume
                            }
                        });
    
                        System.out.println("Top 5 highest volume stocks for " + volumeDate + ":");
                        count = 0;
                        for (StockData data : volumeSpecificList) {
                            if (count >= 5) break;
                            System.out.println(data);
                            count++;
                        }
                        break;
                    case 12: // Exit
                        System.out.println("Exiting the program.");
                        return;
    
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}