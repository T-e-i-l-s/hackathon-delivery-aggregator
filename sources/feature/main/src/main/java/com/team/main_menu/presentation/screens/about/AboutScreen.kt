package com.team.main_menu.presentation.screens.about

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.team.main_menu.R
import com.team.uikit.presentation.layouts.ProdUiShrinkingHeaderScreenLayout
import com.team.uikit.presentation.text.ProdUiText

@Composable
fun AboutScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val versionName = remember {
        try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "1.0"
        } catch (_: Exception) {
            "1.0"
        }
    }

    ProdUiShrinkingHeaderScreenLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        header = { shrinked ->
            AboutHeader(
                shrinked = shrinked,
                versionName = versionName,
                onBack = onBack
            )
        },
        content = { lazyListState, headerHeightPx, enabled ->
            AboutContent(
                lazyListState = lazyListState,
                headerHeightPx = headerHeightPx,
                enabled = enabled
            )
        }
    )
}

@Composable
private fun AboutHeader(
    shrinked: Boolean,
    versionName: String,
    onBack: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        if (shrinked) Color(0x48383838) else Color.Transparent,
        animationSpec = tween(600)
    )
    val borderRadius by animateDpAsState(
        if (shrinked) 28.dp else 0.dp,
        animationSpec = tween(1000)
    )
    val shrinkProgress by animateFloatAsState(
        if (shrinked) 0f else 1f
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = borderRadius, bottomEnd = borderRadius))
            .background(backgroundColor)
            .statusBarsPadding()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            ProdUiText(
                text = "О приложении",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(Modifier.height(24.dp * shrinkProgress))

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(120.dp * shrinkProgress)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.delivery_truck_bolt_24px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp * shrinkProgress)
            )

            Spacer(Modifier.height(8.dp * shrinkProgress))

            ProdUiText(
                text = "Т-Доставка",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize * shrinkProgress
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(4.dp * shrinkProgress))

            ProdUiText(
                text = "Версия $versionName",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize * shrinkProgress
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AboutContent(
    lazyListState: LazyListState,
    headerHeightPx: Int,
    enabled: Boolean
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
        userScrollEnabled = enabled,
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        overscrollEffect = null
    ) {
        item {
            Spacer(Modifier.height(with(LocalDensity.current) { headerHeightPx.toDp() }))
        }

        item {
            SectionCard(title = "Описание") {
                ProdUiText(
                    text = "Т-Доставка — агрегатор служб доставки, который помогает найти " +
                            "лучший вариант отправки посылки из Москвы в любой город России.\n\n" +
                            "Сервис сравнивает предложения от ведущих транспортных компаний — " +
                            "СДЭК, Деловые Линии, ПЭК, Boxberry, Почта России и других — " +
                            "по цене, срокам и типу доставки.\n\n" +
                            "Искусственный интеллект анализирует исторические данные и " +
                            "прогнозирует реальные сроки доставки, которые часто отличаются " +
                            "от заявленных перевозчиками. Это позволяет принимать более " +
                            "взвешенные решения при выборе способа отправки.\n\n" +
                            "Приложение разработано командой T-e-i-l-s в рамках хакатона " +
                            "Product Contest.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                        .padding(16.dp)
                )
            }
        }

        item {
            SectionCard(title = "Возможности") {
                val features = listOf(
                    "Поиск по городам" to "Быстрый поиск города назначения с автодополнением и историей",
                    "Сравнение тарифов" to "Все предложения перевозчиков в одном списке с фильтрацией по цене, срокам и типу тарифа",
                    "ИИ-прогнозы" to "Прогнозирование реальных сроков доставки на основе машинного обучения",
                    "Оформление заказа" to "Выбор дополнительных услуг (страхование, упаковка, SMS) и оплата в пару нажатий",
                    "История заказов" to "Отслеживание всех оформленных заказов с трек-номерами"
                )
                Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                    features.forEachIndexed { index, (title, description) ->
                        if (index > 0) {
                            HorizontalDivider(
                                thickness = 0.5.dp,
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                        FeatureItem(title = title, description = description)
                    }
                }
            }
        }

        item {
            SectionCard(title = "Юридическая информация") {
                val documents = listOf(
                    "Пользовательское соглашение" to "Условия использования сервиса Т-Доставка, " +
                            "включая правила оформления заказов, ответственность сторон и порядок " +
                            "разрешения споров. Актуальная редакция от 01.01.2025.",
                    "Политика конфиденциальности" to "Информация о сборе, хранении и обработке " +
                            "персональных данных пользователей в соответствии с ФЗ-152 " +
                            "«О персональных данных». Мы не передаём ваши данные третьим лицам " +
                            "без вашего согласия.",
                    "Условия оплаты" to "Порядок и способы оплаты услуг доставки. Поддерживаются " +
                            "банковские карты Visa, MasterCard, МИР. Оплата производится через " +
                            "защищённый платёжный шлюз Т-Банка.",
                    "Правила доставки" to "Общие условия перевозки грузов, ограничения по весу " +
                            "и габаритам, сроки хранения посылок в пунктах выдачи, порядок " +
                            "возврата и компенсации при повреждении груза.",
                    "Лицензии" to "Перечень используемых библиотек и компонентов с открытым " +
                            "исходным кодом: Jetpack Compose, Retrofit, Hilt, Room, Coil, " +
                            "Material Design 3, Haze и других. Все лицензии соответствуют " +
                            "Apache 2.0 или MIT."
                )
                Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                    documents.forEachIndexed { index, (title, description) ->
                        if (index > 0) {
                            HorizontalDivider(
                                thickness = 0.5.dp,
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            ProdUiText(
                                text = title,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.height(4.dp))
                            ProdUiText(
                                text = description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        item {
            SectionCard(title = "Технологии") {
                ProdUiText(
                    text = "Приложение построено на современном стеке Android-разработки:\n\n" +
                            "• Kotlin + Jetpack Compose — декларативный UI\n" +
                            "• Material Design 3 — система дизайна\n" +
                            "• Hilt — внедрение зависимостей\n" +
                            "• Retrofit + OkHttp — сетевой слой\n" +
                            "• Room — локальная база данных\n" +
                            "• Kotlin Coroutines + Flow — асинхронность\n" +
                            "• Navigation Compose — навигация\n" +
                            "• Coil — загрузка изображений\n" +
                            "• Haze — эффекты размытия\n\n" +
                            "Архитектура: Clean Architecture + MVVM.\n" +
                            "Мультимодульный проект из 13 модулей.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                        .padding(16.dp)
                )
            }
        }

        item {
            SectionCard(title = "Команда") {
                ProdUiText(
                    text = "Проект T-e-i-l-s\n\n" +
                            "Разработано с ❤ для Product Contest.\n" +
                            "Москва, 2025",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.06f))
                        .padding(24.dp)
                )
            }
        }

        item {
            Spacer(Modifier.height(16.dp))
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        ProdUiText(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(12.dp))
        content()
    }
}

@Composable
private fun FeatureItem(
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .padding(top = 6.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.primary)
        )
        Column(modifier = Modifier.weight(1f)) {
            ProdUiText(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(2.dp))
            ProdUiText(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
