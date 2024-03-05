package com.example.composelayouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ChatLayoutRecieved(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                5.dp
            )
    ) {
        Row() {
            Column(
                Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically)
                    .padding(start = 2.dp, end = 3.dp)
            ) {
                Icon(
                    painterResource(id = R.drawable.user),
                    contentDescription = "",
                    Modifier
                        .height(18.dp)
                        .width(18.dp)
                )
            }
            Column(
                Modifier
                    .background(
                        color = colorResource(id = R.color.chatBackground),
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .padding(5.dp, 7.dp)
            ) {
                Text(text = message)
            }
        }
    }
}

@Composable
fun ChatLayoutSend(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                5.dp
            )
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Column(
                Modifier
                    .background(
                        color = colorResource(id = R.color.chatBackground),
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .padding(5.dp, 7.dp)
            ) {
                Text(text = message)
            }

            Column(
                Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically)
                    .padding(start = 2.dp, end = 3.dp)
            ) {
                Icon(
                    painterResource(id = R.drawable.user),
                    contentDescription = "",
                    Modifier
                        .height(18.dp)
                        .width(18.dp)
                )
            }
        }
    }
}


@Composable
fun InputLayout(btnClick: (String?) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {

        TextField(value = text, onValueChange = { newText ->
            text = newText
        }, placeholder = { Text(text = "Enter Your Message...") }, trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.attach),
                contentDescription = "Attachment",
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
                    .background(Color.White)
            )
        }, modifier = Modifier
            .background(Color.White)
            .border(
                1.dp,
                colorResource(id = R.color.ligheGray),
                shape = RoundedCornerShape(15.dp)
            )
            .weight(9f)
        )
        Image(
            painterResource(R.drawable.send),
            contentDescription = "Send Button",
            Modifier
                .weight(1f)
                .height(45.dp)
                .width(45.dp)
                .padding(8.dp, 10.dp)
                .align(Alignment.CenterVertically)
                .clickable {
                    btnClick(text.text)
                    text = TextFieldValue(text = "")
                }

        )


    }

}
