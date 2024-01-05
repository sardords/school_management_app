package uz.pdp.courseapp.ui.fragments.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.pdp.courseapp.R
import uz.pdp.courseapp.adapters.MentorSpinnerAdapter
import uz.pdp.courseapp.adapters.TimeSpinnerAdapter
import uz.pdp.courseapp.databinding.FragmentAddGroupBinding
import uz.pdp.courseapp.db.DBHelper
import uz.pdp.courseapp.models.Group
import uz.pdp.courseapp.ui.activity.MainActivity

class AddGroupFragment : Fragment() {
    private var courseID: Int? = null
    lateinit var helper: DBHelper
    lateinit var mentorSpinner: MentorSpinnerAdapter
    lateinit var timeList: ArrayList<String>
    lateinit var timeAdapter: TimeSpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddGroupBinding.inflate(inflater, container, false)
        courseID = arguments?.getInt("courseID")
        helper = DBHelper(requireContext())
        timeList = ArrayList()
        val mainActivity = requireActivity() as MainActivity
        mainActivity.setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val allMentors = helper.getAllMentors(courseID!!)
        mentorSpinner = MentorSpinnerAdapter(allMentors)
        binding.mentorSpinner.adapter = mentorSpinner

        timeList.add("16:30 - 18:30")
        timeList.add("19:00 - 21:00")
        timeAdapter = TimeSpinnerAdapter(timeList)
        binding.timeSpinner.adapter = timeAdapter


        binding.saveBtn.setOnClickListener {
            if (binding.groupName.text.isNotBlank()) {
                var isHave = false
                val allOpeningGroups = helper.getAllOpeningGroups(courseID!!)
                val groupName = binding.groupName.text.toString()
                for (i in allOpeningGroups.indices) {
                    if (groupName == allOpeningGroups[i].name) {
                        isHave = true
                        break
                    }
                }
                if (isHave) {
                    Toast.makeText(requireContext(), "Name is already taken!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val mentorId = allMentors[binding.mentorSpinner.selectedItemPosition].id
                    val time = timeList[binding.timeSpinner.selectedItemPosition]
                    val group = Group(groupName, mentorId, courseID, time, false)
                    helper.insertGroup(group)
                    Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().popBackStack()
                }
            } else {
                Toast.makeText(requireContext(), "Fill All Spaces!", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}