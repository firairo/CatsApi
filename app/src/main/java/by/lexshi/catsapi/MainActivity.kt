package by.lexshi.catsapi

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import by.lexshi.catsapi.databinding.ActivityMainBinding
import by.lexshi.catsapi.network.RestClient
import by.lexshi.catsapi.util.saveMediaToStorage
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var job: Job
    lateinit var front_anim: AnimatorSet
    lateinit var back_anim: AnimatorSet
    var isFront = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.rvList) {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        updateView()


        val scale = applicationContext.resources.displayMetrics.density
        binding.rvList.cameraDistance = 8000 * scale
        binding.cardView.cameraDistance = 8000 * scale

        front_anim = AnimatorInflater.loadAnimator(
            applicationContext,
            R.animator.front_animator
        ) as AnimatorSet
        back_anim = AnimatorInflater.loadAnimator(
            applicationContext,
            R.animator.back_animator
        ) as AnimatorSet

        //?????????????????? ?????????????? ???? ???????????? ????????????????, ?????????? ???????????????? isGone
        binding.fullImageView.setOnClickListener {
            flip()
        }

        binding.saveButton.setOnClickListener {
            val img = binding.fullImageView.drawable
            saveMediaToStorage(this, img)
        }
    }



    @SuppressLint("NotifyDataSetChanged")
    fun updateView() {
        job = GlobalScope.launch(Dispatchers.IO) {
            val response = RestClient().service.getDataFromAPI()
            val data = response.body()
            withContext(Dispatchers.Main) {
                val rvAdapter = RVAdapter(data) //???????????????? ?? ??????????????
                rvAdapter.notifyDataSetChanged()
                binding.rvList.adapter = rvAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()

    }

    fun fullSize(text: String, url: String) {

        /* ???????????????? ?? ???????????????????? ??????????
        binding.rvList.visibility = View.GONE
        binding.cardView.visibility = View.VISIBLE*/
        Glide
            .with(this@MainActivity)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.fullImageView)
        binding.fullTextView.text = text
        flip()
    }

    private fun flip() {
        val handler = Handler()
        if (isFront) {
            front_anim.setTarget(binding.rvList)
            back_anim.setTarget(binding.cardView)
            front_anim.start()
            back_anim.start()
            isFront = false
            binding.cardView.visibility = View.VISIBLE

            handler.postDelayed({
                binding.rvList.visibility = View.GONE
            }, 1000)


        } else {
            front_anim.setTarget(binding.cardView)
            back_anim.setTarget(binding.rvList)
            front_anim.start()
            back_anim.start()
            binding.cardView.visibility = View.VISIBLE
            isFront = true
            binding.rvList.visibility = View.VISIBLE

            handler.postDelayed({
                binding.cardView.visibility = View.GONE
            }, 1000)

        }
    }
}
