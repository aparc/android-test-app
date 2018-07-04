package com.example.aparc.testapp;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.GeoObject;
import com.yandex.mapkit.GeoObjectCollection;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Geo;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.GeoObjectSelectionMetadata;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

public class YandexMapActivity extends AppCompatActivity {

    private MapView mapView;
    private Bitmap bitmap;
    private static final String TAG = YandexMapActivity.class.getSimpleName();



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("api-key");
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_yandex_map);

        bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.GREEN);

        mapView = (MapView)findViewById(R.id.mapview);
        mapView.getMap().move(
                new CameraPosition(new Point(55.964375, 38.465829), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH,0),
                null);
        mapView.getMap().getMapObjects().addPlacemark(new Point(55.964375, 38.465829),
                ImageProvider.fromBitmap(bitmap), new IconStyle());
        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "CLICK");
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
