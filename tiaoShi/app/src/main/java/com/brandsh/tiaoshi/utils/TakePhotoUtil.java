package com.brandsh.tiaoshi.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout.LayoutParams;


import com.brandsh.tiaoshi.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * by libokang
 */
public class TakePhotoUtil {

    public static final int PHOTO_REQUEST_GALLERY = 0x2131;
    public static final int PHOTO_REQUEST_CAREMA = 0x2121;
    public static final int PHOTO_REQUEST_CUT = 0x2112;


    private static final String PHOTO_FILE = Environment
            .getExternalStorageDirectory() + "/photo_file.jpg";
    private static final String CROP_FILE = Environment
            .getExternalStorageDirectory() + "/crop_file.jpg";
    public static  String picPath;


    /**
     * 显示对话框
     *
     * @param context
     */
    public static final void showDialog(final Fragment context) {
        final Dialog photoDialog = new Dialog(context.getActivity(),
                R.style.dialog_style);
        photoDialog.setContentView(R.layout.takephoto_dialog);
        Window win = photoDialog.getWindow();
        win.setGravity(Gravity.BOTTOM);
        win.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
        win.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        photoDialog.setCanceledOnTouchOutside(true);

        photoDialog.findViewById(R.id.fromphoto).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 打开相册
                        startGetPhotoIntent(context, PHOTO_REQUEST_GALLERY);

                        photoDialog.dismiss();
                    }
                });

        photoDialog.findViewById(R.id.takephoto).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //打开拍照
                        startTaKePhotoIntent(context, PHOTO_REQUEST_CAREMA);

                        photoDialog.dismiss();
                    }
                });

        photoDialog.findViewById(R.id.btn_cancel).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取消
                        photoDialog.dismiss();
                    }
                });

        photoDialog.show();
    }

    public static final void showDialog(final Activity context) {
        final Dialog photoDialog = new Dialog(context,
                R.style.dialog_style);
        photoDialog.setContentView(R.layout.takephoto_dialog);
        Window win = photoDialog.getWindow();
        win.setGravity(Gravity.BOTTOM);
        win.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
        win.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        photoDialog.setCanceledOnTouchOutside(true);

        photoDialog.findViewById(R.id.fromphoto).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 打开相册
                        startGetPhotoIntent(context, PHOTO_REQUEST_GALLERY);

                        photoDialog.dismiss();
                    }
                });

        photoDialog.findViewById(R.id.takephoto).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //打开拍照
                        startTaKePhotoIntent(context, PHOTO_REQUEST_CAREMA);

                        photoDialog.dismiss();
                    }
                });

        photoDialog.findViewById(R.id.btn_cancel).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取消
                        photoDialog.dismiss();
                    }
                });

        photoDialog.show();
    }

    /**
     * 打开相册
     *
     * @param context
     * @param flag
     */
    private static void startGetPhotoIntent(Fragment context, int flag) {

        Intent picture = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(picture, flag);

    }

    /**
     * 打开相册
     *
     * @param context
     * @param flag
     */
    private static void startGetPhotoIntent(Activity context, int flag) {

        Intent picture = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(picture, flag);

    }

    /**
     * 打开拍照
     *
     * @param context
     * @param flag
     */
    public static void startTaKePhotoIntent(Fragment context, int flag) {

        File photoFile = new File(PHOTO_FILE);
        if (!photoFile.exists()) {
            try {
                photoFile.createNewFile();
            } catch (IOException e) {
                ToastUtil.showShort(context.getActivity(), "没有找到存储卡");
                return;
            }
        }


        File cropFile = new File(CROP_FILE);
        if (!cropFile.exists()) {
            try {
                cropFile.createNewFile();
            } catch (IOException e) {
                ToastUtil.showShort(context.getActivity(), "没有找到存储卡");
                return;
            }
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        context.startActivityForResult(intent, flag);
    }

    /**
     * 打开拍照
     *
     * @param context
     * @param flag
     */
    public static void startTaKePhotoIntent(Activity context, int flag) {

        File photoFile = new File(PHOTO_FILE);
        if (!photoFile.exists()) {
            try {
                photoFile.createNewFile();
            } catch (IOException e) {
                ToastUtil.showShort(context, "没有找到存储卡");
                return;
            }
        }


        File cropFile = new File(CROP_FILE);
        if (!cropFile.exists()) {
            try {
                cropFile.createNewFile();
            } catch (IOException e) {
                ToastUtil.showShort(context, "没有找到存储卡");
                return;
            }
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        context.startActivityForResult(intent, flag);
    }


    /**
     * @param context
     * @param requestCode
     * @param resultCode
     * @param data
     * @param crop        是否需要剪裁
     * @return
     */
    public static Bitmap dealActivityResult(Fragment context, int requestCode, int resultCode, Intent data, boolean crop) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                // 拍照
                case TakePhotoUtil.PHOTO_REQUEST_CAREMA:

                    if (crop) {

                        cropImageUri(context, Uri.fromFile(new File(PHOTO_FILE)), Uri.fromFile(new File(CROP_FILE)), 800, 800, PHOTO_REQUEST_CUT);

                    } else {
                        picPath=getPath(context,Uri.fromFile(new File(PHOTO_FILE)));
                        return decodeUriAsCompressBitmap(context, Uri.fromFile(new File(PHOTO_FILE)));

                    }

                    break;

                // 相册
                case TakePhotoUtil.PHOTO_REQUEST_GALLERY:

                    if (data == null) {
                        return null;
                    }

                    if (crop) {

                        //从相册选取成功后，需要从Uri中拿出图片的绝对路径，再调用剪切
                        Uri newUri = Uri.parse("file:///" + getPath(context, data.getData()));
                        if (newUri == null) {
                            return null;
                        }
                        cropImageUri(context, newUri, Uri.fromFile(new File(CROP_FILE)), 800, 800, PHOTO_REQUEST_CUT);

                    } else {
                        picPath=getPath(context, data.getData());
                        return decodeUriAsCompressBitmap(context, data.getData());

//                        try {
//                            return MediaStore.Images.Media.getBitmap(context
//                                    .getActivity().getContentResolver(), data.getData());
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }

                    break;

                // 裁剪
                case TakePhotoUtil.PHOTO_REQUEST_CUT:
                    picPath=Uri.fromFile(new File(CROP_FILE)).getPath();
                    return decodeUriAsBitmap(context, Uri.fromFile(new File(CROP_FILE)));
                default:
                    break;
            }
        }

        return null;
    }


    /**
     * @param context
     * @param requestCode
     * @param resultCode
     * @param data
     * @param crop        是否需要剪裁
     * @return
     */
    public static Bitmap dealActivityResult(Activity context, int requestCode, int resultCode, Intent data, boolean crop) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                // 拍照
                case TakePhotoUtil.PHOTO_REQUEST_CAREMA:

                    if (crop) {

                        cropImageUri(context, Uri.fromFile(new File(PHOTO_FILE)), Uri.fromFile(new File(CROP_FILE)), 800, 800, PHOTO_REQUEST_CUT);

                    } else {
                        picPath=getPath(context,Uri.fromFile(new File(CROP_FILE)));
                        return decodeUriAsCompressBitmap(context, Uri.fromFile(new File(PHOTO_FILE)));

                    }

                    break;

                // 相册
                case TakePhotoUtil.PHOTO_REQUEST_GALLERY:

                    if (data == null) {
                        return null;
                    }

                    if (crop) {

                        //从相册选取成功后，需要从Uri中拿出图片的绝对路径，再调用剪切
                        Uri newUri = Uri.parse("file:///" + getPath(context, data.getData()));
                        if (newUri == null) {
                            return null;
                        }
                        cropImageUri(context, newUri, Uri.fromFile(new File(CROP_FILE)), 800, 800, PHOTO_REQUEST_CUT);

                    } else {
                        picPath=getPath(context, data.getData());
                        return decodeUriAsCompressBitmap(context, data.getData());

//                        try {
//                            return MediaStore.Images.Media.getBitmap(context
//                                    .getActivity().getContentResolver(), data.getData());
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }

                    break;

                // 裁剪
                case TakePhotoUtil.PHOTO_REQUEST_CUT:
                    picPath=getPath(context,Uri.fromFile(new File(CROP_FILE)));
                    return decodeUriAsBitmap(context, Uri.fromFile(new File(CROP_FILE)));
                default:
                    break;
            }
        }

        return null;
    }

    //调用系统的剪裁处理图片并保存至imageUri中
    public static void cropImageUri(Fragment context, Uri orgUri, Uri desUri, int width, int height, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(orgUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 800);
        intent.putExtra("aspectY", 799);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", false);
        //将剪切的图片保存到目标Uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        context.startActivityForResult(intent, requestCode);
    }

    //调用系统的剪裁处理图片并保存至imageUri中
    public static void cropImageUri(Activity context, Uri orgUri, Uri desUri, int width, int height, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(orgUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 800);
        intent.putExtra("aspectY", 799);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", false);
        //将剪切的图片保存到目标Uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        context.startActivityForResult(intent, requestCode);
    }

    //从Uri中获取Bitmap格式的图片
    private static Bitmap decodeUriAsBitmap(Fragment context, Uri uri) {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(context.getActivity().getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    //从Uri中获取Bitmap格式的图片
    private static Bitmap decodeUriAsBitmap(Activity context, Uri uri) {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    private static Bitmap decodeUriAsCompressBitmap(Fragment context, Uri uri) {
        Bitmap bitmap;
        try {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;//只读边,不读内容
//            Bitmap bitmapInfo = BitmapFactory.decodeStream(context.getActivity().getContentResolver().openInputStream(uri));
            Bitmap bitmapInfo = BitmapFactory.decodeFile(getImageAbsolutePath(context, uri));

            newOpts.inJustDecodeBounds = false;

            //获取图片的宽高
            int bitmapWidth = bitmapInfo.getWidth();
            int bitmapHeight = bitmapInfo.getHeight();

            //设置需要的宽高
            float width = 1000f;
            float height = 1000f;

            float be = 1;//设置比例为1

            if (bitmapWidth > bitmapHeight && bitmapHeight > height) {

                be = bitmapWidth / width;

            } else if (bitmapHeight > bitmapWidth && bitmapWidth > width) {

                be = bitmapHeight / height;

            }

            if (be < 1) {
                be = 1;
            }


            newOpts.inSampleSize = (int) Math.ceil(be);
            newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//该模式是默认的,可不设
            newOpts.inPurgeable = true;// 同时设置才会有效
            newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

            bitmap = BitmapFactory.decodeStream(context.getActivity().getContentResolver().openInputStream(uri), null, newOpts);


        } catch (Exception e) {
            ToastUtil.showShort(context.getActivity(), "读取图片信息出错！");
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    private static Bitmap decodeUriAsCompressBitmap(Activity context, Uri uri) {
        Bitmap bitmap;
        try {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;//只读边,不读内容
//            Bitmap bitmapInfo = BitmapFactory.decodeStream(context.getActivity().getContentResolver().openInputStream(uri));
            Bitmap bitmapInfo = BitmapFactory.decodeFile(getImageAbsolutePath(context, uri));

            newOpts.inJustDecodeBounds = false;

            //获取图片的宽高
            int bitmapWidth = bitmapInfo.getWidth();
            int bitmapHeight = bitmapInfo.getHeight();

            //设置需要的宽高
            float width = 1000f;
            float height = 1000f;

            float be = 1;//设置比例为1

            if (bitmapWidth > bitmapHeight && bitmapHeight > height) {

                be = bitmapWidth / width;

            } else if (bitmapHeight > bitmapWidth && bitmapWidth > width) {

                be = bitmapHeight / height;

            }

            if (be < 1) {
                be = 1;
            }


            newOpts.inSampleSize = (int) Math.ceil(be);
            newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//该模式是默认的,可不设
            newOpts.inPurgeable = true;// 同时设置才会有效
            newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, newOpts);


        } catch (Exception e) {
            ToastUtil.showShort(context, "读取图片信息出错！");
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


    @SuppressLint("NewApi")
    public static String getPath(final Fragment context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context.getActivity(), uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    @SuppressLint("NewApi")
    public static String getPath(final Activity context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Fragment context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getActivity().getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param context
     * @param imageUri
     * @author yaoxing
     * @date 2014-10-12
     */
    @TargetApi(19)
    public static String getImageAbsolutePath(Fragment context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context.getActivity(), imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param context
     * @param imageUri
     * @author yaoxing
     * @date 2014-10-12
     */
    @TargetApi(19)
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }


    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 200) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

}
