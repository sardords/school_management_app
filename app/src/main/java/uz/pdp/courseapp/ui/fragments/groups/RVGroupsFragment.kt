package uz.pdp.courseapp.ui.fragments.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.pdp.courseapp.R
import uz.pdp.courseapp.adapters.MainAdapter
import uz.pdp.courseapp.databinding.FragmentRVGroupsBinding
import uz.pdp.courseapp.db.DBHelper
import uz.pdp.courseapp.models.Course
import uz.pdp.courseapp.ui.activity.MainActivity

class RVGroupsFragment : Fragment() {
    lateinit var helper: DBHelper
    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRVGroupsBinding.inflate(inflater, container, false)
        helper = DBHelper(requireContext())
        val allCourses = helper.getAllCourses()

        val mainActivity = requireActivity() as MainActivity
        adapter = MainAdapter(allCourses, object : MainAdapter.ClickController {
            override fun onButtonClick(course: Course, pos: Int) {
                val bundle = Bundle()
                bundle.putInt("groupID", pos + 1)
                findNavController().navigate(R.id.groupsOutputFragment, bundle)
            }
        })
        binding.rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rv.adapter = adapter

        mainActivity.setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

}