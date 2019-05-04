package br.com.sobrerodas.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.sobrerodas.R;
import br.com.sobrerodas.models.Feedback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editTextNome;
    private EditText editTextCidade;
    private EditText editTextConteudo;
    private Button btnEnviarSugestao;

    // Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        // Firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        initializeComponents();
    }

    private void initializeComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbarFeedback);
        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextCidade = (EditText) findViewById(R.id.editTextCidade);
        editTextConteudo = (EditText) findViewById(R.id.editTextConteudo);
        btnEnviarSugestao = (Button) findViewById(R.id.btnEnviarSugestao);

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

        btnEnviarSugestao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (editTextNome.getText().toString().equals(""))
                {
                    showAlertDialog(getCurrentFocus(), "Atenção", "Insira o seu nome");
                }
                else if (editTextCidade.getText().toString().equals(""))
                {
                    showAlertDialog(getCurrentFocus(), "Atenção", "Insira a sua cidade");
                }
                else if (editTextConteudo.getText().toString().equals(""))
                {
                    showAlertDialog(getCurrentFocus(), "Atenção", "Insira sua mensagem");
                }
                else
                {
                    salvarNoFirebase();
                }
            }
        });
    }

    private void salvarNoFirebase() {
        // Inserting into Firebase
        mFirebaseInstance = mFirebaseDatabase.getReference("Feedback");
        Feedback feedback = new Feedback();
        feedback.setNome(editTextNome.getText().toString());
        feedback.setCidade(editTextCidade.getText().toString());
        feedback.setConteudo(editTextConteudo.getText().toString());
        String nextID = mFirebaseInstance.push().getKey();
        mFirebaseInstance.child(nextID).setValue(feedback);
        Toast.makeText(FeedbackActivity.this, "Feedback enviado com sucesso!", Toast.LENGTH_LONG).show();
        finish();
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

}