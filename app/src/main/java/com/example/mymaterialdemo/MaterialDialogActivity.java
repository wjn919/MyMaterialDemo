package com.example.mymaterialdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.utils.AlertUtils;

/**
 * Created by wjn on 2017/2/9.
 */
public class MaterialDialogActivity extends AppCompatActivity {
    ListView mListView;
    private Toolbar toolbar;
    private MaterialDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_dialog);
        initView();
        init();

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (ListView) findViewById(R.id.rv_material_dialog);

    }

    private void init() {
        toolbar.setTitle("常用MaterialDialog演示");
        setSupportActionBar(toolbar);
        //决定左上角图标的右侧是否有向左的小箭头,true 有
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //左侧小箭头的返回事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //简单设置测试字符数组
        String[] data = {"BasicDialog", "checkBoxBasicDialog", "listDialog", "singleChoiceDialog", "mulityChoiceDialog", "inputDialog", "progressDialog"};
        ArrayAdapter<String> array = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, data);
        mListView.setAdapter(array);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        showBasicDialog();
                        break;
                    case 1:
                        showCheckBoxDialog();
                        break;
                    case 2:
                        showListDialog();
                        break;
                    case 3:
                        showSingleChoiceDialog();
                        break;
                    case 4:
                        showMulityChoiceDialog();
                        break;
                    case 5:
                        showInputDialog();
                        break;
                    case 6:
                        showProgressDialog();
                        break;
                }
            }


        });


    }

    private void showProgressDialog() {

        //1.圆形progressDialog
        /*  new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .progress(true, 0)
                .show();
        */
        //2.横向progressDialog
        /*new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();*/
        boolean showMinMax = true;
        dialog = new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .progress(false, 150, showMinMax)
                .show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Loop until the dialog's progress value reaches the max (150)
                while (dialog.getCurrentProgress() != dialog.getMaxProgress()) {
                    // If the progress dialog is cancelled (the user closes it before it's done), break the loop
                    if (dialog.isCancelled()) break;
                    // Wait 50 milliseconds to simulate doing work that requires progress
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        break;
                    }
                    // Increment the dialog's progress by 1 after sleeping for 50ms
                    dialog.incrementProgress(1);
                }
                handler.sendEmptyMessage(0);
            }
        }).start();


// When the loop exits, set the dialog content to a string that equals "Done"



    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what ==0){
                dialog.setContent(getString(R.string.done));
            }
        }
    };
    private void showInputDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.input)
                .content(R.string.input_content)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                    }
                }).show();
    }

    private void showMulityChoiceDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.mulity_title)
                .items(R.array.item2)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        /**
                         * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected check box to actually be selected
                         * (or the newly unselected check box to be unchecked).
                         * See the limited multi choice dialog example in the sample project for details.
                         **/
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void showSingleChoiceDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.single_choice_title)
                .items(R.array.items)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         *
                         **/
                        if(!TextUtils.isEmpty(text)){
                            AlertUtils.showToast(MaterialDialogActivity.this, text.toString() + which);

                        }
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void showListDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.list_title)
                .items(R.array.items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if(!TextUtils.isEmpty(text)){
                            AlertUtils.showToast(MaterialDialogActivity.this, text.toString() + which);
                        }

                    }
                })
                .show();
    }

    private void showCheckBoxDialog() {
        new MaterialDialog.Builder(this)
                .iconRes(R.mipmap.icon)
                .limitIconToDefaultSize()
                .title(R.string.example_title)
                .positiveText(R.string.allow)
                .negativeText(R.string.deny)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        AlertUtils.showToast(MaterialDialogActivity.this, "Prompt checked? " + dialog.isPromptCheckBoxChecked());
                    }
                })
                .checkBoxPromptRes(R.string.dont_ask_again, false, null)
                .show();
    }

    private void showBasicDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.title)
                .content(R.string.content)
                .positiveText(R.string.agree)
                .negativeText(R.string.cancel)
                .icon(getResources().getDrawable(R.mipmap.icon))
                .limitIconToDefaultSize()//限制图标大小
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        AlertUtils.showToast(MaterialDialogActivity.this, "agree");
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        AlertUtils.showToast(MaterialDialogActivity.this, "cancel");
                    }
                }).show();


    }


}
