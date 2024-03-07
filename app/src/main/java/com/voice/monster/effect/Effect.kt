package com.voice.monster.effect

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import com.demon.fmodsound.FmodSound
import com.voice.monster.R

sealed class Effect(val id: Int, val name: String,val  icon: Int)

data object Normal : Effect(FmodSound.MODE_NORMAL, "Normal", R.mipmap.effect_normal)
data object Robot : Effect(FmodSound.MODE_ROBOT, "Robot", R.mipmap.effect_robot)
data object Monster : Effect(FmodSound.MODE_FUNNY, "Monster", R.mipmap.effect_monster)
data object Man : Effect(FmodSound.MODE_UNCLE, "Man", R.mipmap.effect_man)
data object Girl : Effect(FmodSound.MODE_FUNNY, "Girl", R.mipmap.effect_girl)
data object Child : Effect(FmodSound.MODE_LOLITA, "Child", R.mipmap.effect_child)
data object Alien : Effect(FmodSound.MODE_ETHEREAL, "Alien", R.mipmap.effect_alien)
data object Death : Effect(FmodSound.MODE_HORROR, "Death", R.mipmap.effect_death)

data class EffectWrapper(val effect: Effect, var isSel: Boolean = false)