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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText editIsim, editEmail, editSifre;
    private String txtIsim, txtEmail, txtSifre;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;
    private HashMap<String,Object> mData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        editIsim = (EditText) findViewById(R.id.editTextName);
        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editSifre = (EditText) findViewById(R.id.editTextPassword);

        mAuth=FirebaseAuth.getInstance();
        mReference= FirebaseDatabase.getInstance().getReference();
    }

    public void Kayitol(View v) {
        txtIsim = editIsim.getText().toString();
        txtEmail = editEmail.getText().toString();
        txtSifre = editSifre.getText().toString();

        if (!TextUtils.isEmpty(txtIsim) && !TextUtils.isEmpty(txtEmail) && (!TextUtils.isEmpty(txtSifre))) {
            mAuth.createUserWithEmailAndPassword(txtEmail,txtSifre)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mUser=mAuth.getCurrentUser();

                                mData=new HashMap<>();
                                mData.put("KullaniciAdi",txtIsim);
                                mData.put("KullaniciEmail",txtEmail);
                                mData.put("KullaniciSifre",txtSifre);
                                mData.put("KullaniciId",mUser.getUid());

                                mReference.child("Kullanıcılar").child(mUser.getUid())
                                                .setValue(mData)
                                                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){




                                                                }else{
                                                                    Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                Toast.makeText(RegisterActivity.this,"Kayıt başarılı",Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(RegisterActivity.this,AccountActivity.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



        }else{
            Toast.makeText(this,"Bilgiler boş olamaz!",Toast.LENGTH_SHORT).show();
        }
    }

    public void KayitolGeriButton(View v){
        Intent intent=new Intent(RegisterActivity.this,AccountActivity.class);
        startActivity(intent);
    }

    public void KayitOlToGirisYap(View v){
        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}