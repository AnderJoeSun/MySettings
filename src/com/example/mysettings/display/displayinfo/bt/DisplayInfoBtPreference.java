/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.zhengnian.mysettings.display.displayinfo.bt;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Set;

import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import me.zhengnian.mysettings.R;

public class DisplayInfoBtPreference extends DialogPreference {
	private Context context;
	private BluetoothAdapter btAdapt = BluetoothAdapter.getDefaultAdapter();

	public DisplayInfoBtPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		super.onPrepareDialogBuilder(builder);
		// View content = View.inflate(builder.getContext(),
		// R.layout.brightness_toggle_slider, null);
		// TextView value_tv = (TextView)comtent.findViewById(R.id.value_tv);
//
//		Context context = builder.getContext();
//		View content = View.inflate(context,
//				R.layout.display_info_bt_dialog, null);
//		
//		// server
//		((Button)content.findViewById(R.id.getSizeAndSendViaBt)).setOnClickListener(new View.OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				//MainActivity.displayWidthPixel MainActivity.displayHeightPixel
//				if(btAdapt==null ){
//					Toast.makeText( DisplayInfoBtPreference.this.context, "There is no bluetooth device with you phone.", Toast.LENGTH_LONG);
//				} else if (!btAdapt.isEnabled()) { 
//				    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); 
//				    DisplayInfoBtPreference.this.context.startActivityForResult(enableBtIntent, ); 
//				} else {
//					new SppServer().start();
//				}
//				
//			}
//			
//		});
//		
//		
//		// client
//		((Button)content.findViewById(R.id.receiveSizeAndSet)).setOnClickListener(new View.OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				//MainActivity.displayWidthPixel MainActivity.displayHeightPixel
//				
//				if (btAdapt!=null && !btAdapt.isEnabled()) { 
//				    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); 
//				    DisplayInfoBtPreference.this.context.startActivityForResult(enableBtIntent, ); 
//				    
//				   
//				} else {
//					
//				}
//				
//			}
//			
//		});
//		
//		
//		Intent discoverableIntent = new 
//				Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE); 
//				discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300); 
//				DisplayInfoBtPreference.this.context.startActivity(discoverableIntent);
//		
////		Log.e("ms","metric: w: "+ metric.widthPixels + metric.heightPixels);
//		builder.setView(content);
//		
//		// ACTION_SCAN_MODE_CHANGED Intent注册一个BroadcastReceiver，包含额外的字段信息EXTRA_SCAN_MODE和EXTRA_PREVIOUS_SCAN_MODE分别表示新旧扫描模式，其可能的值为SCAN_MODE_CONNECTABLE_DISCOVERABLE（discoverable mode），SCAN_MODE_CONNECTABLE（not in discoverable mode but still able to receive connections），SCAN_MODE_NONE（not in discoverable mode and unable to receive connections）。
//		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND); 
//		context.registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
	}

	@Override
	protected void showDialog(Bundle state) {
		super.showDialog(state);
	}

	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
	
//	private final BroadcastReceiver mReceiver = new BroadcastReceiver() { 
//	    public void onReceive(Context context, Intent intent) { 
//	        String action = intent.getAction(); 
//	        // When discovery finds a device 
//	        if (BluetoothDevice.ACTION_FOUND.equals(action)) { 
//	            // Get the BluetoothDevice object from the Intent 
//	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//	            // Add the name and address to an array adapter to show in a ListView
//	            Log.e("fj-dbt",device.getName() + ", " + device.getAddress()); 
//	            if(){
//	            	new SppServer().start();
//	            }
//	        } 
//	    } 
//	}; 
//	
//	private class SppServer extends Thread {
//		private BluetoothServerSocket btServerSocket;
//		private BluetoothSocket btSocket;
//		
//		private BluetoothAdapter myBtAdapt;
//		
//		static final String SPP_UUID="00001101-0000-1000-8000-00805F9B34FB";
//		static final int REQUEST_ENABLE_BT="00001101-0000-1000-8000-00805F9B34FB";
//		
//		
//		public SppServer(BluetoothAdapter btAdapt) {
//			myBtAdapt = btAdapt;
//			try {
//				btServerSocket = btAdapt.listenUsingRfcommWithServiceRecord("SPP",SPP_UUID);// SPP_UUID="00001101-0000-1000-8000-00805F9B34FB"
//				if (!btAdapt.isEnabled()) { 
//				    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); 
//				    DisplayInfoBtPreference.this.context.startActivityForResult(enableBtIntent, ); 
//				} else {
//					Set<BluetoothDevice> pairedDevices = btAdapt.getBondedDevices(); 
//					// If there are paired devices 
//					if (pairedDevices.size() > 0) { 
//					    // Loop through paired devices 
//					    for (BluetoothDevice device : pairedDevices) { 
//					        // Add the name and address to an array adapter to show in a ListView 
//					        Log.e("fj-dbt",device.getName() + ", " + device.getAddress()); 
//					    } 
//					}
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		public void run() {
//	                btAdapt.cancelDiscovery();  //退出搜索
//	                try {
//	                	btSocket = btServerSocket.accept();  //BluetoothSocket bs = null; 此方法为阻塞方法，有连接进来后返回。
//	                        synchronized (SppServer.this) {
//	                                if (sppConnected) return;        //如果已经连接，则返回。
//	                                btIn = bs.getInputStream();          //InputStream btIn;
//	                                btOut = bs.getOutputStream();          //OutputStream btOut;
////	                                ....  //此时SPP已经连接，可以通过btout/btin收发消息了。        
//	                                }
//	                        } catch (IOException e) {
//	                                // TODO Auto-generated catch block
//	                                e.printStackTrace();
//	                        }
//	        }
//
//		public void cancel() {
//			// 退出时的操作
//		}
//
//		public void sppConnect(){
//	            if (btAdapt.isDiscovering()) {
//	                    btAdapt.cancelDiscovery();
//	            }; 
//	            try {
//	                btSocket = btAdapt.getRemoteDevice(sCurdev)
//	                                        .createRfcommSocketToServiceRecord(spp_uuid);
//	                btSocket.connect();
//	                synchronized (Spp.this) {
//	                        if(btServerSocket!=null) btServerSocket.close();  //关闭之前可能保持的socket
//	                        btIn = btSocket.getInputStream();
//	                        btOut = btSocket.getOutputStream();
//	                        .......//需另起一个线程接收消息，同样因为阻塞。
//	                        }
//	                } catch (IOException e) {
//	                // TODO Auto-generated catch block
//	                        e.printStackTrace();
//	                } catch (IOException e1) {
//	                                // TODO Auto-generated catch block
//	                                e1.printStackTrace();
//	                        }
//	                }
//	}
//	
//	
//	private class AcceptThread extends Thread { 
//	    private final BluetoothServerSocket mmServerSocket; 
//	  
//	    public AcceptThread() { 
//	        // Use a temporary object that is later assigned to mmServerSocket, 
//	        // because mmServerSocket is final 
//	        BluetoothServerSocket tmp = null; 
//	        try { 
//	            // MY_UUID is the app's UUID string, also used by the client code 
//	            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
//	        } catch (IOException e) { } 
//	        mmServerSocket = tmp; 
//	    } 
//	  
//	    public void run() { 
//	        BluetoothSocket socket = null; 
//	        // Keep listening until exception occurs or a socket is returned 
//	        while (true) { 
//	            try { 
//	                socket = mmServerSocket.accept(); 
//	            } catch (IOException e) { 
//	                break; 
//	            } 
//	            // If a connection was accepted 
//	            if (socket != null) { 
//	                // Do work to manage the connection (in a separate thread) 
//	                manageConnectedSocket(socket); 
//	                mmServerSocket.close(); 
//	                break; 
//	            } 
//	        } 
//	    } 
//	  
//	    /** Will cancel the listening socket, and cause the thread to finish */ 
//	    public void cancel() { 
//	        try { 
//	            mmServerSocket.close(); 
//	        } catch (IOException e) { } 
//	    } 
//	}
//	
//	private class ConnectThread extends Thread {
//	    private final BluetoothSocket mmSocket;
//	    private final BluetoothDevice mmDevice;
//
//	    public ConnectThread(BluetoothDevice device) {
//	        // Use a temporary object that is later assigned to mmSocket,
//	        // because mmSocket is final
//	        BluetoothSocket tmp = null;
//	        mmDevice = device;
//
//	        // Get a BluetoothSocket to connect with the given BluetoothDevice
//	        try {
//	            // MY_UUID is the app's UUID string, also used by the server code
//	            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
//	        } catch (IOException e) { }
//	        mmSocket = tmp;
//	    }
//
//	    public void run() {
//	        // Cancel discovery because it will slow down the connection
//	        mAdapter.cancelDiscovery();
//
//	        try {
//	            // Connect the device through the socket. This will block
//	            // until it succeeds or throws an exception
//	            mmSocket.connect();
//	        } catch (IOException connectException) {
//	            // Unable to connect; close the socket and get out
//	            try {
//	                mmSocket.close();
//	            } catch (IOException closeException) { }
//	            return;
//	        }
//
//	        // Do work to manage the connection (in a separate thread)
//	        manageConnectedSocket(mmSocket);
//	    }
//
//	    /** Will cancel an in-progress connection, and close the socket */
//	    public void cancel() {
//	        try {
//	            mmSocket.close();
//	        } catch (IOException e) { }
//	    }
//	}
	
}




class ZeeTest{

    private boolean connected = false;
    private BluetoothSocket sock;
    private InputStream in;
    public void test() throws Exception {
        if (connected) {
            return;
        }
        BluetoothDevice zee = BluetoothAdapter.getDefaultAdapter().
            getRemoteDevice("00:1C:4D:02:A6:55");
        Method m = zee.getClass().getMethod("createRfcommSocket",
            new Class[] { int.class });
        sock = (BluetoothSocket)m.invoke(zee, Integer.valueOf(1));
        Log.d("ZeeTest", "++++ Connecting");
        sock.connect();
        Log.d("ZeeTest", "++++ Connected");
        in = sock.getInputStream();
        byte[] buffer = new byte[50];
        int read = 0;
        Log.d("ZeeTest", "++++ Listening...");
        try {
            while (true) {
                read = in.read(buffer);
                connected = true;
                StringBuilder buf = new StringBuilder();
                for (int i = 0; i < read; i++) {
                    int b = buffer[i] & 0xff;
                    if (b < 0x10) {
                        buf.append("0");
                    }
                    buf.append(Integer.toHexString(b)).append(" ");
                }
                Log.d("ZeeTest", "++++ Read "+ read +" bytes: "+ buf.toString());
            }
        } catch (IOException e) {}
        Log.d("ZeeTest", "++++ Done: test()");
        
        
        try {
            if (in != null) {
                in.close();
            }
            if (sock != null) {
                sock.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}