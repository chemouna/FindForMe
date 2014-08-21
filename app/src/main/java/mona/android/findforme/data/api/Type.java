package mona.android.findforme.data.api;


public enum Type {
    CLOTHING("clothing"),
    SPORTS("sports"),
    BEAUTY("BEAUTY"),
    Electronics("ELECTRONICS"),
    TOYS("TOYS"),
    OTHER("OTHER");

    public final String value;

    Type(String value) {
        this.value = value;
    }

    @Override public String toString() {
        return value;
    }
}

