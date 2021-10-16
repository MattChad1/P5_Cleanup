package com.cleanup.todoc;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repository.Repository;
import com.cleanup.todoc.ui.MainViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(new Repository(MyApplication.getInstance()));
                }
            }
        }
        return factory;
    }

    // This field inherit the singleton property from the ViewModelFactory : it is scoped to the ViewModelFactory
    // Ask your mentor about DI scopes (Singleton, ViewModelScope, ActivityScope)
    @NonNull
    private final Repository repository;

    private ViewModelFactory(@NonNull Repository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(MyApplication.getInstance(), repository);
        }
        else throw new IllegalArgumentException("Unknown ViewModel class");
    }
}