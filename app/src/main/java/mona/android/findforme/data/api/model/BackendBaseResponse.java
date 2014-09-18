package mona.android.findforme.data.api.model;

/**
 * Created by cheikhna on 17/08/2014.
 */
public abstract class BackendBaseResponse {
    public final int status;
    public final boolean success;

    public BackendBaseResponse(int status, boolean success) {
        this.status = status;
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return success;
    }
}
