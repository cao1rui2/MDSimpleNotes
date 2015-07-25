package com.example.caorui.mdsimplenotes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by caorui on 2015/7/24.
 */
public class AboutActivity extends AppCompatActivity {

    private Toolbar eToolbar;
    private TextView aboutTitle;
    private TextView aboutText;
    private Button point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        eToolbar = (Toolbar) findViewById(R.id.toolbar);
        eToolbar.setTitle("关于");
        setSupportActionBar(eToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        aboutTitle = (TextView) findViewById(R.id.about_title);
        aboutTitle.setText("简单笔记 1.0");
        aboutText = (TextView) findViewById(R.id.about_text);
        aboutText.setText(
                "功能说明\n\n" +
                "存储：存储行为放在了onPause()里，所以返回自动存储后台自动存储，就不再弹窗询问是否保存\n\n" +
                "删除：可长按卡片删除，可在编辑活动里删除，选项都在二级菜单里，所以也不再弹窗询问\n\n" +
                "闹钟：因为感觉内部闹钟实现起来有点麻烦，所以直接调用系统闹钟，可能有部分系统不兼容\n\n" +
                "导出：导出文本是把所有笔记一起导出到一个叫笔记文本.txt的文件，这导致导入比较麻烦，所以导入就没做了\n\n" +
                "刷子：复制笔记到剪切板\n\n" +
                "分享：分享笔记，本应用也支持接收分享，不过当然仅限文字\n\n" +
                "字号：仅能在编辑活动中使用且无法保存，因为不知道怎样实现保存设置 _(:3 」∠)_\n\n" +
                "沉浸模式：因为没有5.0设备，而在虚拟机上不管怎么整状态栏就是不变色，所以等以后有钱了再说吧\n\n" +
                "清单模式：想了想该如何实现，结果完全没有头绪ಥ_ಥ\n\n" +
                "插入图片：都能插入图片了，想必相当不简单了，所以本应用必然无此功能");

        point = (Button) findViewById(R.id.point);
        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id="+getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                AboutActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
