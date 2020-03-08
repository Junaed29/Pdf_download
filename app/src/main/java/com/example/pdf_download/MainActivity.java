package com.example.pdf_download;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Pdf_creator create_pdf;
    ArrayList<String> ids;
    ArrayList<String> percentage;
    String batch, course_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ids = new ArrayList<>();
        percentage = new ArrayList<>();

        ids.add("16204029");
        ids.add("16204029");
        ids.add("16204029");
        ids.add("16204029");
        ids.add("16204029");

        percentage.add("55");
        percentage.add("60");
        percentage.add("70");
        percentage.add("80");
        percentage.add("90");

        batch = "CSE_4th";
        course_id = "CSE2213";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.pdf_downloadId){
            create_pdf= new Pdf_creator(this);
            create_pdf.permission("Junaed",batch,course_id,ids,percentage);
        }
        return super.onOptionsItemSelected(item);
    }
}
