package uz.pdp.courseapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.courseapp.databinding.GroupItemBinding
import uz.pdp.courseapp.models.Group
import uz.pdp.courseapp.models.StudentsCount

class GroupRVAdapter(var list: List<StudentsCount>, var clickController: ClickController) :
    RecyclerView.Adapter<GroupRVAdapter.Vh>() {

    inner class Vh(private var groupItemBinding: GroupItemBinding) :
        RecyclerView.ViewHolder(groupItemBinding.root) {
        fun onBind(group: Group, pos: Int) {
            val text = "O'quvchilar soni: ${list[pos].count}"
            groupItemBinding.groupName.text = group.name
            groupItemBinding.studentsCount.text = text

            groupItemBinding.seeCard.setOnClickListener {
                clickController.see(list[pos].group, pos)
            }
            groupItemBinding.deleteCard.setOnClickListener {
                clickController.delete(list[pos].group, pos)
            }
            groupItemBinding.editCard.setOnClickListener {
                clickController.edit(list[pos].group, pos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(GroupItemBinding.inflate(LayoutInflater.from(parent.context), null, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position].group, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ClickController {
        fun see(group: Group, position: Int)
        fun edit(group: Group, position: Int)
        fun delete(group: Group, position: Int)
    }
}