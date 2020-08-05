package com.junka.jnknotes.presenter.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.junka.jnknotes.R
import com.junka.jnknotes.databinding.FragmentDashboardBinding
import com.junka.jnknotes.presenter.ui.adapters.NotesAdapter
import com.junka.jnknotes.presenter.ui.observer
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.scope.viewModel

class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by lifecycleScope.viewModel(this)

    private val notesAdapter by lazy { NotesAdapter(onClickListener = viewModel::onNoteItemClick) }

    private lateinit var binding: FragmentDashboardBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentDashboardBinding.inflate(inflater)

        binding.imageAddNoteMain.setOnClickListener {

            findNavController().navigate(R.id.navigation_create_note)
        }

        with(viewModel) {
            observer(loading) { binding.loading.visibility = if (it) View.VISIBLE else View.GONE }
            observer(notes) { notesAdapter.notes = it }
            observer(navigateToNote) {
                it.getContentIfNotHandled()?.let { id ->
                    val action =
                        DashboardFragmentDirections.actionNavigationDashboardToNavigationCreateNote(
                            id
                        )
                    findNavController().navigate(action)

                }
            }
        }

        binding.notesRecyclerView.apply {
            adapter = notesAdapter
        }

        return binding.root
    }


}