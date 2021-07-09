

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFromFile {

    public ArrayList<City> readFromFile(String fileName) throws FileNotFoundException {
        List<City> cityList= new ArrayList<>();
        Scanner fileScanner = new Scanner(new FileReader(fileName));
        while (fileScanner.hasNext()) {
            cityList.add(parseToobj(fileScanner.nextLine()));


        }
        fileScanner.close();
        return (ArrayList<City>) cityList;


    }
    public static City parseToobj(String s){
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter(";");
        int id = scanner.nextInt();
        String name = scanner.next();
        String region =scanner.next();
        String district = scanner.next();
        long population = scanner.nextLong();
        int foundation=scanner.nextInt();
        return new City(id, name, region, district,  population, foundation);
    }
}
