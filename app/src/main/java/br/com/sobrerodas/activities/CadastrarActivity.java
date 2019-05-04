package br.com.sobrerodas.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import br.com.sobrerodas.R;
import br.com.sobrerodas.models.Ponto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CadastrarActivity extends AppCompatActivity {

    // Interface components
    private Toolbar toolbar;
    private Button btnCadastrarPonto;
    private ImageButton imageButton;
    private Spinner spinnerDropDown;
    private RadioGroup radioGroupTemporario;
    private EditText etCadastrarDescricao;
    private TextView textViewPlace;
    private Dialog mDialog;
    private ProgressBar progressBar;

    // Firebase instances
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseInstance;

    private String currentPhotoPath;
    private ArrayList<String> categorias = new ArrayList<String>();
    private int selectedItem;
    private boolean temporario = false;
    private boolean imageCaptured = false;
    private String latitude;
    private String longitude;
    private String keyHashFirebase;

    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int REQUEST_CAMERA_AND_WRITE_STORAGE_PERMISISON = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        // Cancel focus of EditText
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Getting point's position from another activity
        latitude = getIntent().getExtras().getString("latitude");
        longitude = getIntent().getExtras().getString("longitude");
        // Firebase instance
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        initializeComponents();
        setAddressLine();
        updateCategorias();
    }

    private void initializeComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_cadastrar);
        btnCadastrarPonto = (Button) findViewById(R.id.btnCadastrarPonto);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        spinnerDropDown = (Spinner) findViewById(R.id.spinner1);
        radioGroupTemporario = (RadioGroup) findViewById(R.id.radioGroupTemporario);
        etCadastrarDescricao = (EditText) findViewById(R.id.etCadastrarDescricao);
        textViewPlace = (TextView) findViewById(R.id.textViewPlace);

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

        // Capturing image
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageCaptured = true;
                takePictureIntent();
            }
        });

        // Configuring Spinner
        spinnerDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Configuring RadioButtons
        radioGroupTemporario.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioBtSim)
                {
                    temporario = true;
                }
                else if (checkedId == R.id.radioBtNao)
                {
                    temporario = false;
                }
            }
        });

        // btnCadastrarPonto OnClick
        btnCadastrarPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFacebookLoggedIn() == false)
                {
                    showAlertDialog(getCurrentFocus(), "Atenção", "Faça login para cadastrar um local");
                }
                else
                {
                    // Show dialog
                    mDialog = new Dialog(CadastrarActivity.this);
                    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    mDialog.setContentView(R.layout.dialog);
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    mDialog.setCancelable(false);
                    progressBar = ProgressBar.class.cast(mDialog.findViewById(R.id.progressBarCadastrar));
                    mDialog.show();
                    // Insert on Firebase Realtime Database
                    insertOnFirebaseDatabase();
                    if (imageCaptured)
                    {
                        // Check if user is authenticated
                        mAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user == null)
                        {
                            signInFirebaseAnonymously();
                        }
                        else
                        {
                            uploadImageToFirebaseStorage();
                        }
                    }
                    else
                    {
                        mDialog.dismiss();
                        Toast.makeText(CadastrarActivity.this, "Cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                        MainActivity.telaCadastrarSucesso = true;
                        finish();
                    }
                }
            }
        });
    }

    private void setAddressLine() {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = null;
        try
        {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);
            if (addresses.size() > 0)
            {
                String zip = addresses.get(0).getAddressLine(0);
                textViewPlace.setText(zip);
            }
            else
            {
                textViewPlace.setText("");
            }
        }
        catch (IOException e)
        {
            textViewPlace.setText("");
        }
    }

    private void updateCategorias() {
        // Retrieving data from Firebase
        mFirebaseInstance = mFirebaseDatabase.getReference("Categorias");
        mFirebaseInstance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren())
                {
                    String categoria = objSnapshot.getValue().toString();
                    categorias.add(categoria);
                }
                // Adding categorias to Spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CadastrarActivity.this, android.R.layout.simple_spinner_item, categorias);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDropDown.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void takePictureIntent() {
        // Asking for permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_AND_WRITE_STORAGE_PERMISISON);
            return;
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            // Create the File where the photo should go
            File photoFile = null;
            try
            {
                photoFile = createImageFile();
            }
            catch (IOException ex)
            {
                // Error occurred while creating the File
                Log.e("ERRO", "Erro ao criar file: " + ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                addPictureToGallery();
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), "com.sobrerodas.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void addPictureToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPictureOnView() {
        // Get the dimensions of the View
        int targetW = imageButton.getWidth();
        int targetH = imageButton.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        // Set image to view
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageButton.setImageBitmap(bitmap);

        // Rotate image
        try
        {
            ExifInterface exif = new ExifInterface(currentPhotoPath);
            String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientation = orientString != null ? Integer.parseInt(orientString) :  ExifInterface.ORIENTATION_NORMAL;
            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bmOptions.outWidth, bmOptions.outHeight, matrix, true);
            // Rotate file
            try (FileOutputStream out = new FileOutputStream(currentPhotoPath))
            {
                rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            // Show rotated bitmap
            imageButton.setImageBitmap(rotatedBitmap);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void signInFirebaseAnonymously() {
        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                uploadImageToFirebaseStorage();
            }
        })
        .addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("ERRO", "signInAnonymously:FAILURE", exception);
            }
        });
    }

    private void insertOnFirebaseDatabase() {
        mFirebaseInstance = mFirebaseDatabase.getReference("Pontos");
        keyHashFirebase = mFirebaseInstance.push().getKey();
        Ponto ponto = new Ponto();
        ponto.setPontoId(keyHashFirebase);
        ponto.setLatitude(latitude);
        ponto.setLongitude(longitude);
        ponto.setTemporario(temporario);
        ponto.setCategoria(spinnerDropDown.getSelectedItem().toString());
        ponto.setDescricao(etCadastrarDescricao.getText().toString());
        ponto.setIdFacebookUser(Profile.getCurrentProfile().getId());
        mFirebaseInstance.child(ponto.getPontoId()).setValue(ponto);
    }

    private void uploadImageToFirebaseStorage() {
        // Compress image
        File filePhoto = new File(currentPhotoPath);
        Uri uriPhoto = Uri.fromFile(filePhoto);
        Bitmap bitmap = null;
        try
        {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriPhoto);
        }
        catch (FileNotFoundException e)
        {
            mDialog.dismiss();
            Toast.makeText(CadastrarActivity.this, "Não foi possível fazer o upload da imagem", Toast.LENGTH_SHORT).show();
            MainActivity.telaCadastrarSucesso = true;
            finish();
            return;
        }
        catch (IOException e)
        {
            mDialog.dismiss();
            Toast.makeText(CadastrarActivity.this, "Não foi possível fazer o upload da imagem", Toast.LENGTH_SHORT).show();
            MainActivity.telaCadastrarSucesso = true;
            finish();
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] data = baos.toByteArray();

        // Send to Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Uri file = Uri.fromFile(new File(currentPhotoPath));
        StorageReference riversRef = storageRef.child("pontos/" + keyHashFirebase + file.getLastPathSegment().substring(file.getLastPathSegment().lastIndexOf("."), file.getLastPathSegment().length()));
        UploadTask uploadTask = riversRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(CadastrarActivity.this, "Não foi possível fazer o upload da imagem", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(CadastrarActivity.this, "Cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                mDialog.dismiss();
                MainActivity.telaCadastrarSucesso = true;
                finish();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                int currentprogress = (int) progress;
                progressBar.setProgress(currentprogress);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_CAMERA_AND_WRITE_STORAGE_PERMISISON: {
                // Check if permission has been granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                {
                    // Permission Granted
                    takePictureIntent();
                }
                else
                {
                    // Permission Denied
                    Toast.makeText(this, "É preciso conceder permissão para tirar fotos", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            setPictureOnView();
        }
        else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_CANCELED)
        {
            imageCaptured = false;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setAddressLine();
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

}