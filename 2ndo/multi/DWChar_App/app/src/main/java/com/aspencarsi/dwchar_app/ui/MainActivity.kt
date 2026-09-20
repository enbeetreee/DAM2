package com.aspencarsi.dwchar_app.ui

import android.os.Bundle
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aspen.dwchar_app.databinding.ActivityMainBinding
import com.aspencarsi.dwchar_app.domain.model.CharacterM
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val CHARNAME = "CHARNAME"

    val viewModel: CharactersViewModel by viewModels { CharactersViewModel.Factory }

    private val characterAdapter by lazy {
        CharacterListAdapter { character ->
            onCharacterClick(
                character
            )
        }
    }

    private fun onCharacterClick(character: CharacterM) {



        Snackbar.make(binding.root, character.description, Snackbar.LENGTH_SHORT)
            .show()
        //Cambiar a otra actividad con datos de personaje

        val intent = Intent(this, CharacterActivity::class.java)
        intent.putExtra(CHARNAME, character.name!!)

         startActivity(intent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.characterList.adapter = characterAdapter



        viewModel.fetchCharacters()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.characters.collect { characters ->
                        characterAdapter.submitList(characters)
                    }
                }
            }


        }
    }

}