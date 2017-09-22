package cz.nasa.fallensky;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cz.nasa.fallensky.data.Meteorit;
import cz.nasa.fallensky.utils.MyConstants;
import io.realm.Realm;

import static cz.nasa.fallensky.R.id.map;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    int id;
    Meteorit meteoritCopy;
    private GoogleMap mMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng landing = new LatLng(meteoritCopy.reclat, meteoritCopy.reclong);
        MarkerOptions marker = new MarkerOptions().position(landing).title(getString(R.string.landing_area));

        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(4));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(landing));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        id = getIntent().getIntExtra(MyConstants.ID,0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Realm realm = Realm.getDefaultInstance();
        Meteorit meteorit = realm.where(Meteorit.class).equalTo("id",id).findFirst();
        meteoritCopy = realm.copyFromRealm(meteorit);
        setTitle(meteorit.name+" ("+meteorit.mass+"g)");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);
    }
}
