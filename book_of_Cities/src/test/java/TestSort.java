import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestSort {
    @Test(expected = AssertionError.class)
    public void test2()  {
        List<City> testList = new ArrayList<>();
        testList.add(new City(13,"Алапаевск", "Свердловская область", "Уральский" ,38198,1639));

        testList.add(new City(1,"Абаза", "Хакасия", "Сибирский",17111,1867));

        testList.add(new City(587,"Минеральные Воды","Ставропольский край","Северо-Кавказский",76715,1878));

        Collections.sort(testList, new NameAndDistComarator());
        for(City y: testList) {
            System.out.println(y);
        }
       assert ((testList.get(0).getName().equals(new City(1,"Абаза", "Хакасия", "Сибирский",17111,1867))));
        assert ((testList.get(1).getName().equals(new City(13,"Алапаевск", "Свердловская область", "Уральский" ,38198,1639))));
        assert ((testList.get(2).getName().equals(new City(587,"Минеральные Воды","Ставропольский край","Северо-Кавказский",76715,1878))));


    }




    }
