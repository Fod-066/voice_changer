package com.voice.monster.pages.change

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.voice.monster.R
import com.voice.monster.databinding.EffectItemViewBinding
import com.voice.monster.effect.Alien
import com.voice.monster.effect.Child
import com.voice.monster.effect.Death
import com.voice.monster.effect.Effect
import com.voice.monster.effect.EffectWrapper
import com.voice.monster.effect.Girl
import com.voice.monster.effect.Man
import com.voice.monster.effect.Monster
import com.voice.monster.effect.Normal
import com.voice.monster.effect.Robot
import com.voice.monster.ext.singleClick

class EffectAdapter(val context: Context, val onSel: (Effect) -> Unit) :
  RecyclerView.Adapter<EffectAdapter.EffectVH>() {

  private var selIndex = 0

  private val datas = listOf<EffectWrapper>(
    EffectWrapper(Normal, true),
    EffectWrapper(Robot, false),
    EffectWrapper(Monster, false),
    EffectWrapper(Man, false),
    EffectWrapper(Girl, false),
    EffectWrapper(Child, false),
    EffectWrapper(Alien, false),
    EffectWrapper(Death, false),
  )

  inner class EffectVH(val binding: EffectItemViewBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EffectVH {
    return EffectVH(EffectItemViewBinding.inflate(LayoutInflater.from(context), parent, false))
  }

  override fun getItemCount(): Int = datas.size

  override fun onBindViewHolder(holder: EffectVH, position: Int) {
    val wrapper = datas[position]
    holder.binding.apply {
      root.setBackgroundResource(if (wrapper.isSel) R.drawable.shape_conner_rect_stoke_12 else R.drawable.shape_conner_rect_12)

      effectIv.setImageResource(wrapper.effect.icon)
      effectTv.text = wrapper.effect.name
      root.singleClick {
        wrapper.isSel = true
        datas[selIndex].isSel = false
        notifyItemChanged(selIndex)
        selIndex = holder.adapterPosition
        notifyItemChanged(holder.adapterPosition)
        onSel.invoke(wrapper.effect)
      }
    }
  }
}