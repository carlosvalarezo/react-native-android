package com.androiddemo;

import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import android.app.Activity;
import android.content.Intent;

import javax.annotation.Nonnull;

public class ImagePickerModule extends ReactContextBaseJavaModule{

    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;

    private Callback pickerSuccessCallback;
    private Callback pickerCancelCallback;

    public ImagePickerModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Nonnull
    @Override
    public String getName() {
        return "ImagePicker";
    }

    @ReactMethod
    public void navigateToExample() {
        Activity activity = getCurrentActivity();

        if (activity != null) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            activity.startActivityForResult(intent, PICK_Camera_IMAGE);
        }
    }

    @ReactMethod
    public void openSelectDialog(ReadableMap config, Callback successCallback, Callback cancelCallback) {
        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
            cancelCallback.invoke("Activity doesn't exist");
            return;
        }

        pickerSuccessCallback = successCallback;
        pickerCancelCallback = cancelCallback;

        try {
            final Intent galleryIntent = new Intent();

            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_VIEW);

            final Intent chooserIntent = Intent.createChooser(galleryIntent, "Pick an image");

            currentActivity.startActivityForResult(chooserIntent, PICK_IMAGE);
        } catch (Exception e) {
            cancelCallback.invoke(e);
        }
    }

    @ReactMethod
    public void openActivity() {
        ReactApplicationContext activity = getReactApplicationContext();
            Intent rctActivityIntent = new Intent(activity,ExampleActivity.class);
            if(rctActivityIntent.resolveActivity(activity.getPackageManager()) !=null ) {
                rctActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(rctActivityIntent);
            }
    }

    @ReactMethod
    public void openOtherActivity() {
        ReactApplicationContext activity = getReactApplicationContext();


        Intent rctActivityIntent = new Intent(activity, Main2Activity.class);
        if(rctActivityIntent.resolveActivity(activity.getPackageManager()) !=null ) {
            rctActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(rctActivityIntent);
        }

    }

    public void onNewIntent(Intent intent) {
    }


    public void onActivityResult(Activity activity, final int requestCode, final int resultCode, final Intent intent) {
        if (pickerSuccessCallback != null) {
            if (resultCode == Activity.RESULT_CANCELED) {
                pickerCancelCallback.invoke("ImagePicker was cancelled");
            } else if (resultCode == Activity.RESULT_OK) {
            }
        }
    }


}