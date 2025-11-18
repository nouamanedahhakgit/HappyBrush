package com.mylancaster.gardens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {

    private LinearLayout emailLayout, phoneLayout, addressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        emailLayout = findViewById(R.id.layout_email);
        phoneLayout = findViewById(R.id.layout_phone);
        addressLayout = findViewById(R.id.layout_address);

        // 📧 Open email app
        emailLayout.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:support@lancastergardens.online"));
            startActivity(emailIntent);
        });

        // 📞 Call number
        phoneLayout.setOnClickListener(v -> {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
            phoneIntent.setData(Uri.parse("tel:+447922639390"));
            startActivity(phoneIntent);
        });

        // 📍 Open address on Maps
        addressLayout.setOnClickListener(v -> {
            String url = "geo:0,0?q=71-73 Hoghton Street, Southport PR9 0PR";
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(mapIntent);
        });
    }
}
