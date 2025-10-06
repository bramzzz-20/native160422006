package com.ubaya.studentproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.studentproject.R
import com.ubaya.studentproject.databinding.FragmentStudentListBinding
import com.ubaya.studentproject.viewmodel.ListViewModel


class StudentListFragment : Fragment() {
    private lateinit var  binding : FragmentStudentListBinding
    private lateinit var viewmodel:ListViewModel
    private val studentListAdapter = StudentListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewmodel.refresh()

        binding.recViewStudent.layoutManager = LinearLayoutManager(context)
        binding.recViewStudent.adapter = studentListAdapter

        binding.refreshLayout.setOnRefreshListener {
            viewmodel.refresh()
            binding.refreshLayout.isRefreshing = false
        }

        // viewmodel.testSaveFile()

        observeViewModel()
    }

    fun observeViewModel() {
        viewmodel.studentsLD.observe(viewLifecycleOwner, Observer {
            studentListAdapter.updateStudentList(it)
        })

        viewmodel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.progressLoad.visibility = View.VISIBLE
                binding.recViewStudent.visibility = View.INVISIBLE
            } else {
                binding.progressLoad.visibility = View.INVISIBLE
                binding.recViewStudent.visibility = View.VISIBLE
            }
        })

        viewmodel.errorLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.txtError.text = "Something wrong when load student data"
                binding.txtError.visibility = View.VISIBLE
                binding.recViewStudent.visibility = View.INVISIBLE
            } else {
                binding.txtError.visibility = View.INVISIBLE
                binding.recViewStudent.visibility = View.VISIBLE
            }
        })
    }
}