package com.beybi.beyblos;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Basit bir liste görünümü oluşturuyoruz
        ListView listView = new ListView(this);
        setContentView(listView);

        // Telefonunuzdaki yüklü uygulamaları alıyoruz
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> apps = getPackageManager().queryIntentActivities(intent, 0);

        ArrayList<String> appNames = new ArrayList<>();
        for (int i = 0; i < apps.size(); i++) {
            appNames.add(apps.get(i).loadLabel(getPackageManager()).toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appNames);
        listView.setAdapter(adapter);

        // Listeden bir uygulamaya tıklandığında açılması için
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResolveInfo appInfo = apps.get(position);
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(appInfo.activityInfo.packageName);
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Launcher'da geri tuşu ana ekranı kapatmasın diye boş bırakıyoruz
    }
}
