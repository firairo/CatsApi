package by.lexshi.catsapi

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.lexshi.catsapi.databinding.ActivityMainBinding
import by.lexshi.catsapi.ui.WorksViewModel
import by.lexshi.catsapi.util.saveMediaToStorage
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var job: Job
    lateinit var front_anim: AnimatorSet
    lateinit var back_anim: AnimatorSet
    var isFront = true
    private val viewModel: WorksViewModel by viewModels()

    private lateinit var recyclerViewAdapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupRecyclerView()
        loadData()


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

        //Слушатель нажатия на полную картинку, когда рекуклер isGone
        binding.fullImageView.setOnClickListener {
            flip()
        }

        binding.saveButton.setOnClickListener {
            val img = binding.fullImageView.drawable
            saveMediaToStorage(this, img)
        }
    }


    private fun setupRecyclerView() {
        recyclerViewAdapter = RVAdapter()

        binding.rvList.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)

        }

    }



    private fun loadData() {
        lifecycleScope.launch {
            viewModel.listData.collect {
                recyclerViewAdapter.submitData(it)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()

    }

    fun fullSize(text: String, url: String) {

        /* Скрываем и показываем вьюхи
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
