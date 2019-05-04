package br.com.sobrerodas.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import br.com.sobrerodas.R;
import br.com.sobrerodas.models.config.StyleRequest;
import br.com.sobrerodas.models.config.Styler;
import com.sobrerodas.utils.ConfiguracoesStyles;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ConfiguracoesActivity extends AppCompatActivity {

    // Interface components
    private Toolbar toolbar;
    private Button btnSalvarConfiguracoes;
    private RadioGroup radioGroupTemas;
    private RadioButton radioBtMapaPadrao;
    private RadioButton radioBtMapaCinza;
    private RadioButton radioBtMapaRetro;
    private RadioButton radioBtMapaEscuro;
    private RadioButton radioBtMapaNoite;
    private RadioButton radioBtMapaAzul;
    private Switch switchNomeRuas;
    private Switch switchBairrosCidades;
    private Switch switchPontosTuristicos;
    private Switch switchComercios;
    private Switch switchOrgaosGoverno;
    private Switch switchHospitais;
    private Switch switchParques;
    private Switch switchIgrejas;
    private Switch switchEscolas;
    private Switch switchComplexosEsportivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        MainActivity.telaConfigChanged = false;
        initializeComponents();
        loadConfigurations();
    }

    private void initializeComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbarConfiguracoes);
        btnSalvarConfiguracoes = (Button) findViewById(R.id.btnSalvarConfiguracoes);
        radioGroupTemas = (RadioGroup) findViewById(R.id.radioGroupTemas);
        radioBtMapaPadrao = (RadioButton) findViewById(R.id.radioBtMapaPadrao);
        radioBtMapaCinza = (RadioButton) findViewById(R.id.radioBtMapaCinza);
        radioBtMapaRetro = (RadioButton) findViewById(R.id.radioBtMapaRetro);
        radioBtMapaEscuro = (RadioButton) findViewById(R.id.radioBtMapaEscuro);
        radioBtMapaNoite = (RadioButton) findViewById(R.id.radioBtMapaNoite);
        radioBtMapaAzul = (RadioButton) findViewById(R.id.radioBtMapaAzul);
        switchNomeRuas = (Switch) findViewById(R.id.switchNomeRuas);
        switchBairrosCidades = (Switch) findViewById(R.id.switchBairrosCidades);
        switchPontosTuristicos = (Switch) findViewById(R.id.switchPontosTuristicos);
        switchComercios = (Switch) findViewById(R.id.switchComercios);
        switchOrgaosGoverno = (Switch) findViewById(R.id.switchOrgaosGoverno);
        switchHospitais = (Switch) findViewById(R.id.switchHospitais);
        switchParques = (Switch) findViewById(R.id.switchParques);
        switchIgrejas = (Switch) findViewById(R.id.switchIgrejas);
        switchEscolas = (Switch) findViewById(R.id.switchEscolas);
        switchComplexosEsportivos = (Switch) findViewById(R.id.switchComplexosEsportivos);

        // Adding ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Displaying back button
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // btnSalvarConfiguracoes OnCLick
        btnSalvarConfiguracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<StyleRequest> listaStyleRequest = new ArrayList<StyleRequest>();
                if (radioBtMapaPadrao.isChecked())
                {

                }
                if (radioBtMapaCinza.isChecked())
                {
                    listaStyleRequest.addAll(ConfiguracoesStyles.mapaCinza());
                }
                if (radioBtMapaRetro.isChecked())
                {
                    listaStyleRequest.addAll(ConfiguracoesStyles.mapaRetro());
                }
                if (radioBtMapaEscuro.isChecked())
                {
                    listaStyleRequest.addAll(ConfiguracoesStyles.mapaEscuro());
                }
                if (radioBtMapaNoite.isChecked())
                {
                    listaStyleRequest.addAll(ConfiguracoesStyles.mapaNoite());
                }
                if (radioBtMapaAzul.isChecked())
                {
                    listaStyleRequest.addAll(ConfiguracoesStyles.mapaAzul());
                }
                if (switchNomeRuas.isChecked() == false)
                {
                    StyleRequest styleRequest = new StyleRequest();
                    styleRequest.setFeatureType("road");
                    styleRequest.setElementType("labels");
                    ArrayList<Styler> lista = new ArrayList<Styler>();
                    Styler styler = new Styler();
                    styler.setVisibility("off");
                    lista.add(styler);
                    styleRequest.setStylers(lista);
                    listaStyleRequest.add(styleRequest);
                }
                if (switchBairrosCidades.isChecked() == false)
                {
                    StyleRequest styleRequest = new StyleRequest();
                    styleRequest.setFeatureType("administrative.locality");
                    styleRequest.setElementType("labels");
                    ArrayList<Styler> lista = new ArrayList<Styler>();
                    Styler styler = new Styler();
                    styler.setVisibility("off");
                    lista.add(styler);
                    styleRequest.setStylers(lista);
                    listaStyleRequest.add(styleRequest);
                }
                if (switchPontosTuristicos.isChecked() == false && !radioBtMapaCinza.isChecked() && !radioBtMapaEscuro.isChecked())
                {
                    StyleRequest styleRequest = new StyleRequest();
                    styleRequest.setFeatureType("poi.attraction");
                    styleRequest.setElementType("labels");
                    ArrayList<Styler> lista = new ArrayList<Styler>();
                    Styler styler = new Styler();
                    styler.setVisibility("off");
                    lista.add(styler);
                    styleRequest.setStylers(lista);
                    listaStyleRequest.add(styleRequest);
                }
                if (switchComercios.isChecked() == false && !radioBtMapaCinza.isChecked() && !radioBtMapaEscuro.isChecked())
                {
                    StyleRequest styleRequest = new StyleRequest();
                    styleRequest.setFeatureType("poi.business");
                    styleRequest.setElementType("labels");
                    ArrayList<Styler> lista = new ArrayList<Styler>();
                    Styler styler = new Styler();
                    styler.setVisibility("off");
                    lista.add(styler);
                    styleRequest.setStylers(lista);
                    listaStyleRequest.add(styleRequest);
                }
                if (switchOrgaosGoverno.isChecked() == false && !radioBtMapaCinza.isChecked() && !radioBtMapaEscuro.isChecked())
                {
                    StyleRequest styleRequest = new StyleRequest();
                    styleRequest.setFeatureType("poi.government");
                    styleRequest.setElementType("labels");
                    ArrayList<Styler> lista = new ArrayList<Styler>();
                    Styler styler = new Styler();
                    styler.setVisibility("off");
                    lista.add(styler);
                    styleRequest.setStylers(lista);
                    listaStyleRequest.add(styleRequest);
                }
                if (switchHospitais.isChecked() == false && !radioBtMapaCinza.isChecked() && !radioBtMapaEscuro.isChecked())
                {
                    StyleRequest styleRequest = new StyleRequest();
                    styleRequest.setFeatureType("poi.medical");
                    styleRequest.setElementType("labels");
                    ArrayList<Styler> lista = new ArrayList<Styler>();
                    Styler styler = new Styler();
                    styler.setVisibility("off");
                    lista.add(styler);
                    styleRequest.setStylers(lista);
                    listaStyleRequest.add(styleRequest);
                }
                if (switchParques.isChecked() == false && !radioBtMapaCinza.isChecked() && !radioBtMapaEscuro.isChecked())
                {
                    StyleRequest styleRequest = new StyleRequest();
                    styleRequest.setFeatureType("poi.park");
                    styleRequest.setElementType("labels");
                    ArrayList<Styler> lista = new ArrayList<Styler>();
                    Styler styler = new Styler();
                    styler.setVisibility("off");
                    lista.add(styler);
                    styleRequest.setStylers(lista);
                    listaStyleRequest.add(styleRequest);
                }
                if (switchIgrejas.isChecked() == false && !radioBtMapaCinza.isChecked() && !radioBtMapaEscuro.isChecked())
                {
                    StyleRequest styleRequest = new StyleRequest();
                    styleRequest.setFeatureType("poi.place_of_worship");
                    styleRequest.setElementType("labels");
                    ArrayList<Styler> lista = new ArrayList<Styler>();
                    Styler styler = new Styler();
                    styler.setVisibility("off");
                    lista.add(styler);
                    styleRequest.setStylers(lista);
                    listaStyleRequest.add(styleRequest);
                }
                if (switchEscolas.isChecked() == false && !radioBtMapaCinza.isChecked() && !radioBtMapaEscuro.isChecked())
                {
                    StyleRequest styleRequest = new StyleRequest();
                    styleRequest.setFeatureType("poi.school");
                    styleRequest.setElementType("labels");
                    ArrayList<Styler> lista = new ArrayList<Styler>();
                    Styler styler = new Styler();
                    styler.setVisibility("off");
                    lista.add(styler);
                    styleRequest.setStylers(lista);
                    listaStyleRequest.add(styleRequest);
                }
                if (switchComplexosEsportivos.isChecked() == false && !radioBtMapaCinza.isChecked() && !radioBtMapaEscuro.isChecked())
                {
                    StyleRequest styleRequest = new StyleRequest();
                    styleRequest.setFeatureType("poi.sports_complex");
                    styleRequest.setElementType("labels");
                    ArrayList<Styler> lista = new ArrayList<Styler>();
                    Styler styler = new Styler();
                    styler.setVisibility("off");
                    lista.add(styler);
                    styleRequest.setStylers(lista);
                    listaStyleRequest.add(styleRequest);
                }
                String json = new Gson().toJson(listaStyleRequest);
                writeConfigFileOnInternalStorage(json);
                MainActivity.telaConfigChanged = true;
                Toast.makeText(getApplicationContext(), "Configurações salvas com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // radioBtMapaPadrao OnClick
        radioBtMapaPadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioBtMapaPadrao.setChecked(true);
                radioBtMapaCinza.setChecked(false);
                radioBtMapaRetro.setChecked(false);
                radioBtMapaEscuro.setChecked(false);
                radioBtMapaNoite.setChecked(false);
                radioBtMapaAzul.setChecked(false);
                enableSwitchs();
                checkOnSwitchs();
            }
        });
        // radioBtMapaCinza OnClick
        radioBtMapaCinza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioBtMapaPadrao.setChecked(false);
                radioBtMapaCinza.setChecked(true);
                radioBtMapaRetro.setChecked(false);
                radioBtMapaEscuro.setChecked(false);
                radioBtMapaNoite.setChecked(false);
                radioBtMapaAzul.setChecked(false);
                checkOffSwitchs();
                disableSwitchs();
            }
        });
        // radioBtMapaRetro OnClick
        radioBtMapaRetro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioBtMapaPadrao.setChecked(false);
                radioBtMapaCinza.setChecked(false);
                radioBtMapaRetro.setChecked(true);
                radioBtMapaEscuro.setChecked(false);
                radioBtMapaNoite.setChecked(false);
                radioBtMapaAzul.setChecked(false);
                enableSwitchs();
                checkOnSwitchs();
            }
        });
        // radioBtMapaEscuro OnClick
        radioBtMapaEscuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioBtMapaPadrao.setChecked(false);
                radioBtMapaCinza.setChecked(false);
                radioBtMapaRetro.setChecked(false);
                radioBtMapaEscuro.setChecked(true);
                radioBtMapaNoite.setChecked(false);
                radioBtMapaAzul.setChecked(false);
                checkOffSwitchs();
                disableSwitchs();
            }
        });
        // radioBtMapaNoite OnClick
        radioBtMapaNoite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioBtMapaPadrao.setChecked(false);
                radioBtMapaCinza.setChecked(false);
                radioBtMapaRetro.setChecked(false);
                radioBtMapaEscuro.setChecked(false);
                radioBtMapaNoite.setChecked(true);
                radioBtMapaAzul.setChecked(false);
                enableSwitchs();
                checkOnSwitchs();
            }
        });
        // radioBtMapaAzul OnClick
        radioBtMapaAzul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioBtMapaPadrao.setChecked(false);
                radioBtMapaCinza.setChecked(false);
                radioBtMapaRetro.setChecked(false);
                radioBtMapaEscuro.setChecked(false);
                radioBtMapaNoite.setChecked(false);
                radioBtMapaAzul.setChecked(true);
                enableSwitchs();
                checkOnSwitchs();
            }
        });
    }

    private void loadConfigurations() {
        String config = readConfigFileFromInternalStorage();
        try
        {
            // Update radioButtons
            JSONArray jsonArray = new JSONArray(config);
            JSONObject object = null;
            String stylers = null;
            JSONArray jsonArrayStylers = null;
            JSONObject objectStylers = null;
            String color = null;
            if (jsonArray.length() == 0)
            {
                color = "";
            }
            else
            {
                object = jsonArray.getJSONObject(0);
                stylers = object.get("stylers").toString();
                jsonArrayStylers = new JSONArray(stylers);
                objectStylers = jsonArrayStylers.getJSONObject(0);
                if (objectStylers.isNull("color"))
                {
                    color = "";
                }
                else
                {
                    color = objectStylers.get("color").toString();
                }
            }

            if (color.equals("#f5f5f5")) // Mapa Cinza
            {
                clearRadioButtons();
                radioBtMapaCinza.setChecked(true);
            }
            else if (color.equals("#ebe3cd")) // Mapa Retro
            {
                clearRadioButtons();
                radioBtMapaRetro.setChecked(true);
            }
            else if (color.equals("#212121")) // Mapa Escuro
            {
                clearRadioButtons();
                radioBtMapaEscuro.setChecked(true);
            }
            else if (color.equals("#242f3e")) // Mapa Noite
            {
                clearRadioButtons();
                radioBtMapaNoite.setChecked(true);
            }
            else if (color.equals("#1d2c4d")) // Mapa Azul
            {
                clearRadioButtons();
                radioBtMapaAzul.setChecked(true);
            }
            else // Mapa Padrao
            {
                clearRadioButtons();
                radioBtMapaPadrao.setChecked(true);
            }

            // Update switchs
            for (int i=0; i<jsonArray.length(); ++i)
            {
                JSONObject obj = jsonArray.getJSONObject(i);
                StyleRequest styleRequest = new Gson().fromJson(obj.toString(), StyleRequest.class);
                if (styleRequest.getFeatureType() != null)
                {
                    if (styleRequest.getElementType() != null)
                    {
                        if (styleRequest.getStylers() != null)
                        {
                            if (styleRequest.getStylers().get(0).getVisibility() != null)
                            {
                                if (styleRequest.getFeatureType().equals("road") && styleRequest.getElementType().equals("labels") && styleRequest.getStylers().get(0).getVisibility().equals("off"))
                                {
                                    switchNomeRuas.setChecked(false);
                                }
                                if (styleRequest.getFeatureType().equals("administrative.locality") && styleRequest.getElementType().equals("labels") && styleRequest.getStylers().get(0).getVisibility().equals("off"))
                                {
                                    switchBairrosCidades.setChecked(false);
                                }
                                if (styleRequest.getFeatureType().equals("poi.attraction") && styleRequest.getElementType().equals("labels") && styleRequest.getStylers().get(0).getVisibility().equals("off"))
                                {
                                    switchPontosTuristicos.setChecked(false);
                                }
                                if (styleRequest.getFeatureType().equals("poi.business") && styleRequest.getElementType().equals("labels") && styleRequest.getStylers().get(0).getVisibility().equals("off"))
                                {
                                    switchComercios.setChecked(false);
                                }
                                if (styleRequest.getFeatureType().equals("poi.government") && styleRequest.getElementType().equals("labels") && styleRequest.getStylers().get(0).getVisibility().equals("off"))
                                {
                                    switchOrgaosGoverno.setChecked(false);
                                }
                                if (styleRequest.getFeatureType().equals("poi.medical") && styleRequest.getElementType().equals("labels") && styleRequest.getStylers().get(0).getVisibility().equals("off"))
                                {
                                    switchHospitais.setChecked(false);
                                }
                                if (styleRequest.getFeatureType().equals("poi.park") && styleRequest.getElementType().equals("labels") && styleRequest.getStylers().get(0).getVisibility().equals("off"))
                                {
                                    switchParques.setChecked(false);
                                }
                                if (styleRequest.getFeatureType().equals("poi.place_of_worship") && styleRequest.getElementType().equals("labels") && styleRequest.getStylers().get(0).getVisibility().equals("off"))
                                {
                                    switchIgrejas.setChecked(false);
                                }
                                if (styleRequest.getFeatureType().equals("poi.school") && styleRequest.getElementType().equals("labels") && styleRequest.getStylers().get(0).getVisibility().equals("off"))
                                {
                                    switchEscolas.setChecked(false);
                                }
                                if (styleRequest.getFeatureType().equals("poi.sports_complex") && styleRequest.getElementType().equals("labels") && styleRequest.getStylers().get(0).getVisibility().equals("off"))
                                {
                                    switchComplexosEsportivos.setChecked(false);
                                }
                            }
                        }
                    }
                }
            }
            if (radioBtMapaCinza.isChecked())
            {
                checkOffSwitchs();
            }
            if (radioBtMapaEscuro.isChecked())
            {
                checkOffSwitchs();
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void writeConfigFileOnInternalStorage(String content) {
        try
        {
            FileOutputStream fOut = openFileOutput("config", MODE_PRIVATE);
            fOut.write(content.getBytes());
            fOut.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Falha ao salvar configurações", Toast.LENGTH_LONG).show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Falha ao salvar configurações", Toast.LENGTH_LONG).show();
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

    private void clearRadioButtons() {
        radioBtMapaPadrao.setChecked(false);
        radioBtMapaCinza.setChecked(false);
        radioBtMapaRetro.setChecked(false);
        radioBtMapaEscuro.setChecked(false);
        radioBtMapaNoite.setChecked(false);
        radioBtMapaAzul.setChecked(false);
    }

    private void disableSwitchs() {
        switchNomeRuas.setEnabled(true);
        switchBairrosCidades.setEnabled(true);
        switchPontosTuristicos.setEnabled(false);
        switchComercios.setEnabled(false);
        switchOrgaosGoverno.setEnabled(false);
        switchHospitais.setEnabled(false);
        switchParques.setEnabled(false);
        switchIgrejas.setEnabled(false);
        switchEscolas.setEnabled(false);
        switchComplexosEsportivos.setEnabled(false);
    }

    private void enableSwitchs() {
        switchNomeRuas.setEnabled(true);
        switchBairrosCidades.setEnabled(true);
        switchPontosTuristicos.setEnabled(true);
        switchComercios.setEnabled(true);
        switchOrgaosGoverno.setEnabled(true);
        switchHospitais.setEnabled(true);
        switchParques.setEnabled(true);
        switchIgrejas.setEnabled(true);
        switchEscolas.setEnabled(true);
        switchComplexosEsportivos.setEnabled(true);
    }

    private void checkOffSwitchs() {
        switchPontosTuristicos.setChecked(false);
        switchComercios.setChecked(false);
        switchOrgaosGoverno.setChecked(false);
        switchHospitais.setChecked(false);
        switchParques.setChecked(false);
        switchIgrejas.setChecked(false);
        switchEscolas.setChecked(false);
        switchComplexosEsportivos.setChecked(false);
    }

    private void checkOnSwitchs() {
        switchPontosTuristicos.setChecked(true);
        switchComercios.setChecked(true);
        switchOrgaosGoverno.setChecked(true);
        switchHospitais.setChecked(true);
        switchParques.setChecked(true);
        switchIgrejas.setChecked(true);
        switchEscolas.setChecked(true);
        switchComplexosEsportivos.setChecked(true);
    }

}