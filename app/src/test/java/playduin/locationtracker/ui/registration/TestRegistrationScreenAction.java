package playduin.locationtracker.ui.registration;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TestRegistrationScreenAction {
    @Test
    public void testCreateSetRegistrationFragmentAction() {
        RegistrationContract.View view = mock(RegistrationContract.View.class);
        RegistrationScreenAction registrationScreenAction = RegistrationScreenAction.createSetLoginFragmentAction();
        registrationScreenAction.visit(view);

        verify(view).setLoginFragment();

        verifyNoMoreInteractions(view);
    }

    @Test
    public void testCreateSetTrackerFragmentAction() {
        RegistrationContract.View view = mock(RegistrationContract.View.class);
        RegistrationScreenAction registrationScreenAction = RegistrationScreenAction.createSetTrackerFragmentAction();
        registrationScreenAction.visit(view);

        verify(view).setTrackerFragment();

        verifyNoMoreInteractions(view);
    }

    @Test
    public void testCreateSetMapFragmentAction() {
        RegistrationContract.View view = mock(RegistrationContract.View.class);
        RegistrationScreenAction registrationScreenAction = RegistrationScreenAction.createSetMapFragmentAction();
        registrationScreenAction.visit(view);

        verify(view).setMapFragment();

        verifyNoMoreInteractions(view);
    }

    @Test
    public void testCreateShowToastAction() {
        RegistrationContract.View view = mock(RegistrationContract.View.class);
        RegistrationScreenAction registrationScreenAction = RegistrationScreenAction.createShowToastAction(0);
        registrationScreenAction.visit(view);

        verify(view).showToast(0);

        verifyNoMoreInteractions(view);
    }
}
