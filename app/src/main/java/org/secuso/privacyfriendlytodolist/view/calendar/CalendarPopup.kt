package org.secuso.privacyfriendlytodolist.view.calendar

import android.os.Bundle
import android.view.MenuItem
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import org.secuso.privacyfriendlytodolist.R
import org.secuso.privacyfriendlytodolist.model.ModelServices
import org.secuso.privacyfriendlytodolist.model.TodoTask
import org.secuso.privacyfriendlytodolist.view.ExpandableTodoTaskAdapter
import org.secuso.privacyfriendlytodolist.viewmodel.LifecycleViewModel

/**
 * Created by sebbi on 07.03.2018.
 *
 * This class helps to show the tasks that are on a specific deadline
 */
class CalendarPopup : AppCompatActivity() {
    private lateinit var lv: ExpandableListView
    private var expandableTodoTaskAdapter: ExpandableTodoTaskAdapter? = null
    private var tasks: ArrayList<TodoTask>? = ArrayList()
    private var model: ModelServices? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[LifecycleViewModel::class.java]
        model = viewModel.model
        setContentView(R.layout.calendar_popup)
        lv = findViewById(R.id.deadline_tasks)
        val toolbar: Toolbar = findViewById(R.id.toolbar_deadlineTasks)
        toolbar.setTitle(R.string.deadline)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(toolbar)
        val supportActionBarCopy = supportActionBar
        if (supportActionBarCopy != null) {
            supportActionBarCopy.setHomeAsUpIndicator(R.drawable.arrow)
            supportActionBarCopy.setDisplayHomeAsUpEnabled(true)
            supportActionBarCopy.setDisplayShowHomeEnabled(true)
        }
        val bundle = intent.extras
        if (bundle != null) {
            tasks = bundle.getParcelableArrayList(CalendarActivity.PARCELABLE_KEY_FOR_DEADLINES)
        }
        updateAdapter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateAdapter() {
        expandableTodoTaskAdapter = ExpandableTodoTaskAdapter(this, model!!, tasks!!)
        lv.setAdapter(expandableTodoTaskAdapter)
    }
}
