package com.cqupt.kindergarten.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqupt.kindergarten.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendKnowledgeActivity extends AppCompatActivity {

    @BindView(R.id.activity_send_knowledge_toolbar)
    Toolbar activitySendKnowledgeToolbar;
    @BindView(R.id.knowledge_title_input_edit)
    EditText knowledgeTitleInputEdit;
    @BindView(R.id.knowledge_pic)
    ImageView knowledgePic;
    @BindView(R.id.choose_pic_button)
    TextView choosePicButton;
    @BindView(R.id.knowledge_website_input_edit)
    EditText knowledgeWebsiteInputEdit;
    @BindView(R.id.send_button)
    TextView sendButton;
    @BindView(R.id.cancel_button)
    TextView cancelButton;
    @BindView(R.id.knowledge_layout)
    LinearLayout knowledgeLayout;


    public static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_knowledge);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(activitySendKnowledgeToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }

    @OnClick({R.id.knowledge_pic, R.id.choose_pic_button, R.id.send_button, R.id.cancel_button, R.id.knowledge_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.knowledge_pic:
            case R.id.choose_pic_button:
                chooseFromAlbum();
                break;
            case R.id.send_button:
                break;
            case R.id.cancel_button:
                break;
            case R.id.knowledge_layout:
                hideKeyBoard();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * 以下为从相册选择图片时需要调用的方法、权限申请等
    *
    * */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_PHOTO:
                if (resultCode == -1 && data != null){
                    Uri imageUri = data.getData();
                    String imagePath = getImagePath(imageUri, null);
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    knowledgePic.setImageBitmap(bitmap);
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(SendKnowledgeActivity.this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void chooseFromAlbum() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else {
            openAlbum();
        }

    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    /*
    *   隐藏软键盘
    * */
    private void hideKeyBoard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
