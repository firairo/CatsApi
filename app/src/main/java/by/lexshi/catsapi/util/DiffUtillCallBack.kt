package by.lexshi.catsapi.util

import androidx.recyclerview.widget.DiffUtil
import by.lexshi.catsapi.model.Response
import by.lexshi.catsapi.model.ResponseItem

class DiffUtilCallBack: DiffUtil.ItemCallback<ResponseItem>(){
    override fun areItemsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean {
        return oldItem.id == newItem.id
                && oldItem.url == newItem.url
    }

}
