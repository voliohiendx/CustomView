package com.example.custtomview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.epoxy.EpoxyController

class ExoPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player)

        val listPath = mutableListOf<String>()
        for (i in 1..10) {
            listPath.add("https://store-data.volio.vn/stores/store-1/2022/10/17/1665990924_Snaptik_7134886593990544666_jordan-tran%20(online-video-cutter.com).mp4")
            listPath.add("https://store-data.volio.vn/stores/store-1/2022/10/17/1665990600_Snaptik_7043047593865219354_tiktok%20(1)%20(online-video-cutter.com).mp4")
            listPath.add("https://store-data.volio.vn/stores/store-1/2022/10/17/1665990382_Snaptik_7123734242206567706_tiktok%20(online-video-cutter.com).mp4")
            listPath.add("https://store-data.volio.vn/stores/store-1/2022/10/17/1665990225_logo%20spiderman.mp4")
            listPath.add("https://store-data.volio.vn/stores/store-1/2022/10/17/1665990079_Snaptik_7029989349164944666_joe-ma%20(online-video-cutter.com).mp4")
            listPath.add("https://store-data.volio.vn/stores/store-1/2022/10/17/1665989688_led.mp4")
        }
        //   findViewById<EpoxyRecyclerView>(R.id.rvTest).layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        val controller = object : EpoxyController() {
            override fun buildModels() {
                listPath.forEachIndexed { index, s ->
                    add(EpoxyexoBindingModel_().id(index).path(s))
                }
            }

        }
        findViewById<ViewPager2>(R.id.viewPage2).adapter = controller.adapter
        controller.requestModelBuild()
//        findViewById<EpoxyRecyclerView>(R.id.rvTest).buildModelsWith(object :
//            EpoxyRecyclerView.ModelBuilderCallback {
//            override fun buildModels(controller: EpoxyController) {
//                listPath.forEachIndexed { index, s ->
//                    controller.add(
//                        EpoxyexoBindingModel_().id(index).path(s)
//                    )
//                }
//
//            }
//        })
    }
}