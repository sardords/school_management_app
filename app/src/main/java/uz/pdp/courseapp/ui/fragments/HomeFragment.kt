package uz.pdp.courseapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.pdp.courseapp.R
import uz.pdp.courseapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.coursesCard.setOnClickListener {
            findNavController().navigate(R.id.RVStudentsFragent)
        }

        binding.groupCard.setOnClickListener {
            findNavController().navigate(R.id.RVGroupsFragment)
        }

        binding.mentorsCard.setOnClickListener {
            findNavController().navigate(R.id.RVMentorsFragments)
        }

        return binding.root
    }
}