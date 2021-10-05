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
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)

        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RVAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        /*val url = data?.get(position)?.url.toString()
        val text = data?.get(position)?.id.toString()

        holder.itemView.setOnClickListener {
            (holder.itemView.context as MainActivity).fullSize(text, url)

            //Toast.makeText(holder.itemView.context, url, Toast.LENGTH_SHORT).show()

        }*/
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgView: ImageView = view.findViewById(R.id.image_view)


        fun bind(data: ResponseItem) {
            Glide
                .with(itemView.context)
                .load(data.url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgView)


        }
    }
}