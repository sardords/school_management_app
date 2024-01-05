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
import uz.pdp.courseapp.databinding.FragmentEditStudentsBinding
import uz.pdp.courseapp.db.DBHelper
import uz.pdp.courseapp.models.Group
import uz.pdp.courseapp.models.Student
import uz.pdp.courseapp.ui.activity.MainActivity

class EditStudentsFragment : Fragment() {
    lateinit var helper: DBHelper
    lateinit var binding: FragmentEditStudentsBinding
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
        binding = FragmentEditStudentsBinding.inflate(inflater, container, false)
        helper = DBHelper(requireContext())
        val mainActivity = requireActivity() as MainActivity
        mainActivity.setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        val studentID = arguments?.getInt("studentID")
        val student = helper.getStudentById(studentID!!)
        val courseID = student.courseId
        val groupID = student.groupId
        val group = helper.getGroupById(groupID!!)
        val mentor = helper.getMentorById(student.mentorId!!)

        val allMentors = helper.getAllMentors(courseID!!)
        getGroups = helper.getAllGroupsByCourseAndMentor(courseID, group.mentorId!!)
        val allTypesOfDay = arrayListOf<String>("Toq kunlar", "Juft kunlar")
        val allTypesOfTime = arrayListOf<String>("16:30 - 18:30", "19:00 - 21:00")

        val mentorSpinner = MentorSpinnerAdapter(allMentors)
        val timeSpinnerAdapter = TimeSpinnerAdapter(allTypesOfTime)
        val daySpinnerAdapter = TimeSpinnerAdapter(allTypesOfDay)
        var groupSpinnerAdapter = GroupSpinnerAdapter(getGroups)

        binding.groupSpinner.adapter = groupSpinnerAdapter
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
                val mentor2 = allMentors[position].id
                getGroups = helper.getAllGroupsByCourseAndMentor(courseID, mentor2!!)
                groupSpinnerAdapter = GroupSpinnerAdapter(getGroups)
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
                    }
                }, 2021, 4, 24)
            datePickerDialog.show()
        }

        for (i in getGroups.indices) {
            if (getGroups[i].id == group.id) {
                binding.groupSpinner.setSelection(i)
                break
            }
        }

        for (i in allMentors.indices) {
            if (allMentors[i].id == mentor.id) {
                binding.mentorSpinner.setSelection(i)
                break
            }
        }

        binding.studentsName.setText(student.name)
        binding.studentsSurname.setText(student.surname)
        binding.studentsFathersName.setText(student.fathersName)
        binding.dateText.text = student.regDate.toString()
        binding.daysSpinner.setSelection(allTypesOfDay.indexOf(student.typesOfDay))
        binding.timesSpinner.setSelection(allTypesOfTime.indexOf(student.typesOfTime))

        binding.saveBtn.setOnClickListener {
            if (binding.studentsName.text.isNotBlank() && binding.studentsSurname.text.isNotBlank() && binding.studentsFathersName.text.isNotBlank() && binding.dateText.text.isNotBlank()) {
                val name = binding.studentsName.text.toString()
                val surname = binding.studentsSurname.text.toString()
                val fathersName = binding.studentsFathersName.text.toString()
                val mentorObj = allMentors[binding.mentorSpinner.selectedItemPosition]
                val groupObj = getGroups[binding.groupSpinner.selectedItemPosition]
                val day = allTypesOfDay[binding.daysSpinner.selectedItemPosition]
                val groupTime = allTypesOfTime[binding.timesSpinner.selectedItemPosition]
                val studentObj = Student(
                    student.id,
                    name,
                    surname,
                    fathersName,
                    binding.dateText.text.toString(),
                    mentorObj.id,
                    groupObj.id,
                    courseID,
                    day,
                    groupTime
                )
                helper.updateStudent(studentObj)
                Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Fill All Spaces!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}