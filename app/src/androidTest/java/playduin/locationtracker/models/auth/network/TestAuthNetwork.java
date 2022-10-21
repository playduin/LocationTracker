package playduin.locationtracker.models.auth.network;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class TestAuthNetwork implements AuthNetwork {
    private Single<String> createAccountValue;
    private Single<String> signInValue;
    private Completable signOutValue;

    @Override
    public Single<String> createAccount(String email, String password) {
        return createAccountValue;
    }

    @Override
    public Single<String> signIn(String email, String password) {
        return signInValue;
    }

    @Override
    public Completable signOut() {
        return signOutValue;
    }

    public void setCreateAccountValue(Single<String> createAccountValue) {
        this.createAccountValue = createAccountValue;
    }

    public void setSignInValue(Single<String> signInValue) {
        this.signInValue = signInValue;
    }

    public void setSignOutValue(Completable signOutValue) {
        this.signOutValue = signOutValue;
    }
}
