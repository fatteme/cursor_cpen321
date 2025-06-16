package com.biteswipe.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.biteswipe.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        setupButtons()
        observeViewModel()
    }

    private fun setupButtons() {
        binding.editProfileButton.setOnClickListener {
            // TODO: Navigate to edit profile screen
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            // TODO: Navigate to login screen
        }
    }

    private fun observeViewModel() {
        viewModel.profile.observe(viewLifecycleOwner) { profile ->
            binding.usernameText.text = profile.username
            binding.emailText.text = profile.email
            binding.likesCount.text = profile.likesCount.toString()
            binding.dislikesCount.text = profile.dislikesCount.toString()
            binding.matchesCount.text = profile.matchesCount.toString()

            // Load profile image
            profile.profileImageUrl?.let { imageUrl ->
                Glide.with(this)
                    .load(imageUrl)
                    .circleCrop()
                    .into(binding.profileImage)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                // TODO: Show error message to user
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 