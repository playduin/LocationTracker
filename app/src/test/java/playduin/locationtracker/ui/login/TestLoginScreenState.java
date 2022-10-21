package playduin.locationtracker.ui.login;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TestLoginScreenState {
    @Test
    public void testCreateShowProgressBarState() {
        LoginContract.View view = mock(LoginContract.View.class);
        LoginScreenState loginScreenState = LoginScreenState.createShowProgressBarState();
        loginScreenState.visit(view);

        verify(view).showProgressBar();

        verifyNoMoreInteractions(view);
    }

    @Test
    public void testCreateHideProgressBarState() {
        LoginContract.View view = mock(LoginContract.View.class);
        LoginScreenState loginScreenState = LoginScreenState.createHideProgressBarState();
        loginScreenState.visit(view);

        verify(view).hideProgressBar();

        verifyNoMoreInteractions(view);
    }

    @Test
    public void testCreateSetFormState() {
        LoginContract.View view = mock(LoginContract.View.class);
        LoginScreenState loginScreenState = LoginScreenState.createSetFormState(false, false);
        loginScreenState.visit(view);
        loginScreenState = LoginScreenState.createSetFormState(false, true);
        loginScreenState.visit(view);
        loginScreenState = LoginScreenState.createSetFormState(true, false);
        loginScreenState.visit(view);
        loginScreenState = LoginScreenState.createSetFormState(true, true);
        loginScreenState.visit(view);

        verify(view).setForm(false, false);
        verify(view).setForm(false, true);
        verify(view).setForm(true, false);
        verify(view).setForm(true, true);

        verifyNoMoreInteractions(view);
    }
}
