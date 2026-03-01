package com.example.memorify.core.presentation.components

import androidx.compose.ui.unit.dp

object Dimens {

    // ════════════════════════════════════════════════════════
    //  SPACING — отступы между элементами (Arrangement, padding)
    // ════════════════════════════════════════════════════════
    val spacing2 = 2.dp
    val spacing4 = 4.dp
    val spacing8 = 8.dp
    val spacing12 = 12.dp
    val spacing16 = 16.dp
    val spacing24 = 24.dp

    // ════════════════════════════════════════════════════════
    // PADDING — отступы внутри контейнеров
    // (синонимы spacing, выделены для ясности намерения)
    //
    //  paddingXs   → внутри мелких компонентов (чипы, badge)
    //  paddingMd   → внутри карточек, диалогов, боттом-шитов
    //  paddingScreen → стандартный горизонтальный отступ экрана, используй везде: Column, LazyColumn contentPadding
    // ════════════════════════════════════════════════════════
    val paddingXs     = spacing4
    val paddingMd     = spacing16
    val paddingScreen = spacing16   // горизонтальный отступ всех экранов

    // ════════════════════════════════════════════════════════
    //  COMPONENT HEIGHTS — минимальные высоты компонентов
    //  Используй heightIn(min = ...) чтобы компонент растягивался, но не был меньше нужного.
    //  heightChip     → Chip, маленький Tag
    //  heightButton   → Button, OutlinedButton, TextButton
    //  heightTab      → Tab, NavigationBarItem
    //  heightTextField → OutlinedTextField, TextField
    //  heightListItem → ListItem, строка в списке
    //  heightAppBar   → TopAppBar, BottomAppBar
    // ════════════════════════════════════════════════════════
    val heightChip      = 32.dp
    val heightButton    = 48.dp
    val heightTab       = 48.dp
    val heightTextField = 56.dp
    val heightListItem  = 56.dp
    val heightAppBar    = 64.dp

    // ════════════════════════════════════════════════════════
    //  ELEVATION — Material 3 elevation levels
    //  M3 использует tonal elevation (цвет) вместо тени.
    //  Уровни соответствуют спецификации.
    //  elevation0 → фоновые поверхности (background, scaffold)
    //  elevation1 → surface tint barely visible: Navigation Rail, Drawer
    //  elevation2 → Cards — ИСПОЛЬЗУЙ ДЛЯ КАРТОЧЕК
    //  elevation3 → FAB, Chip
    //  elevation4 → Navigation Drawer (открытый)
    //  elevation5 → Modal Bottom Sheet, Dialog
    // ════════════════════════════════════════════════════════
    val elevation0 = 0.dp
    val elevation1 = 1.dp
    val elevation2 = 3.dp
    val elevation3 = 6.dp
    val elevation4 = 8.dp
    val elevation5 = 12.dp

    // ════════════════════════════════════════════════════════
    // ICON SIZES
    //  iconXs → индикатор статуса, декоративная точка рядом с текстом
    //  iconSm → вспомогательная иконка: check в password rules, trailing icon в TextField, иконка в чипе
    //  iconMd → стандартная иконка: NavigationBar, IconButton, leading icon в TextField, иконка в кнопке — ИСПОЛЬЗУЙ ПО УМОЛЧАНИЮ
    //  iconLg → крупная иконка: FAB, пустое состояние, иконка провайдера (Google) в AuthButton
    // ════════════════════════════════════════════════════════
    val iconXs = 12.dp
    val iconSm = 16.dp
    val iconMd = 24.dp
    val iconLg = 32.dp
    val iconXl = 56.dp

    // ════════════════════════════════════════════════════════
    // FAB SIZES — FloatingActionButton
    // ════════════════════════════════════════════════════════
    val fabMini    = 40.dp
    val fabDefault = 56.dp
    val fabLarge   = 96.dp

    val contentMaxWidth = 600.dp
}