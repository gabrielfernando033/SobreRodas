package br.com.sobrerodas.models.routes;

public class Routes {

    private String summary;
    private String copyrights;
    private String[] waypoint_order;
    private String[] warnings;
    private Overview_polyline overview_polyline;

    public String getSummary ()
    {
        return summary;
    }

    public void setSummary (String summary)
    {
        this.summary = summary;
    }

    public String getCopyrights ()
    {
        return copyrights;
    }

    public void setCopyrights (String copyrights)
    {
        this.copyrights = copyrights;
    }

    public String[] getWaypoint_order ()
    {
        return waypoint_order;
    }

    public void setWaypoint_order (String[] waypoint_order)
    {
        this.waypoint_order = waypoint_order;
    }

    public String[] getWarnings ()
    {
        return warnings;
    }

    public void setWarnings (String[] warnings)
    {
        this.warnings = warnings;
    }

    public Overview_polyline getOverview_polyline ()
    {
        return overview_polyline;
    }

    public void setOverview_polyline (Overview_polyline overview_polyline)
    {
        this.overview_polyline = overview_polyline;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [summary = "+summary+", copyrights = "+copyrights+", waypoint_order = "+waypoint_order+", warnings = "+warnings+", overview_polyline = "+overview_polyline+"]";
    }

}