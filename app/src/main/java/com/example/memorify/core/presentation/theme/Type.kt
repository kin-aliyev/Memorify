package com.example.memorify.core.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.memorify.R

val InterFontFamily = FontFamily(
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_extrabold, FontWeight.ExtraBold)
)


// Set of Material typography styles to start with
val Typography = Typography(
    // ─────────────────────────────────────────────────────────
    // DISPLAY — Героический декоративный текст
    // Где: онбординг, экран результата ("🎉 Отлично!"),
    //      splash screen, пустые состояния с большим акцентом.
    // Не используй для обычных заголовков экранов.
    // ─────────────────────────────────────────────────────────
    displayLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp,
        lineHeight = 38.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 34.sp,
    ),

    // ─────────────────────────────────────────────────────────
    // HEADLINE — Заголовки экранов и крупных секций
    // Где:
    //   headlineLarge  → "Sign In", "Sign Up" — главный заголовок экрана
    //   headlineMedium → заголовок диалога, боттом-шита
    //   headlineSmall  → заголовок вложенного раздела внутри экрана
    // ─────────────────────────────────────────────────────────
    headlineLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
    ),

    // ─────────────────────────────────────────────────────────
    // TITLE — Подзаголовки, навигация, метки компонентов
    // Где:
    //   titleLarge  → TopAppBar title, название карточки-флэшкарты
    //   titleMedium → Label над TextField ("Email", "Password"), заголовок элемента списка, название секции
    //   titleSmall  → Вторичный подзаголовок, caption к карточке, Selected tab label
    // ─────────────────────────────────────────────────────────
    titleLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 26.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 22.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 18.sp,
    ),

    // ─────────────────────────────────────────────────────────
    // BODY — Весь читаемый пользователем контент
    // Где:
    //   bodyLarge  → Контент флэшкарты (вопрос / ответ), основной текст в длинных описаниях
    //   bodyMedium → Placeholder внутри TextField, описания, вторичный текст в списках под заголовком
    //   bodySmall  → Мелкие описания, метаданные (дата, счётчик), текст в тултипах
    // ─────────────────────────────────────────────────────────
    bodyLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 25.sp, // чуть больше для комфортного чтения длинных текстов
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp,
    ),

    // ─────────────────────────────────────────────────────────
    // LABEL — UI-элементы управления, не для чтения
    // Где:
    //   labelLarge  → Текст кнопок (Button), текст таба
    //   labelMedium → Чипы, badge, текст в SnackBar, error/helper text под TextField
    //   labelSmall  → Самые мелкие подписи: счётчик символов, текст на иконке-нотификации, watermark
    // ─────────────────────────────────────────────────────────
    labelLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.2.sp,
    ),
)