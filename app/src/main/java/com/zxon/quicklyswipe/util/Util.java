package com.zxon.quicklyswipe.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.socks.library.KLog;
import com.zxon.quicklyswipe.QuicklySwipeApplication;
import com.zxon.quicklyswipe.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leon on 16/4/7.
 */

public class Util {

    public static final int START_INTENT_SUCCESS = 0;
    public static final int START_INTENT_FAILED_NO_ACTIVITY_FOUND = -1;
    public static final int START_INTENT_FAILED_NOT_EXPORTED = -2;
    public static final int START_INTENT_FAILED_NULL_POINTER = -3;
    public static final String URL_GOOGLE_PLAY = "https://play.google.com/store/apps/details?id=";

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (density * dp + .5f);
    }

//    public static String formatShortFileSize(Context context, long number) {
//        if (context == null) {
//            return "";
//        }
//
//        float result = number;
//        int suffix = R.string.kilobyteShort;
//        if (result > 900) {
//            suffix = R.string.kilobyteShort;
//            result = result / 1024;
//        }
//        if (result > 900) {
//            suffix = R.string.megabyteShort;
//            result = result / 1024;
//        }
//        if (result > 900) {
//            suffix = R.string.gigabyteShort;
//            result = result / 1024;
//        }
//        if (result > 900) {
//            suffix = R.string.terabyteShort;
//            result = result / 1024;
//        }
//        if (result > 900) {
//            suffix = R.string.petabyteShort;
//            result = result / 1024;
//        }
//        String value;
//        if (result < 1) {
//            value = String.format("%.2f", result);
//        } else if (result < 10) {
//            value = String.format("%.2f", result);
//        } else if (result < 100) {
//            value = String.format("%.1f", result);
//        } else {
//            value = String.format("%.0f", result);
//        }
//        return context.getResources().
//                getString(R.string.clean_file_size_suffix,
//                        value, context.getString(suffix));
//    }

    /**
     * 获取可用RAM大小
     */
    public static long getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem / (1024 * 1024);
    }

    /**
     * 获取总RAM大小
     */
    public static long getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件 
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                KLog.i(str2, num + "\t");
            }
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return initial_memory / (1024 * 1024);
    }

    public static int startActivityByStat(Context context, Intent intent) {
        int bResult = START_INTENT_SUCCESS;
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            bResult = START_INTENT_FAILED_NO_ACTIVITY_FOUND;
        } catch (SecurityException e) {
            bResult = START_INTENT_FAILED_NOT_EXPORTED;
        } catch (NullPointerException e) {
            bResult = START_INTENT_FAILED_NULL_POINTER;
        }
        return bResult;
    }

    public static boolean isIntentExist(Context context, Intent intent) {
        if (intent == null) {
            return false;
        }
        PackageManager localPackageManager = context.getPackageManager();
        if (localPackageManager.resolveActivity(intent, 0) == null) {
            return false;
        }
        return true;
    }

    public static boolean startActivity(Context context, Intent intent) {
        boolean bResult = true;
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            bResult = false;
        } catch (SecurityException e) {
            bResult = false;
        } catch (NullPointerException e) {
            bResult = false;
        } catch (Exception e) {
            bResult = false;
        }
        return bResult;
    }

    public static boolean launchApp(String pkgName) {
        if (TextUtils.isEmpty(pkgName))
            return false;
        Context context = QuicklySwipeApplication.getInstance();
        Intent intent = context.getPackageManager().
                getLaunchIntentForPackage(pkgName);
        return startActivity(context, intent);
    }


    public static boolean isAppInstalled(String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = QuicklySwipeApplication.getInstance().getPackageManager().
                    getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
            return false;
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    public static List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = QuicklySwipeApplication.getInstance().getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }

    public static void storeImage(Bitmap image, File pictureFile) {
        if (pictureFile == null) {
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    @SuppressLint("NewApi")
    public static List<String> getNotificationText(Notification notification) {
        if (null == notification) return null;

        RemoteViews views = null;
        if (Build.VERSION.SDK_INT >= 16) {
            views = notification.bigContentView;
        }

        if (views == null) views = notification.contentView;
        if (views == null) return null;

        List<String> text = new ArrayList<String>();
        try {
            Field field = views.getClass().getDeclaredField("mActions");
            field.setAccessible(true);

            @SuppressWarnings("unchecked")
            ArrayList<Parcelable> actions = (ArrayList<Parcelable>) field.get(views);

            for (Parcelable p : actions) {
                Parcel parcel = Parcel.obtain();
                p.writeToParcel(parcel, 0);
                parcel.setDataPosition(0);

                int tag = parcel.readInt();
                if (tag != 2) continue;

                parcel.readInt();
                String methodName = parcel.readString();
                if (null == methodName) {
                    continue;
                } else if (methodName.equals("setText")) {
                    parcel.readInt();

                    String t = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel).toString().trim();
                    text.add(t);
                }
                parcel.recycle();
            }
        } catch (Exception e) {
        }

        return text;
    }

    public static String getMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) return false;

        File file = new File(path);
        return file.exists() && file.delete();
    }

    public static void showToast(Context context, String msg) {
        showToast(context, msg, true);
    }

    public static void showToast(Context context, String msg, boolean shortTime) {
        Toast.makeText(context, msg,
                shortTime ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }

    public static final void showAppInGooglePlay(Context context, String pkgName) {
        if (context == null ||
                TextUtils.isEmpty(pkgName)) {

            return;
        }

        try {
            StringBuilder gpBuilder = new StringBuilder();
            gpBuilder.append(URL_GOOGLE_PLAY);
            gpBuilder.append(pkgName);
            final String gpUrl = gpBuilder.toString();

//            StringBuilder marketBuilder = new StringBuilder();
//            marketBuilder.append("market://details?id=");
//            marketBuilder.append(pkgName);
//            marketBuilder.append(suffixStr);
//            final String marketUrl = marketBuilder.toString();

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(gpUrl));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClassName("com.android.vending", "com.google.android.finsky.activities.MainActivity");
//            intent.setData(Uri.parse(marketUrl));
            if (startActivity(context, intent)) {
                return;
            }

            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(gpUrl));
            startActivity(context, intent);

        } catch (Exception e) {
        }
    }

//    public static void shareTo(Context context, String conent) {
//        try {
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_SEND);
//            intent.putExtra(Intent.EXTRA_TEXT, conent);
//            intent.setType("text/plain");
//            context.startActivity(Intent.createChooser(intent,
//                    context.getString(R.string.setting_share_chooser)));
//        } catch (Exception e) {
//        }
//    }

//    public static void feedBackByEmail(Context context, String mailAdress, String title) {
//        Uri uri = Uri.parse("mailto:" + mailAdress);
////        String[] email = {"3802**92@qq.com"};
//        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
////        intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
//        intent.putExtra(Intent.EXTRA_SUBJECT, title); // 主题
////        intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分"); // 正文
//        context.startActivity(Intent.createChooser(intent, context.getString(R.string.setting_email_chooser)));
//    }

    public static String getVersionName() {
        try {
            Context context = QuicklySwipeApplication.getInstance().getApplicationContext();
            String pkgName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkgName, 0).versionName;
            return "v" + versionName;
        } catch (Exception e) {
        }
        return "";
    }

    public static boolean isPermissionGranted(Context context, String permission) {
        PackageManager pm = context.getPackageManager();
        if (pm == null)
            return false;
        return PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission(permission,
                        QuicklySwipeApplication.getInstance().getPackageName());
    }

}


