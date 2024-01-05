package uz.pdp.courseapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.courseapp.databinding.CoursesItemBinding
import uz.pdp.courseapp.models.Course

class MainAdapter(private var list: ArrayList<Course>, var clickController: ClickController) :
    RecyclerView.Adapter<MainAdapter.Vh>() {

    inner class Vh(private var itemCoursesItemBinding: CoursesItemBinding) :
        RecyclerView.ViewHolder(itemCoursesItemBinding.root) {
        fun onBind(course: Course, position: Int) {
            itemCoursesItemBinding.courseTitle.text = course.title
            itemCoursesItemBinding.mainCard.setOnClickListener {
                clickController.onButtonClick(course, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(CoursesItemBinding.inflate(LayoutInflater.from(parent.context), null, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ClickController {
        fun onButtonClick(course: Course, pos: Int)
    }
}