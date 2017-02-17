package utils;

import utils.interfaces.Bild;

/**
 * Created by Robin on 10.02.2017.
 */
public class BildSettings implements Bild {
    private int dauerInSec;
    private String location;

    public BildSettings(String location, int dauerInSec) {
        this.location = location;
        this.dauerInSec = dauerInSec;
    }

    @Override
    public String toString() {
        return getName();
    }

    public int getDauerInSec() {
        return dauerInSec;
    }

    public void setDauerInSec(int dauerInSec) {
        this.dauerInSec = dauerInSec;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BildSettings that = (BildSettings) o;

      //  if (dauerInSec != that.dauerInSec) return false;
        return location.equals(that.location);
    }

    @Override
    public int hashCode() {
        int result = dauerInSec;
        result = 31 * result + location.hashCode();
        return result;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getName() {
        return location.substring(location.lastIndexOf('/')+1, location.length());
    }
}
