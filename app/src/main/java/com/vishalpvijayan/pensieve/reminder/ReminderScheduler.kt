package com.vishalpvijayan.pensieve.reminder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class ReminderScheduler(
    private val context: Context,
    private val workManager: WorkManager,
    private val alarmManager: AlarmManager
) {
    @SuppressLint("ScheduleExactAlarm")
    fun scheduleReminder() {
        val work = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(1, TimeUnit.HOURS)
            .build()
        workManager.enqueueUniqueWork("pensieve_reminder", ExistingWorkPolicy.REPLACE, work)

        val canUseExactAlarm = Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()
        if (!canUseExactAlarm) return

        val intent = Intent(context, ReminderWorkerReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1001,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + TimeUnit.HOURS.toMillis(12),
            pendingIntent
        )
    }
}
