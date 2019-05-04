package br.com.sobrerodas.models.config;

import java.util.List;

public class StyleRequest {

    private String elementType;
    private List<Styler> stylers = null;
    private String featureType;

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public List<Styler> getStylers() {
        return stylers;
    }

    public void setStylers(List<Styler> stylers) {
        this.stylers = stylers;
    }

    public String getFeatureType() {
        return featureType;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

}