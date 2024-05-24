package com.example.arackiralama;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText  editEmail, editSifre;
    private String  txtEmail, txtSifre;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private HashMap<String, Object> mData;
    private FirebaseFirestore mFirestore;
    private DatabaseReference mReferance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        editEmail = (EditText) findViewById(R.id.GirisTextEmail);
        editSifre = (EditText) findViewById(R.id.GirisTextPassword);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

    }

    public void GirisYapGeriButton(View v){
        Intent intent =new Intent(LoginActivity.this,AccountActivity.class);
        startActivity(intent);
    }

    public void GirisYapToKayitOl(View v){
        Intent intent =new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void GirisYap(View v){
        txtEmail = editEmail.getText().toString();
        txtSifre = editSifre.getText().toString();

        if(!TextUtils.isEmpty(txtEmail) && !TextUtils.isEmpty(txtSifre)){
            mAuth.signInWithEmailAndPassword(txtEmail,txtSifre)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mUser=mAuth.getCurrentUser();

                            mReferance=FirebaseDatabase.getInstance().getReference("Kullanıcılar").child(mUser.getUid());
                            mReferance.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    for(DataSnapshot snp:snapshot.getChildren()){
                                        System.out.println(snp.getKey()+"="+snp.getValue());
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(LoginActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });








                            Toast.makeText(LoginActivity.this,"Giriş başarılı",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);



                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });

        }else{
            Toast.makeText(this,"Bilgiler boş olamaz!",Toast.LENGTH_SHORT).show();
        }

    }
}