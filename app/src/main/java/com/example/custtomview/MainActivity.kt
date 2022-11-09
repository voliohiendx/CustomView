package com.example.custtomview

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      //  IapConnector.initIap()

        findViewById<Button>(R.id.btnTest).setOnClickListener {
            findViewById<DrawPath>(R.id.drawPath).drawLineByModeScreen( ModeScreen.NOTCH_IP_14)
        }

        findViewById<Button>(R.id.btnTest2).setOnClickListener {
            findViewById<DrawPath>(R.id.drawPath).drawLineByModeScreen( ModeScreen.NOTCH_BUNNY_EAR)
        }

        findViewById<SeekBar>(R.id.seekBarWidth).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                findViewById<DrawPath>(R.id.drawPath).setRabbitEarsWidth(p1 / 100f)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStartTrackingTouch: ")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStopTrackingTouch: ")
            }

        })

        findViewById<SeekBar>(R.id.seekBarHeight).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                findViewById<DrawPath>(R.id.drawPath).setRabbitEarsHeight(p1 / 100f)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStartTrackingTouch: ")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStopTrackingTouch: ")
            }

        })

        findViewById<SeekBar>(R.id.seekBarUpperBorder).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                findViewById<DrawPath>(R.id.drawPath).setUpperBorderCurvature(p1 / 100f)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStartTrackingTouch: ")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStopTrackingTouch: ")
            }

        })

        findViewById<SeekBar>(R.id.seekBarBottomBorder).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                findViewById<DrawPath>(R.id.drawPath).setBottomBorderCurvature(p1 / 100f)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStartTrackingTouch: ")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStopTrackingTouch: ")
            }

        })

        findViewById<SeekBar>(R.id.seekX14).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                findViewById<DrawPath>(R.id.drawPath).setXNotch14(p1 / 100f)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStartTrackingTouch: ")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStopTrackingTouch: ")
            }

        })

        findViewById<SeekBar>(R.id.seekBarY14).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                findViewById<DrawPath>(R.id.drawPath).setYNotch14(p1 / 100f)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStartTrackingTouch: ")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStopTrackingTouch: ")
            }

        })

        findViewById<SeekBar>(R.id.seekWight14).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                findViewById<DrawPath>(R.id.drawPath).setWightNotch14(p1 / 100f)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStartTrackingTouch: ")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStopTrackingTouch: ")
            }

        })

        findViewById<SeekBar>(R.id.seekBarHeight14).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                findViewById<DrawPath>(R.id.drawPath).setHeightNotch14(p1 / 100f)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStartTrackingTouch: ")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStopTrackingTouch: ")
            }

        })

        findViewById<SeekBar>(R.id.seekBarBorderNotch14).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                findViewById<DrawPath>(R.id.drawPath).setRadiusNotch14(p1 / 100f)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStartTrackingTouch: ")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStopTrackingTouch: ")
            }

        })

        findViewById<SeekBar>(R.id.seekRadiusLine).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                findViewById<DrawPath>(R.id.drawPath).setRadiusLightning(p1 / 100f)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStartTrackingTouch: ")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStopTrackingTouch: ")
            }

        })

        findViewById<SeekBar>(R.id.seekBarBorderLine).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                findViewById<DrawPath>(R.id.drawPath).setBorderSizeLightning(p1 / 100f)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStartTrackingTouch: ")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.d("Na0000007", "onStopTrackingTouch: ")
            }

        })
    }
}