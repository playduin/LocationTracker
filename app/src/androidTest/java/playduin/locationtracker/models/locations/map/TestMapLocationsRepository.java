package playduin.locationtracker.models.locations.map;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import playduin.locationtracker.models.locations.Location;

public class TestMapLocationsRepository implements MapLocationsRepository {
    private Single<List<Location>> updateLocationsValue;
    private Single<List<Location>> getLocationsForDateValue;

    @Override
    public Single<List<Location>> updateLocations() {
        return updateLocationsValue;
    }

    @Override
    public Single<List<Location>> getLocationsForDate(long date) {
        return getLocationsForDateValue;
    }

    public void setUpdateLocationsValue(Single<List<Location>> updateLocationsValue) {
        this.updateLocationsValue = updateLocationsValue;
    }

    public void setGetLocationsForDateValue(Single<List<Location>> getLocationsForDateValue) {
        this.getLocationsForDateValue = getLocationsForDateValue;
    }
}
