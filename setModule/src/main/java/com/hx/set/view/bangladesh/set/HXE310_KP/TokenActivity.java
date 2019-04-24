package com.hx.set.view.bangladesh.set.HXE310_KP;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.set.R;
import com.hx.set.contact.bangladesh.HXE310_KP.TokenContact;
import com.hx.set.presenter.bangladesh.HXE310_KP.TokenPresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hexing.model.TranXADRAssist;

public class TokenActivity extends RxMvpBaseActivity<TokenContact.Presenter> implements TokenContact.View {
    private EditText tvTime;
    private TextView tvWrite;
    private HeaderLayout headerLayout;

    int deleteSelect;
    String lastString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        tvWrite = findViewById(R.id.tvWrite);
        tvWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.writeToken(tvTime.getText().toString());
            }
        });
        tvTime = findViewById(R.id.tvTime);

        tvTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //因为重新排序之后setText的存在
                //会导致输入框的内容从0开始输入，这里是为了避免这种情况产生一系列问题
                if (start == 0 && count > 0) {
                    return;
                }
                String editTextContent = tvTime.getText().toString();
                if (TextUtils.isEmpty(editTextContent) || TextUtils.isEmpty(lastString)) {
                    return;
                }
                editTextContent = addSpeaceByCredit(editTextContent);
                //如果最新的长度 < 上次的长度，代表进行了删除
                if (editTextContent.length() <= lastString.length()) {
                    deleteSelect = start;
                } else {
                    deleteSelect = editTextContent.length();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                //获取输入框中的内容,不可以去空格
                String etContent = tvTime.getText().toString();
                if (TextUtils.isEmpty(etContent)) {
                    tvWrite.setEnabled(false);
                    return;
                }
                //重新拼接字符串
                String newContent = addSpeaceByCredit(etContent);
                //保存本次字符串数据
                lastString = newContent;
                //如果有改变，则重新填充
                //防止EditText无限setText()产生死循环
                if (!etContent.equals(newContent)) {
                    tvTime.setText(newContent);
                    //保证光标的位置
                    tvTime.setSelection(deleteSelect > newContent.length() ? newContent.length() : deleteSelect);
                }
                if ((newContent.replaceAll("-", "")).length() == 20 && isNumeric((newContent.replaceAll("-", "")))) {
                    tvWrite.setEnabled(true);
                    return;
                }
                tvWrite.setEnabled(false);

            }
        });
        headerLayout.showTitle(getString(R.string.set_function_token));
        headerLayout.showLeftBackButton();
    }

    @Override
    public TokenContact.Presenter createPresenter() {
        return new TokenPresenter(this);
    }

    @Override
    public void showData(TranXADRAssist item) {

    }

    public static String addSpeaceByCredit(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        //去空格
        content = content.replaceAll("-", "");
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        //卡号限制为16位
        if (content.length() > 20) {
            content = content.substring(0, 20);
        }
        StringBuilder newString = new StringBuilder();
        for (int i = 1; i <= content.length(); i++) {
            //当为第4位时，并且不是最后一个第4位时
            //拼接字符的同时，拼接一个空格
            //如果在最后一个第四位也拼接，会产生空格无法删除的问题
            //因为一删除，马上触发输入框改变监听，又重新生成了空格
            if (i % 4 == 0 && i != content.length()) {
                newString.append(content.charAt(i - 1) + "-");
            } else {
                //如果不是4位的倍数，则直接拼接字符即可
                newString.append(content.charAt(i - 1));

            }
        }
        return newString.toString();
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
