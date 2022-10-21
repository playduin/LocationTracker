package playduin.locationtracker.ui.login;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TestLoginScreenAction {
    @Test
    public void testCreateSetRegistrationFragmentAction() {
        LoginContract.View view = mock(LoginContract.View.class);
        LoginScreenAction loginScreenAction = LoginScreenAction.createSetRegistrationFragmentAction();
        loginScreenAction.visit(view);

        verify(view).setRegistrationFragment();

        verifyNoMoreInteractions(view);
    }

    @Test
    public void testCreateSetTrackerFragmentAction() {
        LoginContract.View view = mock(LoginContract.View.class);
        LoginScreenAction loginScreenAction = LoginScreenAction.createSetTrackerFragmentAction();
        loginScreenAction.visit(view);

        verify(view).setTrackerFragment();

        verifyNoMoreInteractions(view);
    }

    @Test
    public void testCreateSetMapFragmentAction() {
        LoginContract.View view = mock(LoginContract.View.class);
        LoginScreenAction loginScreenAction = LoginScreenAction.createSetMapFragmentAction();
        loginScreenAction.visit(view);

        verify(view).setMapFragment();

        verifyNoMoreInteractions(view);
    }

    @Test
    public void testCreateShowToastAction() {
        LoginContract.View view = mock(LoginContract.View.class);
        LoginScreenAction loginScreenAction = LoginScreenAction.createShowToastAction(0);
        loginScreenAction.visit(view);

        verify(view).showToast(0);

        verifyNoMoreInteractions(view);
    }
}
