package playduin.locationtracker.ui.tracker;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TestTrackerScreenAction {
    @Test
    public void testCreateStartTrackerAction() {
        TrackerContract.View trackerContractView = mock(TrackerContract.View.class);
        TrackerScreenAction trackerScreenAction = TrackerScreenAction.createStartTrackerAction();
        trackerScreenAction.visit(trackerContractView);

        verify(trackerContractView).startTracker();

        verifyNoMoreInteractions(trackerContractView);
    }

    @Test
    public void testCreateStopTrackerAction() {
        TrackerContract.View trackerContractView = mock(TrackerContract.View.class);
        TrackerScreenAction trackerScreenAction = TrackerScreenAction.createStopTrackerAction();
        trackerScreenAction.visit(trackerContractView);

        verify(trackerContractView).stopTracker();

        verifyNoMoreInteractions(trackerContractView);
    }

    @Test
    public void testCreateShowLogoutAlertDialog() {
        TrackerContract.View trackerContractView = mock(TrackerContract.View.class);
        TrackerScreenAction trackerScreenAction = TrackerScreenAction.createShowLogoutAlertDialog();
        trackerScreenAction.visit(trackerContractView);

        verify(trackerContractView).showLogoutAlertDialog();

        verifyNoMoreInteractions(trackerContractView);
    }

    @Test
    public void testCreateShowLoginFragmentAction() {
        TrackerContract.View trackerContractView = mock(TrackerContract.View.class);
        TrackerScreenAction trackerScreenAction = TrackerScreenAction.createShowLoginFragmentAction();
        trackerScreenAction.visit(trackerContractView);

        verify(trackerContractView).showLoginFragment();

        verifyNoMoreInteractions(trackerContractView);
    }

    @Test
    public void testCreateRequestPermissionAction() {
        TrackerContract.View trackerContractView = mock(TrackerContract.View.class);
        TrackerScreenAction trackerScreenAction = TrackerScreenAction.createRequestPermissionAction();
        trackerScreenAction.visit(trackerContractView);

        verify(trackerContractView).requestPermission();

        verifyNoMoreInteractions(trackerContractView);
    }
}
