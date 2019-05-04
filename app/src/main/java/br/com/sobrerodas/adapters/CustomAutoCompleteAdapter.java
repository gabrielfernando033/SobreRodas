package br.com.sobrerodas.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import br.com.sobrerodas.R;
import br.com.sobrerodas.models.PlaceGoogle;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class CustomAutoCompleteAdapter extends ArrayAdapter {

    public static final String TAG = "CustomAutoCompAdapter";
    private List<PlaceGoogle> dataList;
    private Context mContext;
    private GeoDataClient geoDataClient;

    private CustomAutoCompleteAdapter.CustomAutoCompleteFilter listFilter = new CustomAutoCompleteAdapter.CustomAutoCompleteFilter();

    public CustomAutoCompleteAdapter(Context context) {
        super(context, R.layout.autocomplete_content, new ArrayList<Place>());
        mContext = context;
        geoDataClient = Places.getGeoDataClient(mContext, null);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        PlaceGoogle placeGoogle = getItem(position);
        if (view == null)
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.autocomplete_content, parent, false);
        }
        TextView txtAutocompletePlaceName = (TextView) view.findViewById(R.id.txtAutocompletePlaceName);
        txtAutocompletePlaceName.setText(placeGoogle.getPlaceText());
        return view;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public PlaceGoogle getItem(int position) {
        return dataList.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class CustomAutoCompleteFilter extends Filter {

        private Object lock = new Object();
        private Object lockTwo = new Object();
        private boolean placeResults = false;

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            placeResults = false;
            final List<PlaceGoogle> placesList = new ArrayList<>();

            if (prefix == null || prefix.length() == 0)
            {
                synchronized (lock) {
                    results.values = new ArrayList<Place>();
                    results.count = 0;
                }
            }
            else
            {
                final String searchStrLowerCase = prefix.toString().toLowerCase();
                Task<AutocompletePredictionBufferResponse> task = getAutoCompletePlaces(searchStrLowerCase);
                task.addOnCompleteListener(new OnCompleteListener<AutocompletePredictionBufferResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<AutocompletePredictionBufferResponse> task) {
                        if (task.isSuccessful())
                        {
                            AutocompletePredictionBufferResponse predictions = task.getResult();
                            PlaceGoogle autoPlace;
                            for (AutocompletePrediction prediction : predictions)
                            {
                                autoPlace = new PlaceGoogle();
                                autoPlace.setPlaceId(prediction.getPlaceId());
                                autoPlace.setPlaceText(prediction.getFullText(null).toString());
                                placesList.add(autoPlace);
                            }
                            predictions.release();
                        }
                        else
                        {
                            Log.d(TAG, "Auto complete prediction unsuccessful");
                        }
                        //inform waiting thread about api call completion
                        placeResults = true;
                        synchronized (lockTwo)
                        {
                            lockTwo.notifyAll();
                        }
                    }
                });

                // wait for the results from asynchronous API call
                while (!placeResults)
                {
                    synchronized (lockTwo)
                    {
                        try
                        {
                            lockTwo.wait();
                        }
                        catch (InterruptedException e)
                        {

                        }
                    }
                }
                results.values = placesList;
                results.count = placesList.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null)
            {
                dataList = (ArrayList<PlaceGoogle>) results.values;
            }
            else
            {
                dataList = null;
            }
            if (results.count > 0)
            {
                notifyDataSetChanged();
            }
            else
            {
                notifyDataSetInvalidated();
            }
        }

        private Task<AutocompletePredictionBufferResponse> getAutoCompletePlaces(String query) {
            AutocompleteFilter.Builder filterBuilder = new AutocompleteFilter.Builder();
            Task<AutocompletePredictionBufferResponse> results = geoDataClient.getAutocompletePredictions(query, null, filterBuilder.build());
            return results;
        }
    }
}