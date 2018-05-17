package com.example.morfi.espacemembretuto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PremiereActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private TextView textView;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premiere);

        textView = (TextView) findViewById(R.id.tv_pseudo);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        sessionManager = new SessionManager(this);
        if (sessionManager.isLogged()){
            String pseudo = sessionManager.getPseudo();
            String id = sessionManager.getId();
            textView.setText(pseudo);
        }

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
