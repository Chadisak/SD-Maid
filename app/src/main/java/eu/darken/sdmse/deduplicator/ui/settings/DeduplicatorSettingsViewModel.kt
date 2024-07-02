package eu.darken.sdmse.deduplicator.ui.settings

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.darken.sdmse.common.coroutine.DispatcherProvider
import eu.darken.sdmse.common.debug.logging.logTag
import eu.darken.sdmse.common.uix.ViewModel3
import eu.darken.sdmse.common.upgrade.UpgradeRepo
import eu.darken.sdmse.deduplicator.core.Deduplicator
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class DeduplicatorSettingsViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    dispatcherProvider: DispatcherProvider,
    upgradeRepo: UpgradeRepo,
    deduplicator: Deduplicator,
) : ViewModel3(dispatcherProvider) {

    val state = combine(
        deduplicator.state,
        upgradeRepo.upgradeInfo.map { it.isPro },
    ) { state, isPro ->
        State(
            isPro = isPro,
            state = state,
        )
    }.asLiveData2()

    data class State(
        val state: Deduplicator.State,
        val isPro: Boolean,
    )

    companion object {
        private val TAG = logTag("Settings", "Deduplicator", "ViewModel")
    }
}