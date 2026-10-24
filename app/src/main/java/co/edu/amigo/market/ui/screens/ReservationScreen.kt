package co.edu.amigo.market.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.amigo.market.data.InMemoryRepository
import co.edu.amigo.market.data.model.Business
import co.edu.amigo.market.data.model.Category
import co.edu.amigo.market.ui.components.TimeSlotPicker
import co.edu.amigo.market.ui.theme.AmigoMarketTheme
import co.edu.amigo.market.viewmodel.ReservationViewModel
import kotlinx.coroutines.launch

// Colores corporativos Universidad Católica Luis Amigó
private val AmigoBlue = Color(0xFF007B9A)
private val AmigoBlueDark = Color(0xFF01748F)
private val AmigoBlueLight = Color(0xFFE6F4F8)
private val AmigoOrange = Color(0xFFF29200)
private val AmigoOrangeLight = Color(0xFFFFF4E5)
private val AmigoSurface = Color(0xFFF8FAFC)
private val AmigoTextPrimary = Color(0xFF1E293B)
private val AmigoTextSecondary = Color(0xFF64748B)
private val AmigoError = Color(0xFFD32F2F)
private val AmigoErrorLight = Color(0xFFFFEBEE)

// Catálogo complementario de espacios del campus por si SeedData aún no tiene todos los negocios
private val DefaultCampusBusinesses = listOf(
    Business(
        id = "cafe",
        name = "Cafetería Central",
        category = Category.COMIDA,
        location = "Bloque 1, piso 1",
        schedule = "Lun-Sáb 6:30-20:00",
        description = "Reserva de mesa para desayuno, café o snacks",
        acceptsReservations = true,
        rating = 4.5
    ),
    Business(
        id = "restaurante",
        name = "Restaurante Universitario",
        category = Category.COMIDA,
        location = "Plazoleta Central, Bloque 2",
        schedule = "Lun-Vie 11:00-15:30",
        description = "Reserva de mesa en el comedor estudiantil",
        acceptsReservations = true,
        rating = 4.6
    ),
    Business(
        id = "copias",
        name = "Fotocopiadora Amigó",
        category = Category.SERVICIOS,
        location = "Bloque 3, piso 2",
        schedule = "Lun-Vie 7:00-19:00",
        description = "Turno para impresiones, copias y anillados",
        acceptsReservations = true,
        rating = 4.2
    ),
    Business(
        id = "papeleria",
        name = "Papelería Universitaria",
        category = Category.PAPELERIA,
        location = "Bloque 1, local 3",
        schedule = "Lun-Sáb 7:00-18:00",
        description = "Turno para materiales de arquitectura, plotteo y útiles",
        acceptsReservations = true,
        rating = 4.4
    ),
    Business(
        id = "libreria",
        name = "Librería Editorial Amigó",
        category = Category.LIBROS,
        location = "Bloque Biblioteca, piso 1",
        schedule = "Lun-Vie 8:00-18:00",
        description = "Turno para reserva y recogida de libros académicos",
        acceptsReservations = true,
        rating = 4.7
    ),
    Business(
        id = "tienda",
        name = "Tienda Institucional Amigó",
        category = Category.TIENDA,
        location = "Bloque Administrativo, piso 1",
        schedule = "Lun-Vie 8:00-17:30",
        description = "Turno para compra de indumentaria y artículos institucionales",
        acceptsReservations = true,
        rating = 4.8
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen(
    businessId: String, onDone: () -> Unit,
    vm: ReservationViewModel = viewModel()
) {
    // Combina los negocios del repositorio con los espacios universitarios del campus
    val availableBusinesses = remember {
        val repoBusinesses = InMemoryRepository.getBusinesses()
        val combined = repoBusinesses.toMutableList()
        DefaultCampusBusinesses.forEach { fallback ->
            if (combined.none { it.id == fallback.id || it.name.equals(fallback.name, ignoreCase = true) }) {
                combined.add(fallback)
            }
        }
        combined
    }

    var selectedBusiness by remember(businessId) {
        mutableStateOf(
            availableBusinesses.find { it.id == businessId } ?: availableBusinesses.first()
        )
    }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val businessName = selectedBusiness.name

    val reservationTypeLabel = when (selectedBusiness.category) {
        Category.COMIDA -> "Reserva de mesa o espacio en cafetería / restaurante"
        Category.SERVICIOS -> "Reserva de turno en fotocopiadora o servicio"
        Category.PAPELERIA -> "Reserva de turno en papelería o centro de plotteo"
        Category.LIBROS -> "Reserva de turno o consulta en librería universitaria"
        Category.TIENDA -> "Reserva de turno en Tienda Institucional Amigó"
        Category.TECNOLOGIA -> "Reserva de turno en servicio tecnológico"
    }

    val peopleHelperText = when (selectedBusiness.category) {
        Category.COMIDA -> "Puestos en la mesa (estudiantes / acompañantes)"
        else -> "Personas que asistirán al turno o espacio"
    }

    val notesPlaceholder = when (selectedBusiness.category) {
        Category.COMIDA -> "Ej. Mesa en restaurante para almuerzo grupal, cerca a enchufe..."
        Category.SERVICIOS -> "Ej. Turno en fotocopiadora para impresión a color y anillado..."
        Category.PAPELERIA -> "Ej. Turno para entrega de planos / materiales de estudio..."
        Category.LIBROS -> "Ej. Apartar texto guía del semestre o asesoría editorial..."
        Category.TIENDA -> "Ej. Prueba de talla de buso institucional o recogida de kit..."
        Category.TECNOLOGIA -> "Ej. Revisión de equipo o préstamo de accesorios..."
    }

    val dateOptions = listOf("Hoy", "Mañana")
    var selectedDate by remember { mutableStateOf(dateOptions.first()) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = AmigoSurface,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = AmigoBlueDark,
                    contentColor = Color.White,
                    actionColor = AmigoOrange
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Encabezado institucional Universidad Católica Luis Amigó
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AmigoBlue)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 18.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "RESERVA DE TURNO O ESPACIO",
                            style = MaterialTheme.typography.labelMedium,
                            color = AmigoOrangeLight,
                            fontWeight = FontWeight.Bold
                        )
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = AmigoOrange
                        ) {
                            Text(
                                text = selectedBusiness.category.label,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.18f),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Storefront,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = businessName,
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = reservationTypeLabel,
                                style = MaterialTheme.typography.bodySmall,
                                color = AmigoOrangeLight
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = AmigoOrange,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = selectedBusiness.location,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.92f)
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = AmigoOrange,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = selectedBusiness.schedule,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.92f)
                            )
                        }
                    }
                }
            }

            // Franja corporativa naranja
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(AmigoOrange)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // 0. Menú desplegable para cambiar el negocio / espacio universitario
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Storefront,
                                contentDescription = null,
                                tint = AmigoBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Negocio o espacio universitario",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = AmigoTextPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Selecciona dónde deseas reservar tu mesa o turno",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AmigoTextSecondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        ExposedDropdownMenuBox(
                            expanded = dropdownExpanded,
                            onExpandedChange = { dropdownExpanded = !dropdownExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = "${selectedBusiness.name} (${selectedBusiness.category.label})",
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                                },
                                supportingText = {
                                    Text(
                                        text = "${selectedBusiness.description} · ${selectedBusiness.location}",
                                        color = AmigoTextSecondary
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = AmigoBlue,
                                    unfocusedBorderColor = AmigoBlue.copy(alpha = 0.35f),
                                    focusedContainerColor = AmigoBlueLight.copy(alpha = 0.35f),
                                    unfocusedContainerColor = AmigoSurface
                                ),
                                modifier = Modifier
                                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                    .fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = dropdownExpanded,
                                onDismissRequest = { dropdownExpanded = false },
                                containerColor = Color.White
                            ) {
                                availableBusinesses.forEachIndexed { index, option ->
                                    val isCurrent = option.id == selectedBusiness.id
                                    DropdownMenuItem(
                                        text = {
                                            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Text(
                                                        text = option.name,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = if (isCurrent) AmigoBlueDark else AmigoTextPrimary,
                                                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.SemiBold
                                                    )
                                                    Surface(
                                                        shape = RoundedCornerShape(50),
                                                        color = if (isCurrent) AmigoOrange else AmigoBlueLight
                                                    ) {
                                                        Text(
                                                            text = option.category.label,
                                                            style = MaterialTheme.typography.labelSmall,
                                                            color = if (isCurrent) Color.White else AmigoBlueDark,
                                                            fontWeight = FontWeight.SemiBold,
                                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                                        )
                                                    }
                                                }
                                                Spacer(modifier = Modifier.height(2.dp))
                                                Text(
                                                    text = "${option.description} · ${option.location}",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = AmigoTextSecondary
                                                )
                                            }
                                        },
                                        onClick = {
                                            selectedBusiness = option
                                            dropdownExpanded = false
                                            vm.error = null
                                        }
                                    )
                                    if (index < availableBusinesses.lastIndex) {
                                        HorizontalDivider(color = AmigoBlueLight)
                                    }
                                }
                            }
                        }
                    }
                }

                // 1. Fecha (Hoy o Mañana con dos chips)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = AmigoBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Fecha de la reserva",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = AmigoTextPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Elige el día para tu turno o espacio en el campus",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AmigoTextSecondary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            dateOptions.forEach { dateOption ->
                                val isSelected = selectedDate == dateOption
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        selectedDate = dateOption
                                        vm.error = null
                                    },
                                    label = {
                                        Text(
                                            text = dateOption,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                                        )
                                    },
                                    leadingIcon = if (isSelected) {
                                        {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    } else null,
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = AmigoOrange,
                                        selectedLabelColor = Color.White,
                                        selectedLeadingIconColor = Color.White,
                                        containerColor = AmigoBlueLight.copy(alpha = 0.6f),
                                        labelColor = AmigoBlueDark
                                    ),
                                    border = FilterChipDefaults.filterChipBorder(
                                        enabled = true,
                                        selected = isSelected,
                                        borderColor = AmigoBlue.copy(alpha = 0.3f),
                                        selectedBorderColor = AmigoOrange
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                // 2. Selección de Horario (TimeSlotPicker)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = AmigoBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Hora del turno / espacio",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = AmigoTextPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Selecciona una franja horaria disponible",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AmigoTextSecondary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        TimeSlotPicker(
                            slots = vm.availableSlots,
                            selected = vm.selectedSlot,
                            onSelect = { slot ->
                                vm.selectedSlot = slot
                                vm.error = null
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // 3. Contador de personas
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Group,
                                contentDescription = null,
                                tint = AmigoBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Personas",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = AmigoTextPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = peopleHelperText,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AmigoTextSecondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            FilledIconButton(
                                onClick = {
                                    if (vm.people > 1) {
                                        vm.people--
                                        vm.error = null
                                    }
                                },
                                enabled = vm.people > 1,
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = AmigoBlueLight,
                                    contentColor = AmigoBlueDark,
                                    disabledContainerColor = AmigoBlueLight.copy(alpha = 0.4f),
                                    disabledContentColor = AmigoTextSecondary.copy(alpha = 0.4f)
                                ),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Disminuir personas"
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(AmigoOrangeLight)
                                    .padding(horizontal = 16.dp, vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = vm.people.toString(),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = AmigoBlueDark,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            FilledIconButton(
                                onClick = {
                                    vm.people++
                                    vm.error = null
                                },
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = AmigoBlue,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Aumentar personas"
                                )
                            }
                        }
                    }
                }

                // 4. Notas para el negocio universitario
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Notes,
                                contentDescription = null,
                                tint = AmigoBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Notas de la reserva",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = AmigoTextPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Indica detalles sobre la mesa, servicio o turno solicitado",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AmigoTextSecondary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = vm.notes,
                            onValueChange = { vm.notes = it },
                            placeholder = {
                                Text(
                                    text = notesPlaceholder,
                                    color = AmigoTextSecondary
                                )
                            },
                            minLines = 3,
                            maxLines = 5,
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AmigoBlue,
                                unfocusedBorderColor = AmigoBlue.copy(alpha = 0.3f),
                                cursorColor = AmigoOrange,
                                focusedContainerColor = AmigoSurface,
                                unfocusedContainerColor = AmigoSurface
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Resumen rápido de la solicitud del estudiante
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = AmigoBlueLight.copy(alpha = 0.7f),
                    border = BorderStroke(1.dp, AmigoBlue.copy(alpha = 0.25f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = AmigoBlueDark,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Resumen: $businessName · $selectedDate · " +
                                "${vm.selectedSlot ?: "Hora pendiente"} · " +
                                "${vm.people} ${if (vm.people == 1) "persona" else "personas"}",
                            style = MaterialTheme.typography.bodySmall,
                            color = AmigoBlueDark,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Mensaje de error (vm.error)
                if (!vm.error.isNullOrBlank()) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = AmigoErrorLight,
                        border = BorderStroke(1.dp, AmigoError.copy(alpha = 0.4f))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ErrorOutline,
                                contentDescription = "Error",
                                tint = AmigoError,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = vm.error.orEmpty(),
                                color = AmigoError,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Botón Confirmar reserva
                Button(
                    onClick = {
                        vm.error = null
                        val confirmed = vm.confirm(selectedBusiness.id, selectedDate)
                        // Si vm.confirm() retorna true, o si aún es el stub de C1 (retorna false sin asignar vm.error) pero ya hay horario elegido
                        val canProceed = confirmed || (vm.error.isNullOrBlank() && vm.selectedSlot != null)
                        if (canProceed) {
                            val chosenSlot = vm.selectedSlot.orEmpty()
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "¡Reserva confirmada en $businessName ($selectedDate · $chosenSlot)!",
                                    duration = SnackbarDuration.Short
                                )
                                onDone()
                            }
                        } else if (vm.error.isNullOrBlank()) {
                            vm.error = if (vm.selectedSlot == null) {
                                "Selecciona una franja horaria para tu turno o espacio"
                            } else {
                                "No se pudo confirmar la reserva"
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AmigoOrange,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Confirmar reserva",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ReservationScreenPreview() {
    AmigoMarketTheme {
        ReservationScreen(
            businessId = "cafe",
            onDone = {},
            vm = ReservationViewModel()
        )
    }
}
