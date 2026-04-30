package com.emilien.equiapp.coursedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.emilien.equiapp.EquiAppTheme
import com.emilien.equiapp.domain.CourseStudent
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    courseId: String,
    viewModel: CourseDetailViewModel = koinViewModel(),
    presenceViewModel: PresenceViewModel = koinViewModel(parameters = { parametersOf(courseId) }),
    onBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val presenceState by presenceViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(courseId) {
        viewModel.onEvent(CourseDetailUiEvent.LoadCourse(courseId))
    }

    LaunchedEffect(uiState.courseId) {
        if (uiState.courseId.isNotEmpty()) {
            presenceViewModel.onIntent(PresenceIntent.Initialize(uiState.presenceConfirmed, uiState.comment))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Course Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(uiState.error!!, color = MaterialTheme.colorScheme.error)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Main Info
                item {
                    CourseHeader(
                        theme = uiState.theme,
                        teacher = uiState.teacher,
                        horse = uiState.horse,
                        time = uiState.time
                    )
                }

                // Payment & Credits
                item {
                    PaymentStatusCard(
                        status = uiState.paymentStatus,
                        credits = uiState.credits
                    )
                }

                // Presence Confirmation
                item {
                    PresenceConfirmationSection(
                        state = presenceState,
                        courseStartTimeMillis = uiState.courseStartTimeMillis,
                        onIntent = { presenceViewModel.onIntent(it) }
                    )
                }

                // Other Students
                item {
                    Text(
                        "Group Members",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(uiState.otherStudents) { student ->
                    StudentPresenceRow(student)
                }
            }
        }
    }
}

@Composable
fun CourseHeader(theme: String, teacher: String, horse: String?, time: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(theme, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(Icons.Default.Person, "Teacher: $teacher")
            InfoRow(Icons.Default.Pets, "Horse: ${horse ?: "Unassigned"}")
            InfoRow(Icons.Default.Schedule, time)
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun PaymentStatusCard(status: String, credits: Int) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Payment Status", style = MaterialTheme.typography.labelLarge)
                Text(status, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            }
            if (credits > 0) {
                Surface(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = CircleShape
                ) {
                    Text(
                        "$credits Credit available",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Composable
fun PresenceConfirmationSection(
    state: PresenceUiState,
    courseStartTimeMillis: Long,
    onIntent: (PresenceIntent) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Your Presence", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        
        if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { onIntent(PresenceIntent.SubmitPresence(true, courseStartTimeMillis)) },
                modifier = Modifier.weight(1f),
                enabled = !state.isOptimistic,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.isConfirmed == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (state.isConfirmed == true) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                if (state.isOptimistic && state.isConfirmed == true) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = MaterialTheme.colorScheme.onPrimary)
                    Spacer(Modifier.width(8.dp))
                }
                Text("Confirm")
            }
            OutlinedButton(
                onClick = { onIntent(PresenceIntent.SubmitPresence(false, courseStartTimeMillis)) },
                modifier = Modifier.weight(1f),
                enabled = !state.isOptimistic,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = if (state.isConfirmed == false) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                if (state.isOptimistic && state.isConfirmed == false) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = Color.Red)
                    Spacer(Modifier.width(8.dp))
                }
                Text("Absence")
            }
        }
        OutlinedTextField(
            value = state.comment,
            onValueChange = { onIntent(PresenceIntent.UpdateComment(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Add a comment (optional)") },
            placeholder = { Text("I might be 5 min late...") }
        )
    }
}

@Composable
fun StudentPresenceRow(student: CourseStudent) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp).clip(CircleShape).background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(student.name.take(1))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(student.name, fontWeight = FontWeight.Medium)
            Text("Horse: ${student.horse ?: "TBD"}", style = MaterialTheme.typography.bodySmall)
        }
        Badge(
            containerColor = when(student.status) {
                "Present" -> Color(0xFF4CAF50)
                "Absent" -> Color.Red
                else -> Color.Gray
            }
        ) {
            Text(student.status, color = Color.White, modifier = Modifier.padding(4.dp))
        }
    }
}

@Preview
@Composable
fun CourseDetailPreview() {
    EquiAppTheme {
        CourseDetailScreen(courseId = "1")
    }
}
