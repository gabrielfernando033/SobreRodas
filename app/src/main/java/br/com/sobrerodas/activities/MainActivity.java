package br.com.sobrerodas.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import br.com.sobrerodas.R;
import br.com.sobrerodas.models.PlaceGoogle;
import br.com.sobrerodas.models.Ponto;
import br.com.sobrerodas.models.routes.RoutesAcessible;
import br.com.sobrerodas.models.facebook.FacebookLoginResponse;
import br.com.sobrerodas.models.routes.Overview_polyline;
import br.com.sobrerodas.models.routes.RotasResponse;
import br.com.sobrerodas.models.routes.Routes;
import br.com.sobrerodas.adapters.CustomAutoCompleteAdapter;
import br.com.sobrerodas.adapters.CustomInfoWindowAdapter;
import br.com.sobrerodas.utils.HttpCall;
import br.com.sobrerodas.utils.HttpRequest;
import br.com.sobrerodas.utils.ProfilePictureRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMyLocationChangeListener, LocationListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    // Google Maps
    private GoogleMap mMap;

    // Interface components
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private AutoCompleteTextView autoCompleteOrigem;
    private AutoCompleteTextView autoCompleteDestino;
    private ImageButton btnConfirmar;
    private ImageButton btnCadastrar;
    private ImageButton btnBack;
    private ImageButton btnRotas;
    private ImageButton btnRotasBack;
    private ImageButton btnLocation;
    private ImageButton btnCalcularRotas;
    private ImageButton btnMaisZoom;
    private ImageButton btnMenosZoom;
    private ImageButton btnAlinharNorte;
    private ImageView imageViewProfilePicture;
    private TextView textViewMenuUsuario;
    private TextView textViewMenuEmail;
    private Button btnFazerLogin;
    private Button btnFazerLogoff;
    private FrameLayout barRotas;
    private ProgressDialog progressDialog;

    // Markers
    private Marker markerCadastrar;
    private Marker markerRotas1;
    private Marker markerRotas2;
    private Marker markerSearch;

    // Routes
    private Polyline[] polylines = new Polyline[3];

    // Pontos cadastrados
    private ArrayList<Ponto> listPontos = new ArrayList<Ponto>();

    // Current Location
    private double currentLat;
    private double currentLon;
    private LocationManager locationManager;

    // Flags
    private boolean cadastrarIsActive = false;
    private boolean rotasIsActive = false;
    public static boolean telaCadastrarSucesso = false;
    public static boolean telaConfigChanged = false;

    // Firebase Database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseInstance;

    // Facebook Login
    private CallbackManager callbackManager;
    private AccessToken mAccessToken;

    // Google Places Autocomplete
    protected GeoDataClient geoDataClient;

    private final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true); // Offline mode
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseInstance = mFirebaseDatabase.getReference("Pontos");

        // Getting all points from Firebase
        retrievingPoints();

        // Google Places Autocomplete
        geoDataClient = Places.getGeoDataClient(this, null);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        mapFragment.getMapAsync(this);

        // Facebook Login
        callbackManager = CallbackManager.Factory.create();

        // Initializing UI components
        initializeComponents();

        // Updating UI if Facebook logged in
        updateUIFacebookLogin();

        // Current Location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Checking permission for location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1000, this); // You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
    }

    private void initializeComponents() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        textViewMenuUsuario = (TextView) headerView.findViewById(R.id.textViewMenuUsuario);
        textViewMenuEmail = (TextView) headerView.findViewById(R.id.textViewMenuEmail);
        imageViewProfilePicture = (ImageView) headerView.findViewById(R.id.imageViewProfilePicture);
        btnFazerLogin = (Button) headerView.findViewById(R.id.btFazerLogin);
        btnFazerLogoff = (Button) headerView.findViewById(R.id.btFazerLogoff);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        autoCompleteOrigem = (AutoCompleteTextView) findViewById(R.id.autoCompleteOrigem);
        autoCompleteDestino = (AutoCompleteTextView) findViewById(R.id.autoCompleteDestino);
        btnConfirmar = (ImageButton) findViewById(R.id.btnConfirmar);
        btnCadastrar = (ImageButton) findViewById(R.id.btnCadastrar);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnRotas = (ImageButton) findViewById(R.id.btnRotas);
        btnRotasBack = (ImageButton) findViewById(R.id.btnRotasBack);
        btnCalcularRotas = (ImageButton) findViewById(R.id.btnCalcularRotas);
        btnLocation = (ImageButton) findViewById(R.id.btnLocation);
        btnMaisZoom = (ImageButton) findViewById(R.id.btnMaisZoom);
        btnMenosZoom = (ImageButton) findViewById(R.id.btnMenosZoom);
        btnAlinharNorte = (ImageButton) findViewById(R.id.btnAlinharNorte);
        barRotas = (FrameLayout) findViewById(R.id.barRotas);

        btnConfirmar.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
        barRotas.setVisibility(View.GONE);

        // Adding Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //Remove toolbar title

        CustomAutoCompleteAdapter autoCompleteAdapter = new CustomAutoCompleteAdapter(this);
        // Configuring autoCompleteOrigem
        autoCompleteOrigem.setAdapter(autoCompleteAdapter);
        autoCompleteOrigem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                PlaceGoogle place = (PlaceGoogle) adapterView.getItemAtPosition(itemIndex);
                autoCompleteOrigem.setText(place.getPlaceText());
                // Getting place coordinates by ID
                geoDataClient.getPlaceById(place.getPlaceId()).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                        if (task.isSuccessful())
                        {
                            PlaceBufferResponse places = task.getResult();
                            Place place = places.get(0);
                            if (markerRotas1 == null)
                            {
                                // Adding marker
                                MarkerOptions marker = new MarkerOptions();
                                marker.position(place.getLatLng());
                                marker.draggable(true);
                                markerRotas1 = mMap.addMarker(marker);
                                autoCompleteOrigem.setText(place.getAddress());
                            }
                            else
                            {
                                markerRotas1.setPosition(place.getLatLng());
                            }
                            // Move camera to place
                            LatLng latLng = place.getLatLng();
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                            mMap.animateCamera(cameraUpdate);
                            // Change focus to autoCompleteDestino
                            autoCompleteDestino.requestFocus();
                            places.release();
                        }
                        else
                        {
                            showAlertDialog(getCurrentFocus(), "Atenção", "Local foi encontrado.");
                        }
                    }
                });
            }
        });
        // Configuring autoCompleteDestino
        autoCompleteDestino.setAdapter(autoCompleteAdapter);
        autoCompleteDestino.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                PlaceGoogle place = (PlaceGoogle) adapterView.getItemAtPosition(itemIndex);
                autoCompleteDestino.setText(place.getPlaceText());
                // Getting place coordinates by ID
                geoDataClient.getPlaceById(place.getPlaceId()).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                        if (task.isSuccessful())
                        {
                            PlaceBufferResponse places = task.getResult();
                            Place place = places.get(0);
                            if (markerRotas2 == null)
                            {
                                // Adding marker
                                MarkerOptions marker = new MarkerOptions();
                                marker.position(place.getLatLng());
                                marker.draggable(true);
                                markerRotas2 = mMap.addMarker(marker);
                                autoCompleteDestino.setText(place.getAddress());
                            }
                            else
                            {
                                markerRotas2.setPosition(place.getLatLng());
                            }
                            // Move camera to place
                            LatLng latLng = place.getLatLng();
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                            mMap.animateCamera(cameraUpdate);
                            // Hide Keyboard
                            hideKeyboard();
                            places.release();
                        }
                        else
                        {
                            showAlertDialog(getCurrentFocus(), "Atenção", "Local foi encontrado.");
                        }
                    }
                });
            }
        });

        // btnCadastrar OnClick
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableCadastrarComponents();
            }
        });
        // btnBack OnClick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableCadastrarComponents();
            }
        });
        // btnConfirmar OnClick
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (markerCadastrar == null)
                {
                    showAlertDialog(getCurrentFocus(), "Atenção", "Insira um ponto no mapa");
                }
                else if (isFacebookLoggedIn() == false)
                {
                    showAlertDialog(getCurrentFocus(), "Atenção", "Faça login para cadastrar um local");
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), CadastrarActivity.class);
                    intent.putExtra("latitude", "" + markerCadastrar.getPosition().latitude);
                    intent.putExtra("longitude", "" + markerCadastrar.getPosition().longitude);
                    startActivity(intent);
                }
            }
        });
        // btnRotas OnClick
        btnRotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableRotasComponents();
            }
        });
        // btnCalcularRotas OnClick
        btnCalcularRotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (markerRotas1 != null && markerRotas2 != null)
                {
                    calculatingRotes();
                }
                else
                {
                    showAlertDialog(getCurrentFocus(), "Atenção", "Insira os pontos de origem e destino");
                }
            }
        });
        // btnRotasBack OnClick
        btnRotasBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableRotasComponents();
            }
        });
        // btnLocation OnClick
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = new LatLng(currentLat, currentLon);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
                mMap.animateCamera(cameraUpdate);
            }
        });
        // btnMaisZoom OnClick
        btnMaisZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom + 1.5f));
            }
        });
        // btnMenosZoom OnClick
        btnMenosZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom - 1.5f));
            }
        });
        // btnAlinharNorte OnClick
        btnAlinharNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                        .target(mMap.getCameraPosition().target)
                        .zoom(mMap.getCameraPosition().zoom)
                        .bearing(0)
                        .tilt(0)
                        .build()));
            }
        });
        // Registering callback for 'btnFazerLogin'
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mAccessToken = loginResult.getAccessToken();
                getFacebookUserProfileData(mAccessToken);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
                showAlertDialog(getCurrentFocus(), "Error", exception.getMessage());
            }
        });
        // btnFazerLogin
        btnFazerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance()
                        .logInWithReadPermissions(
                                MainActivity.this,
                                Collections.singletonList("public_profile, email")
                        );
            }
        });
        // btnFazerLogoff OnClick
        btnFazerLogoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                updateUIFacebookLogin();
                Toast.makeText(getApplicationContext(), "Logoff feito com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        // DrawerLayout code
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // NavigationView code
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void enableCadastrarComponents() {
        btnCadastrar.setVisibility(View.GONE);
        btnRotas.setVisibility(View.GONE);
        btnConfirmar.setVisibility(View.VISIBLE);
        btnBack.setVisibility(View.VISIBLE);

        // Adding marker on center
        CameraPosition position = mMap.getCameraPosition();
        MarkerOptions marker = new MarkerOptions();
        marker.position(position.target);
        marker.draggable(true);
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_active));
        markerCadastrar = mMap.addMarker(marker);

        cadastrarIsActive = true;
    }

    private void enableRotasComponents() {
        barRotas.setVisibility(View.VISIBLE);
        btnRotas.setVisibility(View.GONE);
        btnCadastrar.setVisibility(View.GONE);

        // Putting btnAlinharNorte below barRotas
        RelativeLayout.LayoutParams parameter =  (RelativeLayout.LayoutParams) btnAlinharNorte.getLayoutParams();
        parameter.setMargins(18, 20, parameter.rightMargin, parameter.bottomMargin);
        parameter.addRule(RelativeLayout.BELOW, R.id.barRotas);
        btnAlinharNorte.setLayoutParams(parameter);

        // Animation
        TranslateAnimation anim = new TranslateAnimation(0, 0, -200, 0);
        anim.setDuration(250);
        barRotas.startAnimation(anim);

        rotasIsActive = true;
    }

    private void disableCadastrarComponents() {
        btnCadastrar.setVisibility(View.VISIBLE);
        btnRotas.setVisibility(View.VISIBLE);
        btnConfirmar.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
        if (markerCadastrar != null)
        {
            markerCadastrar.remove();
            markerCadastrar = null;
        }
        cadastrarIsActive = false;
    }

    private void disableRotasComponents() {
        // Animation
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, -200);
        anim.setDuration(150);
        barRotas.startAnimation(anim);

        autoCompleteOrigem.setText("");
        autoCompleteDestino.setText("");

        barRotas.setVisibility(View.GONE);
        btnRotas.setVisibility(View.VISIBLE);
        btnCadastrar.setVisibility(View.VISIBLE);

        // Putting btnAlinharNorte below btnBack
        RelativeLayout.LayoutParams parameter = (RelativeLayout.LayoutParams) btnAlinharNorte.getLayoutParams();
        parameter.setMargins(18, 16, parameter.rightMargin, parameter.bottomMargin);
        parameter.addRule(RelativeLayout.BELOW, R.id.btnBack);
        btnAlinharNorte.setLayoutParams(parameter);

        if (markerRotas1 != null)
        {
            markerRotas1.remove();
            markerRotas1 = null;
        }
        if (markerRotas2 != null)
        {
            markerRotas2.remove();
            markerRotas2 = null;
        }
        rotasIsActive = false;

        // Cleaning polylines
        for (int i=0; i<polylines.length; i++)
        {
            if (polylines[i] != null)
            {
                polylines[i].remove();
            }
        }

        hideKeyboard();
    }

    private void updateUIFacebookLoggedIn(String nome, String email) {
        textViewMenuUsuario.setVisibility(View.VISIBLE);
        textViewMenuEmail.setVisibility(View.VISIBLE);
        btnFazerLogin.setVisibility(View.GONE);
        btnFazerLogoff.setVisibility(View.VISIBLE);
        textViewMenuUsuario.setText(nome);
        textViewMenuEmail.setText(email);
    }

    private void updateUIFacebookLoggedOut() {
        textViewMenuUsuario.setVisibility(View.GONE);
        textViewMenuEmail.setVisibility(View.GONE);
        btnFazerLogin.setVisibility(View.VISIBLE);
        btnFazerLogoff.setVisibility(View.GONE);
        Drawable imageDrawable = getResources().getDrawable(R.drawable.ic_profilepicture);
        imageViewProfilePicture.setImageDrawable(imageDrawable);
    }

    private void retrievingPoints() {
        // Retrieving data from Firebase
        mFirebaseInstance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listPontos.clear();
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                    {
                        Ponto ponto = new Ponto();
                        ponto.setPontoId(objSnapshot.child("pontoId").getValue().toString());
                        ponto.setCategoria(objSnapshot.child("categoria").getValue().toString());
                        ponto.setDescricao(objSnapshot.child("descricao").getValue().toString());
                        ponto.setLatitude(objSnapshot.child("latitude").getValue().toString());
                        ponto.setLongitude(objSnapshot.child("longitude").getValue().toString());
                        ponto.setTemporario((boolean) objSnapshot.child("temporario").getValue());
                        if (objSnapshot.child("idFacebookUser").getValue() != null)
                        {
                            ponto.setIdFacebookUser(objSnapshot.child("idFacebookUser").getValue().toString());
                        }
                        else
                        {
                            ponto.setIdFacebookUser(null);
                        }
                        listPontos.add(ponto);
                    }
                    addPointsToMap();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addPointsToMap() {
        mMap.clear();
        // Adding markers
        for (int i=0; i<listPontos.size(); i++)
        {
            float lat = Float.parseFloat(listPontos.get(i).getLatitude());
            float lon = Float.parseFloat(listPontos.get(i).getLongitude());
            LatLng latlng = new LatLng(lat, lon);
            if (listPontos.get(i).getTemporario() == true)
            {
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latlng)
                        .title(listPontos.get(i).getCategoria())
                        .snippet(listPontos.get(i).getDescricao())
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_temporary)));
                marker.setTag(listPontos.get(i));
            }
            else
            {
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latlng)
                        .title(listPontos.get(i).getCategoria())
                        .snippet(listPontos.get(i).getDescricao())
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                marker.setTag(listPontos.get(i));
            }
        }
    }

    private void calculatingRotes() {
        // Configuring ProgressDialog
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Rotas acessíveis");
        progressDialog.setMessage("Calculando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        // Making request
        HttpCall httpCall = new HttpCall();
        httpCall.setMethodtype(HttpCall.GET);
        httpCall.setUrl("https://maps.googleapis.com/maps/api/directions/json");
        HashMap<String,String> params = new HashMap<>();
        params.put("origin", markerRotas1.getPosition().latitude + "," + markerRotas1.getPosition().longitude);
        params.put("destination", markerRotas2.getPosition().latitude + "," + markerRotas2.getPosition().longitude);
        params.put("alternatives", "true");
        params.put("mode", "walking");
        params.put("key", getString(R.string.google_maps_key));
        httpCall.setParams(params);
        new HttpRequest() {
            @Override
            protected void onPreExecute() {
                progressDialog.show();
            }

            @Override
            public void onResponse(String response) {
                super.onResponse(response);

                // Cleaning polylines
                for (int i=0; i<polylines.length; i++)
                {
                    if (polylines[i] != null)
                    {
                        polylines[i].remove();
                    }
                }

                // Drawing the routes
                String json = response;
                //json = "{\"geocoded_waypoints\":[{\"geocoder_status\":\"OK\",\"place_id\":\"ChIJY2wOQhQA0pQRwwNG5ThWMl0\",\"types\":[\"street_address\"]},{\"geocoder_status\":\"OK\",\"place_id\":\"ChIJ58hUWmsA0pQRWwQPCptpne0\",\"types\":[\"establishment\",\"food\",\"liquor_store\",\"point_of_interest\",\"store\"]}],\"routes\":[{\"bounds\":{\"northeast\":{\"lat\":-23.9763012,\"lng\":-46.1891685},\"southwest\":{\"lat\":-23.9794814,\"lng\":-46.1937193}},\"copyrights\":\"Dadoscartográficos©2018Google\",\"legs\":[{\"distance\":{\"text\":\"0,7km\",\"value\":713},\"duration\":{\"text\":\"9minutos\",\"value\":553},\"end_address\":\"Av.MarjorydaSilvaPrado,311-417-BalnearioPraiadoPernambuco,Guarujá-SP,11444-410,Brasil\",\"end_location\":{\"lat\":-23.9769418,\"lng\":-46.1891685},\"start_address\":\"R.SantaTerezinha,156-BalnearioPraiadoPerequê,Guarujá-SP,11446-340,Brasil\",\"start_location\":{\"lat\":-23.9794814,\"lng\":-46.1933436},\"steps\":[{\"distance\":{\"text\":\"91m\",\"value\":91},\"duration\":{\"text\":\"1min\",\"value\":64},\"end_location\":{\"lat\":-23.978737,\"lng\":-46.1937193},\"html_instructions\":\"Siganadireção<b>noroeste</b>na<b>R.Cinco</b>emdireçãoa<b>R.Um</b>\",\"polyline\":{\"points\":\"vnjqCjc}xGsCjA\"},\"start_location\":{\"lat\":-23.9794814,\"lng\":-46.1933436},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"0,3km\",\"value\":344},\"duration\":{\"text\":\"4minutos\",\"value\":253},\"end_location\":{\"lat\":-23.9767364,\"lng\":-46.1911692},\"html_instructions\":\"Vireà<b>direita</b>na<b>R.Um</b>\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"bjjqCve}xG[w@[w@QWQYc@o@a@o@W_@m@o@_ByAgAqA\"},\"start_location\":{\"lat\":-23.978737,\"lng\":-46.1937193},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"68m\",\"value\":68},\"duration\":{\"text\":\"1min\",\"value\":51},\"end_location\":{\"lat\":-23.9763012,\"lng\":-46.190706},\"html_instructions\":\"Continuepara<b>Av.doBosque</b>\",\"polyline\":{\"points\":\"r}iqCxu|xGEEqAuA\"},\"start_location\":{\"lat\":-23.9767364,\"lng\":-46.1911692},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"0,2km\",\"value\":182},\"duration\":{\"text\":\"3minutos\",\"value\":164},\"end_location\":{\"lat\":-23.9771878,\"lng\":-46.18920170000001},\"html_instructions\":\"Vireà<b>direita</b>na<b>R.dosHibiscos</b>\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"zziqC|r|xGvAwCxAuC\"},\"start_location\":{\"lat\":-23.9763012,\"lng\":-46.190706},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"28m\",\"value\":28},\"duration\":{\"text\":\"1min\",\"value\":21},\"end_location\":{\"lat\":-23.9769418,\"lng\":-46.1891685},\"html_instructions\":\"Vireà<b>esquerda</b>na<b>Av.MarjorydaSilvaPrado</b><divstyle=\\\"font-size:0.9em\\\">Odestinoestaráàdireita</div>\",\"maneuver\":\"turn-left\",\"polyline\":{\"points\":\"l`jqCni|xGq@E\"},\"start_location\":{\"lat\":-23.9771878,\"lng\":-46.18920170000001},\"travel_mode\":\"WALKING\"}],\"traffic_speed_entry\":[],\"via_waypoint\":[]}],\"overview_polyline\":{\"points\":\"vnjqCjc}xGsCjA[w@[w@QWu@iAy@oAmCiCmAwAqAuAvAwCxAuCq@E\"},\"summary\":\"R.UmeR.dosHibiscos\",\"warnings\":[\"Versãobetadarotaapé.Sejacuidadoso–Estetrajetopodenãotercalçadasoucaminhosdepedestres.\"],\"waypoint_order\":[]},{\"bounds\":{\"northeast\":{\"lat\":-23.9767364,\"lng\":-46.1891685},\"southwest\":{\"lat\":-23.9794814,\"lng\":-46.1937193}},\"copyrights\":\"Dadoscartográficos©2018Google\",\"legs\":[{\"distance\":{\"text\":\"0,8km\",\"value\":752},\"duration\":{\"text\":\"10minutos\",\"value\":580},\"end_address\":\"Av.MarjorydaSilvaPrado,311-417-BalnearioPraiadoPernambuco,Guarujá-SP,11444-410,Brasil\",\"end_location\":{\"lat\":-23.9769418,\"lng\":-46.1891685},\"start_address\":\"R.SantaTerezinha,156-BalnearioPraiadoPerequê,Guarujá-SP,11446-340,Brasil\",\"start_location\":{\"lat\":-23.9794814,\"lng\":-46.1933436},\"steps\":[{\"distance\":{\"text\":\"91m\",\"value\":91},\"duration\":{\"text\":\"1min\",\"value\":64},\"end_location\":{\"lat\":-23.978737,\"lng\":-46.1937193},\"html_instructions\":\"Siganadireção<b>noroeste</b>na<b>R.Cinco</b>emdireçãoa<b>R.Um</b>\",\"polyline\":{\"points\":\"vnjqCjc}xGsCjA\"},\"start_location\":{\"lat\":-23.9794814,\"lng\":-46.1933436},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"0,3km\",\"value\":344},\"duration\":{\"text\":\"4minutos\",\"value\":253},\"end_location\":{\"lat\":-23.9767364,\"lng\":-46.1911692},\"html_instructions\":\"Vireà<b>direita</b>na<b>R.Um</b>\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"bjjqCve}xG[w@[w@QWQYc@o@a@o@W_@m@o@_ByAgAqA\"},\"start_location\":{\"lat\":-23.978737,\"lng\":-46.1937193},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"8m\",\"value\":8},\"duration\":{\"text\":\"1min\",\"value\":7},\"end_location\":{\"lat\":-23.9768021,\"lng\":-46.191129},\"html_instructions\":\"Vireà<b>direita</b>na<b>Av.CruzeirodoSul</b>\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"r}iqCxu|xGDCDC\"},\"start_location\":{\"lat\":-23.9767364,\"lng\":-46.1911692},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"0,2km\",\"value\":214},\"duration\":{\"text\":\"3minutos\",\"value\":184},\"end_location\":{\"lat\":-23.9777854,\"lng\":-46.18932119999999},\"html_instructions\":\"Curvasuaveà<b>esquerda</b>na<b>R.dosGuaimbés</b>\",\"maneuver\":\"turn-slight-left\",\"polyline\":{\"points\":\"~}iqCpu|xGZw@t@}ArBsE\"},\"start_location\":{\"lat\":-23.9768021,\"lng\":-46.191129},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"0,1km\",\"value\":95},\"duration\":{\"text\":\"1min\",\"value\":72},\"end_location\":{\"lat\":-23.9769418,\"lng\":-46.1891685},\"html_instructions\":\"Vireà<b>esquerda</b>na<b>Av.MarjorydaSilvaPrado</b><divstyle=\\\"font-size:0.9em\\\">Odestinoestaráàdireita</div>\",\"maneuver\":\"turn-left\",\"polyline\":{\"points\":\"ddjqCfj|xGQEOCMCgAIq@E\"},\"start_location\":{\"lat\":-23.9777854,\"lng\":-46.18932119999999},\"travel_mode\":\"WALKING\"}],\"traffic_speed_entry\":[],\"via_waypoint\":[]}],\"overview_polyline\":{\"points\":\"vnjqCjc}xGsCjA[w@[w@QWu@iAy@oAmCiCgAqADCDCZw@hDqHa@IuAMq@E\"},\"summary\":\"R.UmeR.dosGuaimbés\",\"warnings\":[\"Versãobetadarotaapé.Sejacuidadoso–Estetrajetopodenãotercalçadasoucaminhosdepedestres.\"],\"waypoint_order\":[]},{\"bounds\":{\"northeast\":{\"lat\":-23.9769418,\"lng\":-46.1891685},\"southwest\":{\"lat\":-23.9794814,\"lng\":-46.1937193}},\"copyrights\":\"Dadoscartográficos©2018Google\",\"legs\":[{\"distance\":{\"text\":\"0,8km\",\"value\":784},\"duration\":{\"text\":\"10minutos\",\"value\":616},\"end_address\":\"Av.MarjorydaSilvaPrado,311-417-BalnearioPraiadoPernambuco,Guarujá-SP,11444-410,Brasil\",\"end_location\":{\"lat\":-23.9769418,\"lng\":-46.1891685},\"start_address\":\"R.SantaTerezinha,156-BalnearioPraiadoPerequê,Guarujá-SP,11446-340,Brasil\",\"start_location\":{\"lat\":-23.9794814,\"lng\":-46.1933436},\"steps\":[{\"distance\":{\"text\":\"91m\",\"value\":91},\"duration\":{\"text\":\"1min\",\"value\":64},\"end_location\":{\"lat\":-23.978737,\"lng\":-46.1937193},\"html_instructions\":\"Siganadireção<b>noroeste</b>na<b>R.Cinco</b>emdireçãoa<b>R.Um</b>\",\"polyline\":{\"points\":\"vnjqCjc}xGsCjA\"},\"start_location\":{\"lat\":-23.9794814,\"lng\":-46.1933436},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"0,1km\",\"value\":97},\"duration\":{\"text\":\"1min\",\"value\":73},\"end_location\":{\"lat\":-23.9782786,\"lng\":-46.1929101},\"html_instructions\":\"Vireà<b>direita</b>na<b>R.Um</b>\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"bjjqCve}xG[w@[w@QWQY\"},\"start_location\":{\"lat\":-23.978737,\"lng\":-46.1937193},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"0,2km\",\"value\":182},\"duration\":{\"text\":\"3minutos\",\"value\":165},\"end_location\":{\"lat\":-23.9794344,\"lng\":-46.1916384},\"html_instructions\":\"Vireà<b>direita</b>na<b>R.Sete</b>\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"fgjqCt`}xGvA_BtA_BFGn@u@\"},\"start_location\":{\"lat\":-23.9782786,\"lng\":-46.1929101},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"0,1km\",\"value\":126},\"duration\":{\"text\":\"2minutos\",\"value\":105},\"end_location\":{\"lat\":-23.9791233,\"lng\":-46.1904541},\"html_instructions\":\"Vireà<b>esquerda</b>na<b>EstradadePernambuco</b>\",\"maneuver\":\"turn-left\",\"polyline\":{\"points\":\"lnjqCvx|xGCME[CQCSAWIk@CMGUI[IY\"},\"start_location\":{\"lat\":-23.9794344,\"lng\":-46.1916384},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"0,3km\",\"value\":288},\"duration\":{\"text\":\"3minutos\",\"value\":209},\"end_location\":{\"lat\":-23.9769418,\"lng\":-46.1891685},\"html_instructions\":\"Continuepara<b>Av.MarjorydaSilvaPrado</b><divstyle=\\\"font-size:0.9em\\\">Odestinoestaráàdireita</div>\",\"polyline\":{\"points\":\"nljqChq|xGGOEKIMQWU[SS[WUOKGIEcAg@WIQGQEOCMCgAIq@E\"},\"start_location\":{\"lat\":-23.9791233,\"lng\":-46.1904541},\"travel_mode\":\"WALKING\"}],\"traffic_speed_entry\":[],\"via_waypoint\":[]}],\"overview_polyline\":{\"points\":\"vnjqCjc}xGsCjA[w@[w@QWQYvA_B|AgBn@u@CMIm@Ek@My@Qq@Qi@OYg@s@o@k@a@WmAm@i@Qa@IuAMq@E\"},\"summary\":\"Av.MarjorydaSilvaPrado\",\"warnings\":[\"Versãobetadarotaapé.Sejacuidadoso–Estetrajetopodenãotercalçadasoucaminhosdepedestres.\"],\"waypoint_order\":[]}],\"status\":\"OK\"}";
                RotasResponse data = new Gson().fromJson(json, RotasResponse.class);
                Routes[] routes = data.getRoutes();

                if (data.getErrorMessage() != "" && data.getErrorMessage() != null && data.getErrorMessage() != "null")
                {
                    progressDialog.dismiss();
                    showAlertDialog(getCurrentFocus(), "Falha na requisição", data.getErrorMessage());
                }
                else if (data.getRoutes().length == 0)
                {
                    progressDialog.dismiss();
                    showAlertDialog(getCurrentFocus(), "Atenção", "Não existe rotas para esses pontos");
                }
                else
                {
                    // Calculating which route is acessible
                    ArrayList<RoutesAcessible> routesAcessbile = new ArrayList<RoutesAcessible>();
                    for (int i=0; i<routes.length; i++)
                    {
                        Overview_polyline overview_polyline = routes[i].getOverview_polyline();
                        ArrayList<LatLng> lats = (ArrayList<LatLng>) PolyUtil.decode(overview_polyline.getPoints());
                        RoutesAcessible route = new RoutesAcessible();
                        route.setOverview_polyline(overview_polyline);
                        // Interate all registered points
                        for (int j=0; j<listPontos.size(); j++)
                        {
                            LatLng point = new LatLng(Double.parseDouble(listPontos.get(j).getLatitude()), Double.parseDouble(listPontos.get(j).getLongitude()));
                            boolean isLocation = PolyUtil.isLocationOnPath(point, lats, false, 5.0);
                            // If point is in route
                            if (isLocation)
                            {
                                route.sumQtd();
                            }
                        }
                        routesAcessbile.add(route);
                    }

                    Collections.sort(routesAcessbile);

                    // Adding routes to map
                    for (int i=0; i<routesAcessbile.size(); i++)
                    {
                        Overview_polyline overview_polyline = routesAcessbile.get(i).getOverview_polyline();
                        ArrayList<LatLng> lats = (ArrayList<LatLng>) PolyUtil.decode(overview_polyline.getPoints());

                        // Calculating route color
                        int color = 0;
                        int qtdPoints = routesAcessbile.get(i).getQtd_points();
                        if (qtdPoints == 0)
                        {
                            color = Color.rgb(23, 255, 23); // Verde
                        }
                        if (qtdPoints == 1)
                        {
                            color = Color.rgb(168, 198, 0); // Verde-amarelo
                        }
                        if (qtdPoints == 2)
                        {
                            color = Color.rgb(242, 255, 0); // Amarelo
                        }
                        if (qtdPoints == 3)
                        {
                            color = Color.rgb(255, 191, 0); // Laranja
                        }
                        if (qtdPoints == 4)
                        {
                            color = Color.rgb(255, 121, 0); // Laranja-vermelho
                        }
                        if (qtdPoints >= 5)
                        {
                            color = Color.rgb(255, 0, 0); // Vermelho
                        }

                        // Drawing route
                        polylines[i] = mMap.addPolyline(new PolylineOptions()
                                .addAll(lats)
                                .width(12)
                                .color(color));
                    }

                    progressDialog.dismiss();
                }
            }
        }.execute(httpCall);
    }

    private boolean isFacebookLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        return isLoggedIn;
    }

    private void updateUIFacebookLogin() {
        if (isFacebookLoggedIn())
        {
            getFacebookUserProfileData(AccessToken.getCurrentAccessToken());
        }
        else
        {
            updateUIFacebookLoggedOut();
        }
    }

    private void getFacebookUserProfileData(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (object != null)
                {
                    FacebookLoginResponse facebookLoginResponse = new Gson().fromJson(object.toString(), FacebookLoginResponse.class);
                    updateUIFacebookLoggedIn(facebookLoginResponse.getName(), facebookLoginResponse.getEmail());
                    // Getting profilePicture
                    new ProfilePictureRequest(imageViewProfilePicture).execute(facebookLoginResponse.getPicture().getData().getUrl());
                }
                else
                {
                    updateUIFacebookLoggedOut();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(140)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // Facebook Login request
        if (FacebookSdk.isFacebookRequestCode(requestCode))
        {
            // If loggin was sucessfull
            if (resultCode != 0)
            {
                updateUIFacebookLogin();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        updateUIFacebookLogin();

        // If configuration has been changed so update map style
        if (telaConfigChanged)
        {
            onMapReady(mMap);
        }

        // Remove all points from map
        mMap.clear();
        // Retrieve all points from firebase if Cadastro was successfull
        if (telaCadastrarSucesso)
        {
            telaCadastrarSucesso = false;
            retrievingPoints();
            disableCadastrarComponents();
        }
        else
        {
            // Add all points to map
            addPointsToMap();
        }
        // Add markerCadastrar
        if (markerCadastrar != null)
        {
            MarkerOptions marker = new MarkerOptions();
            marker.position(markerCadastrar.getPosition());
            marker.draggable(true);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_active));
            markerCadastrar = mMap.addMarker(marker);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            if (cadastrarIsActive)
            {
                disableCadastrarComponents();
            }
            else if (rotasIsActive)
            {
                disableRotasComponents();
            }
            else
            {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tutorial)
        {
            Intent intent = new Intent(getApplicationContext(), TutorialActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_feedback)
        {
            Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_configuracoes)
        {
            Intent intent = new Intent(getApplicationContext(), ConfiguracoesActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_exit)
        {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which)
                    {
                        case DialogInterface.BUTTON_POSITIVE:
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            showAlertDialogYesNo(dialogClickListener, "Atenção", "Você deseja realmente sair?");
        }

        // Close novigation after clicked
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Configuring searchView
        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) mSearch.getActionView();
        searchView.setQueryHint("Digite o nome do local");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                AutocompleteFilter.Builder filterBuilder = new AutocompleteFilter.Builder();
                Task<AutocompletePredictionBufferResponse> task = geoDataClient.getAutocompletePredictions(query, null, filterBuilder.build());
                task.addOnCompleteListener(new OnCompleteListener<AutocompletePredictionBufferResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<AutocompletePredictionBufferResponse> task) {
                        if (task.isSuccessful())
                        {
                            AutocompletePredictionBufferResponse predictions = task.getResult();
                            if (predictions.getCount() > 0)
                            {
                                // Getting place coordinates by ID
                                geoDataClient.getPlaceById(predictions.get(0).getPlaceId()).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
                                    @Override
                                    public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                                        if (task.isSuccessful())
                                        {
                                            PlaceBufferResponse places = task.getResult();
                                            Place place = places.get(0);
                                            // Add marker to place
                                            if (markerSearch == null)
                                            {
                                                MarkerOptions marker = new MarkerOptions();
                                                marker.position(place.getLatLng());
                                                markerSearch = mMap.addMarker(marker);
                                            }
                                            else
                                            {
                                                markerSearch.setPosition(place.getLatLng());
                                            }
                                            // Move camera to place
                                            LatLng latLng = place.getLatLng();
                                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                                            mMap.animateCamera(cameraUpdate);
                                            places.release();
                                        }
                                        else
                                        {
                                            showAlertDialog(getCurrentFocus(), "Atenção", "Local não encontrado.");
                                        }
                                    }
                                });
                            }
                            else
                            {
                                showAlertDialog(getCurrentFocus(), "Atenção", "Local não encontrado.");
                            }
                            predictions.release();
                        }
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // When close searchView
        MenuItemCompat.setOnActionExpandListener(mSearch, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (markerSearch != null)
                {
                    markerSearch.remove();
                    markerSearch = null;
                }
                return true;
            }
        });

        // Configuring mSearchAutoComplete
        CustomAutoCompleteAdapter adapter = new CustomAutoCompleteAdapter(this);
        final SearchView.SearchAutoComplete mSearchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        mSearchAutoComplete.setDropDownAnchor(R.id.action_search);
        mSearchAutoComplete.setThreshold(1);
        mSearchAutoComplete.setAdapter(adapter);
        mSearchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                PlaceGoogle place = (PlaceGoogle) adapterView.getItemAtPosition(itemIndex);
                mSearchAutoComplete.setText(place.getPlaceText());
                // Getting place coordinates by ID
                geoDataClient.getPlaceById(place.getPlaceId()).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                        if (task.isSuccessful())
                        {
                            PlaceBufferResponse places = task.getResult();
                            Place place = places.get(0);
                            // Add marker to place
                            if (markerSearch == null)
                            {
                                MarkerOptions marker = new MarkerOptions();
                                marker.position(place.getLatLng());
                                markerSearch = mMap.addMarker(marker);
                            }
                            else
                            {
                                markerSearch.setPosition(place.getLatLng());
                            }
                            // Move camera to place
                            LatLng latLng = place.getLatLng();
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                            mMap.animateCamera(cameraUpdate);
                            places.release();
                        }
                        else
                        {
                            showAlertDialog(getCurrentFocus(), "Atenção", "Local foi encontrado.");
                        }
                    }
                });
            }
        });

        return true;
    }

    /**
     * When map is ready
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Loading map style
        try
        {
            String configFileContent = readConfigFileFromInternalStorage();
            MapStyleOptions style = new MapStyleOptions(configFileContent);
            boolean success = googleMap.setMapStyle(style);
            if (!success)
            {
                Log.e("MainActivity", "Falha ao fazer parse do estilo do map");
            }
        }
        catch (Resources.NotFoundException e)
        {
            Log.e("MainActivity", "Não foi possível achar o arquivo de estilo", e);
        }

        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMyLocationChangeListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnMarkerDragListener(this);
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this));
        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setMyLocationButtonEnabled(false); // Disable/Enable location button
        mMap.getUiSettings().setZoomControlsEnabled(false); // Disable/Enable zoom button
        mMap.getUiSettings().setCompassEnabled(false); // Disable/Enable realign north button
        mMap.getUiSettings().setMapToolbarEnabled(false); // Disable/Enable map toolbar (routes button)

        // Checking permission for location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    /**
     * When click on map
     */
    @Override
    public void onMapClick(LatLng latLng) {
        if (rotasIsActive)
        {
            if (markerRotas1 == null)
            {
                MarkerOptions marker = new MarkerOptions();
                marker.position(latLng);
                marker.draggable(true);
                // Adding marker
                markerRotas1 = mMap.addMarker(marker);
                // Set address
                String address = getAddressFromLocation(marker.getPosition());
                autoCompleteOrigem.setText(address);
            }
            else if (markerRotas2 == null)
            {
                MarkerOptions marker = new MarkerOptions();
                marker.position(latLng);
                marker.draggable(true);
                // Adding marker
                markerRotas2 = mMap.addMarker(marker);
                // Set address
                String address = getAddressFromLocation(marker.getPosition());
                autoCompleteDestino.setText(address);
            }
        }
    }

    /**
     * When click on marker
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        Ponto ponto = (Ponto) marker.getTag();
        String pontoAsString = new Gson().toJson(ponto);
        Intent intent = new Intent(getApplicationContext(), VisualizarActivity.class);
        intent.putExtra("pontoPassed", pontoAsString);
        startActivity(intent);
    }

    /**
     * When location change
     */
    @Override
    public void onMyLocationChange(Location location) {
        currentLat = location.getLatitude();
        currentLon = location.getLongitude();
    }

    /**
     *  When Location Changed (Go to current location when map starts)
     */
    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        mMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    /**
     * When camera move
     */
    @Override
    public void onCameraMove() {
        CameraPosition position = mMap.getCameraPosition();
        if (position.bearing == 0 && position.tilt == 0)
        {
            btnAlinharNorte.setVisibility(View.GONE);
        }
        else
        {
            btnAlinharNorte.setVisibility(View.VISIBLE);
        }

        if (cadastrarIsActive)
        {
            if (markerCadastrar == null)
            {
                MarkerOptions marker = new MarkerOptions();
                marker.position(position.target);
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_active));
                markerCadastrar = mMap.addMarker(marker);
            }
            else
            {
                markerCadastrar.setPosition(position.target);
            }
        }
    }

    /**
     * When drag on Marker
     */
    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (rotasIsActive)
        {
            String address = getAddressFromLocation(marker.getPosition());
            if (markerRotas1.getId().equals(marker.getId()))
            {
                autoCompleteOrigem.setText(address);
            }
            else if (markerRotas2.getId().equals(marker.getId()))
            {
                autoCompleteDestino.setText(address);
            }
        }
    }

    /**
     * When click on Marker
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (markerCadastrar != null)
        {
            if (marker.getId().equals(markerCadastrar.getId()))
            {
                // Hide infoWindow
                return true;
            }
        }
        if (markerRotas1 != null)
        {
            if (marker.getId().equals(markerRotas1.getId()))
            {
                // Hide infoWindow
                return true;
            }
        }
        if (markerRotas2 != null)
        {
            if (marker.getId().equals(markerRotas2.getId()))
            {
                // Hide infoWindow
                return true;
            }
        }
        if (markerSearch != null)
        {
            if (marker.getId().equals(markerSearch.getId()))
            {
                // Hide infoWindow
                return true;
            }
        }
        return false;
    }

    /**
     * When request dialog result
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_REQUEST_ACCESS_FINE_LOCATION :
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    mMap.setMyLocationEnabled(true);
                    // Current Location
                    //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1000, this); // You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "O aplicativo precisa da permissão de localização para funcionar corretamente", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void showAlertDialog(View view, String title, String message) {
        // Setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        // Add a button
        builder.setPositiveButton("OK", null);
        // Create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAlertDialogYesNo(DialogInterface.OnClickListener dialogClickListener, String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton("Sim", dialogClickListener);
        dialog.setNegativeButton("Não", dialogClickListener);
        dialog.show();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private String readConfigFileFromInternalStorage() {
        try
        {
            FileInputStream fin = openFileInput("config");
            int c;
            String temp = "";
            while ((c = fin.read()) != -1)
            {
                temp = temp + Character.toString((char)c);
            }
            fin.close();
            return temp;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return "[]";
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "[]";
        }
    }

    private String getAddressFromLocation(LatLng latlon) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = null;
        try
        {
            addresses = geocoder.getFromLocation(latlon.latitude, latlon.longitude, 1);
            if (addresses.size() > 0)
            {
                String zip = addresses.get(0).getAddressLine(0);
                return zip;
            }
            else
            {
                return "";
            }
        }
        catch (IOException e)
        {
            return "";
        }
    }

}