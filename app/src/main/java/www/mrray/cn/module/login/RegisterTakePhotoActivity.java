package www.mrray.cn.module.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eightbitlab.rxbus.Bus;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import www.mrray.cn.R;
import www.mrray.cn.event.RegisterFaceEvent;
import www.mrray.cn.http.RequestApi;
import www.mrray.cn.utils.CameraManager;
import www.mrray.cn.utils.LogUtil;
import www.mrray.cn.view.CameraSurfaceView;
import www.mrray.cn.view.TitleBar;

public class RegisterTakePhotoActivity extends AppCompatActivity {
    int mCameraID;
    private Camera mCamera;
    private static final String PATH_IMAGES = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "easy_check";
    private CameraSurfaceView mCameraSurfaceView;
    private int faceCount = 0;
    //拍照快门的回调
    private Camera.ShutterCallback mShutterCallback = () -> {
    };
    //拍照完成之后返回原始数据的回调
    private Camera.PictureCallback rawPictureCallback = (data, camera) -> {
    };
    //拍照完成之后返回压缩数据的回调
    private Camera.PictureCallback jpegPictureCallback = new Camera.PictureCallback() {
        @SuppressLint("CheckResult")
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            mCamera.startPreview();
            saveFile(data);
            setPictureDegreeZero(PATH_IMAGES + File.separator + fileName);
            Observable.just(PATH_IMAGES + File.separator + fileName).subscribeOn(Schedulers.io())
                    .flatMap((Function<String, ObservableSource<String>>) s -> Observable.just(getBase64Data(s)))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> faceLogin(s));
        }
    };
    private Disposable timeDisable;
    private Disposable requestDisable;

    /**
     * 人脸识别登录
     *
     * @param s
     */
    @SuppressLint("CheckResult")
    private void faceLogin(String s) {
        requestDisable = RequestApi.Companion.getInstance().faceRegister(phone, mUserName, s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(arrayListBaseHttpModel -> {
                    if (arrayListBaseHttpModel.getCode() != 1) {
                        //识别失败
                        if (faceCount == 3) {
                            Toast.makeText(this, "人脸识别失败！！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            faceScan();
                        }
                    } else {
                        //TODO 识别成功
                        Toast.makeText(this, "人脸注册成功", Toast.LENGTH_SHORT).show();
                        RegisterFaceEvent event = new RegisterFaceEvent();
                        Bus.INSTANCE.send(event);
                        finish();
                    }
                }, throwable -> {
                    LogUtil.INSTANCE.e(throwable.getMessage(), throwable);
                    if (faceCount == 3) {
                        Toast.makeText(RegisterTakePhotoActivity.this, "人脸识别失败！！", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        faceScan();
                    }
                });
    }

    /**
     * 获取图片的base64数据
     */
    private String getBase64Data(String s) throws IOException {
        File file = new File(s);
        InputStream inputStream = new FileInputStream(file);
        byte[] b = new byte[inputStream.available()];
        inputStream.read(b);
        String str = Base64.encodeToString(b, Base64.DEFAULT);
        File saveFile = new File(PATH_IMAGES + File.separator + "test.txt");
        if (!saveFile.exists()) {
            saveFile.createNewFile();
        }
        FileOutputStream out = new FileOutputStream(saveFile);
        out.write(str.getBytes());
        out.close();
        return str;
    }

    public static final String EXTE_PHONE = "phone";
    public static final String EXTRA_USERNAME = "username";
    private String mUserName = "";

    public static void start(Context context, String phone) {
        Intent starter = new Intent(context, RegisterTakePhotoActivity.class);
        starter.putExtra(EXTE_PHONE, phone);
        context.startActivity(starter);
    }

    private String phone = "";

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phone = getIntent().getStringExtra(EXTE_PHONE) == null ? "" : getIntent().getStringExtra(EXTE_PHONE);
        mUserName = getIntent().getStringExtra(EXTRA_USERNAME) == null ? "" : getIntent().getStringExtra(EXTRA_USERNAME);
        setContentView(R.layout.activity_face_layout);
        TitleBar titleBar = findViewById(R.id.title_bar);
        titleBar.setLeftImgListener(view -> {
            finish();
            return null;
        });
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA).subscribe(it -> {
            if (it) {
                setupCamera();
                faceScan();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("警告");
                builder.setMessage("请给于相机和存储权限，否则会影响部分功能使用！");
                builder.create().show();
                builder.setPositiveButton("确定", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                });
                builder.setOnDismissListener(dialog -> finish());
            }
        });
    }

    /**
     * 设置相机属性等
     */
    private void setupCamera() {
        mCameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
        mCameraSurfaceView = (CameraSurfaceView) findViewById(R.id.glsurfaceView);
        mCamera = Camera.open(mCameraID);
        CameraManager cameraManager = new CameraManager(mCamera);
        cameraManager.setCameraDisplay(RegisterTakePhotoActivity.this, mCameraID, mCameraSurfaceView);
        mCamera.startPreview();
    }

    @SuppressLint("CheckResult")
    private void faceScan() {
        faceCount++;
        timeDisable = countDown(3).subscribe(integer -> {
            if (isFinishing())
                return;
            if (integer == 0) {
                //拍照 关闭声音
//                closeVoice();
                mCameraSurfaceView.takePicture(mShutterCallback, rawPictureCallback, jpegPictureCallback);
//                t.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        soundHandler.post(() -> {
//                            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//                            audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
//                        });
//                    }
//                }, 1000);
            }
        });
    }

    // 消除拍照声音
    final Handler soundHandler = new Handler();
    Timer t = new Timer();


    private void closeVoice() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
    }

    private String fileName;

    //保存图片到硬盘
    public void saveFile(byte[] data) {
        fileName = UUID.randomUUID().toString() + ".jpg";
        FileOutputStream outputStream = null;
        try {
            File file = new File(PATH_IMAGES);
            if (!file.exists()) {
                file.mkdirs();
            }
            outputStream = new FileOutputStream(PATH_IMAGES + File.separator + fileName);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(data, 0, data.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadImage(ImageView faceImage) {
        Glide.with(this)
                .load(R.drawable.face_gif)
                .into(faceImage);
    }


    private Observable<Integer> countDown(final int time) {
        return Observable.interval(0, 1L, TimeUnit.SECONDS)
                .map(aLong -> time - Integer.valueOf(aLong.toString()))
                .take(time + 1)
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 将图片的旋转角度置为0
     *
     * @param path
     * @return void
     * @Title: setPictureDegreeZero
     * @date 2012-12-10 上午10:54:46
     */
    public static void setPictureDegreeZero(String path) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            // 修正图片的旋转角度，设置其不旋转。这里也可以设置其旋转的角度，可以传值过去，
            // 例如旋转90度，传值ExifInterface.ORIENTATION_ROTATE_90，需要将这个值转换为String类型的
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, "" + ExifInterface.ORIENTATION_ROTATE_270);
            exifInterface.saveAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeDisable != null) {
            timeDisable.dispose();
        }
        if (requestDisable != null) {
            requestDisable.dispose();
        }
    }
}
