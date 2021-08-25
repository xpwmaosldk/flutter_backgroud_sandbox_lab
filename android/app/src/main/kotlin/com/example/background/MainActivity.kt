package com.example.background

import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Notifications.createNotificationChannels(this)
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        val binaryMessenger = flutterEngine.dartExecutor.binaryMessenger

        MethodChannel(binaryMessenger, "com.example/background_service").apply {
            setMethodCallHandler { method, result ->
                when (method.method) {
                    "startService" -> {
                        val callbackRawHandle = method.arguments as Long
                        BackgroundService.startService(this@MainActivity, callbackRawHandle)
                        result.success(null)
                    }
                    "stopService" -> {
                        BackgroundService.stopService(this@MainActivity)
                        result.success(null)
                    }
                    else -> {
                        result.notImplemented()
                    }
                }
            }
        }
        
        MethodChannel(binaryMessenger, "com.example/test").apply {
            setMethodCallHandler { method, result ->
                if (method.method == "test")
                    result.success("testttttt mainAct")
            }
        }

        MethodChannel(binaryMessenger, "com.example/app_retain").apply {
            setMethodCallHandler { method, result ->
                if (method.method == "sendToBackground") {
                    moveTaskToBack(true)
                    result.success(null)
                } else {
                    result.notImplemented()
                }
            }
        }
    }
}
