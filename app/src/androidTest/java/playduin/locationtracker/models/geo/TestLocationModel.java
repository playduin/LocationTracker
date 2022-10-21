package playduin.locationtracker.models.geo;

import io.reactivex.rxjava3.core.Observable;
import playduin.locationtracker.models.locations.Location;

public class TestLocationModel implements LocationModel {
    private Observable<Boolean> requestLocationAvailabilityValue;
    private Observable<Location> requestLocationValue;

    @Override
    public Observable<Boolean> requestLocationAvailability() {
        return requestLocationAvailabilityValue;
    }

    @Override
    public Observable<Location> requestLocation() {
        return requestLocationValue;
    }

    @Override
    public void createLocationRequest() {

    }

    @Override
    public void removeLocationRequest() {

    }

    public void setRequestLocationAvailabilityValue(Observable<Boolean> requestLocationAvailabilityValue) {
        this.requestLocationAvailabilityValue = requestLocationAvailabilityValue;
    }

    public void setRequestLocationValue(Observable<Location> requestLocationValue) {
        this.requestLocationValue = requestLocationValue;
    }
}
