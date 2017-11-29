package com.alejo_zr.exceldb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;

public class ManualUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_usuario);
        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset("manual1.pdf").load();

    }

    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()){
            case R.id.toolbar:
                intent = new Intent(ManualUsuario.this, MainActivity.class);
                startActivity(intent);
                break;

        }

    }
}
