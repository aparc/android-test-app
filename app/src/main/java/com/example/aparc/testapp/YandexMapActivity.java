package com.example.aparc.testapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.road_events.EventType;
import com.yandex.mapkit.search.SearchManager;
import com.yandex.mapkit.search.SearchManagerType;
import com.yandex.mapkit.search.SearchOptions;
import com.yandex.mapkit.search_layer.PlacemarkListener;
import com.yandex.mapkit.search_layer.SearchLayer;
import com.yandex.mapkit.search_layer.SearchResultItem;
import com.yandex.mapkit.search_layer.SearchResultListener;
import com.yandex.mapkit.traffic.TrafficLayer;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;

import java.util.List;

public class YandexMapActivity extends AppCompatActivity implements PlacemarkListener {

    private MapView mapView;
    private TrafficLayer trafficLayer;
    private SearchLayer searchLayer;
    private SearchManager searchManager;
    private static final String TAG = YandexMapActivity.class.getSimpleName();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("api-key");
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_yandex_map);

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.getMap().move(
                new CameraPosition(new Point(59.949937, 30.314994), 14.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        trafficLayer = mapView.getMap().getTrafficLayer();
        searchManager = MapKitFactory.getInstance().createSearchManager(SearchManagerType.ONLINE);
        searchLayer = mapView.getMap().getSearchLayer();
        searchLayer.addSearchResultListener(new SearchResultListener() {
            @Override
            public void onSearchStart() {
            }

            @Override
            public void onSearchSuccess() {
                System.out.println("searchSuccess");
                List<SearchResultItem> visibleResults = searchLayer.getVisibleResults();
                for (SearchResultItem resultItem : visibleResults) {
                    System.out.println(resultItem.getGeoObject().getDescriptionText());
                }
            }

            @Override
            public void onSearchError(Error error) {

            }
        });

        searchLayer.addPlacemarkListener(new PlacemarkListener() {
            @Override
            public boolean onTap(SearchResultItem searchResultItem) {
                Toast.makeText(getApplicationContext(), searchResultItem.getGeoObject().getDescriptionText(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        searchLayer.submitQuery("пулково", new SearchOptions());
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

        mapView.getMap().addInputListener(new InputListener() {
            @Override
            public void onMapTap(Map map, Point point) {
                PlacemarkMapObject placemarkMapObject = map.getMapObjects().
                        addPlacemark(point, ImageProvider.fromResource(getApplicationContext(), R.drawable.selected));
            }

            @Override
            public void onMapLongTap(Map map, Point point) {
                map.getMapObjects().addCollection();
            }
        });

        trafficLayer.setRoadEventVisible(EventType.ACCIDENT, true);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onTap(SearchResultItem searchResultItem) {
        return false;
    }
}
