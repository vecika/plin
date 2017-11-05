package com.vecika.plin.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.vecika.plin.R;
import com.vecika.plin.models.Workorder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vecika on 05.11.2017..
 */

public class DetailsActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 100;
    private static final String TAG = DetailsActivity.class.getSimpleName();

    @BindView(R.id.textViewSerialNumber)    TextView    mTextViewSerialNumber;
    @BindView(R.id.textViewName)            TextView    mTextViewName;
    @BindView(R.id.textViewSurname)         TextView    mTextViewSurname;
    @BindView(R.id.textViewAddress)         TextView    mTextViewAddress;
    @BindView(R.id.textViewLastState)       TextView    mTextViewLastRead;
    @BindView(R.id.imageViewImage)          ImageView   mImageViewImage;
    @BindView(R.id.textViewNewState)        EditText    mEditTextNewState;

    String pictureImagePath;
    Bitmap myBitmap, rotated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Workorder workorder = getIntent().getExtras().getParcelable("OBJECT");
        mTextViewSerialNumber.setText(workorder.getSerialNumber());
        mTextViewName.setText(workorder.getName());
        mTextViewSurname.setText(workorder.getSurname());
        mTextViewAddress.setText(workorder.getAddress());
        mTextViewLastRead.setText(workorder.getLastRead());

    }

    @OnClick(R.id.buttonReadState)
    public void onButtonReadStatePressed(){

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pictureImagePath);
        Uri outputFileUri = Uri.fromFile(file);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            File imgFile = new File(pictureImagePath);
            if (imgFile.exists()) {
                myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                rotated = rotateBitmap(myBitmap,6);
                mImageViewImage.setImageBitmap(rotated);
            }
        }


    }


    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }


    private void processImage() {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (textRecognizer.isOperational()) {
            Log.d(TAG, "processImage: started");

            Frame frame = new Frame.Builder().setBitmap(rotated).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < items.size(); i++) {
                TextBlock textBlock = items.valueAt(i);
                stringBuilder.append(textBlock.getValue().replaceAll("[^0-9]+",""));
                stringBuilder.append("\n");
            }
            String stringToShow = stringBuilder.toString();
            mEditTextNewState.setText(stringToShow);
            Log.d(TAG, "processImage: "+ stringBuilder.toString());
        } else {
            Log.d(TAG, "processImage: not operational");
        }
    }


    @OnClick(R.id.imageViewImage)
    public void onImageClicked(){
        processImage();
    }
}
