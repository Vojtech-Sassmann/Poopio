package cz.tyckouni.poopio.base.entities;

public enum PoopAttributeType {

    COLOR(ValueType.TEXT),
    SIZE(ValueType.NUMBER),
    DEER_COUNT(ValueType.PERCENTAGE),
    PARTS_COUNT(ValueType.NUMBER);

    private ValueType valueType;

    PoopAttributeType(ValueType valueType) {
        this.valueType = valueType;
    }

    public ValueType getValueType() {
        return valueType;
    }
}
