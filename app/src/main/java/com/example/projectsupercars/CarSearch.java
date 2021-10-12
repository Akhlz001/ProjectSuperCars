package com.example.projectsupercars;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CarSearch extends AppCompatActivity {

    EditText name, plate, mileage;
    Button insert, delete, view;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_search);

        name = findViewById(R.id.car);
        plate = findViewById(R.id.numberplate);
        mileage = findViewById(R.id.mileage);

        insert = findViewById(R.id.insertData);
        delete = findViewById(R.id.deleteData);
        view = findViewById(R.id.viewData);


        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehiclenameTXT = name.getText().toString();
                String numberplateTXT = plate.getText().toString();
                String mileageTXT = mileage.getText().toString();


                Boolean checkinsertdata = DB.insertvehicledata(vehiclenameTXT, numberplateTXT, mileageTXT);
                if(checkinsertdata==true)
                {
                    Toast.makeText(CarSearch.this, "New Vehicle Added", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(CarSearch.this, "Cannot add the same vehicle details twice!", Toast.LENGTH_SHORT).show();
                }

                try {

                    String addr = "https://cartaxcheck.co.uk/free-car-check/?vrm=LN11VUM";
                    URL url = new URL(addr);
                    HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                    httpCon.setUseCaches(false);
                    httpCon.setAllowUserInteraction(false);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehiclenameTXT = name.getText().toString();
                Boolean checkdeletedata = DB.deletevehicledata(vehiclenameTXT);
                if(checkdeletedata==true)
                {
                    Toast.makeText(CarSearch.this, "Vehicle deleted from database", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(CarSearch.this, "Cannot delete the same vehicle details twice!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getvehicledata();
                if(res.getCount()==0)
                {
                    Toast.makeText(CarSearch.this, "Vehicle does not exist in the database", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext())
                {
                    buffer.append("vehicleName :"+res.getString(0) +"\n");
                    buffer.append("numberPlate :"+res.getString(1) +"\n");
                    buffer.append("mileage :"+res.getString(2) +"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(CarSearch.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });
    }
}