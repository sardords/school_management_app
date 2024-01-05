package uz.pdp.courseapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.courseapp.databinding.MentorItemBinding
import uz.pdp.courseapp.models.Mentor

class MentorRVAdapter(var list: List<Mentor>, var clickController: ClickController) : RecyclerView.Adapter<MentorRVAdapter.Vh>() {
    inner class Vh(private var itemMentorBinding: MentorItemBinding) :
        RecyclerView.ViewHolder(itemMentorBinding.root) {
        fun onBind(mentor: Mentor, position: Int) {
            val nameTxt = mentor.surname + " " + mentor.name
            itemMentorBinding.mentorName.text = nameTxt
            itemMentorBinding.fathersName.text = mentor.fathersName

            itemMentorBinding.deleteCard.setOnClickListener {
                clickController.deleteBtnClick(mentor, position)
            }
            itemMentorBinding.editCard.setOnClickListener {
                clickController.editBtnClick(mentor, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(MentorItemBinding.inflate(LayoutInflater.from(parent.context), null, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ClickController{
        fun editBtnClick(mentor: Mentor, position: Int)
        fun deleteBtnClick(mentor: Mentor, position: Int)
    }
}