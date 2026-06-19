package ru.vuz.lab09dbnetwork;

import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.vuz.lab09dbnetwork.data.CatDatabase;
import ru.vuz.lab09dbnetwork.network.RetrofitProvider;
import ru.vuz.lab09dbnetwork.repository.CachePolicy;
import ru.vuz.lab09dbnetwork.repository.CatRepository;
import ru.vuz.lab09dbnetwork.ui.CatAdapter;
import ru.vuz.lab09dbnetwork.ui.PicassoCatImageLoader;
import ru.vuz.lab09dbnetwork.viewmodel.CatViewModel;
import ru.vuz.lab09dbnetwork.viewmodel.CatViewModelFactory;

public class MainActivity extends AppCompatActivity {
    private CatAdapter catAdapter;
    private CatViewModel viewModel;
    private TextView statusTextView;
    private TextView counterTextView;
    private EditText urlEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTextView = findViewById(R.id.status_text_view);
        counterTextView = findViewById(R.id.counter_text_view);
        urlEditText = findViewById(R.id.manual_url_edit_text);
        Button checkCacheButton = findViewById(R.id.button_check_cache);
        Button refreshButton = findViewById(R.id.button_refresh);
        Button clearButton = findViewById(R.id.button_clear);
        Button addButton = findViewById(R.id.button_add_manual);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        catAdapter = new CatAdapter(new PicassoCatImageLoader());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(catAdapter);
        recyclerView.setHasFixedSize(false);

        CatRepository repository = new CatRepository(
                CatDatabase.getInstance(getApplicationContext()).catImageDao(),
                RetrofitProvider.createCatApi(),
                CatDatabase.databaseWriteExecutor,
                new CachePolicy()
        );
        viewModel = new ViewModelProvider(
                this,
                new CatViewModelFactory(repository)
        ).get(CatViewModel.class);

        viewModel.getImages().observe(this, images -> {
            catAdapter.submitImages(images);
            counterTextView.setText(getString(R.string.counter_template, images.size()));
        });
        viewModel.getStatus().observe(this, statusTextView::setText);

        checkCacheButton.setOnClickListener(view -> viewModel.refresh(false));
        refreshButton.setOnClickListener(view -> viewModel.refresh(true));
        clearButton.setOnClickListener(view -> viewModel.clearCache());
        addButton.setOnClickListener(view -> addManualImage());

        viewModel.refresh(false);
    }

    private void addManualImage() {
        viewModel.addManualImage(urlEditText.getText().toString());
        urlEditText.setText("");
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(urlEditText.getWindowToken(), 0);
        }
    }
}
