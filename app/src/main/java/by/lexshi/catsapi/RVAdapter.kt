package by.lexshi.catsapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import by.lexshi.catsapi.databinding.ItemRvBinding
import by.lexshi.catsapi.model.ResponseItem
import by.lexshi.catsapi.util.DiffUtilCallBack
import com.bumptech.glide.Glide

class RVAdapter : PagingDataAdapter<ResponseItem, RVAdapter.ViewHolder>(DiffUtilCallBack()) {

    private lateinit var binding: ItemRvBinding

    // Создает новый объект ViewHolder всякий раз, когда Recycler нуждается в этом
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.ViewHolder {
        val binding = ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RVAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)

        var data = holder.getData()

        holder.itemView.setOnClickListener {
            (holder.itemView.context as MainActivity).fullSize(data[0], data[1])

            //Toast.makeText(holder.itemView.context, url, Toast.LENGTH_SHORT).show()

        }
    }


    class ViewHolder(binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {
        var imgView: ImageView = binding.imageView

        private lateinit var text: String
        private lateinit var url: String


        fun bind(data: ResponseItem) {
            url = data?.url.toString()
            text = data?.id.toString()

            Glide
                .with(itemView.context)
                .load(data.url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgView)


        }

        fun getData(): List<String> {
            return listOf<String>(text,url)
        }
    }
}