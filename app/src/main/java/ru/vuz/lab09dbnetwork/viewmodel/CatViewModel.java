package ru.vuz.lab09dbnetwork.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.vuz.lab09dbnetwork.data.CatImage;
import ru.vuz.lab09dbnetwork.repository.CatRepository;

public class CatViewModel extends ViewModel {
    private final CatRepository repository;

    public CatViewModel(CatRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<CatImage>> getImages() {
        return repository.observeImages();
    }

    public LiveData<String> getStatus() {
        return repository.observeStatus();
    }

    public void refresh(boolean forceNetwork) {
        repository.refresh(forceNetwork);
    }

    public void addManualImage(String url) {
        repository.addManualImage(url);
    }

    public void clearCache() {
        repository.clearCache();
    }
}
