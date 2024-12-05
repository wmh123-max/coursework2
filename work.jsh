import java.util.ArrayList;
import java.io.*;


String BASE_PATH = "D:/coursework2/coursework2_files/"; 

int cardCompare(String card1, String card2) {
    String suits = "HCDS";  
    int number1 = Integer.parseInt(card1.substring(0, card1.length() - 1));
    char suit1 = card1.charAt(card1.length() - 1);
    int number2 = Integer.parseInt(card2.substring(0, card2.length() - 1));
    char suit2 = card2.charAt(card2.length() - 1);
    if (suits.indexOf(suit1) != suits.indexOf(suit2)) {
        return Integer.compare(suits.indexOf(suit1), suits.indexOf(suit2));
    }
    return Integer.compare(number1, number2);
}

import java.util.ArrayList;

void bubbleSort(ArrayList<String> list) {
    int n = list.size();
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (cardCompare(list.get(j), list.get(j + 1)) > 0) {
                String temp = list.get(j);
                list.set(j, list.get(j + 1));
                list.set(j + 1, temp);
            }
        }
    }
	 System.out.println("排序后的列表: " + list);
}


ArrayList<String> mergeSort(ArrayList<String> list) {
    if (list.size() <= 1) {
        return list;
    }
    int mid = list.size() / 2;
    ArrayList<String> left = new ArrayList<>(list.subList(0, mid));
    ArrayList<String> right = new ArrayList<>(list.subList(mid, list.size()));
    return merge(mergeSort(left), mergeSort(right));
}

ArrayList<String> merge(ArrayList<String> left, ArrayList<String> right) {
    ArrayList<String> merged = new ArrayList<>();
    int i = 0, j = 0;
    while (i < left.size() && j < right.size()) {
        if (cardCompare(left.get(i), right.get(j)) <= 0) {
            merged.add(left.get(i++));
        } else {
            merged.add(right.get(j++));
        }
    }
    while (i < left.size()) merged.add(left.get(i++));
    while (j < right.size()) merged.add(right.get(j++));
    return merged;
}

long measureBubbleSort(String filename) throws IOException {
    ArrayList<String> list = readCardsFromFile(filename);
    long startTime = System.currentTimeMillis();
    bubbleSort(list);
    return System.currentTimeMillis() - startTime;
}

long measureMergeSort(String filename) throws IOException {
    ArrayList<String> list = readCardsFromFile(filename);
    long startTime = System.currentTimeMillis();
    list = mergeSort(list);
    return System.currentTimeMillis() - startTime;
}

ArrayList<String> readCardsFromFile(String filename) throws IOException {
    ArrayList<String> cards = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(BASE_PATH + filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            cards.add(line.trim());
        }
    }
    return cards;
}

void sortComparison(String[] filenames) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("sortComparison.csv"))) {
        bw.write(","); 
        for (String filename : filenames) {
            int cardCount = readCardsFromFile(filename).size();  
            bw.write(cardCount + ",");
        }
        bw.newLine();
        
        bw.write("bubbleSort,");
        for (String filename : filenames) {
            long bubbleSortTime = measureBubbleSort(filename);  
            bw.write(bubbleSortTime + ",");
        }
        bw.newLine();
        
        bw.write("mergeSort,");
        for (String filename : filenames) {
            long mergeSortTime = measureMergeSort(filename); 
            bw.write(mergeSortTime + ",");
        }
        bw.newLine();
        
        System.out.println("sortComparison.csv 文件已生成！");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

