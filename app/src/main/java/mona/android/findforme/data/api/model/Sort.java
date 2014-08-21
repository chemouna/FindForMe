package mona.android.findforme.data.api.model;

/**
 * Created by cheikhna on 17/08/2014.
 */
public enum Sort {
    POPULAR("popular"),
    TIME("time");

    private final String value;

    Sort(String value) {
        this.value = value;
    }

    @Override public String toString() {
        return value;
    }
}
