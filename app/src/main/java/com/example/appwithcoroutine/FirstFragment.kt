package com.example.appwithcoroutine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.appwithcoroutine.databinding.FragmentFirstBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private var textValue: Int = 0

    private suspend fun assignTextValue(): Int {
        delay(2000)
        return if (textValue == 0) {
            textValue = binding.numberEditText.text.toString().toInt()
            ++textValue
        } else {
            ++textValue
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numberEditText.doOnTextChanged { text, start, before, count ->
            if (binding.numberEditText.length() > 0) {
                binding.button.isEnabled = true
            } else binding.button.isEnabled = textValue > 0
        }

        binding.button.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                binding.numberText.text = assignTextValue().toString()
            }
        }

    }


}