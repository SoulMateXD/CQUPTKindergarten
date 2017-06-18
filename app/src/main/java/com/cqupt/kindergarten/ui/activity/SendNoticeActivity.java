package com.cqupt.kindergarten.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqupt.kindergarten.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendNoticeActivity extends AppCompatActivity {

    @BindView(R.id.notice_title_input_edit)
    EditText noticeTitleInputEdit;
    @BindView(R.id.notice_content_input_edit)
    EditText noticeContentInputEdit;
    @BindView(R.id.send_button)
    TextView sendButton;
    @BindView(R.id.cancel_button)
    TextView cancelButton;
    @BindView(R.id.send_notice_layout)
    LinearLayout sendNoticeLayout;
    @BindView(R.id.activity_send_notice_toolbar)
    Toolbar activitySendNoticeToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notice);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        setSupportActionBar(activitySendNoticeToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
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

    @OnClick(R.id.send_notice_layout)
    public void onViewClicked() {
        hideKeyBoard();
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
