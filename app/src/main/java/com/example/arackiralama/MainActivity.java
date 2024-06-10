package com.example.arackiralama;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


    public class MainActivity extends AppCompatActivity {
        ImageButton resimbuton1,resimbuton2,resimbuton3;

        private android.widget.SearchView searchview;
        private CarAdapter adapter;
        private RecyclerView recyclerView;
        List<CarModel.Car> cars=new ArrayList<>();
        //Ayrı ayrı nesne oluşturunca arada sayfa boşluğu oluyor.
        CarModel carModel=new CarModel();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);

            resimbuton1 = findViewById(R.id.imageButton);
            resimbuton2 = findViewById(R.id.imageButton2);
            resimbuton3 = findViewById(R.id.imageButton3);

            searchview = findViewById(R.id.searchView);
            searchview.clearFocus();



    searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            List<CarModel.Car> filteredCars = new ArrayList<>();
            String lowerCaseNewText = newText.toLowerCase(); // Arama metnini küçük harfe dönüştür

            for (CarModel.Car car : carModel.getCars()) {
                String brand = car.getBrand().toLowerCase();
                String model = car.getModel().toLowerCase();
                String year = car.getYear().toLowerCase();
                String color = car.getColor().toLowerCase();
                // Marka, model, yıl veya renkte arama metnini içeren arabaları filtrele
                if (brand.contains(lowerCaseNewText) || model.contains(lowerCaseNewText) ||
                        year.contains(lowerCaseNewText) || color.contains(lowerCaseNewText)) {
                    filteredCars.add(car);
                }
            }
            recyclerView.setVisibility(View.VISIBLE);

            adapter.setCars(filteredCars); // Filtrelenmiş araçları adapter'a ayarla
            return true;
        }
    });


            recyclerView=findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));





            adapter=new CarAdapter(carModel.getCars());
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new CarAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    CarModel.Car clickedCar = adapter.getCars().get(position);

                    if(clickedCar.getBrand().equals("Hyundai") && clickedCar.getModel().equals("i20")
                            && clickedCar.getYear().equals("2022") && clickedCar.getColor().equals("Fume")){
                        Intent intent=new Intent(MainActivity.this, hyundaii20.class);
                        startActivity(intent);
                    } else if (clickedCar.getBrand().equals("Fiat") && clickedCar.getModel().equals("Egea") && clickedCar.getYear().equals("2022") && clickedCar.getColor().equals("Beyaz")){
                        Intent intent1=new Intent(MainActivity.this, fiategea.class);
                        startActivity(intent1);
                    } else if (clickedCar.getBrand().equals("Toyota") && clickedCar.getModel().equals("Corolla") && clickedCar.getYear().equals("2023") && clickedCar.getColor().equals("Mavi")){
                        Intent intent2=new Intent(MainActivity.this, toyotacorolla.class);
                        startActivity(intent2);
                    } else if (clickedCar.getBrand().equals("Renault") && clickedCar.getModel().equals("Megane") && clickedCar.getYear().equals("2020") && clickedCar.getColor().equals("Lacivert")){
                        Intent intent3=new Intent(MainActivity.this, renaultmegane.class);
                        startActivity(intent3);
                    } else if (clickedCar.getBrand().equals("Opel") && clickedCar.getModel().equals("Astra") && clickedCar.getYear().equals("2012") && clickedCar.getColor().equals("Beyaz")){
                        Intent intent4=new Intent(MainActivity.this, opelastra.class);
                        startActivity(intent4);
                    } else if (clickedCar.getBrand().equals("Skoda") && clickedCar.getModel().equals("Scala") && clickedCar.getYear().equals("2020") && clickedCar.getColor().equals("Beyaz")){
                        Intent intent5=new Intent(MainActivity.this, skodascala.class);
                        startActivity(intent5);
                    } else if (clickedCar.getBrand().equals("Ford") && clickedCar.getModel().equals("Focus") && clickedCar.getYear().equals("2023") && clickedCar.getColor().equals("Mavi")){
                        Intent intent6=new Intent(MainActivity.this, fordfocus.class);
                        startActivity(intent6);
                    } else if (clickedCar.getBrand().equals("Toyota") && clickedCar.getModel().equals("C-HR") && clickedCar.getYear().equals("2017") && clickedCar.getColor().equals("Gri")){
                        Intent intent7=new Intent(MainActivity.this, toyotachr.class);
                        startActivity(intent7);
                    } else if (clickedCar.getBrand().equals("Bmw") && clickedCar.getModel().equals("216d") && clickedCar.getYear().equals("2020") && clickedCar.getColor().equals("Mavi")){
                        Intent intent8=new Intent(MainActivity.this, bmw216d.class);
                        startActivity(intent8);
                    } else if (clickedCar.getBrand().equals("Mercedes") && clickedCar.getModel().equals("EQE") && clickedCar.getYear().equals("2022") && clickedCar.getColor().equals("Gri")){
                        Intent intent9=new Intent(MainActivity.this, mercedeseqe.class);
                        startActivity(intent9);
                    }
                }
            });



            carModel.add("Hyundai","i20","2022","Fume");
            carModel.add("Fiat","Egea","2022","Beyaz");
            carModel.add("Toyota","Corolla","2023","Mavi");
            carModel.add("Renault","Megane","2020","Lacivert");
            carModel.add("Opel","Astra","2012","Beyaz");
            carModel.add("Skoda","Scala","2020","Beyaz");
            carModel.add("Ford","Focus","2023","Mavi");
            carModel.add("Toyota","C-HR","2017","Gri");
            carModel.add("Bmw","216d","2020","Mavi");
            carModel.add("Mercedes","EQE","2022","Gri");




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    public void onMovement(View view)
    {
        Intent intent=new Intent(MainActivity.this,MovementActivity.class);
        startActivity(intent);
    }

    public void onAccount(View view)
    {
        Intent intent=new Intent(MainActivity.this,AccountActivity.class);
        startActivity(intent);
    }
    public void i20click(View view)
    {
        Intent intent=new Intent(MainActivity.this, hyundaii20.class);
        startActivity(intent);
    }

    public void corollaclick(View view)
    {
        Intent intent=new Intent(MainActivity.this, toyotacorolla.class);
        startActivity(intent);
    }
    public void chrclick(View view)
    {
        Intent intent=new Intent(MainActivity.this, toyotachr.class);
        startActivity(intent);
    }
    public void egeaclick(View view)
    {
        Intent intent=new Intent(MainActivity.this, fiategea.class);
        startActivity(intent);
    }
    public void meganeclick(View view)
    {
        Intent intent=new Intent(MainActivity.this, renaultmegane.class);
        startActivity(intent);
    }
    public void astraclick(View view)
    {
        Intent intent=new Intent(MainActivity.this, opelastra.class);
        startActivity(intent);
    }
    public void scalaclick(View view)
    {
        Intent intent=new Intent(MainActivity.this, skodascala.class);
        startActivity(intent);
    }
    public void focusclick(View view)
    {
        Intent intent=new Intent(MainActivity.this, fordfocus.class);
        startActivity(intent);
    }
    public void bmwclick(View view)
    {
        Intent intent=new Intent(MainActivity.this, bmw216d.class);
        startActivity(intent);
    }
    public void mercedesclick(View view)
    {
        Intent intent=new Intent(MainActivity.this, mercedeseqe.class);
        startActivity(intent);
    }

}