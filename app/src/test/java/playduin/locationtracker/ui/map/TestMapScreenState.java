package playduin.locationtracker.ui.map;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import playduin.locationtracker.models.locations.Marker;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TestMapScreenState {
    @Test
    public void testCreateAddMarkersState() {
        List<Marker> markers = new ArrayList<>();
        markers.add(new Marker(new LatLng(1.23, 3.21), "Test"));
        markers.add(new Marker(new LatLng(3.21, 1.23), "Test 2"));

        MapContract.View mapContractView = mock(MapContract.View.class);
        MapScreenState mapScreenState = MapScreenState.createAddMarkersState(markers);
        mapScreenState.visit(mapContractView);

        verify(mapContractView).mapAddMarkers(markers);

        verifyNoMoreInteractions(mapContractView);
    }

    @Test
    public void testCreateMoveCameraState() {
        LatLng pos = new LatLng(1.23, 3.21);

        MapContract.View mapContractView = mock(MapContract.View.class);
        MapScreenState mapScreenState = MapScreenState.createMoveCameraState(pos);
        mapScreenState.visit(mapContractView);

        verify(mapContractView).mapMoveCamera(pos);

        verifyNoMoreInteractions(mapContractView);
    }

    @Test
    public void testCreateClearMapState() {
        MapContract.View mapContractView = mock(MapContract.View.class);
        MapScreenState mapScreenState = MapScreenState.createClearMapState();
        mapScreenState.visit(mapContractView);

        verify(mapContractView).mapClear();

        verifyNoMoreInteractions(mapContractView);
    }
}
