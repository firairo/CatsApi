package by.lexshi.catsapi

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import by.lexshi.catsapi.databinding.ItemRvBinding
import by.lexshi.catsapi.model.Response
import com.bumptech.glide.Glide

class RVAdapter(private val data: Response?) : RecyclerView.Adapter<RVAdapter.ViewHolder> () {

    private lateinit var binding: ItemRvBinding

    // Создает новый объект ViewHolder всякий раз, когда Recycler нуждается в этом
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RVAdapter.ViewHolder, position: Int) {
        val url = data?.get(position)?.url.toString()
        val text = data?.get(position)?.id.toString()

        Glide
            .with(holder.itemView.context)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imageView)


        holder.itemView.setOnClickListener {
            (holder.itemView.context as MainActivity).fullSize(text, url)

            //Toast.makeText(holder.itemView.context, url, Toast.LENGTH_SHORT).show()

        }
    }

    override fun getItemCount(): Int {
        return data?.size!!
    }


    class ViewHolder(binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {
        var imgView: ImageView = binding.imageView
    }
}