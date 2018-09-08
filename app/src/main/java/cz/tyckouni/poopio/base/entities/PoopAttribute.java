package cz.tyckouni.poopio.base.entities;

public class PoopAttribute {

    private PoopAttributeType poopAttributeType;
    private Object value;

    public PoopAttribute(PoopAttributeType poopAttributeType, Object value) {
        this.poopAttributeType = poopAttributeType;
        this.value = value;
    }

    public PoopAttributeType getPoopAttributeType() {
        return poopAttributeType;
    }

    public void setPoopAttributeType(PoopAttributeType poopAttributeType) {
        this.poopAttributeType = poopAttributeType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PoopAttribute that = (PoopAttribute) o;
        return poopAttributeType == that.poopAttributeType &&
                value.equals(that.value);
    }

    @Override
    public int hashCode() {

        return poopAttributeType.hashCode() * 11 +
               value.hashCode();
    }
}
