package com.aspencarsi.dwchar_app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aspen.dwchar_app.R
import com.aspen.dwchar_app.databinding.ViewCharacterItemBinding

import com.aspencarsi.dwchar_app.domain.model.CharacterM
import com.aspencarsi.dwchar_app.ui.transformations.LeftCropTransformation
import com.aspencarsi.dwchar_app.ui.transformations.RightCropTransformation
import com.aspencarsi.dwchar_app.ui.transformations.TopCropTransformation
import com.bumptech.glide.Glide


class CharacterListAdapter(val onItemClick: (CharacterM) -> Unit) :
    ListAdapter<CharacterM, CharacterListAdapter.CharactersViewHolder>(
        DIFF_CALLBACK
    ) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CharacterM>() {
            override fun areContentsTheSame(oldItem: CharacterM, newItem: CharacterM): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: CharacterM, newItem: CharacterM): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    // private val characterResponse = mutableListOf<CharacterM>()
    class CharactersViewHolder(private val binding: ViewCharacterItemBinding, val onItemClick: (CharacterM) -> Unit) :
        ViewHolder(binding.root) {

         val image: ImageView by lazy {
            itemView.findViewById(R.id.char_img)

        }

        fun bindTo(character: CharacterM) {
            binding.charName.text = character.name


            binding.charImg.setImageResource(R.mipmap.ic_launcher)

            binding.charImg.setOnClickListener{onItemClick(character)}


            if (character.name == "Ninth Doctor"){//corrige crop de imagenes
                Glide.with(itemView.context)
                    .load(character.image)
                    .placeholder(R.mipmap.ic_launcher)
                    .fitCenter()
                    .centerCrop()
                    .into(image)
            }else if((Regex("Alistair").containsMatchIn(character.name)) ) {
                Glide.with(itemView.context)
                    .load(character.image)
                    .placeholder(R.mipmap.ic_launcher)
                    .fitCenter()
                    .transform(LeftCropTransformation(itemView.context))
                    .into(image)
            }else if((Regex("Gogh").containsMatchIn(character.name))){
                Glide.with(itemView.context)
                    .load(character.image)
                    .placeholder(R.mipmap.ic_launcher)
                    .fitCenter()
                    .transform(RightCropTransformation(itemView.context))
                    .into(image)
            } else{
                Glide.with(itemView.context)
                    .load(character.image)
                    .placeholder(R.mipmap.ic_launcher)
                    .fitCenter()
                    .transform(TopCropTransformation(itemView.context))
                    .into(image)
            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewCharacterItemBinding.inflate(layoutInflater)
        val vh = CharactersViewHolder(binding, onItemClick)


        return vh
    }


    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }


}