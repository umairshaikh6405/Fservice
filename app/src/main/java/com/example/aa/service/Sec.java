package com.example.aa.service;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Sec extends AppCompatActivity {

    Uri uri;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        imageView = findViewById(R.id.immmmmm);
    }

    public void neww(View view) {
        Bean bean = new Bean();
        bean.setName("umair");
        bean.setUri(uri);
       // Media.arrayList.add(bean);
        startActivity(new Intent(this, Media.class));
    }

    public void pic(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            uri = data.getData();
            imageView.setImageURI(uri);

    }
}
