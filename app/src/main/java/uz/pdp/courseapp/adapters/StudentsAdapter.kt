package uz.pdp.courseapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.courseapp.databinding.StudentItem2Binding
import uz.pdp.courseapp.models.Student

class StudentsAdapter(var list: List<Student>, var clickController: ClickController) :
    RecyclerView.Adapter<StudentsAdapter.Vh>() {

    inner class Vh(var itemBinding: StudentItem2Binding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(student: Student, position: Int) {
            var fullNameTxt = student.surname + " " + student.name
            itemBinding.studentFullName.text = fullNameTxt
            itemBinding.studentsFathersName.text = student.fathersName
            itemBinding.studentsDate.text = student.regDate

            itemBinding.deleteCard.setOnClickListener {
                clickController.deleteCard(student, position)
            }
            itemBinding.editCard.setOnClickListener {
                clickController.editCard(student, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(StudentItem2Binding.inflate(LayoutInflater.from(parent.context), null, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface ClickController {
        fun editCard(student: Student, position: Int)
        fun deleteCard(student: Student, position: Int)
    }
}