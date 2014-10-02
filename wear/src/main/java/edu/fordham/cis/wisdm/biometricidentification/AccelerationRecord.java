package edu.fordham.cis.wisdm.biometricidentification;

/**
 * Created by andrew on 10/2/14.
 */
public class AccelerationRecord {

    private float x;
    private float y;
    private float z;
    private long timestamp;

    public AccelerationRecord(float _x, float _y, float _z, long _time) {
        x         = _x;
        y         = _y;
        z         = _z;
        timestamp = _time;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
