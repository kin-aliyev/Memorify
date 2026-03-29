package com.example.core_domain.model.collection

import com.example.core_domain.model.word.WordCard

data class CollectionDetail(
    val collection: Collection,
    val words: List<WordCard>,
)
