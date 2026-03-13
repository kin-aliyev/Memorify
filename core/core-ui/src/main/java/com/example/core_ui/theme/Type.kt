package com.example.core_ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.core_ui.R

val InterFontFamily = FontFamily(
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_extrabold, FontWeight.ExtraBold)
)

fun Typography.withFontFamily(fontFamily: FontFamily) = copy(
    // DISPLAY — самые крупные, декоративные заголовки
    // Используй для: hero-секций, splash экранов, пустых состояний
    displayLarge = displayLarge.copy(fontFamily = fontFamily), // 57sp — огромный заголовок на весь экран
    displayMedium = displayMedium.copy(fontFamily = fontFamily), // 45sp — крупный акцентный текст
    displaySmall = displaySmall.copy(fontFamily = fontFamily), // 36sp — подзаголовок hero-секции

    // HEADLINE — заголовки экранов и секций
    // Используй для: заголовок экрана, заголовок диалога, крупные числа
    headlineLarge = headlineLarge.copy(fontFamily = fontFamily), // 32sp — главный заголовок экрана, крупные цифры
    headlineMedium = headlineMedium.copy(fontFamily = fontFamily), // 28sp — заголовок диалога, bottom sheet
    headlineSmall = headlineSmall.copy(fontFamily = fontFamily), // 24sp — заголовок секции внутри экрана

    // TITLE — заголовки компонентов
    // Используй для: названия карточек, TopAppBar, список-заголовки
    titleLarge = titleLarge.copy(fontFamily = fontFamily), // 22sp — TopAppBar title, названия разделов
    titleMedium = titleMedium.copy(fontFamily = fontFamily), // 16sp — название карточки, ListItem primary (твой label)
    titleSmall = titleSmall.copy(fontFamily = fontFamily), // 14sp — подзаголовок карточки, chips

    // BODY — основной текст
    // Используй для: длинный текст, описания, контент статей
    bodyLarge = bodyLarge.copy(fontFamily = fontFamily), // 16sp — основной текст статьи, описание
    bodyMedium = bodyMedium.copy(fontFamily = fontFamily), // 14sp — вторичный текст, описание карточки
    bodySmall = bodySmall.copy(fontFamily = fontFamily), // 12sp — сноски, вспомогательный текст

    // LABEL — мелкие подписи и компоненты
    // Используй для: кнопки, chips, метки, вспомогательные подписи
    labelLarge = labelLarge.copy(fontFamily = fontFamily), // 14sp — текст кнопки Button
    labelMedium = labelMedium.copy(fontFamily = fontFamily), // 12sp — NavigationBar label, chip text
    labelSmall = labelSmall.copy(fontFamily = fontFamily), // 11sp — временные метки, счётчики (твой formattedDate)
)

val Typography = Typography().withFontFamily(InterFontFamily)
