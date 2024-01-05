package uz.pdp.courseapp.ui.fragments.students

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.pdp.courseapp.R
import uz.pdp.courseapp.adapters.MainAdapter
import uz.pdp.courseapp.databinding.AddCourseBinding
import uz.pdp.courseapp.databinding.FragmentRVStudentsFragentBinding
import uz.pdp.courseapp.db.DBHelper
import uz.pdp.courseapp.models.Course
import uz.pdp.courseapp.ui.activity.MainActivity

class RVStudentsFragent : Fragment() {
    lateinit var binding: FragmentRVStudentsFragentBinding
    lateinit var helper: DBHelper
    lateinit var allCourses: ArrayList<Course>
    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRVStudentsFragentBinding.inflate(inflater, container, false)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.setSupportActionBar(binding.toolbar)
        helper = DBHelper(requireContext())
        allCourses = helper.getAllCourses()

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onResume() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        adapter = MainAdapter(allCourses, object : MainAdapter.ClickController {
            override fun onButtonClick(course: Course, pos: Int) {
                val bundle = Bundle()
                bundle.putInt("courseID", course.id!!)
                findNavController().navigate(R.id.outputCoursesFragment, bundle)
            }
        })
        binding.rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rv.adapter = adapter

        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            R.id.addBtn -> {
                val dialogBinding = AddCourseBinding.inflate(layoutInflater, null, false)
                val mainDialog = AlertDialog.Builder(requireContext())
                mainDialog.setCancelable(false)
                mainDialog.setPositiveButton(
                    "Qo'shish"
                ) { dialog, which ->
                    if (dialogBinding.courseName.text.isNotBlank() && dialogBinding.courseText.text.isNotBlank()) {
                        val name = dialogBinding.courseName.text.toString()
                        val mainTxt = dialogBinding.courseText.text.toString()
                        val course = Course(name, mainTxt)
                        allCourses.add(course)
                        helper.insertCourse(course)
                        adapter.notifyItemInserted(allCourses.size - 1)
                    } else {
                        Toast.makeText(requireContext(), "Fill All Spaces!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                mainDialog.setNegativeButton(
                    "Yopish"
                ) { dialog, which ->
                    dialog.dismiss()
                }
                mainDialog.setView(dialogBinding.root)
                mainDialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}