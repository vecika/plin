package com.vecika.plin.tessTest;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.vecika.plin.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fotoapparat.Fotoapparat;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.photo.BitmapPhoto;
import io.fotoapparat.result.PendingResult;
import io.fotoapparat.view.CameraView;

import static io.fotoapparat.parameter.selector.FlashSelectors.autoFlash;
import static io.fotoapparat.parameter.selector.FlashSelectors.autoRedEye;
import static io.fotoapparat.parameter.selector.FlashSelectors.torch;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.autoFocus;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.continuousFocus;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.fixed;
import static io.fotoapparat.parameter.selector.LensPositionSelectors.back;
import static io.fotoapparat.parameter.selector.Selectors.firstAvailable;
import static io.fotoapparat.parameter.selector.SizeSelectors.smallestSize;

/**
 * Created by Azenic on 06-Nov-17.
 */

public class TestActivity extends AppCompatActivity {

	@BindView(R.id.camera_view)
	CameraView cameraView;

	@BindView(R.id.result_text_view)
	TextView resultTextView;

	private TessBaseAPI mTess;
	private String datapath = "";
	private Fotoapparat fotoapparat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		ButterKnife.bind(this);
		//init image

		//initialize Tesseract API
		String language = "hrv";
		datapath = getFilesDir() + "/tesseract/";
		mTess = new TessBaseAPI();
		mTess.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "0123456789");
		mTess.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST,"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz");
		mTess.setPageSegMode(TessBaseAPI.PageSegMode.PSM_RAW_LINE);
		checkFile(new File(datapath + "tessdata/"));

		mTess.init(datapath, language);

		fotoapparat = Fotoapparat
				.with(this)
				.into(cameraView)           // view which will draw the camera preview
				.previewScaleType(ScaleType.CENTER_CROP)  // we want the preview to fill the view
				.photoSize(smallestSize())   // we want to have the biggest photo possible
				.lensPosition(back())       // we want back camera
				.focusMode(firstAvailable(  // (optional) use the first focus mode which is supported by device
						continuousFocus(),
						autoFocus(),        // in case if continuous focus is not available on device, auto focus will be used
						fixed()             // if even auto focus is not available - fixed focus mode will be used
				))
				.flash(firstAvailable(      // (optional) similar to how it is done for focus mode, this time for flash
						autoRedEye(),
						autoFlash(),
						torch()
				))
				.build();
		fotoapparat.start();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		fotoapparat.stop();
	}

	@OnClick(R.id.run_ocr_button)
	public void onRunOcrButtonClick() {
		fotoapparat.takePicture()
				.toBitmap()
				.whenAvailable(new PendingResult.Callback<BitmapPhoto>() {
					@Override
					public void onResult(BitmapPhoto result) {
						processImage(result.bitmap);
					}
				});

	}

	public void processImage(Bitmap bitmap) {
		mTess.setImage(bitmap);
		resultTextView.setText(mTess.getUTF8Text());
	}

	private void checkFile(File dir) {
		if (!dir.exists() && dir.mkdirs()) {
			copyFiles();
		}
		if (dir.exists()) {
			String datafilepath = datapath + "/tessdata/hrv.traineddata";
			File datafile = new File(datafilepath);

			if (!datafile.exists()) {
				copyFiles();
			}
		}
	}

	private void copyFiles() {
		try {
			String filepath = datapath + "/tessdata/hrv.traineddata";
			AssetManager assetManager = getAssets();

			InputStream instream = assetManager.open("tessdata/hrv.traineddata");
			OutputStream outstream = new FileOutputStream(filepath);

			byte[] buffer = new byte[1024];
			int read;
			while ((read = instream.read(buffer)) != -1) {
				outstream.write(buffer, 0, read);
			}


			outstream.flush();
			outstream.close();
			instream.close();

			File file = new File(filepath);
			if (!file.exists()) {
				throw new FileNotFoundException();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
