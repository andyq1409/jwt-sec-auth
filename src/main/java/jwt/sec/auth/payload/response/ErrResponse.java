package jwt.sec.auth.payload.response;

public class ErrResponse {
    private int status;
    private String errormessages;

    public ErrResponse(int status, String errormessages) {
        this.status = status;
        this.errormessages = errormessages;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrormessages() {
        return errormessages;
    }

    public void setErrormessages(String errormessages) {
        this.errormessages = errormessages;
    }


}
