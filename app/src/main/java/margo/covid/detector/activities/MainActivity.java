package margo.covid.detector.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import margo.covid.detector.R;
import margo.covid.detector.models.Classifier;
import margo.covid.detector.tensorflow.TFImageClassifier;
import margo.covid.detector.utils.ImagePicker;
import margo.covid.detector.utils.ProgressDialogManager;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_ID = 101;
    private static final String MODEL_PATH = "covid_detector.tflite";
    private static final boolean QUANT = false;
    private static final String LABEL_PATH = "labels.txt";
    private static final int INPUT_SIZE = 224;

    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();
    private ImageView chestImage;
    private Bitmap pickedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chestImage = findViewById(R.id.chest_image);
        initTensorFlowAndLoadModel();
    }

    public void pickImage(View view) {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_ID) {
            try {
                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
                chestImage.setImageBitmap(bitmap);
                pickedImage = bitmap;
            } catch (Exception ex) {
                Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(() -> classifier.close());
    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(() -> {
            try {
                classifier = TFImageClassifier.create(
                        getAssets(),
                        MODEL_PATH,
                        LABEL_PATH,
                        INPUT_SIZE,
                        QUANT);
            } catch (final Exception e) {
                throw new RuntimeException("Error initializing TensorFlow!", e);
            }
        });
    }

    public void analyzeImage(View view) {
        if (classifier == null || pickedImage == null)
            Toast.makeText(this, getString(R.string.pick_image_first), Toast.LENGTH_SHORT).show();
        else
            new ClassifyAsyncTask().execute(pickedImage);
    }

    @SuppressLint("StaticFieldLeak")
    private class ClassifyAsyncTask extends AsyncTask<Bitmap, Void, List<Classifier.Recognition>> {

        @Override
        protected void onPreExecute() {
            ProgressDialogManager.getInstance(MainActivity.this).showDialog("Processing Image..");
        }

        @Override
        protected List<Classifier.Recognition> doInBackground(Bitmap... params) {
            return classifier.recognizeImage(params[0]);
        }

        @Override
        protected void onPostExecute(List<Classifier.Recognition> result) {
            analyzeResult(result.get(0));
            ProgressDialogManager.getInstance(MainActivity.this).closeDialog();
        }
    }

    private void analyzeResult(Classifier.Recognition recognition) {
        if (recognition.getTitle().equals("normal"))
            startActivity(new Intent(this, HealthyActivity.class));
        else {
            CovidActivity.recognition = recognition;
            startActivity(new Intent(this, CovidActivity.class));
        }
    }
}