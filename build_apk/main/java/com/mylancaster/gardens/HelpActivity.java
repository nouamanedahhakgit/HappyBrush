package com.mylancaster.gardens;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    private TextView helpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        helpTextView = findViewById(R.id.tv_help_content);

        helpTextView.setText(
            "📘 HELP & SUPPORT\n\n" +
            "Welcome to Lancaster Gardens RTM Portal.\n\n" +
            "If you need assistance, here are the ways we can help you:\n\n" +

            "💡 What you can do in this portal:\n" +
            "• Contact management\n" +
            "• Access member services\n\n" +

            "📞 Contact Support:\n" +
            "• Email: support@lancastergardens.online\n" +
            "• Phone: +44 7512 345678\n\n" +

            "📍 Address:\n" +
            "71-73 Hoghton Street,\nSouthport, PR9 0PR\n\n" +

            "Thank you for being part of Lancaster Gardens RTM!"
        );
    }
}
