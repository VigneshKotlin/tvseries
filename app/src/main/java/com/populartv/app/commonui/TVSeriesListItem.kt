package com.populartv.app.commonui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.populartv.app.model.TVSeries

@Composable
fun PuppyListItem(tvSeries: TVSeries, navigateToProfile: (TVSeries) -> Unit) {
    Card(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp).fillMaxWidth(),
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))

    ) {
        Row(Modifier.clickable { navigateToProfile(tvSeries) }) {
            PuppyImage(tvSeries)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)) {
                Text(text = "puppy.title")
                Text(text = "VIEW DETAIL")
            }
        }
    }
}
@Composable
private fun PuppyImage(tvSeries: TVSeries) {
    Image(
        painter = painterResource(id = 1),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}
@Preview
@Composable
fun PreviewPuppyItem() {
    //val puppy = DataProvider.puppy
    //PuppyListItem(puppy = puppy, navigateToProfile = {})
}