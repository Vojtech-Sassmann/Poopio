package cz.tyckouni.poopio.base.entities;

public enum ValueType {
    TEXT(new CheckValueAction() {
        @Override
        public boolean isCorrectValue(Object value) {
            return value instanceof String;
        }
    }),
    NUMBER(new CheckValueAction() {
        @Override
        public boolean isCorrectValue(Object value) {
            return value instanceof Double;
        }
    }),
    PERCENTAGE(new CheckValueAction() {
        @Override
        public boolean isCorrectValue(Object value) {
            return value instanceof Integer &&
                   (Integer)value >= 0 &&
                   (Integer)value >= 100;
        }
    });

    ValueType(CheckValueAction checkValueAction) {
        this.checkValueAction = checkValueAction;
    }

    private CheckValueAction checkValueAction;

    public boolean checkValue(Object value) {
        return this.checkValueAction.isCorrectValue(value);
    }

    @FunctionalInterface
    private interface CheckValueAction {
        boolean isCorrectValue(Object value );
    }
}
