package com.cqupt.kindergarten.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.bean.ItemForTeacher;
import com.cqupt.kindergarten.bean.ItemForTeacherParent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

public class ParentDetailInfoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.parent_details_child_name)
    TextView parentDetailsChildName;
    @BindView(R.id.parent_details_father_name)
    TextView parentDetailsFatherName;
    @BindView(R.id.parent_details_father_tel)
    TextView parentDetailsFatherTel;
    @BindView(R.id.parent_details_mother_name)
    TextView parentDetailsMotherName;
    @BindView(R.id.parent_details_mother_tel)
    TextView parentDetailsMotherTel;


    private ItemForTeacher bean;
    private ArrayList<ItemForTeacherParent> parents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_detail_info);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        Intent intent = getIntent();
        bean = intent.getParcelableExtra("Bean");
        parents = bean.getParents();
        parentDetailsChildName.setText("学生姓名 : " + bean.getChildName());
        if (parents.size() == 1) {
            parentDetailsFatherName.setVisibility(GONE);
            parentDetailsFatherTel.setVisibility(GONE);
            parentDetailsMotherName.setText("家长姓名 : " + parents.get(0).getParentName());
            parentDetailsMotherTel.setText("联系方式 : " + parents.get(0).getParentTel());
        } else {
            parentDetailsMotherName.setText("家长姓名 : " + parents.get(0).getParentName());
            parentDetailsMotherTel.setText("联系方式 : " + parents.get(0).getParentTel());
            parentDetailsFatherName.setText("家长姓名 : " + parents.get(1).getParentName());
            parentDetailsFatherTel.setText("联系方式 : " + parents.get(1).getParentTel());
        }
    }

    @OnClick({R.id.parent_details_father_tel, R.id.parent_details_mother_tel})
    public void onViewClicked(View view) {
        String tel = null;

        switch (view.getId()) {
            case R.id.parent_details_father_tel:
                tel = parents.get(1).getParentTel();
                break;
            case R.id.parent_details_mother_tel:
                tel = parents.get(0).getParentTel();
                break;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + tel));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
