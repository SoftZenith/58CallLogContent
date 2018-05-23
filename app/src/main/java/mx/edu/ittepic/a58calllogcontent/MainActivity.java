package mx.edu.ittepic.a58calllogcontent;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    Button btnRespaldar;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRespaldar = findViewById(R.id.btnRespaldo);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CALL_LOG)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CALL_LOG},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        textView = findViewById(R.id.texto);
        ObtenerDatosLlamadas();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void ObtenerDatosLlamadas() {

        Uri uri;

        uri = Uri.parse("content://call_log/calls");

        String[] projeccion = new String[]{CallLog.Calls.TYPE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};

        Cursor c = getContentResolver().query(
                uri,
                projeccion,
                null,
                null,
                null);

        textView.setText("");

        while(c.moveToNext()){
            textView.append("Tipo: " + c.getString(0) + " Número: " + c.getString(1) + " Duración: " + c.getString(2) +"\n");
            TDACall nuevo = new TDACall(c.getString(0),c.getString(1),c.getString(2));
            /*
            String id = myRef.push().getKey();
            myRef.child("contacts").child(id).setValue(nuevo);*/
        }
        c.close();

    }

    public void respaldar(View view) {
        //myRef.setValue("Contacts");
        Uri uri;

        uri = Uri.parse("content://call_log/calls");

        String[] projeccion = new String[]{CallLog.Calls.TYPE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};

        Cursor c = getContentResolver().query(
                uri,
                projeccion,
                null,
                null,
                null);

        textView.setText("");

        while(c.moveToNext()){
            //textView.append("Tipo: " + c.getString(0) + " Número: " + c.getString(1) + " Duración: " + c.getString(2) +"\n");
            TDACall nuevo = new TDACall(c.getString(0),c.getString(1),c.getString(2));

            String id = myRef.push().getKey();
            myRef.child("contacts").child(id).setValue(nuevo);
        }
        c.close();

        ObtenerDatosLlamadas();
    }
}
