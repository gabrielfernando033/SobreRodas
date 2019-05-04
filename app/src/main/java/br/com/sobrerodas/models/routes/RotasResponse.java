package br.com.sobrerodas.models.routes;

public class RotasResponse {

    private String error_message;
    private String status;
    private Routes[] routes;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getErrorMessage() {
        return error_message;
    }

    public void setErrorMessage(String errorMessage) {
        this.error_message = errorMessage;
    }

    public Routes[] getRoutes ()
    {
        return routes;
    }

    public void setRoutes (Routes[] routes)
    {
        this.routes = routes;
    }

}