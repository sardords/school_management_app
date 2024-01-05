package uz.pdp.courseapp.ui.fragments.students

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.pdp.courseapp.R
import uz.pdp.courseapp.databinding.FragmentOutputCoursesBinding
import uz.pdp.courseapp.db.DBHelper
import uz.pdp.courseapp.ui.activity.MainActivity

class OutputCoursesFragment : Fragment() {
    lateinit var helper: DBHelper
    lateinit var binding: FragmentOutputCoursesBinding
    var courseID: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOutputCoursesBinding.inflate(layoutInflater, container, false)
        helper = DBHelper(requireContext())
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        courseID = arguments?.getInt("courseID")
        val course = helper.getCourseById(courseID!!)
        binding.toolbar.title = course.title
        binding.mainTxt.text = course.text
        val mainActivity = requireActivity() as MainActivity
        mainActivity.setSupportActionBar(binding.toolbar)

        binding.addStudent.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("courseID", courseID!!)
            findNavController().navigate(R.id.addStudentsFragment, bundle)
        }
        return binding.root
    }

    override fun onResume() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        super.onResume()
    }
}