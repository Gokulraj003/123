package com.function.gokulraj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class activity_manual extends AppCompatActivity {
    Button submit;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        UserID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        submit = findViewById(R.id.submitBtn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText gluEd = findViewById(R.id.gluEdit);
                EditText ResEd = findViewById(R.id.ResEdi);
                EditText BDEd = findViewById(R.id.BDEdi);
                EditText BPEd = findViewById(R.id.BPEdit);
                EditText OxyEd = findViewById(R.id.OXyEdi);


                String glu,Res,BD,BP,Oxy;
                glu = gluEd.getText().toString();
                Res = ResEd.getText().toString();
                BP = BPEd.getText().toString();
                BD = BDEd.getText().toString();
                Oxy = OxyEd.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                Map<String, Object> user = new HashMap<>();
                user.put("glucose", glu);
                user.put("Respiration", Res);
                user.put("Blood Pressure", BP);
                user.put("Body Temparature", BD);
                user.put("Oxygen Saturation", Oxy);


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String format = simpleDateFormat.format(new Date());
                DocumentReference subdocumentReference = db.collection("users").document(UserID).collection("values").document(format);

// Add a new document with a generated ID
                subdocumentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {


                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "onSuccess: user Profile is created for ");
                        Toast.makeText(getApplicationContext(),"added",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_LONG).show();
                    }
                });


            }
        });


    }
}