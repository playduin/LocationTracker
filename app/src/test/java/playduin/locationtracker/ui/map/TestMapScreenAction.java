package playduin.locationtracker.ui.map;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TestMapScreenAction {
    @Test
    public void testCreateShowLoginFragmentAction() {
        MapContract.View mapContractView = mock(MapContract.View.class);
        MapScreenAction mapScreenAction = MapScreenAction.createShowLoginFragmentAction();
        mapScreenAction.visit(mapContractView);

        verify(mapContractView).showLoginFragment();

        verifyNoMoreInteractions(mapContractView);
    }
}
