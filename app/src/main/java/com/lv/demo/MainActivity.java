package com.lv.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.lv.demo.dialog.ActionSheetDialog;
import com.lv.demo.dialog.MaterialDialog;
import com.lv.demo.dialog.listener.OnBtnClickL;
import com.lv.demo.dialog.listener.OnOperItemClickL;


public class MainActivity extends AppCompatActivity {
    private ListView lv_one;
    private ListView lv_two;
    private int count = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_one = (ListView) this.findViewById(R.id.lv_one);
        lv_two = (ListView) this.findViewById(R.id.lv_two);
        String[] strs1 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_expandable_list_item_1, strs1);
        lv_one.setAdapter(adapter1);
        String[] strs2 = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, strs2);
        lv_two.setAdapter(adapter2);
        lv_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (count == 2)
                    actionSheetDialog();
                else if (count == 3)
                    actionSheetDialogNoTitle();
                else if (count == 4) {
                    materialDialogDefault();
                } else if (count == 5) {
                    materialDialogOneBtn();
                } else if (count == 6) {
                    materialDialogThreeBtns();
                }
                count++;
                if (count > 6)
                    count = 2;
            }
        });
    }

    private void actionSheetDialog() {
        final String[] stringItems = {"接收消息并提醒", "接收消息但不提醒", "收进群助手且不提醒", "屏蔽群消息"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
        dialog.title("选择群消息提醒方式\r\n(该群在电脑的设置:接收消息并提醒)")//
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, stringItems[position], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void actionSheetDialogNoTitle() {
        final String[] stringItems = {"版本更新", "帮助与反馈", "退出QQ"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, lv_one);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, stringItems[position], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }


    private void materialDialogDefault() {
        final MaterialDialog dialog = new MaterialDialog(this);
        dialog.content(
                "嗨！这是一个 MaterialDialogDefault. 它非常方便使用，你只需将它实例化，这个美观的对话框便会自动地显示出来。"
                        + "它简洁小巧，完全遵照 Google 2014 年发布的 Material Design 风格，希望你能喜欢它！^ ^")//
                .btnText("取消", "确定")//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {//left btn click listener
                    @Override
                    public void onBtnClick() {
                        Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {//right btn click listener
                    @Override
                    public void onBtnClick() {
                        Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
        );
    }


    private void materialDialogThreeBtns() {
        final MaterialDialog dialog = new MaterialDialog(this);
        dialog.isTitleShow(false)//
                .btnNum(3)
                .content("为保证咖啡豆的新鲜度和咖啡的香味，并配以特有的传统烘焙和手工冲。")//
                .btnText("确定", "取消", "知道了")//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {//left btn click listener
                    @Override
                    public void onBtnClick() {
                        Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {//right btn click listener
                    @Override
                    public void onBtnClick() {
                        Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
                ,
                new OnBtnClickL() {//middle btn click listener
                    @Override
                    public void onBtnClick() {
                        Toast.makeText(MainActivity.this, "middle", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
        );
    }

    private void materialDialogOneBtn() {
        final MaterialDialog dialog = new MaterialDialog(this);
        dialog//
                .btnNum(1)
                .content("为保证咖啡豆的新鲜度和咖啡的香味，并配以特有的传统烘焙和手工冲。")//
                .btnText("确定")//
                .show();

        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                Toast.makeText(MainActivity.this, "middle", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }


    /*    findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              *//*  ObjectAnimator.ofFloat(v,"translationX",200,300,400)
                        .setDuration(300)
                        .start();*//*
              *//*  ObjectAnimator animator1 = ObjectAnimator.ofFloat(v, "translationX", 0.0f, 200.0f, 0f);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(v, "scaleX", 1.0f, 2.0f,1.0f);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(v, "rotationX", 0.0f, 90.0f, 0.0F);
                AnimatorSet set=new AnimatorSet();
                set.setDuration(2000);
                set.play(animator1).with(animator2).after(animator3);
                set.start();*//*
                PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.5f,1.0f);
                PropertyValuesHolder valuesHolder2 = PropertyValuesHolder.ofFloat("rotationX", 0.0f, 180.0f, 0.0F);
                PropertyValuesHolder valuesHolder3 = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.3f, 1.0F);
                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(v,  valuesHolder1, valuesHolder2, valuesHolder3);
                objectAnimator.setDuration(2000).start();
            }
        });*/
}
