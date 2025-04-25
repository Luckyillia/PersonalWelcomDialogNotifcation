package com.example.personalwelcome;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        NotificationHelper.creatNotificationChannels(this);
        Button btn = findViewById(R.id.akcja);
        EditText editText = findViewById(R.id.editTextName);

        btn.setOnClickListener((v) -> {
            String name = editText.getText().toString().trim();
            System.out.println(name);
            if (name.isEmpty()) {
                showAlertDialog("Błąd", "Proszę wpisać swoje imię!", "OK","NO","","");
                return;
            }
            NotificationHelper.sendNotification(2,
                    2,
                    this,
                    "Witaj!",
                    "Miło Cię widzieć, "+name+"!",
                    1,0);
            showAlertDialog("Potwierdzenie","Cześć "+name+"! Czy chcesz otrzymać powiadomienie powitalne?","Tak, poproszę","Nie, dziękuję","Powiadomienie zostało wysłane!", "Rozumiem. Nie wysyłam powiadomienia.");
        });
    }

    private void showAlertDialog(String title, String text,String btnPositiveText, String btnNegativeText, String toastPositiveText, String toastNegativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title);
        builder.setMessage(text);

        builder.setPositiveButton(btnPositiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, toastPositiveText, Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton(btnNegativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, toastNegativeText, Toast.LENGTH_LONG).show();

            }
        });
        builder.create().show();
    }
}