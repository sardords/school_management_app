package uz.pdp.courseapp.ui.fragments.mentors

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.pdp.courseapp.R
import uz.pdp.courseapp.adapters.MentorRVAdapter
import uz.pdp.courseapp.databinding.EditMentorDialogBinding
import uz.pdp.courseapp.databinding.FragmentOutputMentorBinding
import uz.pdp.courseapp.db.DBHelper
import uz.pdp.courseapp.models.Mentor
import uz.pdp.courseapp.ui.activity.MainActivity

class OutputMentorFragment : Fragment() {
    lateinit var binding: FragmentOutputMentorBinding
    lateinit var adapter: MentorRVAdapter
    lateinit var helper: DBHelper
    var courseIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOutputMentorBinding.inflate(inflater, container, false)
        courseIndex = arguments?.getInt("courseId")
        helper = DBHelper(requireContext())
        setHasOptionsMenu(true)

        val course = helper.getCourseById(courseIndex!!)
        binding.toolbar.title = course.title
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        val mainActivity = requireActivity() as MainActivity
        mainActivity.setSupportActionBar(binding.toolbar)

        return binding.root
    }

    override fun onResume() {
        val allMentors = helper.getAllMentors(courseIndex!!)
        adapter = MentorRVAdapter(allMentors, object : MentorRVAdapter.ClickController {
            override fun editBtnClick(mentor: Mentor, position: Int) {
                val dialog = AlertDialog.Builder(requireContext())
                val bindingDialog =
                    EditMentorDialogBinding.inflate(LayoutInflater.from(requireContext()))
                bindingDialog.mentorName.setText(mentor.name)
                bindingDialog.mentorSurname.setText(mentor.surname)
                bindingDialog.mentorFatherName.setText(mentor.fathersName)
                dialog.setView(bindingDialog.root)
                dialog.setCancelable(false)
                dialog.setPositiveButton("O'zgartirish", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        if (bindingDialog.mentorName.text.isNotBlank() && bindingDialog.mentorSurname.text.isNotBlank() && bindingDialog.mentorFatherName.text.isNotBlank()) {
                            val name = bindingDialog.mentorName.text.toString()
                            val surname = bindingDialog.mentorSurname.text.toString()
                            val fName = bindingDialog.mentorFatherName.text.toString()
                            val mentorObj = Mentor(mentor.id, name, surname, fName, mentor.courseId)
                            helper.updateMentor(mentorObj)
                            allMentors[position] = mentorObj
                            adapter.notifyItemChanged(position)
                        }
                    }
                })
                dialog.setNegativeButton("Yopish", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                    }
                })
                dialog.show()
            }

            override fun deleteBtnClick(mentor: Mentor, position: Int) {
                allMentors.remove(mentor)
                helper.deleteMentor(mentor)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, allMentors.size - 1)
                Toast.makeText(requireContext(), "Deleted Successfully!", Toast.LENGTH_SHORT).show()
            }
        })
        binding.rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rv.adapter = adapter
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
        val int = arguments?.getInt("courseId")
        val itemId = item.itemId
        when (itemId) {
            R.id.addBtn -> {
                val bundle = Bundle()
                bundle.putInt("courseID", int!!)
                findNavController().navigate(R.id.addMentorFragment, bundle)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}