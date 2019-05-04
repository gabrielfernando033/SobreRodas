package com.sobrerodas.utils;

import br.com.sobrerodas.models.config.StyleRequest;
import br.com.sobrerodas.models.config.Styler;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracoesStyles {

    public static List<StyleRequest> mapaCinza() {
        ArrayList<StyleRequest> listaStyleRequest = new ArrayList<StyleRequest>();

        StyleRequest styleRequest = new StyleRequest();
        styleRequest.setElementType("geometry");
        ArrayList<Styler> lista = new ArrayList<Styler>();
        Styler styler = new Styler();
        styler.setColor("#f5f5f5");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setElementType("labels.icon");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setVisibility("off");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#616161");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setElementType("labels.text.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#f5f5f5");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("administrative.land_parcel");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#bdbdbd");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#eeeeee");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#757575");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi.park");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#e5e5e5");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi.park");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#9e9e9e");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#ffffff");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.arterial");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#757575");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#dadada");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#616161");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.local");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#9e9e9e");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("transit.line");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#e5e5e5");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("transit.station");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#eeeeee");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("water");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#c9c9c9");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("water");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#9e9e9e");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        return listaStyleRequest;
    }

    public static List<StyleRequest> mapaRetro() {
        ArrayList<StyleRequest> listaStyleRequest = new ArrayList<StyleRequest>();

        StyleRequest styleRequest = new StyleRequest();
        styleRequest.setElementType("geometry");
        ArrayList<Styler> lista = new ArrayList<Styler>();
        Styler styler = new Styler();
        styler.setColor("#ebe3cd");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#523735");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setElementType("labels.text.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#f5f1e6");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("administrative");
        styleRequest.setElementType("geometry.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#c9b2a6");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("administrative.land_parcel");
        styleRequest.setElementType("geometry.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#dcd2be");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("administrative.land_parcel");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#ae9e90");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("landscape.natural");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#dfd2ae");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#dfd2ae");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#93817c");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi.park");
        styleRequest.setElementType("geometry.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#a5b076");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi.park");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#447530");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#f5f1e6");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.arterial");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#fdfcf8");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#f8c967");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway");
        styleRequest.setElementType("geometry.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#e9bc62");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway.controlled_access");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#e98d58");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway.controlled_access");
        styleRequest.setElementType("geometry.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#db8555");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.local");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#806b63");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("transit.line");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#dfd2ae");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("transit.line");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#8f7d77");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("transit.line");
        styleRequest.setElementType("labels.text.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#ebe3cd");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("transit.station");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#dfd2ae");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("water");
        styleRequest.setElementType("geometry.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#b9d3c2");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("water");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#92998d");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        return listaStyleRequest;
    }

    public static List<StyleRequest> mapaEscuro() {
        ArrayList<StyleRequest> listaStyleRequest = new ArrayList<StyleRequest>();

        StyleRequest styleRequest = new StyleRequest();
        styleRequest.setElementType("geometry");
        ArrayList<Styler> lista = new ArrayList<Styler>();
        Styler styler = new Styler();
        styler.setColor("#212121");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setElementType("labels.icon");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setVisibility("off");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#757575");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setElementType("labels.text.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#212121");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("administrative");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#757575");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("administrative.country");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#9e9e9e");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("administrative.land_parcel");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setVisibility("off");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("administrative.locality");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#bdbdbd");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#757575");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi.park");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#181818");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi.park");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#616161");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi.park");
        styleRequest.setElementType("labels.text.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#1b1b1b");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road");
        styleRequest.setElementType("geometry.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#2c2c2c");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#8a8a8a");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.arterial");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#373737");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#3c3c3c");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway.controlled_access");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#4e4e4e");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.local");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#616161");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("transit");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#757575");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("water");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#000000");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("water");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#3d3d3d");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        return listaStyleRequest;
    }

    public static List<StyleRequest> mapaNoite() {
        ArrayList<StyleRequest> listaStyleRequest = new ArrayList<StyleRequest>();

        StyleRequest styleRequest = new StyleRequest();
        styleRequest.setElementType("geometry");
        ArrayList<Styler> lista = new ArrayList<Styler>();
        Styler styler = new Styler();
        styler.setColor("#242f3e");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#746855");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setElementType("labels.text.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#242f3e");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("administrative.locality");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#d59563");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#d59563");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi.park");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#263c3f");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi.park");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#6b9a76");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#38414e");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road");
        styleRequest.setElementType("geometry.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#212a37");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#9ca5b3");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#746855");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway");
        styleRequest.setElementType("geometry.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#1f2835");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#f3d19c");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("transit");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#2f3948");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("transit.station");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#d59563");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("water");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#17263c");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("water");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#515c6d");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("water");
        styleRequest.setElementType("labels.text.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#17263c");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        return listaStyleRequest;
    }

    public static List<StyleRequest> mapaAzul() {
        ArrayList<StyleRequest> listaStyleRequest = new ArrayList<StyleRequest>();

        StyleRequest styleRequest = new StyleRequest();
        styleRequest.setElementType("geometry");
        ArrayList<Styler> lista = new ArrayList<Styler>();
        Styler styler = new Styler();
        styler.setColor("#1d2c4d");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#8ec3b9");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setElementType("labels.text.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#1a3646");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("administrative.country");
        styleRequest.setElementType("geometry.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#4b6878");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("administrative.land_parcel");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#64779e");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("administrative.province");
        styleRequest.setElementType("geometry.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#4b6878");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("landscape.man_made");
        styleRequest.setElementType("geometry.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#334e87");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("landscape.natural");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#023e58");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#283d6a");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#6f9ba5");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi");
        styleRequest.setElementType("labels.text.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#1d2c4d");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi.park");
        styleRequest.setElementType("geometry.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#023e58");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("poi.park");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#3C7680");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#304a7d");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#98a5be");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road");
        styleRequest.setElementType("labels.text.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#1d2c4d");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#2c6675");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway");
        styleRequest.setElementType("geometry.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#255763");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#b0d5ce");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("road.highway");
        styleRequest.setElementType("labels.text.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#023e58");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("transit");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#98a5be");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("transit");
        styleRequest.setElementType("labels.text.stroke");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#1d2c4d");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("transit.line");
        styleRequest.setElementType("geometry.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#283d6a");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("transit.station");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#3a4762");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("water");
        styleRequest.setElementType("geometry");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#0e1626");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        styleRequest = new StyleRequest();
        styleRequest.setFeatureType("water");
        styleRequest.setElementType("labels.text.fill");
        lista = new ArrayList<Styler>();
        styler = new Styler();
        styler.setColor("#4e6d70");
        lista.add(styler);
        styleRequest.setStylers(lista);
        listaStyleRequest.add(styleRequest);

        return listaStyleRequest;
    }

}