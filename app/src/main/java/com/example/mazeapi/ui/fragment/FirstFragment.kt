package com.example.mazeapi.ui.fragment

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mazeapi.databinding.FragmentFirstBinding
import com.example.mazeapi.ui.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentFirstBinding? = null

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Use the itemId as needed in your fragment
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val itemId = arguments?.getInt("itemId", -1) ?: -1
//        moviesViewModel.getShowById(itemId)
//
//        moviesViewModel.showData.observe(viewLifecycleOwner) {
//            _binding?.itemShowName?.text = it.name
//            _binding?.summary?.text = Html.fromHtml(it.summary,0)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}