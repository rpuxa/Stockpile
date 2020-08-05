package ru.rpuxa.stockpile

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import ru.rpuxa.stockpile.ui.AppActivity
import ru.rpuxa.stockpile.viewmodels.ViewModelFactory

val Fragment.appActivity get() = activity as AppActivity

inline fun <reified VM : ViewModel> Fragment.viewModel() =
    activityViewModels<VM>(factoryProducer = ::ViewModelFactory)