

import java.util.Comparator;

public class NameAndDistComarator implements Comparator<City> {


    @Override
    public int compare(City o1,City o2) {
        if ((o1.name.compareTo(o2.name))!=0) return o1.name.compareTo(o2.name);
        else return o1.district.compareTo(o2.district);
    }
}


