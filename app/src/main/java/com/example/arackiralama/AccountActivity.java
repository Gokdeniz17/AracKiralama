package com.example.arackiralama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);

        // Firebase Authentication referansını al
        mAuth = FirebaseAuth.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            disableNavigationButtons();
        }
    }

    private void disableNavigationButtons() {
        // Oturum açmamışsa, diğer sayfalara geçiş butonlarını devre dışı bırak veya bir uyarı göster
        // Örneğin:
        findViewById(R.id.imageButton2).setEnabled(false);
        findViewById(R.id.imageButton).setEnabled(false);
    }
    public void onMovement(View view)
    {
        Intent intent=new Intent(AccountActivity.this,MovementActivity.class);
        startActivity(intent);
    }
    public void onHome(View view)
    {
        Intent intent=new Intent(AccountActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void KayitOl(View v){
        Intent intent=new Intent(AccountActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    public void GirisYap(View v){
        Intent intent=new Intent(AccountActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}