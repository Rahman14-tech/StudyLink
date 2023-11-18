package com.example.studylink.forum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ForumScreen() {

}

@Composable
fun ForumCard(
    title: String,
    author: String,
    timestamp: String,
    subtitle: String,
    tags: Set<String>,
    modifier: Modifier = Modifier ){

    val orderedTags = tags.sorted()

    Card (
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
            Column (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )

                //Date
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = timestamp)
                    Row(

                    ) {
                        Text(text = "@")
                        Text(text = author)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Subtitle
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,)

                Spacer(modifier = Modifier.height(14.dp))

                // Tags
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                ){
                    items(orderedTags) { tag ->
                        Text(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .background(color = Color(0xFFFFC600))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                                .align(alignment = Alignment.Start)
                                .height(20.dp),
                            text = tag,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black,
                        )
                    }
                }
            }
        }
    }

@Preview
@Composable
fun ForumCardPreview(){
    ForumCard(
        title = "About Palindrome Algorithm",
        timestamp = "19 August 2020 19:20 ",
        author = "James Cameron",
        subtitle = "I still confused as hell about this programming algorithm",
        tags = setOf<String>("C", "Algorithm","Computer Science"),
        modifier = Modifier
            .fillMaxWidth())
}