package playduin.locationtracker.ui.registration;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TestRegistrationScreenState {
    @Test
    public void testCreateShowProgressBarState() {
        RegistrationContract.View view = mock(RegistrationContract.View.class);
        RegistrationScreenState registrationScreenState = RegistrationScreenState.createShowProgressBarState();
        registrationScreenState.visit(view);

        verify(view).showProgressBar();

        verifyNoMoreInteractions(view);
    }

    @Test
    public void testCreateHideProgressBarState() {
        RegistrationContract.View view = mock(RegistrationContract.View.class);
        RegistrationScreenState registrationScreenState = RegistrationScreenState.createHideProgressBarState();
        registrationScreenState.visit(view);

        verify(view).hideProgressBar();

        verifyNoMoreInteractions(view);
    }

    @Test
    public void testCreateSetFormState() {
        RegistrationContract.View view = mock(RegistrationContract.View.class);
        RegistrationScreenState registrationScreenState = RegistrationScreenState.createSetFormState(false, false);
        registrationScreenState.visit(view);
        registrationScreenState = RegistrationScreenState.createSetFormState(false, true);
        registrationScreenState.visit(view);
        registrationScreenState = RegistrationScreenState.createSetFormState(true, false);
        registrationScreenState.visit(view);
        registrationScreenState = RegistrationScreenState.createSetFormState(true, true);
        registrationScreenState.visit(view);

        verify(view).setForm(false, false);
        verify(view).setForm(false, true);
        verify(view).setForm(true, false);
        verify(view).setForm(true, true);

        verifyNoMoreInteractions(view);
    }
}
