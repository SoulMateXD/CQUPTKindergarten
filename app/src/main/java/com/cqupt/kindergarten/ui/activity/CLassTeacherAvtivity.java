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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.bean.InfoForParentBean;
import com.cqupt.kindergarten.bean.InfoForTeacherBean;
import com.cqupt.kindergarten.bean.ItemForParent;
import com.cqupt.kindergarten.bean.ItemForTeacher;
import com.cqupt.kindergarten.bean.ItemForTeacherParent;
import com.cqupt.kindergarten.bean.Parent;
import com.cqupt.kindergarten.bean.Teacher;
import com.cqupt.kindergarten.util.GsonUtil;
import com.cqupt.kindergarten.util.OkHttpUtil;
import com.cqupt.kindergarten.util.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CLassTeacherAvtivity extends AppCompatActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.class_teacher_toolbar)
    Toolbar classTeacherToolbar;
    @BindView(R.id.class_teacher_for_teacher)
    LinearLayout classTeacherForTeacher;
    @BindView(R.id.class_teacher_for_parent)
    LinearLayout classTeacherForParent;
    @BindView(R.id.class_teacher_recycler)
    RecyclerView classTeacherRecycler;

    private static final String URL_FOR_PARENT = "http://172.20.2.164:8080/kindergarden/TeacherByClass";
    private static final String URL_FOR_TEACHER = "http://172.20.2.164:8080/kindergarden/StudentParent";
    private static final String KEY_FOR_PARENT = "classid";
    private static final String KEY_FOR_TEACHER = "classname";


    private String url;
    private String key;
    private Map<String, Object> params;
    private OkHttpUtil okHttpUtil;
    private RecyclerView.Adapter adapter;
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
    }

    private void initView() {
        okHttpUtil = new OkHttpUtil(this);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        params = new HashMap<>();
        classTeacherRecycler.setLayoutManager(new LinearLayoutManager(this));

        //判断登录用户类型，改变名单标题
        if (type == 0) {
            classTeacherForTeacher.setVisibility(View.VISIBLE);
            classTeacherForParent.setVisibility(View.GONE);
            toolbarTitle.setText("学生及家长名单");
        } else if (type == 1) {
            classTeacherForTeacher.setVisibility(View.GONE);
            classTeacherForParent.setVisibility(View.VISIBLE);
            toolbarTitle.setText("班级教师");
        }
        setSupportActionBar(classTeacherToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        //根据用户类型赋值并网络请求
        if (type == 0) {
            adapter = new TeacherAdapter(this, datasForTeacher);
            classTeacherRecycler.setAdapter(adapter);
            url = URL_FOR_TEACHER;
            Teacher teacher = DataSupport.findFirst(Teacher.class);
            key = teacher.getcId();
            params.put(KEY_FOR_TEACHER, key);
            okHttpUtil.mOkHttpPost(url, params, new OkHttpUtil.OkHttpUtilCallback() {
                @Override
                public void onSuccess(String response) {
                    dealResponseForTeacher(response);
                }

                @Override
                public void onFailure(String response) {
                    ToastUtils.showShortToast("不好意思，服务器出故障了");
                }
            });
        } else if (type == 1) {
            adapter = new ParentAdapter(this, datasForParent);
            classTeacherRecycler.setAdapter(adapter);
            url = URL_FOR_PARENT;
            Parent parent = DataSupport.findFirst(Parent.class);
            key = parent.getcId();
            params.put(KEY_FOR_PARENT, key);
            okHttpUtil.mOkHttpPost(url, params, new OkHttpUtil.OkHttpUtilCallback() {
                @Override
                public void onSuccess(String response) {
                    dealResponseForParent(response);
                }

                @Override
                public void onFailure(String response) {
                    ToastUtils.showShortToast("不好意思，服务器出故障了");
                }
            });
        }

        classTeacherRecycler.setAdapter(adapter);

    }

    private void dealResponseForParent(String response) {
        ArrayList<InfoForParentBean> beans = GsonUtil.jsonToArrayList(response, InfoForParentBean.class);
        ArrayList<ItemForParent> datas = new ArrayList<>();
        for (int i = 0; i < beans.size(); i++) {
            InfoForParentBean bean = beans.get(i);
            datas.add(new ItemForParent(bean.gettName(), bean.gettPhone()));
        }
        datasForParent.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    private void dealResponseForTeacher(String response) {
        ArrayList<InfoForTeacherBean> beans = GsonUtil.jsonToArrayList(response, InfoForTeacherBean.class);
        ArrayList<ItemForTeacher> datas = new ArrayList<>();
        for (int i = 0; i < beans.size(); i++) {
            InfoForTeacherBean bean = beans.get(i);
            String childName = bean.getsName();
            ArrayList<ItemForTeacherParent> parents = getParents(bean);
            datas.add(new ItemForTeacher(childName, parents));
        }
        datasForTeacher.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    /*
    *   由于后台给的数据奇葩，要自己解析
    * */
    private ArrayList<ItemForTeacherParent> getParents(InfoForTeacherBean bean) {
        String response = bean.getsAcount();
        String[] strings = response.split("\\.");
        ArrayList<ItemForTeacherParent> parents = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            String[] parent = strings[0].split(":");
            parents.add(new ItemForTeacherParent(parent[0], parent[1]));
        }
        return parents;
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


    /*
    *   老师界面的Adapter
    * */
    private class TeacherAdapter extends RecyclerView.Adapter<TeacherViewHolder> implements View.OnClickListener {
        private ArrayList<ItemForTeacher> datas;
        private LayoutInflater inflater;

        TeacherAdapter(Context context, ArrayList<ItemForTeacher> datas) {
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
            final ItemForTeacher bean = datas.get(position);
            ItemForTeacherParent parent = bean.getParents().get(0);
            holder.itemClassInfolistTeacherParentName.setText(parent.getParentName());
            holder.itemClassInfolistTeacherParentTel.setText(parent.getParentTel());
            holder.itemClassInfolistTeacherChildName.setText(bean.getChildName());
            holder.itemClassInfolistTeacherParentTel.setOnClickListener(this);
            holder.itemClassInfolistTeacherDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CLassTeacherAvtivity.this, ParentDetailInfoActivity.class);
                    intent.putExtra("Bean", bean);
                    startActivity(intent);
                }
            });
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

    /*
    *   老师界面的ViewHolder
    * */

    class TeacherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_class_infolist_teacher_child_name)
        TextView itemClassInfolistTeacherChildName;
        @BindView(R.id.item_class_infolist_teacher_parent_name)
        TextView itemClassInfolistTeacherParentName;
        @BindView(R.id.item_class_infolist_teacher_parent_tel)
        TextView itemClassInfolistTeacherParentTel;
        @BindView(R.id.item_class_infolist_teacher_details)
        TextView itemClassInfolistTeacherDetails;


        public TeacherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }




    /*
    *   家长界面的Adapter
    * */

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
            holder.itemClassInfolistParentTeacherName.setText(data.getTeacherName());
            holder.itemClassInfolistParentTeacherTel.setText(data.getTeacherTel());
            holder.itemClassInfolistParentTeacherTel.setOnClickListener(this);
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

    /*
    *   家长界面的VIewHolder
    * */

    class ParentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_class_infolist_parent_teacher_name)
        TextView itemClassInfolistParentTeacherName;
        @BindView(R.id.item_class_infolist_parent_teacher_tel)
        TextView itemClassInfolistParentTeacherTel;

        public ParentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
