package com.cqupt.kindergarten.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqupt.kindergarten.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CLassTeacherAvtivity extends AppCompatActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ArrayList<ItemForTeacher> datasForTeacher = new ArrayList<>();
    private ArrayList<ItemForParent> datasForParent = new ArrayList<>();


    // 用户登录类型， 0表示教师， 1表示家长
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_teacher_avtivity);
        ButterKnife.bind(this);

        initView();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (type == 0) {
            recyclerView.setAdapter(new TeacherAdapter(datasForTeacher, this));
        } else if (type == 1) {
            recyclerView.setAdapter(new ParentAdapter(this, datasForParent));
        }


    }

    private void initView() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);

        recyclerView = (RecyclerView) findViewById(R.id.class_teacher_recycler);
        toolbar = (Toolbar) findViewById(R.id.class_teacher_toolbar);
        toolbar.setTitle("");
        //判断登录用户类型，改变名单标题
        if (type == 0) {
            toolbarTitle.setText("学生及家长名单");
        } else if (type == 1) {
            toolbarTitle.setText("班级教师");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();
    }

    private void initData() {
        ItemForTeacher itemForTeacher;
        for (int i = 1; i <= 30; i++) {
            itemForTeacher = new ItemForTeacher("张瀚漩", "6666666666" + i, "许建军", "9999999999" + i,
                    "王小" + i, "中一班");
            datasForTeacher.add(itemForTeacher);
        }

        ItemForParent itemForParent;
        for (int i = 1; i <= 30; i++) {
            itemForParent = new ItemForParent("韩" + i + "青老师", "23333333333" + i, "安卓武装部");
            datasForParent.add(itemForParent);
        }
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

    private class TeacherAdapter extends RecyclerView.Adapter<TeacherViewHolder> implements View.OnClickListener {
        private ArrayList<ItemForTeacher> datas;
        private LayoutInflater inflater;

        TeacherAdapter(ArrayList<ItemForTeacher> datas, Context context) {
            this.datas = datas;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TeacherViewHolder teacherViewHolder =
                    new TeacherViewHolder(
                            inflater.inflate(R.layout.item_class_infolist_teacher, parent, false));
            return teacherViewHolder;
        }

        @Override
        public void onBindViewHolder(TeacherViewHolder holder, int position) {
            ItemForTeacher data = datas.get(position);
            holder.motherName.setText(data.getMotherName());
            holder.motherTel.setText(data.getMotherTel());
            holder.fatherName.setText(data.getFatherName());
            holder.fatherTel.setText(data.getFatherTel());
            holder.childName.setText(data.getChildName());
            holder.childClass.setText(data.getChildClass());
            holder.motherTel.setOnClickListener(this);
            holder.fatherTel.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        @Override
        public void onClick(View view) {
            TextView textView = (TextView) view;
            String tel = (String) textView.getText();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + tel));
            startActivity(intent);
        }
    }

    private class TeacherViewHolder extends RecyclerView.ViewHolder {
        private TextView childName;
        private TextView childClass;
        private TextView fatherName;
        private TextView fatherTel;
        private TextView motherName;
        private TextView motherTel;

        public TeacherViewHolder(View itemView) {
            super(itemView);
            childName = (TextView) itemView.findViewById(R.id.item_class_infolist_teacher_child_name);
            childClass = (TextView) itemView.findViewById(R.id.item_class_infolist_teacher_child_class);
            fatherName = (TextView) itemView.findViewById(R.id.item_calss_infolist_teacher_father_name);
            fatherTel = (TextView) itemView.findViewById(R.id.item_calss_infolist_teacher_father_tel);
            motherName = (TextView) itemView.findViewById(R.id.item_calss_infolist_teacher_mother_name);
            motherTel = (TextView) itemView.findViewById(R.id.item_calss_infolist_teacher_mother_tel);
        }
    }

    class ParentAdapter extends RecyclerView.Adapter<ParentViewHolder> implements View.OnClickListener {
        private LayoutInflater inflater;
        private ArrayList<ItemForParent> datas;

        ParentAdapter(Context context, ArrayList<ItemForParent> datas) {
            inflater = LayoutInflater.from(context);
            this.datas = datas;
        }

        @Override
        public ParentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ParentViewHolder(
                    inflater.inflate(R.layout.item_class_infolist_parent, parent, false));
        }

        @Override
        public void onBindViewHolder(ParentViewHolder holder, int position) {
            ItemForParent data = datas.get(position);
            holder.teacherClass.setText(data.getTeacherClass());
            holder.teacherName.setText(data.getTeacherName());
            holder.teacherTel.setText(data.getTeacherTel());
            holder.teacherTel.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        @Override
        public void onClick(View view) {
            TextView textView = (TextView) view;
            String tel = (String) textView.getText();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + tel));
            startActivity(intent);
        }
    }

    class ParentViewHolder extends RecyclerView.ViewHolder {
        private TextView teacherName;
        private TextView teacherClass;
        private TextView teacherTel;

        public ParentViewHolder(View itemView) {
            super(itemView);
            teacherClass = (TextView) itemView.findViewById(R.id.item_class_infolist_parent_teacher_class);
            teacherName = (TextView) itemView.findViewById(R.id.item_class_infolist_parent_teacher_name);
            teacherTel = (TextView) itemView.findViewById(R.id.item_class_infolist_parent_teacher_tel);
        }
    }

    //教师界面， item的信息
    private class ItemForTeacher {
        String fatherName;
        String fatherTel;
        String motherName;
        String motherTel;
        String childName;
        String childClass;

        public ItemForTeacher(String fatherName, String fatherTel, String motherName,
                              String motherTel, String childName, String childClass) {
            this.fatherName = fatherName;
            this.fatherTel = fatherTel;
            this.motherName = motherName;
            this.motherTel = motherTel;
            this.childName = childName;
            this.childClass = childClass;
        }

        public String getFatherName() {
            return fatherName;
        }

        public String getFatherTel() {
            return fatherTel;
        }

        public String getMotherName() {
            return motherName;
        }

        public String getMotherTel() {
            return motherTel;
        }

        public String getChildName() {
            return childName;
        }

        public String getChildClass() {
            return childClass;
        }
    }

    //家长界面， item信息
    private class ItemForParent {
        private String teacherName;
        private String teacherTel;
        private String teacherClass;

        public ItemForParent(String teacherName, String teacherTel, String teacherClass) {
            this.teacherName = teacherName;
            this.teacherTel = teacherTel;
            this.teacherClass = teacherClass;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public String getTeacherTel() {
            return teacherTel;
        }

        public String getTeacherClass() {
            return teacherClass;
        }
    }
}
