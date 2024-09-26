package ru.chantreck.brics2024.memos

import androidx.lifecycle.viewModelScope
import com.example.brics2024.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.chantreck.brics2024.common.BaseViewModel

class MemosViewModel : BaseViewModel<MemosState, MemosAction>() {

    private var stack: MutableList<MemosItem> = mutableListOf()

    fun selectItem(item: MemosItem) {
        if (_screenState.value.isPaused) return

        val items = _screenState.value.items
        stack += item
        if (stack.size < 2) {
            _screenState.value = _screenState.value.copy(
                items = items.map { if (it.id == item.id) it.copy(isOpened = true) else it }
            )
        } else {
            val first = stack.first()
            val second = stack.last()
            if (first.code == second.code) {
                _screenState.value = _screenState.value.copy(
                    items = items.map {
                        if (it.code == item.code) { it.copy(isOpened = true) } else { it }
                    },
                )
            } else {
                _screenState.value = _screenState.value.copy(
                    items = items.map {
                        if (it.id == first.id || it.id == second.id) { it.copy(isOpened = true) } else { it }
                    },
                    isPaused = true,
                )
                viewModelScope.launch {
                    delay(1000L)
                    _screenState.value = _screenState.value.copy(
                        items = items.map {
                            if (it.id == first.id || it.id == second.id) { it.copy(isOpened = false) } else { it }
                        },
                        isPaused = false,
                    )
                }
            }
            stack = mutableListOf()
        }
    }

    fun changePositionOfItems(from: Int, to: Int) {
        val oldItems = _screenState.value.items
        val first = oldItems[from]
        val second = oldItems[to]
        _screenState.value = _screenState.value.copy(
            items = oldItems.mapIndexed { index, item ->
                when {
                    index == from -> second
                    index == to -> first
                    else -> item
                }
            }
        )
    }

    private fun generateList(size: Size): List<MemosItem> {
        val generateCount = size.pairs
        val items = mutableListOf<MemosItem>()
        repeat(generateCount) { index ->
            val drawable = getRandomDrawable()
            items += MemosItem(
                id = 2 * index,
                drawable = drawable,
                isOpened = false,
                code = index,
            )
            items += MemosItem(
                id = 2 * index + 1,
                drawable = drawable,
                isOpened = false,
                code = index,
            )
        }
        println(items.size)
        return items.shuffled().take(size.columnSize * size.columnSize)
    }

    private fun getRandomDrawable(): Int {
        return listOf(
            R.drawable.ic_politian,
            R.drawable.ic_got,
            R.drawable.ic_hollywood,
            R.drawable.ic_altered_carbon,
            R.drawable.ic_money_heist,
            R.drawable.ic_magicians,
            R.drawable.ic_reasons_why,
            R.drawable.ic_westworld,
        ).random()
    }
}
