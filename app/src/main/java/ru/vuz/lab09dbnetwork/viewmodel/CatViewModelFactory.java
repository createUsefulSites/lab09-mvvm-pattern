package ru.vuz.lab09dbnetwork.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.vuz.lab09dbnetwork.repository.CatRepository;

public class CatViewModelFactory implements ViewModelProvider.Factory {
    private final CatRepository repository;

    public CatViewModelFactory(CatRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CatViewModel.class)) {
            return modelClass.cast(new CatViewModel(repository));
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
