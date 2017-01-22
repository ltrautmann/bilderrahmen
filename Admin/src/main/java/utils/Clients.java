package utils;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by robin on 22.01.17.
 */
public class Clients {

    private String id;
    private String name;
    private static int number = 0;
    private ArrayList<Gruppe> arrayListGroups;

    public static int getNumber() {
        return number;
    }


    public Clients() {
        number = number++;
        arrayListGroups = new ArrayList<Gruppe>();
    }


    public Gruppe removeGruppe(String grupprnName) {
        Gruppe v;
        Iterator i = arrayListGroups.iterator();
        while (i.hasNext()) {
            v = (Gruppe) i.next();
            if (v.getGruppenName().equals(grupprnName)) {
                i.remove();
                return v;
            }

        }
        System.out.println("Gruppe nicht gefunden");
        return null;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
