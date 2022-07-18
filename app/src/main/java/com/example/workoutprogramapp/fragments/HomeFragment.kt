package com.example.workoutprogramapp.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.workoutprogramapp.AlertReceiver
import com.example.workoutprogramapp.DescriptionActivity
import com.example.workoutprogramapp.Item
import com.example.workoutprogramapp.R
import com.example.workoutprogramapp.adapter.ItemAdapter
import com.google.firebase.database.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(){

    private lateinit var tvAlarm: TextView
    private lateinit var cancelAlarm: MediaPlayer
    private lateinit var listItem: ListView
    private lateinit var ref: DatabaseReference
    private lateinit var itemList: MutableList<Item>


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSet = view.findViewById<Button>(R.id.btn_set)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)
        tvAlarm = view.findViewById(R.id.alarm)

        btnSet.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                cal.set(Calendar.SECOND, 0)
                tvAlarm.text = SimpleDateFormat("HH:mm").format(cal.time)

            }
            TimePickerDialog(activity, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

            startAlarm(cal.timeInMillis)

        }

        btnCancel.setOnClickListener{
            cancelAlarm()
        }


        val calendar = Calendar.getInstance()
        val currentDate = DateFormat.getDateInstance().format(calendar.time)
        val currentTime = DateFormat.getTimeInstance().format(calendar.time)

        val tvDate = view.findViewById<TextView>(R.id.tv_day)
        val tvTime = view.findViewById<TextView>(R.id.tv_time)
        tvDate.setText(currentDate)
        tvTime.setText(currentTime)

        ref = FirebaseDatabase.getInstance().getReference("item")

        listItem = view.findViewById(R.id.lv_desc)

        itemList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    itemList.clear()
                    for (h in p0.children){
                        val item = h.getValue(Item::class.java)
                        if (item != null) {
                            itemList.add(item)
                        }
                    }

                    val adapter = activity?.let { ItemAdapter(it, R.layout.list_item, itemList) }
                    listItem.adapter = adapter
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        listItem.setOnItemClickListener{ parent, view, position, id ->
            val item = itemList.get(position)

            val intent = Intent(activity, DescriptionActivity::class.java)
            intent.putExtra(DescriptionActivity.EXTRA_ID, item.id)
            intent.putExtra(DescriptionActivity.EXTRA_NAME, item.name)
            intent.putExtra(DescriptionActivity.EXTRA_TIME, item.time)
            intent.putExtra(DescriptionActivity.EXTRA_IMAGE, item.imageUrl)
            startActivity(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun cancelAlarm() {
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(activity, AlertReceiver::class.java)
        val pi = PendingIntent.getBroadcast(activity, 1, intent, 0)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.cancel(pi)
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.cancel(pi)
            }
        }
        cancelAlarm = MediaPlayer.create(activity, R.raw.slow_morning)
        cancelAlarm.stop()

        tvAlarm.setText("alarm cancel")
    }


    private fun startAlarm(c: Long) {
        /*val requestCode = Random().nextInt()
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(activity, AlertReceiver::class.java)
        intent.putExtra("REQUEST_CODE", requestCode)
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
        val pi = PendingIntent.getBroadcast(activity, requestCode, intent, 0)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pi)
        }
         */

        /*
        var i = Intent(activity, AlertReceiver::class.java)
        var pi = PendingIntent.getBroadcast(activity, 111, i, 0)
        var am : AlarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi)

         */

        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(activity, AlertReceiver::class.java)
        val pi = PendingIntent.getBroadcast(activity, 1, intent, 0)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c, pi)
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, c, pi)
            }
        }
    }


}