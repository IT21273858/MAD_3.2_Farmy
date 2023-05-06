package com.example.farmy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import cz.msebera.android.httpclient.Header;
import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import static
import kotlin.coroutines.jvm.internal.CompletedContinuation.context
junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import androidx.test.ext.junit.runners.AndroidJUnit4
@RunWith(AndroidJUnit4.class)
        public class WeatherUnitTest {

    @Mock
    LocationManager mockLocationManager;

    @Mock
    LocationListener mockLocationListener;

    @Mock
    AsyncHttpClient mockHttpClient;

    Weather weatherActivity;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        Context context = ApplicationProvider . getApplicationContext ();
        when (context.checkCallingOrSelfPermission(any())).thenReturn(PackageManager.PERMISSION_GRANTED);

        weatherActivity = mock(Weather.class);
        weatherActivity.mLocationManager = mockLocationManager;
        weatherActivity.mLocationListner = mockLocationListener;
        weatherActivity.mweatherIcon = mock(ImageView.class);
        weatherActivity.Temperature = mock(TextView.class);
        weatherActivity.weatherState = mock(TextView.class);
        weatherActivity.NameofCity = mock(TextView.class);
        weatherActivity.mCityFinder = mock(RelativeLayout.class);
        weatherActivity.client = mockHttpClient;
    }

    @Test
    public void getWeatherForCurrentLocation()
    {
        Context context = ApplicationProvider . getApplicationContext ();
        when (mockLocationManager.isProviderEnabled(any())).thenReturn(true);
        when (mockLocationManager.getLastKnownLocation(any())).thenReturn(mock(Location.class));
        when (mockLocationManager.getProvider(any())).thenReturn(mock(LocationProvider.class));
        when (mockLocationManager.getProviders(any())).thenReturn(mock(List.class));
        when (mockLocationManager.isProviderEnabled(any())).thenReturn(true);
        when (mockLocationListenerLooper.getMainLooper()).thenReturn(Looper.getMainLooper());

        weatherActivity.getWeatherForCurrentLocation();

        assertNotNull(weatherActivity.mLocationListner);
        assertNotNull(weatherActivity.mLocationManager);
        assertTrue(weatherActivity.mLocationManager.isProviderEnabled(any()));
    }
}