package notepad.itcast.cn.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import notepad.itcast.cn.notepad.adapter.NotepadAdpter;
import notepad.itcast.cn.notepad.bean.NotepadBean;
import notepad.itcast.cn.notepad.database.SQLiteHelper;



public class NotepadActivity extends AppCompatActivity {
    ListView ListView;
    List<NotepadBean> list;
    SQLiteHelper mSQLiteHelper;
    NotepadAdpter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        ListView = (ListView) findViewById(R.id.lv);
        ImageView add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotepadActivity.this,RecordActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        initData();
    }

    protected void initData() {
        mSQLiteHelper = new SQLiteHelper(this);
        showQueryData();
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NotepadBean notepadBean=list.get(position);
                Intent intent = new Intent(NotepadActivity.this,RecordActivity.class);
                intent.putExtra("id",notepadBean.getId());
                intent.putExtra("time",notepadBean.getNotepadtime());
                intent.putExtra("content",notepadBean.getNotepadContent());
                NotepadActivity.this.startActivityForResult(intent,1);
            }
        });
        ListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(NotepadActivity.this)
                        .setMessage("是否删除此记录?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NotepadBean notepadBean =list.get(position);
                                if (mSQLiteHelper.deleteData(notepadBean.getId())){
                                    list.remove((position));
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(NotepadActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialog = builder.create();
                dialog.show();
                return true;
            }
            });
    }
    private void showQueryData() {
        if (list!=null){
            list.clear();
        }
        list = mSQLiteHelper.query();
        adapter =new NotepadAdpter(this, list);
        ListView.setAdapter(adapter);
    }
    @Override
    protected void onActivityResult(int req,int rc,Intent data){
        super.onActivityResult(req,rc,data);
        if (req==1 && rc==2){
            showQueryData();
        }
    }
}
