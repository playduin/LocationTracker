package playduin.locationtracker.ui.tracker;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TestTrackerScreenState {
    @Test
    public void testCreateServiceRunningState() {
        TrackerContract.View trackerContractView = mock(TrackerContract.View.class);
        TrackerScreenState trackerScreenState = TrackerScreenState.createServiceRunningState(1, 2);
        trackerScreenState.visit(trackerContractView);

        verify(trackerContractView).setButtonText(1);
        verify(trackerContractView).setText(2);

        verifyNoMoreInteractions(trackerContractView);
    }

    @Test
    public void testCreateServiceStoppedState() {
        TrackerContract.View trackerContractView = mock(TrackerContract.View.class);
        TrackerScreenState trackerScreenState = TrackerScreenState.createServiceStoppedState(2, 1);
        trackerScreenState.visit(trackerContractView);

        verify(trackerContractView).setButtonText(2);
        verify(trackerContractView).setText(1);

        verifyNoMoreInteractions(trackerContractView);
    }

    @Test
    public void testCreateSetLocationsCountState() {
        TrackerContract.View trackerContractView = mock(TrackerContract.View.class);
        TrackerScreenState trackerScreenState = TrackerScreenState.createSetLocationsCountState(12345);
        trackerScreenState.visit(trackerContractView);

        verify(trackerContractView).setLocationsCount(12345);

        verifyNoMoreInteractions(trackerContractView);
    }

    @Test
    public void testCreateSetGPSState() {
        TrackerContract.View trackerContractView = mock(TrackerContract.View.class);
        TrackerScreenState trackerScreenState = TrackerScreenState.createSetGPSState(true);
        trackerScreenState.visit(trackerContractView);

        verify(trackerContractView).setGPSState(true);

        verifyNoMoreInteractions(trackerContractView);
    }
}
