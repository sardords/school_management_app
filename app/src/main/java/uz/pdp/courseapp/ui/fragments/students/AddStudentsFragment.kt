package uz.pdp.courseapp.ui.fragments.students

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.pdp.courseapp.R
import uz.pdp.courseapp.adapters.GroupSpinnerAdapter
import uz.pdp.courseapp.adapters.MentorSpinnerAdapter
import uz.pdp.courseapp.adapters.TimeSpinnerAdapter
import uz.pdp.courseapp.databinding.FragmentAddStudentsBinding
import uz.pdp.courseapp.db.DBHelper
import uz.pdp.courseapp.models.Group
import uz.pdp.courseapp.models.Student
import uz.pdp.courseapp.ui.activity.MainActivity


class AddStudentsFragment : Fragment() {
    lateinit var helper: DBHelper
    lateinit var binding: FragmentAddStudentsBinding
    lateinit var getGroups: ArrayList<Group>
    var timeStr: String? = null
    var isCLicked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddStudentsBinding.inflate(inflater, container, false)
        helper = DBHelper(requireContext())
        val mainActivity = requireActivity() as MainActivity
        mainActivity.setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        val courseID = arguments?.getInt("courseID")
        val allMentors = helper.getAllMentors(courseID!!)
        val allTypesOfDay = arrayListOf<String>("Toq kunlar", "Juft kunlar")
        val allTypesOfTime = arrayListOf<String>("16:30 - 18:30", "19:00 - 21:00")

        val mentorSpinner = MentorSpinnerAdapter(allMentors)
        val timeSpinnerAdapter = TimeSpinnerAdapter(allTypesOfTime)
        val daySpinnerAdapter = TimeSpinnerAdapter(allTypesOfDay)
        binding.mentorSpinner.adapter = mentorSpinner
        binding.daysSpinner.adapter = daySpinnerAdapter
        binding.timesSpinner.adapter = timeSpinnerAdapter
        binding.mentorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val mentor = allMentors[position].id
                getGroups = helper.getAllGroupsByCourseAndMentor(courseID, mentor!!)
                val groupSpinnerAdapter = GroupSpinnerAdapter(getGroups)
                binding.groupSpinner.adapter = groupSpinnerAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireContext(), "Please choose mentor first!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.calendarBtn.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(requireActivity(), object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(
                        view: DatePicker?,
                        year: Int,
                        month: Int,
                        dayOfMonth: Int
                    ) {
                        timeStr = "$dayOfMonth/$month/$year"
                        binding.dateText.text = timeStr
                        isCLicked = true
                    }
                }, 2021, 4, 24)
            datePickerDialog.show()
        }

        binding.saveBtn.setOnClickListener {
            if (binding.studentsName.text.isNotBlank() && binding.studentsSurname.text.isNotBlank() && binding.studentsFathersName.text.isNotBlank() && isCLicked) {
                val name = binding.studentsName.text.toString()
                val surname = binding.studentsSurname.text.toString()
                val fathersName = binding.studentsFathersName.text.toString()
                val mentor = allMentors[binding.mentorSpinner.selectedItemPosition]
                val group = getGroups[binding.groupSpinner.selectedItemPosition]
                val day = allTypesOfDay[binding.daysSpinner.selectedItemPosition]
                val groupTime = allTypesOfTime[binding.timesSpinner.selectedItemPosition]
                val student = Student(
                    name,
                    surname,
                    fathersName,
                    timeStr,
                    mentor.id,
                    group.id,
                    courseID,
                    day,
                    groupTime
                )
                helper.insertStudent(student)
                Toast.makeText(requireContext(), "Added Successfully!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Fill All Spaces!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.mentorLayout.setOnClickListener {
            if (allMentors.size == 0) {
                Toast.makeText(
                    requireContext(),
                    "Mentors are not found! Add mentor.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                binding.mentorLayout.visibility = View.INVISIBLE
                binding.mentorSpinner.bringToFront()
            }
        }

        binding.groupLayout.setOnClickListener {
            if (allMentors.size == 0) {
                Toast.makeText(requireContext(), "Groups are not found! Add group.", Toast.LENGTH_SHORT).show()
            } else {
                binding.groupLayout.visibility = View.INVISIBLE
                binding.groupSpinner.bringToFront()
            }
        }

        return binding.root
    }
}