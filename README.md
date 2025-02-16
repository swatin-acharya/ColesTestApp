# ColesTestApp

This app consists of a 2 column grid list view of recipes when the phone is tilted to landscape mode. Out of this list, the first recipe
is selected as the "Selected" recipe by default. Pressing another recipe results in that recipe being "selected". Tilting the phone to portrait
shows the detail view of the recipe (1st recipe in the list by default). User can tilt back to landscape mode to change the selected recipe.

In order to demonstrate "navigation compose", I have use compose navigation to navigate to a third screen when the portrait mode recipe image is clicked.
A new screen comes up that only has the image in bigger form and a back button to go back.

This app showcases all the architectures, testing, navigation mentioned in the given document.
This app uses the given json file, which is stored in the assets folder, reads it, parses it into a model and shows the list of items contained in the json.
This app is single activity, all compose.

 
