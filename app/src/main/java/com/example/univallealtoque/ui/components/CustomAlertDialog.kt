import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.univallealtoque.R

@Composable
fun CustomAlertDialog(
    title: String,
    message: String,
    confirmText: String = "OK",
    onDismiss: () -> Unit
) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = { onDismiss() },
        title = {
            if (title == "") {
                Text(
                    text = stringResource(id = R.string.aviso),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.Black
                )
            } else {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.Black
                )
            }

        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(confirmText)
            }
        }
    )
}
