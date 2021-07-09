package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SqlLoader {

    public static String listToString(List<String> data){
        StringBuilder builder = new StringBuilder();
        data.forEach(builder::append);
        String str = builder.toString();

        return str;
    }

    public static List<String> load(String path){
        String filePath =  path;
        File file = new File(filePath);
        List<String> data = new ArrayList<>();
        try(Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                data.add(reader.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }
}
