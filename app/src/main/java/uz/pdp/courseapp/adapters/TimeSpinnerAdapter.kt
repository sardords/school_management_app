package uz.pdp.courseapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import uz.pdp.courseapp.databinding.SpinnerItemBinding

class TimeSpinnerAdapter(var list: List<String>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: Vh
        if (convertView == null) {
            val inflate =
                SpinnerItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            viewHolder = Vh(inflate)
        } else {
            viewHolder = Vh(SpinnerItemBinding.bind(convertView))
        }
        viewHolder.spinnerItemBinding.tv.text = list[position]
        return viewHolder.itemView
    }

    inner class Vh {
        val itemView: View
        val spinnerItemBinding: SpinnerItemBinding

        constructor(spinnerItemBinding: SpinnerItemBinding) {
            itemView = spinnerItemBinding.root
            this.spinnerItemBinding = spinnerItemBinding
        }
    }
}