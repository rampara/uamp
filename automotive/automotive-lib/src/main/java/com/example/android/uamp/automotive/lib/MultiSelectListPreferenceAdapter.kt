/*
 * Copyright 2020 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.uamp.automotive.lib

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.HashSet

/**
 * Adapter that provides bindings between a {@link ListPreference} and set of views to display and
 * allow for selection of the entries of the preference.
 */
class MultiSelectListPreferenceAdapter(preference: MultiSelectListPreference) :
  RecyclerView.Adapter<MultiSelectListPreferenceAdapter.ViewHolder>() {

  private var selectedEntries: MutableSet<String> = HashSet(preference.getValues())
  private var entries: Array<CharSequence>? = preference.getEntries()
  private var entryValues: Array<CharSequence>? = preference.getEntryValues()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context)
      .inflate(R.layout.check_box_list_item, parent, false))
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.title.text = entries?.get(position)
    val entryValue = entryValues?.get(position).toString()

    holder.checkBox.isChecked = selectedEntries.contains(entryValue)
    holder.holderView.setOnClickListener {
      if (selectedEntries.contains(entryValue)) {
        selectedEntries.remove(entryValue)
      } else {
        selectedEntries.add(entryValue)
      }

      notifyItemChanged(position)
    }
  }

  fun getSelectedEntries(): Set<String> {
    return selectedEntries
  }

  override fun getItemCount(): Int {
    entries?.let { return entries!!.size }
    return 0
  }

  class ViewHolder(val holderView: View) : RecyclerView.ViewHolder(holderView) {
    val title: TextView = itemView.findViewById(R.id.title)
    val checkBox: CheckBox = itemView.findViewById(R.id.check_box_widget)
  }
}