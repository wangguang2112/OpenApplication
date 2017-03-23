package com.guang.wang.openapplication.encrypt.utils;

import com.facebook.android.crypto.keychain.AndroidConceal;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.CryptoConfig;
import com.facebook.crypto.Entity;
import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.exception.KeyChainException;
import com.facebook.crypto.keychain.KeyChain;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangguang.
 * Date:2017/1/18
 * Description:
 */

public class EncryptUtil {

    public static final String TAG = "EncryptUtil";

    public static final String ENCRYPT = "encrypt";

    public static final String DECRYPT = "decrypt";

    public static final int UPDATA_PRECENT = 0;

    private static SimpleDateFormat f = new SimpleDateFormat();

    public synchronized static void encryptData(Context context, byte[] data, String toFile) throws IOException, KeyChainException, CryptoInitializationException {
        long start = logStart(ENCRYPT, toFile);
        KeyChain keyChain = new SharedPrefsBackedKeyChain(context, CryptoConfig.KEY_256);
        Crypto crypto = AndroidConceal.get()
                                      .createDefaultCrypto(keyChain);
        if (!crypto.isAvailable()) {
            return;
        }
        OutputStream fileStream = new BufferedOutputStream(new FileOutputStream(toFile));
        OutputStream outputStream = crypto.getCipherOutputStream(fileStream, Entity.create("123"));
        outputStream.write(data);
        outputStream.close();
        logEnd(start, toFile);
    }

    public synchronized static void encryptFile(Context context, String srcFile, String toFile) throws IOException, KeyChainException, CryptoInitializationException {
        long start = logStart(ENCRYPT, toFile);
        KeyChain keyChain = new SharedPrefsBackedKeyChain(context, CryptoConfig.KEY_256);
        Crypto crypto = AndroidConceal.get()
                                    .createDefaultCrypto(keyChain);
        if (!crypto.isAvailable()) {
            return;
        }
        OutputStream fileStream = new BufferedOutputStream(new FileOutputStream(toFile));
        OutputStream outputStream = crypto.getCipherOutputStream(fileStream, Entity.create("123"));
        InputStream in = new FileInputStream(srcFile);
        byte[] data = new byte[1024];
        int rlen = 0;
        while ((rlen = in.read(data)) != -1) {
            outputStream.write(data, 0, rlen);
        }
        in.close();
        outputStream.close();
        logEnd(start, toFile);
    }


    public synchronized static void decryptionFile(Context context, String srcFile, String toFile) throws IOException, KeyChainException, CryptoInitializationException {
        long start = logStart(DECRYPT, toFile);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(toFile));
        FileInputStream fileStream = new FileInputStream(srcFile);
        KeyChain keyChain = new SharedPrefsBackedKeyChain(context, CryptoConfig.KEY_256);
        Crypto crypto = AndroidConceal.get()
                                      .createDefaultCrypto(keyChain);
        InputStream inputStream = crypto.getCipherInputStream(
                fileStream,
                Entity.create("123"));

        int read;
        byte[] buffer = new byte[1024];
        while ((read = inputStream.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }

        inputStream.close();
        out.close();
        logEnd(start, toFile);
    }

    private static long logStart(String type, String tofile) {
        long start = System.currentTimeMillis();
        Log.d(TAG, "=========== " + type + " =========");
        Log.d(TAG, "file:" + tofile + " start=" + f.format(new Date(start)));
        return start;
    }

    private static void logEnd(long start, String tofile) {
        Log.d(TAG, "file:" + tofile + " end=" + f.format(new Date(start)));
        long end = System.currentTimeMillis();
        float u = (float) (end - start) / 1000;
        long lenght = (new File(tofile)).length();
        Log.d(TAG, "size:= " + lenght);
        Log.d(TAG, "size:=  " + lenght / (1024 * 1024) + " mb " + (lenght / 1024) % 1024 + " kb " + lenght % 1024 + " byte ");
        Log.d(TAG, "use:= " + u + "s");
    }

    public static void createBigFile(Handler handler, String toFile, int num) throws IOException {
        OutputStream out = new BufferedOutputStream(new FileOutputStream(toFile));
        int position = 0;
        float temp;
        for (int i = 0; i < num; i++) {
            out.write("11111111".getBytes());
            if (handler != null) {
                if ((temp = (i * 100 / num)) > position) {
                    position = (int) temp;
                    Message m = Message.obtain();
                    m.what = UPDATA_PRECENT;
                    m.arg1 = position;
                    handler.sendMessage(m);
                    Log.d("wangguang", "send msg.arg1=" + m.arg1);
                }
            }
        }
        out.close();
    }
}
