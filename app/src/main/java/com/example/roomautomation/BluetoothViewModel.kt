package com.example.roomautomation

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.io.IOException
import java.io.OutputStream
import java.util.UUID

internal const val TAG = "Room_Control"
internal const val CONNECT = "Connect"
private const val NO_BLUETOOTH_ADAPTER = "Get a damn bluetooth phone you rat bastard!"
private const val CONNECTION_FAILED = "Connection failed"
private const val BLUETOOTH_IMAGE = R.drawable.bluetooth
private const val SKYLIGHT_DIRECTION = R.drawable.arrow
private const val CONNECTING = "Connecting"
private const val CONNECTED = "Connected"
private const val ADDRESS = "DC:A6:32:09:7B:50"
internal const val ENABLE_BLUETOOTH = "Enable Bluetooth"
private const val TOAST_ERROR_TEXT = "Error"
private const val CLOSE_SUCCESS = "Connection closed"
private const val CLOSE_ERROR = "Could not disconnect"
internal const val OPEN_SKYLIGHT = "Open skylight"
internal const val CLOSE_SKYLIGHT = "Close skylight"

class BluetoothViewModel: ViewModel() {

    private var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private lateinit var target: BluetoothDevice
    private lateinit var outputStream: OutputStream
    private val uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
    private lateinit var socket: BluetoothSocket
    private var actuatorState = false

    //LiveData to
    private val liveRunApp = MutableLiveData<Boolean>()
    val runApp: LiveData<Boolean>
        get() = liveRunApp

    //LiveData to hold main button text
    private val liveButtonText = MutableLiveData(ENABLE_BLUETOOTH)
    val buttonText: LiveData<String>
        get() = liveButtonText

    //LiveData to hold resource of ImageView in the middle of screen
    private val liveImageGraphic = MutableLiveData(BLUETOOTH_IMAGE)
    val imageGraphic: LiveData<Int>
        get() = liveImageGraphic

    //LiveData to hold string of Toast notifications
    private val liveToastText = MutableLiveData<String>()
    val toastText: LiveData<String>
        get() = liveToastText

    //LiveData to hold Bluetooth connection status
    private val liveIsConnected = MutableLiveData(false)
    val isConnected: LiveData<Boolean>
        get() = liveIsConnected

    init {
        findTargetDevice()
        initButton()
    }

    private fun findTargetDevice() {
        for(device in bluetoothAdapter.bondedDevices) {
            if(device.address == ADDRESS) {
                target = device
                break
            }
        }
    }

    private fun initButton() {
        if(bluetoothAdapter.isEnabled) liveButtonText.value = CONNECT
        else liveButtonText.value = ENABLE_BLUETOOTH
    }

    internal fun connect() {
        liveToastText.value = CONNECTING
        CoroutineScope(Dispatchers.IO).launch {
            try {
                bluetoothAdapter.cancelDiscovery()
                socket = target.createInsecureRfcommSocketToServiceRecord(uuid)
                socket.connect()
                outputStream = socket.outputStream
                withContext(Dispatchers.Main) {
                    liveIsConnected.value = true
                    liveButtonText.value = OPEN_SKYLIGHT
                    liveToastText.value = CONNECTED
                    liveImageGraphic.value = SKYLIGHT_DIRECTION
                }
            } catch(e: Exception) {
                withContext(Dispatchers.Main) {
                    liveToastText.value = CONNECTION_FAILED
                }
                try {
                    socket.close()
                } catch(e1: Exception) {
                    e1.printStackTrace()
                    this.cancel()
                }
                e.printStackTrace()
                this.cancel()
            }
        }
    }

    internal fun enableBT() {
        if(bluetoothAdapter == null) {
            liveToastText.value = NO_BLUETOOTH_ADAPTER
            liveRunApp.value = true
        } else {
            bluetoothAdapter.enable()
            liveButtonText.value = CONNECT
        }
    }

    internal fun toggleLED() {
        if (actuatorState) {
            liveButtonText.value = OPEN_SKYLIGHT
            actuatorState = false
        } else {
            liveButtonText.value = CLOSE_SKYLIGHT
            actuatorState = true
        }
        CoroutineScope(Dispatchers.IO).launch {
            write("I have been sent.\n".toByteArray())
        }
    }

    private fun write(bytes: ByteArray) {
        try {
            outputStream.write(bytes)
        } catch (e: IOException) {
            Log.e(TAG, "Error occurred when sending data", e)
            liveToastText.value = TOAST_ERROR_TEXT
        }
    }

    internal fun closeConnection() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                outputStream.close()
                socket.close()
                withContext(Dispatchers.Main) {
                    liveIsConnected.value = false
                    liveButtonText.value = CONNECT
                    liveToastText.value = CLOSE_SUCCESS
                    liveImageGraphic.value = BLUETOOTH_IMAGE
                    actuatorState = false
                }
            } catch (e: IOException) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    liveToastText.value = CLOSE_ERROR
                }
                Log.e(TAG, "Could not close connection", e)
            }
        }
    }
}