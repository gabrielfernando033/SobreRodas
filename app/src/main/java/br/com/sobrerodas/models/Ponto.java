package br.com.sobrerodas.models;

public class Ponto {

    private String pontoId;
    private String latitude;
    private String longitude;
    private String categoria;
    private boolean temporario;
    private String descricao;
    private String idFacebookUser;

    public Ponto() {

    }

    public String getPontoId() {
        return pontoId;
    }

    public void setPontoId(String pontoId) {
        this.pontoId = pontoId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean getTemporario() {
        return temporario;
    }

    public void setTemporario(boolean temporario) {
        this.temporario = temporario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdFacebookUser() {
        return idFacebookUser;
    }

    public void setIdFacebookUser(String idFacebookUser) {
        this.idFacebookUser = idFacebookUser;
    }

}