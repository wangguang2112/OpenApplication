package com.guang.wang.openapplication;

import com.guang.wang.openapplication.adapter.MainExListAdapter;
import com.guang.wang.openapplication.animate.AnimateActivity;
import com.guang.wang.openapplication.annotation.AnnotaActivity;
import com.guang.wang.openapplication.cache.DiskCacheActivity;
import com.guang.wang.openapplication.cache.LruCacheActivity;
import com.guang.wang.openapplication.cache.OOMActivity;
import com.guang.wang.openapplication.db.LevelDbActivity;
import com.guang.wang.openapplication.dialog.DialogActivity;
import com.guang.wang.openapplication.encrypt.EncryptActivity;
import com.guang.wang.openapplication.imview.RefreshListViewActivity;
import com.guang.wang.openapplication.notify.NotifyActivity;
import com.guang.wang.openapplication.okhttp.HttpUrlCActivity;
import com.guang.wang.openapplication.okhttp.OkhttpMainActivity;
import com.guang.wang.openapplication.puglin.LoadActivity;
import com.guang.wang.openapplication.rxjava.RxJavaActivity;
import com.guang.wang.openapplication.scroll.DragHelperActivity;
import com.guang.wang.openapplication.scroll.GustureActivity;
import com.guang.wang.openapplication.scroll.NestedScrollActivity;
import com.guang.wang.openapplication.scroll.ScrollActivity;
import com.guang.wang.openapplication.scroll.ScrollerActivity;
import com.guang.wang.openapplication.scroll.TScrollActivity;
import com.guang.wang.openapplication.syn.AsynActivity;
import com.guang.wang.openapplication.webview.PagerWebViewActivity;
import com.guang.wang.openapplication.webview.WebViewActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//自带生命周期测算
public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener, ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {

    private ExpandableListView mListView;

    MainExListAdapter adapter;

    private List<String> mGroupName;

    private List<List<String>> mChildName;
    private Class<? extends Activity>[] mActivities = new Class[]{null, OkhttpMainActivity.class, HttpUrlCActivity.class, RxJavaActivity.class, AsynActivity.class, DialogActivity.class, WebViewActivity.class,
            PagerWebViewActivity.class, ScrollActivity.class, TScrollActivity.class, ScrollerActivity.class, DragHelperActivity.class, NestedScrollActivity.class,GustureActivity.class, AnimateActivity.class,
            RefreshListViewActivity.class, NotifyActivity.class, OOMActivity.class, LruCacheActivity.class, DiskCacheActivity.class, LevelDbActivity.class, EncryptActivity.class, AnnotaActivity.class,
            LoadActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("wangguang", "Activty::onCreate");
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                                   .add(new MyFramgment(), "test")
                                   .commit();
        mListView = (ExpandableListView) findViewById(R.id.list);
//        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,texts);
        initAdapterData();
        mListView.setAdapter(adapter);
        mListView.setOnGroupClickListener(this);
        mListView.setOnChildClickListener(this);
//        TextView textView= (TextView) findViewById(R.id.test_textview);
//        textView.setText("11111111111111111111111111111111111111111111111111111111111111111111111111111"
//                + "22222222222222222222222222222222222222222222222222222222222222222222222222222222"
//                + "3333333333333333333333333333333333333333333333333333333333333333333333333333333333333"
//                + "44444444444444444444444444444444444444444444444444444444444444444444444");
//        textView.setMaxLines(3);
//        Log.d("wang",textView.getText().toString());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle()
                .equals("call")) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:15810826527"));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "未分配打电话权限", Toast.LENGTH_SHORT)
                     .show();
                return false;
            }
            startActivity(intent);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("call");
    }

    private void initAdapterData() {
        mGroupName = Arrays.asList("应用程序", "okhttp", "rxjava", "asyn", "dialog", "web", "scroll", "animate", "imView","notify", "cache","db","encrypt","Annotation","PLUGIN");
        mChildName = new ArrayList<>();
        mChildName.add(Arrays.asList("应用程序"));
        mChildName.add(Arrays.asList("okhttp","httpurl"));
        mChildName.add(Arrays.asList("rxjava"));
        mChildName.add(Arrays.asList("asyn"));
        mChildName.add(Arrays.asList("dialog"));
        mChildName.add(Arrays.asList("web", "webpager"));
        mChildName.add(Arrays.asList("scroll", "Tscroll", "Scroller", "DragHelper", "Nested","Gesture"));
        mChildName.add(Arrays.asList("animate"));
        mChildName.add(Arrays.asList("RefreshListView"));
        mChildName.add(Arrays.asList("nofity"));
        mChildName.add(Arrays.asList("oom","lru","disklru"));
        mChildName.add(Arrays.asList("levelDB"));
        mChildName.add(Arrays.asList("encrypt"));
        mChildName.add(Arrays.asList("annotation"));
        mChildName.add(Arrays.asList("puglin"));
        adapter = new MainExListAdapter(this, mGroupName, mChildName);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        int size = mChildName.get(groupPosition)
                             .size();
        if (size == 1) {
            if (groupPosition == 0) {
                String action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
                Intent intent = new Intent(action);
                intent.setData(Uri.fromParts("package", "com.wuba.bangjob", null));
                try {
                    getPackageManager().getPackageInfo("com.wuba.bangjob", PackageManager.GET_ACTIVITIES);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(MainActivity.this, "未安装该APP!", Toast.LENGTH_SHORT)
                         .show();
                }
                startActivity(intent);
                finish();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(new Intent(this, mActivities[culAllSize(mChildName, groupPosition)]), ActivityOptions.makeSceneTransitionAnimation(this)
                                                                                                                       .toBundle());
                } else {
                    startActivity(new Intent(this, mActivities[culAllSize(mChildName, groupPosition)]));
                }
            }
            return true;
        } else {
            return false;
        }

    }

    private int culAllSize(List<List<String>> lists, int position) {
        if (position == 0) {
            return 0;
        } else {
            int sum = 0;
            for (int i = 0; i < position; i++) {
                List list = lists.get(i);
                sum += list.size();
            }
            return sum;
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        if (mChildName.get(groupPosition)
                      .size() != 1) {
            startActivity(new Intent(this, mActivities[culAllSize(mChildName, groupPosition) + childPosition]));
            return true;
        } else {
            return false;
        }
    }

    public static class MyFramgment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Log.d("wangguang", "Fragment::onCreateView");
            return inflater.inflate(R.layout.activity_fragment, container);

        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Log.d("wangguang", "Fragment::onAttach");
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d("wangguang", "Fragment::onCreate");
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.d("wangguang", "Fragment::onStart");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d("wangguang", "Fragment::onStop");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d("wangguang", "Fragment::onDestroy");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.d("wangguang", "Fragment::onPause");
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.d("wangguang", "Fragment::onResume");
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Log.d("wangguang", "Fragment::onViewCreated");
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.d("wangguang", "Fragment::onActivityCreated");
        }

    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Log.d("wangguang", "Activty::onResumeFragments");
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.d("wangguang", "Activty::onAttachFragment");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("wangguang", "Activty::onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("wangguang", "Activty::onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("wangguang", "Activty::onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("wangguang", "Activty::onPause");
        showMsg(BuildConfig.BUILD_TYPE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("wangguang", "Activty::onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("wangguang", "Activty::onDestroy");
    }
}