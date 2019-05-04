package br.com.sobrerodas.models.routes;

import android.support.annotation.NonNull;

public class RoutesAcessible implements Comparable {

    private Overview_polyline overview_polyline;
    private int qtd_points;

    public void sumQtd() {
        qtd_points = qtd_points + 1;
    }

    public Overview_polyline getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(Overview_polyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public int getQtd_points() {
        return qtd_points;
    }

    public void setQtd_points(int qtd_points) {
        this.qtd_points = qtd_points;
    }

    @Override
    public int compareTo(@NonNull Object compareRoute) {
        int compareage = ((RoutesAcessible) compareRoute).getQtd_points();
        //return this.qtd_points - compareage; // Ascending order
        return compareage - this.qtd_points; // Descending order
    }
}