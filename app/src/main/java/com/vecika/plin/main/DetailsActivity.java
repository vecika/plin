package com.vecika.plin.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.vecika.plin.R;
import com.vecika.plin.models.Workorder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fotoapparat.Fotoapparat;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.photo.BitmapPhoto;
import io.fotoapparat.preview.FrameProcessor;
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
import static io.fotoapparat.parameter.selector.SizeSelectors.biggestSize;
import static io.fotoapparat.parameter.selector.SizeSelectors.smallestSize;

/**
 * Created by vecika on 05.11.2017..
 */

public class DetailsActivity extends AppCompatActivity {

	private static final int CAMERA_REQUEST = 100;

	/*  @BindView(R.id.textViewSerialNumber)    TextView    mTextViewSerialNumber;*/
	@BindView(R.id.textViewName)
	TextView mTextViewName;
	@BindView(R.id.textViewSurname)
	TextView mTextViewSurname;
	@BindView(R.id.textViewAddress)
	TextView mTextViewAddress;
	@BindView(R.id.textViewLastState)
	TextView mTextViewLastRead;
	@BindView(R.id.imageViewImage)
	ImageView mImageViewImage;
	@BindView(R.id.textViewNewState)
	EditText mEditTextNewState;
	@BindView(R.id.camera_view)
	CameraView mCameraView;

	private TessBaseAPI mTess; //Tess API reference
	String datapath = "";
	private static final String TAG = MainActivity.class.getSimpleName();


	private String pictureImagePath;
	private Bitmap myBitmap, rotated;
	private Fotoapparat fotoapparat;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		ButterKnife.bind(this);
		init();
	}

	@Override
	protected void onStop() {
		super.onStop();
		fotoapparat.stop();
	}

	private void init() {
		fotoapparat = Fotoapparat
				.with(this)
				.into(mCameraView)           // view which will draw the camera preview                                                                          // we want the preview to fill the view
				.photoSize(biggestSize())   // we want to have the biggest photo possible
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
		Workorder workorder = getIntent().getExtras().getParcelable("OBJECT");
	  /*  mTextViewSerialNumber.setText(workorder.getSerialNumber());*/
		mTextViewName.setText(workorder.getName());
		mTextViewSurname.setText(workorder.getSurname());
		mTextViewAddress.setText(workorder.getAddress());
		mTextViewLastRead.setText(workorder.getLastRead());

	}

	@OnClick(R.id.buttonReadState)
	public void onButtonReadStatePressed() {
		fotoapparat.takePicture()
				.toBitmap()
				.whenAvailable(new PendingResult.Callback<BitmapPhoto>() {
					@Override
					public void onResult(BitmapPhoto result) {
						Bitmap bitmap = rotateBitmap(result.bitmap, result.rotationDegrees);
						processImage(bitmap);
					}
				});
	}


   /* protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            File imgFile = new File(pictureImagePath);
            if (imgFile.exists()) {
                myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                rotated = rotateBitmap(myBitmap,6);
                mImageViewImage.setImageBitmap(rotated);
            }
        }


    }*/


	public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

		Matrix matrix = new Matrix();
		matrix.setRotate(-orientation);
		try {
			Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			bitmap.recycle();
			return bmRotated;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			return null;
		}
	}

	private void processImage(Bitmap bitmap) {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
		int[] viewLocation = new int[2];
		mCameraView.getLocationInWindow(viewLocation);
		Bitmap croppedBmp = Bitmap.createBitmap(scaledBitmap, viewLocation[0], viewLocation[1] - 45, mCameraView.getMeasuredWidth(), mCameraView.getMeasuredHeight());

		TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
		/*if (textRecognizer.isOperational()) {
			Log.d(TAG, "processImage: started");*/
		Bitmap blackAndWhite = convertToBlackAndWhite(croppedBmp);
		datapath = getFilesDir()+ "/tesseract/";

		//make sure training data has been copied
		checkFile(new File(datapath + "tessdata/"));

		//init Tesseract API
		String language = "eng";

		mTess = new TessBaseAPI();
		mTess.init(datapath, language);
		doIt(blackAndWhite);
			/*Frame frame = new Frame.Builder().setBitmap(blackAndWhite).build();
			SparseArray<TextBlock> items = textRecognizer.detect(frame);
			StringBuilder stringBuilder = new StringBuilder();

			for (int i = 0; i < items.size(); i++) {
				TextBlock textBlock = items.valueAt(i);
				stringBuilder.append(textBlock.getValue().replaceAll("[^0-9]+", ""));
				stringBuilder.append("\n");
			}
			String stringToShow = stringBuilder.toString();
			mEditTextNewState.setText(stringToShow);
			Log.d(TAG, "processImage: " + stringBuilder.toString());
		} else {
			Log.d(TAG, "processImage: not operational");
		}*/
	}

	private Bitmap convertToBlackAndWhite(Bitmap sampleBitmap) {
		ColorMatrix bwMatrix = new ColorMatrix();
		bwMatrix.setSaturation(0);
		final ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(bwMatrix);
		Bitmap rBitmap = sampleBitmap.copy(Bitmap.Config.ARGB_8888, true);
		Paint paint = new Paint();
		paint.setColorFilter(colorFilter);
		Canvas myCanvas = new Canvas(rBitmap);
		myCanvas.drawBitmap(rBitmap, 0, 0, paint);
		return rBitmap;
	}


	private void copyFiles() {
		try {
			//location we want the file to be at
			String filepath = datapath + "/tessdata/eng.traineddata";

			//get access to AssetManager
			AssetManager assetManager = getAssets();

			//open byte streams for reading/writing
			InputStream instream = assetManager.open("tessdata/eng.traineddata");
			OutputStream outstream = new FileOutputStream(filepath);

			//copy the file to the location specified by filepath
			byte[] buffer = new byte[1024];
			int read;
			while ((read = instream.read(buffer)) != -1) {
				outstream.write(buffer, 0, read);
			}
			outstream.flush();
			outstream.close();
			instream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkFile(File dir) {
		//directory does not exist, but we can successfully create it
		if (!dir.exists()&& dir.mkdirs()){
			copyFiles();
		}
		//The directory exists, but there is no data file in it
		if(dir.exists()) {
			String datafilepath = datapath+ "/tessdata/eng.traineddata";
			File datafile = new File(datafilepath);
			if (!datafile.exists()) {
				copyFiles();
			}
		}
	}

	public void doIt(Bitmap bitmap){
		mTess.setImage(bitmap);
		String text = mTess.getUTF8Text();
		String cleanText = text.replaceAll("[^0-9]", "");
		mEditTextNewState.setText(cleanText);
	}


}
