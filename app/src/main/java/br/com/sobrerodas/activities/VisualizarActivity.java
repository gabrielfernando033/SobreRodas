package br.com.sobrerodas.activities;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import br.com.sobrerodas.R;
import br.com.sobrerodas.models.Ponto;

public class VisualizarActivity extends AppCompatActivity {

    // Interface
    private Toolbar toolbar;
    private ImageView imageViewVisualizar;
    private TextView txtViewLoading;
    private TextView txtViewCategoria;
    private TextView txtViewDescricao;
    private TextView txtViewErrado;
    private TextView txtViewCorreto;
    private LinearLayout linlayoutTemporario;
    private LinearLayout linlayoutContadores;
    private LinearLayout linlayoutBotoes;
    private Button btErrado;
    private Button btCorreto;
    private Button btRemoverLocal;

    // Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseInstance;
    private FirebaseAuth mFirebaseAuth;

    private Ponto ponto;
    private long contErrado;
    private long contCorreto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        initializeComponents();
        retrievePoint();
        retrieveValidacoes();
        retrieveImage();
        showButtons(ponto.getIdFacebookUser());
    }

    private void initializeComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbarVisualizar);
        imageViewVisualizar = (ImageView) findViewById(R.id.imageViewVisualizar);
        txtViewLoading = (TextView) findViewById(R.id.txtViewVisualizarLoading);
        txtViewCategoria = (TextView) findViewById(R.id.txtViewVisualizarCategoria);
        txtViewDescricao = (TextView) findViewById(R.id.txtViewVisualizarDescricao);
        txtViewErrado = (TextView) findViewById(R.id.txtViewErrado);
        txtViewCorreto = (TextView) findViewById(R.id.txtViewCorreto);
        linlayoutTemporario = (LinearLayout) findViewById(R.id.linlayoutVisualizarTemporario);
        linlayoutContadores = (LinearLayout) findViewById(R.id.linlayoutVisualizarContadores);
        linlayoutBotoes = (LinearLayout) findViewById(R.id.linlayoutVisualizarBotoes);
        btErrado = (Button) findViewById(R.id.btnErrado);
        btCorreto = (Button) findViewById(R.id.btnCorreto);
        btRemoverLocal = (Button) findViewById(R.id.btnRemoverLocal);

        // Set scroll
        txtViewDescricao.setMovementMethod(new ScrollingMovementMethod());

        // Add ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Display back button
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // btErrado OnClick
        btErrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFacebookLoggedIn() == false)
                {
                    showAlertDialog(getCurrentFocus(), "Atenção", "É preciso estar logado para validar um local");
                }
                else
                {
                    String idFacebookLoggedIn = Profile.getCurrentProfile().getId();
                    mFirebaseInstance = mFirebaseDatabase.getReference("Pontos").child(ponto.getPontoId()).child("validacoes").child(idFacebookLoggedIn);
                    mFirebaseInstance.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                showAlertDialog(getCurrentFocus(), "Atenção", "Você já validou esse local");
                            }
                            else
                            {
                                updateContadores();
                                if (contErrado == contCorreto)
                                {
                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which)
                                            {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    mFirebaseInstance = mFirebaseDatabase.getReference("Pontos");
                                                    mFirebaseInstance.child(ponto.getPontoId()).removeValue();
                                                    Toast.makeText(getApplicationContext(), "Local removido com sucesso!", Toast.LENGTH_LONG).show();
                                                    finish();
                                                    break;

                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    break;
                                            }
                                        }
                                    };
                                    showAlertDialogYesNo(dialogClickListener, "Atenção", "Você está prestes a remover esse local. Deseja continuar?");
                                }
                                else
                                {
                                    String idFacebookLoggedIn = Profile.getCurrentProfile().getId();
                                    // Insert into Firebase
                                    mFirebaseInstance = mFirebaseDatabase.getReference("Pontos").child(ponto.getPontoId()).child("validacoes").child(idFacebookLoggedIn);
                                    mFirebaseInstance.setValue(0);
                                    int contErrado = Integer.parseInt(txtViewErrado.getText().toString());
                                    txtViewErrado.setText((contErrado + 1) + "");
                                    Toast.makeText(getApplicationContext(), "Local validado com sucesso!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        // btCorreto OnClick
        btCorreto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFacebookLoggedIn() == false)
                {
                    showAlertDialog(getCurrentFocus(), "Atenção", "É preciso estar logado para validar um local");
                }
                else
                {
                    String idFacebookLoggedIn = Profile.getCurrentProfile().getId();
                    mFirebaseInstance = mFirebaseDatabase.getReference("Pontos").child(ponto.getPontoId()).child("validacoes").child(idFacebookLoggedIn);
                    mFirebaseInstance.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                showAlertDialog(getCurrentFocus(), "Atenção", "Você já validou esse local");
                            }
                            else
                            {
                                String idFacebookLoggedIn = Profile.getCurrentProfile().getId();
                                // Insert into Firebase
                                mFirebaseInstance = mFirebaseDatabase.getReference("Pontos").child(ponto.getPontoId()).child("validacoes").child(idFacebookLoggedIn);
                                mFirebaseInstance.setValue(1);
                                int contCorreto = Integer.parseInt(txtViewCorreto.getText().toString());
                                txtViewCorreto.setText((contCorreto + 1) + "");
                                btRemoverLocal.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Local validado com sucesso!", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        // btRemoverLocal OnClick
        btRemoverLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFacebookLoggedIn() == false)
                {
                    showAlertDialog(getCurrentFocus(), "Atenção", "É preciso estar logado para remover um local");
                }
                else if (contErrado > 0 || contCorreto > 0)
                {
                    showAlertDialog(getCurrentFocus(), "Atenção", "Esse local foi validado por outro(s) usuário(s). Não é possível remover o local agora.");
                }
                else
                {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which)
                            {
                                case DialogInterface.BUTTON_POSITIVE:
                                    mFirebaseInstance = mFirebaseDatabase.getReference("Pontos");
                                    mFirebaseInstance.child(ponto.getPontoId()).removeValue();
                                    Toast.makeText(getApplicationContext(), "Local removido com sucesso!", Toast.LENGTH_LONG).show();
                                    finish();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    showAlertDialogYesNo(dialogClickListener, "Atenção", "Você deseja realmente remover esse local?");
                }
            }
        });
    }

    private void retrievePoint() {
        // Getting string from another activity
        String pontoPassed = getIntent().getStringExtra("pontoPassed");
        ponto = new Gson().fromJson(pontoPassed, Ponto.class);
        if (ponto.getCategoria() != null)
        {
            txtViewCategoria.setText(ponto.getCategoria());
        }
        if (ponto.getDescricao() != null)
        {
            txtViewDescricao.setText(ponto.getDescricao());
        }
        if (ponto.getTemporario() == false)
        {
            linlayoutTemporario.setVisibility(View.GONE);
        }
        else if (ponto.getTemporario() == true)
        {
            linlayoutTemporario.setVisibility(View.VISIBLE);
        }
    }

    private void retrieveValidacoes() {
        mFirebaseInstance = mFirebaseDatabase.getReference("Pontos").child(ponto.getPontoId()).child("validacoes");
        // Setting contErrado
        Query query = mFirebaseInstance.orderByValue().equalTo(0);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contErrado = dataSnapshot.getChildrenCount();
                txtViewErrado.setText(contErrado + "");
                showButtons(ponto.getIdFacebookUser());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Setting contCorreto
        Query query2 = mFirebaseInstance.orderByValue().equalTo(1);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contCorreto = dataSnapshot.getChildrenCount();
                txtViewCorreto.setText(contCorreto + "");
                showButtons(ponto.getIdFacebookUser());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void retrieveImage() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user == null)
        {
            mFirebaseAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    downloadImageFromFirebaseStorage();
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("ERRO", "Não foi possivel baixar o arquivo");
                }
            });
        }
        else
        {
            downloadImageFromFirebaseStorage();
        }
    }

    private void downloadImageFromFirebaseStorage() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child("pontos/" + ponto.getPontoId() + ".jpg");
        final long MEGABYTE = 1024 * 1024 * 2;
        islandRef.getBytes(MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0, bytes.length);
                imageViewVisualizar.setImageBitmap(bitmap);
                txtViewLoading.getLayoutParams().height = 0;

                final float scale = getResources().getDisplayMetrics().density;
                int height  = (int) (150 * scale);
                imageViewVisualizar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                imageViewVisualizar.setImageResource(R.drawable.ic_broken_image);
                txtViewLoading.getLayoutParams().height = 0;

                final float scale = getResources().getDisplayMetrics().density;
                int height  = (int) (150 * scale);
                imageViewVisualizar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
            }
        });
    }

    private void updateContadores() {
        mFirebaseInstance = mFirebaseDatabase.getReference("Pontos").child(ponto.getPontoId()).child("validacoes");
        Query query = mFirebaseInstance.orderByValue().equalTo(0);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contErrado = dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Query query2 = mFirebaseInstance.orderByValue().equalTo(1);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contCorreto = dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showButtons(String idFacebookAtual) {
        if (contCorreto > 0 || contErrado > 0)
        {
            btRemoverLocal.setVisibility(View.GONE);
        }
        if (isFacebookLoggedIn() == false)
        {
            btRemoverLocal.setVisibility(View.GONE);
        }
        if (isFacebookLoggedIn())
        {
            String idFacebookLoggedIn = Profile.getCurrentProfile().getId();
            if (!idFacebookAtual.equals(idFacebookLoggedIn))
            {
                btRemoverLocal.setVisibility(View.GONE);
            }
        }
    }

    private boolean isFacebookLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        return isLoggedIn;
    }

    private void showAlertDialog(View view, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
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
}