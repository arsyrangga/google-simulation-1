package com.dicoding.todoapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.ui.list.TaskViewModel
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailTaskViewModel
    private lateinit var title: EditText
    private lateinit var desc: EditText
    private lateinit var dueDateMillis: EditText
    private lateinit var deleteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        val id = intent.getIntExtra(TASK_ID, 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailTaskViewModel::class.java]
        viewModel.setTaskId(id)

        title = findViewById(R.id.detail_ed_title)
        desc = findViewById(R.id.detail_ed_description)
        dueDateMillis = findViewById(R.id.detail_ed_due_date)
        deleteButton = findViewById(R.id.btn_delete_task)

        viewModel.task.observe(this, Observer {
            if(it != null){
                title.setText(it.title)
                desc.setText(it.description)
                dueDateMillis.setText(DateConverter.convertMillisToString(it.dueDateMillis))
            }
        })

        deleteButton.setOnClickListener{
            viewModel.deleteTask()
            val intent = Intent(this@DetailTaskActivity, TaskActivity::class.java)
            startActivity(intent)
        }
    }
}