package br.com.sobrerodas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import br.com.sobrerodas.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_infowindow, null);
    }

    private void rendowWindowText(Marker marker, View view){
        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.infoWindowTitle);
        if (title != null)
        {
            tvTitle.setText(title);
        }
        String snippet = marker.getSnippet();
        TextView tvSnippet = (TextView) view.findViewById(R.id.infoWindowContent);
        if (snippet != null)
        {
            tvSnippet.setText(snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

}