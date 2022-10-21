package playduin.locationtracker.ui;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxImmediateSchedulerRule implements TestRule {
    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(__ -> Schedulers.trampoline());
                RxJavaPlugins.setComputationSchedulerHandler(__ -> Schedulers.trampoline());
                RxJavaPlugins.setNewThreadSchedulerHandler(__ -> Schedulers.trampoline());
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}
