package com.example.composelayouts

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composelayouts.api.UserApi
import com.example.composelayouts.dataclass.BottomNavItem
import com.example.composelayouts.dataclass.Note
import com.example.composelayouts.models.Mesaage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity3 : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        items = listOf(
                            BottomNavItem(
                                name = "Settings",
                                route = "settings",
                                icon = Icons.Default.Settings,

                                ),
                            BottomNavItem(
                                name = "Home",
                                route = "home",
                                icon = Icons.Default.Home
                            ),
                            BottomNavItem(
                                name = "Chat",
                                route = "chat",
                                icon = Icons.Default.Notifications,

                                ),
                        ),
                        navController = navController,
                        onItemClick = {
                            navController.navigate(it.route)
                        }
                    )

                }
            ) {
                Box(Modifier.padding(it)) {
                    Navigation(navController = navController, it)
                }
            }

        }
    }

}

var msgs: ArrayList<Mesaage> = ArrayList()

@Composable
fun Navigation(navController: NavHostController, padding: PaddingValues) {
    NavHost(navController = navController, startDestination = "chat") {
        composable("home") {
            ListTaskSheet1()
        }
        composable("chat") {
            ChatScreen()
        }
        composable("settings") {
            SettingsScreen()
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            /*BadgeBox(
                                badgeContent = {
                                    Text(text = item.badgeCount.toString())
                                }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }*/
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}

val noteList1 = arrayListOf<Note>(
    Note(
        "All", ""

    ), Note(
        "Important",
        "Review of Previous Action Items\n" +
                "Product Development Update\n" +
                "User Feedback and Customer Insights\n" +
                "Competitive Analysis\n" +
                "Roadmap Discussion "
    ), Note(
        "Lecture notes",
        "Reply to emails\n" +
                "Prepare presentation slides for the marketing meeting\n" +
                "Conduct research on competitor products\n" +
                "Schedule and plan customer interviews\n" +
                "Take a break and recharge "
    ), Note(
        "To-do lists",
        "Rice\n" +
                "Pasta\n" +
                "Cereal\n" +
                "Yogurt\n" +
                "Cheese\n" +
                "Butter"
    ), Note(
        "Shopping list", "Review of Previous Action Items\n" +
                "Product Development Update\n" +
                "User Feedback and Customer Insights\n" +
                "Competitive Analysis\n" +
                "Roadmap Discussion "
    )
)
val colorList1 =
    arrayListOf<Color>(Color.LightGray, Color.Cyan, Color.Green, Color.Magenta, Color.Yellow)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListTaskSheet1() {

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxHeight()
    ) {
        items(noteList1.drop(1).size) {
            TaskLayout1(noteList1[it])
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskLayout1(note: Note) {
    var showLongClickDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(
                color = colorList1[Random.nextInt(0, colorList1.size - 1)],
                shape = RoundedCornerShape(size = 12.dp)
            )
            .combinedClickable(
                onClick = {
                    showDialog = showDialog.not()
                },
                onLongClick = { showLongClickDialog = showLongClickDialog.not() }

            )

    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = note.title,
                color = Color.Black, fontSize = 22.sp
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = note.note
            )
        }
        if (showLongClickDialog) {
            AlertDialog(
                onDismissRequest = { showLongClickDialog = false },
                title = { Text("Are you sure you want to delete this?") },
                text = { Text("This action cannot be undone") },
                confirmButton = {
                    TextButton(onClick = {


                    }) {
                        Text("Delete it".uppercase())
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLongClickDialog = false }) {
                        Text("Cancel".uppercase())
                    }
                },
            )
        }
        if (showDialog) {
            CustomDialog(
                note.title,
                note.note,
                onDissmissRequest = { showDialog = false }
            )
        }
    }

}

@Composable
fun CustomDialog(
    title: String,
    value: String,
    onDissmissRequest: () -> Unit
) {

    val txtField = remember { mutableStateOf(value) }
    val txtTitle by remember { mutableStateOf(title) }

    Dialog(onDismissRequest = onDissmissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = txtTitle,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "",
                            tint = colorResource(android.R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { onDissmissRequest() }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = R.color.titleSpanColor)
                                ),
                                shape = RoundedCornerShape(5)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = { Text(text = "Note is Null") },
                        value = txtField.value,
                        onValueChange = {
                            txtField.value = it
                        })
                }
            }
        }
    }
}

var call: Call<Any>? = null

fun sendRequest(reply: String, success: () -> Unit) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://api.brainshop.ai")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(UserApi::class.java)

    call = api.getUserById(reply);
    call?.enqueue(object : Callback<Any?> {
        override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
            if (response.isSuccessful) {
                val msgg =
                    response.body().toString().replace("{", "").replace("}", "").replace("cnt=", "")
                msgs.add(Mesaage("r", msgg))
                success()

            }
        }

        override fun onFailure(call: Call<Any?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
        }
    })

}
@Preview
@Composable
fun ChatScreen() {
    val context = LocalContext.current
    val tempList = remember { mutableStateListOf<Mesaage>() }
    tempList.addAll(msgs)
    Scaffold(bottomBar = {
        InputLayout(btnClick = {
            if (it.isNullOrEmpty()) {
                Toast.makeText(context, "Message in Empty", Toast.LENGTH_SHORT).show()
            } else {
                msgs.add(Mesaage("s", it.trim()))
                tempList.clear()
                tempList.addAll(msgs)
                sendRequest(it) {
                    tempList.clear()
                    tempList.addAll(msgs)
                }
                Log.d("TAGCHAT", "ChatScreen: " + it)
            }
        })
    }) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(Modifier.fillMaxWidth()) {
                items(tempList) { i ->
                    if (i.type == "r") {
                        ChatLayoutRecieved(i.Msg)
                    } else {
                        ChatLayoutSend(i.Msg)
                    }
                }
            }

        }
    }

}

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

    }
}