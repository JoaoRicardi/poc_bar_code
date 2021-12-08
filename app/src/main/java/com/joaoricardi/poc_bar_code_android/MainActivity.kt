package com.joaoricardi.poc_bar_code_android

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.zxing.Result
import com.joaoricardi.poc_bar_code_android.databinding.ActivityMainBinding
import me.dm7.barcodescanner.zxing.ZXingScannerView

class MainActivity : AppCompatActivity(),  ZXingScannerView.ResultHandler {

    private lateinit var binding: ActivityMainBinding
    private lateinit var scannerView: ZXingScannerView
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        scannerView = binding.scannerViewId
        btn = binding.btnCallToAction


        binding.askForPermissionBtn.setOnClickListener {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                1)
        }

        btn.setOnClickListener {
            chamarOPluginDeFoto()
        }
    }

    private fun chamarOPluginDeFoto() {
        scannerView.visibility  = View.VISIBLE
        btn.visibility = View.GONE

        scannerView.startCamera()

    }


    override fun onResume() {
        scannerView.setResultHandler(this)
        super.onResume()
    }

    override fun handleResult(rawResult: Result?) {
        Log.i("LOG", "Conteúdo do código lido: ${rawResult!!.text}")
        Log.i("LOG", "Formato do código lido: ${rawResult.barcodeFormat.name}")

        scannerView.resumeCameraPreview( this )

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1){
            print("funfou")
        }

    }
}

//O método setResultHandler() requer uma instância de ZXingScannerView.ResultHandler como argumento. Além do mais, segundo a documentação, o lugar ideal de invocação deste método é no onResume() do fragmento ou atividade em uso.
//
//Se o método resumeCameraPreview() não for invocado posteriormente a leitura de algum código, a tela de leitura fica travada. resumeCameraPreview() também espera uma instância de ZXingScannerView.ResultHandler como argumento.