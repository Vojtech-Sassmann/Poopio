package cz.tyckouni.poopio.base.entities;

import java.util.List;

public class NewPoop {
    private static final long serialVersionUID = 42L;

    private PoopType poopType;
    private List<PoopAttribute> attributes;
    private String date;
    private String time;
    private String uid;

    public PoopType getPoopType() {
        return poopType;
    }

    public void setPoopType(PoopType poopType) {
        this.poopType = poopType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewPoop newPoop = (NewPoop) o;

        return uid != null ? uid.equals(newPoop.uid) : newPoop.uid == null;
    }

    @Override
    public int hashCode() {
        return uid != null ? uid.hashCode() : 0;
    }
}
