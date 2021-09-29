package by.lexshi.catsapi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import by.lexshi.catsapi.databinding.ActivityMainBinding
import by.lexshi.catsapi.network.RestClient
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var job: Job

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.rvList) {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        updateView()
    }


    @SuppressLint("NotifyDataSetChanged")
    @DelicateCoroutinesApi
    fun updateView() {
        job = GlobalScope.launch(Dispatchers.IO) {
            val response = RestClient().service.getDataFromAPI()
            val data = response.body()
            withContext(Dispatchers.Main) {
                val rvAdapter = RVAdapter(data) //передаем в адаптер
                rvAdapter.notifyDataSetChanged()
                binding.rvList.adapter = rvAdapter


            }
        }
    }

    fun fullSize(text: String, url: String){
            binding.rvList.visibility = View.GONE
            binding.cardView.visibility = View.VISIBLE
            binding.fullTextView.text = text

            Glide
                .with(this@MainActivity)
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.fullImageView)

        }
}
