package com.sayan.storefilestosdcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.rdrei.android.dirchooser.DirectoryChooserActivity;
import net.rdrei.android.dirchooser.DirectoryChooserConfig;

import java.io.File;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_DIRECTORY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void choose(View view) {
        final Intent chooserIntent = new Intent(this, DirectoryChooserActivity.class);

        Map<String, File> externalLocations = ExternalStorage.getAllStorageLocations();
        File sdCard = externalLocations.get(ExternalStorage.SD_CARD);
        File externalSdCard = externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);

        ExternalStorage.getAvailableMemorySize(sdCard.getPath());
        ExternalStorage.getTotalMemorySize(sdCard.getPath());
        final DirectoryChooserConfig config = DirectoryChooserConfig.builder()
                .newDirectoryName("DialogSample")
                .allowNewDirectoryNameModification(true)
                .allowReadOnlyDirectory(true)
                .initialDirectory("/storage")
                .build();

        chooserIntent.putExtra(DirectoryChooserActivity.EXTRA_CONFIG, config);

// REQUEST_DIRECTORY is a constant integer to identify the request, e.g. 0
        startActivityForResult(chooserIntent, REQUEST_DIRECTORY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_DIRECTORY) {
            if (resultCode == DirectoryChooserActivity.RESULT_CODE_DIR_SELECTED) {
                handleDirectoryChoice(data
                        .getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR));
            } else {
                // Nothing selected
                Toast.makeText(this, "Nothing selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleDirectoryChoice(String filePath) {
        TextView textView = findViewById(R.id.tv);
        textView.setText(filePath);
        ExternalStorage.getAvailableMemorySize(filePath);
        ExternalStorage.getTotalMemorySize(filePath);
    }
}
