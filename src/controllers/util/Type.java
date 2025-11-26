package controllers.util;

public enum Type {
        TASK ("CONTROLLERS.MODEL.TASK"),
        EPIC ("CONTROLLERS.MODEL.EPIC"),
        SUBTASK ("CONTROLLERS.MODEL.SUBTASK");

        private String title;

        Type(String title) {
                this.title = title;
        }

        public static Type fromString(String value) {
                if (value == null) return null;

                try {
                        return Type.valueOf(value.toUpperCase().trim());
                } catch (IllegalArgumentException e) {
                        return null;
                }
        }
}
