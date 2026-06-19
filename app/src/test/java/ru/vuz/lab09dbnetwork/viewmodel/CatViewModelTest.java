package ru.vuz.lab09dbnetwork.viewmodel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import ru.vuz.lab09dbnetwork.repository.CatRepository;

public class CatViewModelTest {
    @Test
    public void forwardsRefreshCommandToRepository() {
        CatRepository repository = mock(CatRepository.class);
        CatViewModel viewModel = new CatViewModel(repository);

        viewModel.refresh(true);

        verify(repository).refresh(true);
    }

    @Test
    public void forwardsManualInsertCommandToRepository() {
        CatRepository repository = mock(CatRepository.class);
        CatViewModel viewModel = new CatViewModel(repository);

        viewModel.addManualImage("https://cdn.example/manual.jpg");

        verify(repository).addManualImage("https://cdn.example/manual.jpg");
    }
}
