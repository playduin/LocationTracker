package playduin.locationtracker.models.locations.network;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import playduin.locationtracker.models.locations.Location;

public class TestLocationsNetwork implements LocationsNetwork {
    private Single<List<Location>> getLocationsValue;
    private Completable sendLocationValue;

    @Override
    public Single<List<Location>> getLocations(long startTime) {
        return getLocationsValue;
    }

    @Override
    public Completable sendLocation(List<Location> locations) {
        return sendLocationValue;
    }

    public void setGetLocationsValue(Single<List<Location>> getLocationsValue) {
        this.getLocationsValue = getLocationsValue;
    }

    public void setSendLocationValue(Completable sendLocationValue) {
        this.sendLocationValue = sendLocationValue;
    }
}
