package edu.ucne.demoap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.demoap2.ui.theme.DemoAp2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoAp2Theme {
                extracted()
            }
        }
    }

}

@Composable
private fun extracted() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Column {

            Text(text = "Hello, Carlos", modifier = Modifier.padding(innerPadding))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Que tal estas mi hermano")

        }


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    DemoAp2Theme {
        extracted()
    }
}