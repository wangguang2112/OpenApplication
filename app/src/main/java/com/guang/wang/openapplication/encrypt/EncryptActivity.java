package com.guang.wang.openapplication.encrypt;

import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.exception.KeyChainException;
import com.guang.wang.openapplication.BaseActivity;
import com.guang.wang.openapplication.R;
import com.guang.wang.openapplication.encrypt.utils.EncryptUtil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EncryptActivity extends BaseActivity {

    String FILENAME;

    String ENFILENAME;

    ProgressDialog dialog;

    ExecutorService exe = Executors.newSingleThreadExecutor();

    @BindView(R.id.viewxmltext)
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FILENAME = getExternalCacheDir().getAbsolutePath() + "/wang.txt";
        ENFILENAME = getExternalCacheDir().getAbsolutePath() + "/guang.data";
        setContentView(R.layout.activity_encrypt);
        ButterKnife.bind(this);
        addHandlerCallBack(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == EncryptUtil.UPDATA_PRECENT) {
                    Log.d("wangguang", "handleMessage: msg.arg1=" + msg.arg1);
                    if (dialog != null&&dialog.isShowing()) {
                        dialog.setProgress(msg.arg1);
                        if (msg.arg1 >= 99) {
                            dialog.dismiss();
                            Toast.makeText(EncryptActivity.this, "create complete!", Toast.LENGTH_SHORT)
                                 .show();
                        }
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @OnClick(R.id.create)
    void creatFile(View view) {
        if (dialog == null) {
            dialog = new ProgressDialog(EncryptActivity.this);
        }
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgress(0);
        dialog.setMessage(FILENAME.substring(FILENAME.lastIndexOf("/", FILENAME.length() - 1)));
        dialog.setTitle("create");
        dialog.setCancelable(false);
        dialog.show();

        exe.execute(crateThread);

    }

    @OnClick(R.id.encrypt)
    void encryptFile(View view) {
        exe.execute(enr);
    }

    @OnClick(R.id.decrypt)
    void decryptFile(View view) {
        exe.execute(der);
    }

    Runnable enr = new Runnable() {
        @Override
        public void run() {
            try {
                EncryptUtil.encryptFile(EncryptActivity.this, FILENAME, ENFILENAME);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (KeyChainException e) {
                e.printStackTrace();
            } catch (CryptoInitializationException e) {
                e.printStackTrace();
            }
        }
    };
    @OnClick(R.id.viewxml)
    void viewXml(View view){
        String path=getFilesDir().getAbsolutePath();
        File dir=new File(path.substring(0,path.lastIndexOf("/")),"shared_prefs");
        Log.d("wangguang", "dir: name="+dir.getAbsolutePath());
        for(String name:dir.list()) {
            Log.d("wangguang", "viewXml: name="+name);
        }
        File file=new File(dir,"crypto.KEY_256.xml");
        try {
            StringBuilder bu=new StringBuilder();
            FileReader reader=new FileReader(file);
            BufferedReader bfr=new BufferedReader(reader);
            String str;
            while((str=bfr.readLine())!=null){
                bu.append(str+"\n");
            }
            t.setText(bu.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    Runnable der = new Runnable() {
        @Override
        public void run() {
            try {
                EncryptUtil.decryptionFile(EncryptActivity.this, ENFILENAME, FILENAME);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (KeyChainException e) {
                e.printStackTrace();
            } catch (CryptoInitializationException e) {
                e.printStackTrace();
            }
        }
    };

    Runnable crateThread = new Runnable() {
        @Override
        public void run() {
            try {
                EncryptUtil.createBigFile(getMainHandler(), FILENAME, 1024 * 1024 * 4);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

}
