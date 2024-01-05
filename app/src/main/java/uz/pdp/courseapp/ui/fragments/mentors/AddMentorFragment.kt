package uz.pdp.courseapp.ui.fragments.mentors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.pdp.courseapp.R
import uz.pdp.courseapp.databinding.FragmentAddMentorBinding
import uz.pdp.courseapp.db.DBHelper
import uz.pdp.courseapp.models.Mentor
import uz.pdp.courseapp.ui.activity.MainActivity

class AddMentorFragment : Fragment() {
    lateinit var helper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddMentorBinding.inflate(inflater, container, false)
        val mainActivity = requireActivity() as MainActivity
        helper = DBHelper(requireContext())

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        mainActivity.setSupportActionBar(binding.toolbar)

        binding.saveBtn.setOnClickListener {
            if (binding.mentorFatherName.text.isNotBlank() && binding.mentorName.text.isNotBlank() && binding.mentorSurname.text.isNotBlank()) {
                val name = binding.mentorName.text.toString()
                val surname = binding.mentorSurname.text.toString()
                val fathersName = binding.mentorFatherName.text.toString()
                val courseID = arguments?.getInt("courseID")

                val mentor = Mentor(name, surname, fathersName, courseID)
                helper.insertMentor(mentor)
                Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Fill All Spaces!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}