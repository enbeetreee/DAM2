package com.aspencarsi.dwchar_app.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aspen.dwchar_app.R
import com.aspen.dwchar_app.databinding.ActivityCharacterBinding
import com.aspencarsi.dwchar_app.domain.model.CharacterM
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CharacterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCharacterBinding.inflate(layoutInflater) }

    val viewModel: CharacterViewModel by viewModels { CharacterViewModel.Factory }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

       viewModel.fetchOne(intent.getStringExtra("CHARNAME")!!)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.character.collectLatest {
                        character ->
                        binding.name.text= character.name

                        binding.rel.text = character.relationsWithTheDoctor
                        binding.status.text = character.status
                        binding.desc.text = character.description

                        Glide.with(binding.img.context)
                            .load(character.image)
                            .placeholder(R.mipmap.ic_launcher)
                            .fitCenter()
                            .centerCrop()
                            .into(binding.img)

                    }

                }
                }
            }

        }
    }
