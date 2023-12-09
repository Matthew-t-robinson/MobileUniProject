package uk.ac.tees.mgd.a0083681.mobileuniproject.Input;

import android.app.Activity;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public abstract class ShakeActivity extends Activity implements SensorEventListener {
    private SensorManager sensorMgr;
    public ShakeActivity(){
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
}
