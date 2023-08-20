# LESSCOLORS

LESSCOLORS is a tool designed to reduce the number of colors in an image.  
It accomplishes this by identifying the closest color on a specified color palette and replacing the original colors
pixel by pixel.  
<br>
It is available both as a command-line application (runnable JAR) and as a Java library.  
<br>
**Warning for gamedevs:** This tool is not intended for real-time scenarios.

# Use-Cases

There's probably more use cases than these, but to give you an impression of what it can do, let's check some out.

## Game Assets

Bring textures or other game related graphics assets to match your personal game style (e.g. your color palette).

### Your game

Let's say your game looks like this:  
<img src="media/slso8-examples.png" width="300" alt="an image of your game">

### The color palette you used

It looks great and consistent because it only uses colors from your color palette.  
<img src="media/slso8.png" width="300" alt="color palette">

### Non-matching assets from other sources

Unfortunately, this [asset pack](https://cainos.itch.io/pixel-art-top-down-basic) you found
on [insert market place here] and you always wanted to integrate into the game, does not match your art style.  
<img src="media/art_by_cainos.png" width="300" alt="image of a game asset pack">

### Fix it

That's where this tool comes into action.

```console
java -jar lesscolors.jar --input "originalImage.png" -- palette "myPalette.png" --output "fixedImage.png"
```

Et voilà:  
<img src="media/art_by_cainos-slso8.png" width="300" alt="fixed version of the game asset pack">

## Stylize Textures

<div style="display: flex; flex-wrap: wrap; column-gap: 10px;">
    <img src="media/widopeakwindowswall.jpg" width="300" alt="image of a texture">
    <img src="media/widopeakwindowswall_slso8.png" width="300" alt="modified image of a texture">
</div>

## Stylize Familiy Photos (to convince your grandpa of your hacker skills)

<div style="display: flex; flex-wrap: wrap; column-gap: 10px;">
    <img src="media/josue-michel-OMl0o6TSQXU-unsplash.jpg" width="300" alt="family photo">
    <img src="media/josue-michel-OMl0o6TSQXU-unsplash-slso8.png" width="300" alt="modified family photo">
</div>

# Documentation (CLI App)

| Argument      | Description                                                                 |
|---------------|-----------------------------------------------------------------------------|
| --input       | The path to the image you want to modify.                                   |
| --palette     | The path to the color palette image.                                        |
| --output      | The path to the output file (will be created in the process).               |
| --output-type | [Optional] The output file format, "png", "jpg", etc. The default is "png". |

# Installation

For the cli app it's just a jar. Download [here](https://github.com/Hangman/lesscolors/releases).  
<br>
And here's how to integrate the library in your project via Gradle:  
First make sure you have Jitpack declared as repository in your build.gradle.kts file:

```kotlin
repositories {
    maven("https://jitpack.io")
}
```

Then add lesscolors as a dependency:

```kotlin
dependencies {
    implementation("com.github.Hangman:lesscolors:2197eb1972")
}
```

For specific commit versions, branches, or if you want to use Maven, check out
lesscolor's [Jitpack page](https://jitpack.io/#Hangman/lesscolors).
