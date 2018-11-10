package com.ibrahim.flickerbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search){
            Intent intent = new Intent(this,SearchActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        activateToolbar(true);



        int intent=getIntent().getExtras().getInt("i");

        TextView author = findViewById(R.id.photo_auther);
        TextView title = findViewById(R.id.photo_title);
        TextView tags = findViewById(R.id.photo_tags);
        ImageView imageView = (ImageView) findViewById(R.id.photo_image);

        author.setText(GetFlickerJsonData.mPhotoList.get(intent).getAuthor());
        title.setText(GetFlickerJsonData.mPhotoList.get(intent).getTitle());
        tags.setText(GetFlickerJsonData.mPhotoList.get(intent).getTags());

        Picasso.get().load(GetFlickerJsonData.mPhotoList.get(intent).getImage())
                .error(R.drawable.placeholder)                   //place holder is an image icon
                .placeholder(R.drawable.placeholder)
                .into(imageView);


    }

}
