package com.jeupouradulte.monpoker;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.jeupouradulte.monpoker.adapters.PageAdapter;
import com.jeupouradulte.monpoker.base.BaseActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class Home extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    public int getFragmentLayout() { return R.layout.activity_home; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.configureToolbar();
        this.configureViewPagerAndTabs();

        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureNavHeader();
    }

    // VIEW PAGER

    private void configureViewPagerAndTabs(){

        ViewPager pager = (ViewPager)findViewById(R.id.activity_main_viewpager);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        TabLayout tabs= (TabLayout) findViewById(R.id.activity_main_tabs);
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    // TOOLBAR

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_activity_main_params:
                Toast.makeText(this, "Il n'y a rien à paramétrer ici, passez votre chemin...", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_activity_main_search:
                Toast.makeText(this, "Recherche indisponible, demandez plutôt l'avis de Google, c'est mieux et plus rapide.", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    protected void configureToolbar(){

        // Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Picasso.with(this)
                .load(this.getCurrentUser().getPhotoUrl())
                .transform(new CropCircleTransformation())
                .into(new Target()
                {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                    {
                        Drawable logo = new BitmapDrawable(getResources(), bitmap);
                        toolbar.setNavigationIcon(logo);
                    }
                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) { }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) { }
                });

        toolbar.setTitle("PicksManager");

        /*
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Back Arrow Toolbar Image Icon Clicked", Toast.LENGTH_LONG).show();
            }
        });
        */
    }


    // Navigation Drawer

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.activity_home_drawer_news :
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.home_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void configureNavHeader(){

        View headView = navigationView.getHeaderView(0);
        ImageView imgProfile = headView.findViewById(R.id.imageView);
        Glide.with(imgProfile)
                .load(this.getCurrentUser().getPhotoUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(imgProfile);

        TextView prenomProfile = headView.findViewById(R.id.prenom);
        prenomProfile.setText(this.getCurrentUser().getDisplayName());
    }

}