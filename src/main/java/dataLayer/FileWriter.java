package dataLayer;

import java.util.Formatter;

public class FileWriter {

    private Formatter formatter;

    public void openFile() {
        try {
            formatter = new Formatter("bill.txt");
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("You have an error");
        }
    }

    public void addRecords(Object object) {
        formatter.format(String.valueOf(object));
    }

    public void closeFile() {
        formatter.close();
    }

}
