

import java.io.FileNotFoundException;
import java.util.*;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        ReadFromFile read = new ReadFromFile();




        ArrayList<City> listOfCity = new ArrayList<>(read.readFromFile("/Users/u19305354/Desktop/book_of_Cities/src/main/resources/Cities with ID 2.0.txt"));
        for (City city : listOfCity) {
            System.out.println(city);
        }
        System.out.println("========================");


        //public static  void sortForName(){}


        ArrayList<City> listOfCity2 = new ArrayList<>(listOfCity);


       Collections.sort(listOfCity2, new NameComparator());
               for (City city1 : listOfCity2) {
            System.out.println(city1);
        }
        System.out.println("========================");


        Collections.sort(listOfCity2, new NameAndDistComarator());
        for (City city1 : listOfCity2) {
            System.out.println(city1);
        }
        System.out.println("========================");

        City[] arr = new City[listOfCity.size()];
        listOfCity.toArray(arr);
        int index =0;
        long result = arr[0].getPopulation();
        for (int i = 1; i < listOfCity.size() - 1; i++) {
            if (arr[i].getPopulation() > result) {
                result = arr[i].getPopulation();
                index = i;

            }
        } System.out.println("[" + index+1 + "] =" + result);

        System.out.println("==============================");
        Map <String,Integer>  city = new HashMap<>();
        for (City c: listOfCity) {
            city.put(c.getRegion(), city.getOrDefault(c.getRegion(), 0) + 1);
        }
        for (Map.Entry<String,Integer> entry : city.entrySet()){
            System.out.println(entry.getKey()+ " - " + entry.getValue());


        }
    }
}




