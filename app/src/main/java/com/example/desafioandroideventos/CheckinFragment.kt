package com.example.desafioandroideventos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.desafioandroideventos.data.models.Checkin
import com.example.desafioandroideventos.data.network.Status
import com.example.desafioandroideventos.databinding.FragmentCheckinBinding
import com.example.desafioandroideventos.ui.events.EventsViewModel

class CheckinFragment : Fragment() {
    private var _binding: FragmentCheckinBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EventsViewModel by lazy {
        ViewModelProvider(this).get(EventsViewModel::class.java)
    }
    private val args: CheckinFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentCheckinBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkinButton.setOnClickListener {
            binding.name.run {
                if (text.isEmpty()) {
                    error = "Informe seu nome"
                    requestFocus()
                    return@setOnClickListener
                }
            }
            binding.email.run {
                if (text.isEmpty()) {
                    error = "Informe seu email"
                    requestFocus()
                    return@setOnClickListener
                }
            }

            viewModel.checkin(Checkin(
                args.eventId,
                binding.name.text.toString(),
                binding.email.text.toString()
            )).subscribe(
                {response ->
                    if (response == Status.SUCCESS) {
                        Toast.makeText(
                            requireContext(),
                            "Checkin realizado com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigateUp()
                    } else if (response == Status.FAILURE) {
                        Toast.makeText(
                            requireContext(),
                            "Não foi possível realizar checkin",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(requireContext(), "Sem conexão", Toast.LENGTH_SHORT).show()
                    }
                },
                {error -> error.printStackTrace()},
                {}
            )
        }

    }
}