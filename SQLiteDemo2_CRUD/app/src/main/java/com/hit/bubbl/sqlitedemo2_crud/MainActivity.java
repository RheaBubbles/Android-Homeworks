package com.hit.bubbl.sqlitedemo2_crud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etAddName, etAddAge;
    private EditText etDeleteName;
    private EditText etUpdateName, etUpdateAge;
    private PersonDao personDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: 添加增删改查的监听调用功能接口
        etAddName = findViewById(R.id.add_name);
        etAddAge = findViewById(R.id.add_age);
        etDeleteName = findViewById(R.id.delete_name);
        etUpdateName = findViewById(R.id.update_name);
        etUpdateAge = findViewById(R.id.update_age);

        personDao = new PersonDao(this);
    }

    public void addPerson(View view) {
        String name = etAddName.getText().toString();
        int age = Integer.parseInt(etAddAge.getText().toString());
        if(TextUtils.isEmpty(name) || age == 0) {
            Toast.makeText(this, "请输入完整", Toast.LENGTH_SHORT).show();
        } else {
            personDao.add(name, age);
            Toast.makeText(this, "调用PersonDao.add", Toast.LENGTH_SHORT).show();
        }
    }

    public void deletePerson(View view) {
        String name = etDeleteName.getText().toString();
        if(TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入完整", Toast.LENGTH_SHORT).show();
        } else {
            personDao.delete(name);
            Toast.makeText(this, "调用PersonDao.delete", Toast.LENGTH_SHORT).show();
        }
    }

    public void updatePerson(View view) {
        String name = etUpdateName.getText().toString();
        int age = Integer.parseInt(etUpdateAge.getText().toString());
        if(TextUtils.isEmpty(name) || age == 0) {
            Toast.makeText(this, "请输入完整", Toast.LENGTH_SHORT).show();
        } else {
            personDao.update(name, age);
            Toast.makeText(this, "调用PersonDao.update", Toast.LENGTH_SHORT).show();
        }
    }

    public void findAllPerson(View view) {
        List<Person> persons = personDao.findAll();
        Toast.makeText(this, "调用PersonDao.findAll", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "一共有"+persons.size()+"个人的信息", Toast.LENGTH_SHORT).show();
    }
}
