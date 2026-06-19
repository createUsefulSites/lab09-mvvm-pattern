package ru.vuz.lab09dbnetwork.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Response;
import ru.vuz.lab09dbnetwork.data.CatImage;
import ru.vuz.lab09dbnetwork.data.CatImageDao;
import ru.vuz.lab09dbnetwork.network.CatApi;
import ru.vuz.lab09dbnetwork.ui.ImageUrlValidator;

public class CatRepository {
    private static final int REQUEST_LIMIT = 12;

    private final CatImageDao catImageDao;
    private final CatApi catApi;
    private final Executor executor;
    private final CachePolicy cachePolicy;
    private final MutableLiveData<String> status = new MutableLiveData<>(
            "Проверка кэша Room еще не выполнялась."
    );

    public CatRepository(
            CatImageDao catImageDao,
            CatApi catApi,
            Executor executor,
            CachePolicy cachePolicy
    ) {
        this.catImageDao = catImageDao;
        this.catApi = catApi;
        this.executor = executor;
        this.cachePolicy = cachePolicy;
    }

    public LiveData<List<CatImage>> observeImages() {
        return catImageDao.observeImages();
    }

    public LiveData<String> observeStatus() {
        return status;
    }

    public void refresh(boolean forceNetwork) {
        executor.execute(() -> {
            int cachedCount = catImageDao.countImages();
            Long latestCacheTime = catImageDao.latestNetworkCacheTime();
            CacheState state = new CacheState(
                    cachedCount,
                    latestCacheTime == null ? 0L : latestCacheTime
            );

            if (!cachePolicy.shouldRequestNetwork(state, forceNetwork)) {
                status.postValue("Найден актуальный кэш Room. Сетевой запрос не потребовался.");
                return;
            }

            requestImagesFromNetwork(cachedCount);
        });
    }

    public void addManualImage(String url) {
        executor.execute(() -> {
            if (!ImageUrlValidator.isValidImageUrl(url)) {
                status.postValue("Введите корректный http/https URL изображения.");
                return;
            }
            CatImage image = CatImage.createManual(url, System.currentTimeMillis());
            catImageDao.insert(image);
            status.postValue("Новая запись добавлена в Room и сразу отображается в списке.");
        });
    }

    public void clearCache() {
        executor.execute(() -> {
            catImageDao.clearAll();
            status.postValue("Локальный кэш Room очищен.");
        });
    }

    private void requestImagesFromNetwork(int previousCachedCount) {
        status.postValue("Выполняется Retrofit-запрос к The Cat API.");
        try {
            Response<List<CatImage>> response = catApi.getImages(REQUEST_LIMIT, 1).execute();
            if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                long now = System.currentTimeMillis();
                List<CatImage> images = response.body();
                for (CatImage image : images) {
                    image.normalizeNetworkImage(now);
                }
                catImageDao.insertAll(images);
                status.postValue("Данные получены через Retrofit и сохранены в Room.");
                return;
            }
            postNetworkFallbackStatus(previousCachedCount, "сервер вернул пустой или ошибочный ответ");
        } catch (IOException exception) {
            postNetworkFallbackStatus(previousCachedCount, describeNetworkError(exception));
        }
    }

    private void postNetworkFallbackStatus(int previousCachedCount, String reason) {
        if (previousCachedCount > 0) {
            status.postValue("Сеть недоступна, показаны данные из Room. Причина: " + reason);
        } else {
            status.postValue("Не удалось получить данные из сети: " + reason);
        }
    }

    private String describeNetworkError(IOException exception) {
        if (exception instanceof UnknownHostException) {
            return "не удалось определить адрес сервера";
        }
        if (exception instanceof SocketTimeoutException) {
            return "истекло время ожидания ответа";
        }
        return "проверьте подключение к Интернету";
    }
}
