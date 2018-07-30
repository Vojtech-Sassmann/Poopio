package cz.tyckouni.poopio.base.entities;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Poop implements Serializable {
    private static final long serialVersionUID = 42L;
    private String uid;
    private int consistency;
    private int size;
    private String type;
    private int color;
    private String date;

    public Poop() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getConsistency() {
        return consistency;
    }

    public void setConsistency(int consistency) {
        this.consistency = consistency;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String dateAndTime) {
        this.date = dateAndTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poop poop = (Poop) o;
        return uid.equals(poop.uid);
    }

    @Override
    public int hashCode() {

        return uid.hashCode();
    }

    @Override
    public String toString() {
        return "Poop{" +
                "uid='" + uid + '\'' +
                ", consistency=" + consistency +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", color=" + color +
                ", date='" + date + '\'' +
                '}';
    }
}
