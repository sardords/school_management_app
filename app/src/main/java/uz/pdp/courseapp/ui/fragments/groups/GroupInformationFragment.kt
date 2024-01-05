package uz.pdp.courseapp.ui.fragments.groups

import android.os.Binder
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.pdp.courseapp.R
import uz.pdp.courseapp.adapters.StudentsAdapter
import uz.pdp.courseapp.databinding.FragmentGroupInformationBinding
import uz.pdp.courseapp.db.DBHelper
import uz.pdp.courseapp.models.Group
import uz.pdp.courseapp.models.Student
import uz.pdp.courseapp.ui.activity.MainActivity


class GroupInformationFragment : Fragment() {
    lateinit var binding: FragmentGroupInformationBinding
    lateinit var helper: DBHelper
    lateinit var group: Group
    lateinit var adapter: StudentsAdapter
    lateinit var allStudents: ArrayList<Student>
    var courseID: Int? = null
    var groupID: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupInformationBinding.inflate(inflater, container, false)
        helper = DBHelper(requireContext())
        courseID = arguments?.getInt("courseID")
        groupID = arguments?.getInt("groupID")
        group = helper.getGroupById(groupID!!)
        allStudents = helper.getAllStudents(courseID!!, group.mentorId!!, groupID!!)
        setHasOptionsMenu(true)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.setSupportActionBar(binding.toolbar)
        binding.toolbar.title = group.name
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.groupName.text = group.name
        val count = allStudents.size
        val countTxt = "O'quvchilar soni: $count"
        binding.groupCount.text = countTxt
        binding.groupTime.text = group.time

        binding.startLesson.setOnClickListener {
            if (group.isOpened == false) {
                val group =
                    Group(group.id, group.name, group.mentorId, group.courseId, group.time, true)
                helper.updateGroup(group)
                Toast.makeText(requireContext(), "Lesson is started!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Already Opened!", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }

    override fun onResume() {
        adapter = StudentsAdapter(allStudents, object : StudentsAdapter.ClickController {
            override fun editCard(student: Student, position: Int) {
                val bundle = Bundle()
                bundle.putInt("studentID", student.id!!)
                findNavController().navigate(R.id.editStudentsFragment, bundle)
            }

            override fun deleteCard(student: Student, position: Int) {
                helper.deleteStudent(student)
                allStudents.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, adapter.itemCount)
                Toast.makeText(requireContext(), "Removed!", Toast.LENGTH_SHORT).show()
            }
        })
        binding.rv.adapter = adapter
        binding.rv
            .layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
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
                val bundle = Bundle()
                bundle.putInt("courseID", courseID!!)
                bundle.putInt("groupID", groupID!!)
                findNavController().navigate(R.id.addStudentsFragment, bundle)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}