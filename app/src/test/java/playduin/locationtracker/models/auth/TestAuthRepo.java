package playduin.locationtracker.models.auth;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import playduin.locationtracker.models.auth.network.AuthNetwork;
import playduin.locationtracker.models.auth.storage.PasswordStorage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class TestAuthRepo {
    private PasswordStorage passwordStorage;
    private AuthNetwork authNetwork;

    private AuthRepo authRepo;

    @Before
    public void testSetup() {
        passwordStorage = mock(PasswordStorage.class);
        authNetwork = mock(AuthNetwork.class);

        authRepo = new AuthRepoImpl(passwordStorage, authNetwork);
    }

    @Test
    public void testHasToken() {
        when(passwordStorage.hasToken()).thenReturn(false);

        assertFalse(authRepo.hasToken());
        verify(passwordStorage).hasToken();

        verifyNoMoreInteractionsInModels();

        when(passwordStorage.hasToken()).thenReturn(true);

        assertTrue(authRepo.hasToken());
        verify(passwordStorage, times(2)).hasToken();

        verifyNoMoreInteractionsInModels();
    }

    @Test
    public void testCreateAccount() {
        when(authNetwork.createAccount("test", "123")).thenReturn(Single.just("It_is_token!"));

        boolean[] subscribed = {false};
        Disposable disposable = authRepo.createAccount("test", "123").subscribe(token -> {
            assertEquals(token, "It_is_token!");
            subscribed[0] = true;
        });
        assertTrue(subscribed[0]);

        verify(authNetwork).createAccount("test", "123");
        verify(passwordStorage).putToken("It_is_token!");

        verifyNoMoreInteractionsInModels();

        disposable.dispose();
    }

    @Test
    public void testSignIn() {
        when(authNetwork.signIn("test", "123")).thenReturn(Single.just("It_is_token!"));

        boolean[] subscribed = {false};
        Disposable disposable = authRepo.signIn("test", "123").subscribe(token -> {
            assertEquals(token, "It_is_token!");
            subscribed[0] = true;
        });
        assertTrue(subscribed[0]);

        verify(authNetwork).signIn("test", "123");
        verify(passwordStorage).putToken("It_is_token!");

        verifyNoMoreInteractionsInModels();

        disposable.dispose();
    }

    @Test
    public void testSignOut() {
        when(authNetwork.signOut()).thenReturn(Completable.complete());

        boolean[] subscribed = {false};
        Disposable disposable = authRepo.signOut().subscribe(() -> subscribed[0] = true);
        assertTrue(subscribed[0]);

        verify(authNetwork).signOut();
        verify(passwordStorage).clearToken();

        verifyNoMoreInteractionsInModels();

        disposable.dispose();
    }

    private void verifyNoMoreInteractionsInModels() {
        verifyNoMoreInteractions(passwordStorage);
        verifyNoMoreInteractions(authNetwork);
    }
}
