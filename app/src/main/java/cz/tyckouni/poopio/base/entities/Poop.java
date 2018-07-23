package cz.tyckouni.poopio.base.entities;

import java.util.Calendar;
import java.util.Date;

public class Poop {
    private Long id;
    private int consistency;
    private int size;
    private String type;
    private String color;
    private Calendar calendar;

    public Poop(Long id, int consistency, int size) {
        this(id, consistency, size, "Snake", "#441035");
    }

    public Poop(Long id, int consistency, int size, String type, String color) {
        this.id = id;
        this.consistency = consistency;
        this.size = size;
        this.type = type;
        this.color = color;
        this.calendar = Calendar.getInstance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poop poop = (Poop) o;
        return id.equals(poop.id);
    }

    @Override
    public int hashCode() {

        return id.hashCode();
    }
}
