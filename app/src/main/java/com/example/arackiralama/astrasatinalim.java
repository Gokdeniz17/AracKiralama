package com.example.arackiralama;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class astrasatinalim extends AppCompatActivity {




    public static boolean visibellel5;
    DatePicker datepicker,datepicker2;
    int yil,ay,gun;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_astrasatinalim);

        datepicker=findViewById(R.id.datepicker3);
        datepicker2=findViewById(R.id.datepicker4);
        Calendar bugun=Calendar.getInstance();
        Calendar mindate=Calendar.getInstance();
        yil=bugun.get(Calendar.YEAR);
        ay=bugun.get(Calendar.MONTH);
        gun=bugun.get(Calendar.DAY_OF_MONTH);

        datepicker.init(yil, ay, gun, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mindate.set(year,monthOfYear,(dayOfMonth+1));
                datepicker2.setMinDate(mindate.getTimeInMillis());

            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void backclick(View view)
    {
        Intent intent=new Intent(astrasatinalim.this,opelastra.class);
        startActivity(intent);
    }
    public void Onayla(View view) {
        // Firebase Authentication ile kullanıcı bilgilerini al
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // Kullanıcı oturum açmışsa işlemi gerçekleştir
            // Tarih seçimini kontrol et
            int year1 = datepicker.getYear();
            int month1 = datepicker.getMonth();
            int day1 = datepicker.getDayOfMonth();

            int year2 = datepicker2.getYear();
            int month2 = datepicker2.getMonth();
            int day2 = datepicker2.getDayOfMonth();

            // Seçili tarihleri bir Calendar nesnesine dönüştür
            Calendar selectedDate1 = Calendar.getInstance();
            selectedDate1.set(year1, month1, day1);

            Calendar selectedDate2 = Calendar.getInstance();
            selectedDate2.set(year2, month2, day2);

            // Bugünün tarihini al
            Calendar today = Calendar.getInstance();

            // Seçili tarihler bugünden ilerideyse Firestore'a kiralama bilgilerini ekle
            if (selectedDate1.after(today) || selectedDate2.after(today)) {
                // Firestore bağlantısını oluştur
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                String uid = currentUser.getUid();

                // Firestore'a kiralama bilgilerini ekleme
                Map<String, Object> kiralamaBilgileri = new HashMap<>();
                kiralamaBilgileri.put("Araç Model", "Opel Astra"); // Burada aracın kimliğini eklemelisiniz
                kiralamaBilgileri.put("kiralama_baslangic_tarihi", selectedDate1.getTime());
                kiralamaBilgileri.put("kiralama_bitis_tarihi", selectedDate2.getTime());

                db.collection("Kullanıcılar.").document(uid).collection("Kiralananlar")
                        .add(kiralamaBilgileri)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(astrasatinalim.this, "Kiralama bilgileri başarıyla eklendi:" + documentReference.getId(), Toast.LENGTH_SHORT).show();
                            }
                        });

                // AlertDialog oluştur
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Opel Astra aracı başarıyla kiralandı")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // MovementActivity'e geçiş yap
                                Intent intent = new Intent(astrasatinalim.this, MovementActivity.class);
                                visibellel5 = true;
                                startActivity(intent);
                            }
                        });
                // AlertDialog'u göster
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                // Seçilen tarihler bugünden önceyse kullanıcıya uyarı ver
                Toast.makeText(this, "Lütfen uygun bir tarih seçin", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Kullanıcı oturum açmamışsa uyarı göster
            Toast.makeText(this, "Oturum açmadan araç kiralayamazsınız", Toast.LENGTH_SHORT).show();
        }
    }
}
