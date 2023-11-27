
# NewsApp MVVM Architecture

A News Application that allows users to fetch news built by following MVVM Architecture and Jetpack Components with Unit and Instrumentation Testing.

## Major Highlights

- MVVM Architecture
- Kotlin
- Dagger Hilt
- Retrofit
- Coroutines
- Flows
- Stateflow
- Viewbinding
- Pagination
- Compose UI 
- Unit Test
- UI Test

> [!NOTE]
> Compose UI is implemented in the compose-migration branch, the tests are implemented in the compose-without-pagination branch.

<img src="https://github.com/gunishjain/NewsApp-MVVM-Architecture/blob/main/assets/MVVM-Arch.png">


## Features Implemented

- Fetching News
- Pagination
- Top Headlines News
- News Based on Source
- News Based on Single/Multi Country Selection
- News Based on Single/Multi Language Selection
- Instant Search using Flows Operator
  * Debounce
  * Filter
  * DistinctUntilChanged
  * FlatMapLatest
- Unit Test
  - Mockito
  - Espresso
  - [Turbine](https://github.com/cashapp/turbine/)
  
## Dependency Used:
- Recycler View for listing
```
implementation "androidx.recyclerview:recyclerview:1.3.1"

```
- Glide for image loading
```
implementation 'com.github.bumptech.glide:glide:4.15.1'
```
- Retrofit for networking
```
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```
- Android Lifecycle aware component 
```
implementation 'android.arch.lifecycle:extensions:1.1.1'
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2'
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.2'
```
- Dagger Hilt for dependency Injection 
```
implementation "com.google.dagger:hilt-android:2.44"
kapt "com.google.dagger:hilt-compiler:2.44"
```
- For WebView browser 
```
implementation 'androidx.browser:browser:1.4.0'
```
- Card Implementation 
```
implementation "androidx.cardview:cardview:1.0.0"
```
- Paging library 
```
implementation 'androidx.paging:paging-runtime-ktx:3.2.1'
implementation 'androidx.paging:paging-compose:3.2.1'
```
- Local Unit test 
```
testImplementation 'junit:junit:4.13.2'
testImplementation "org.mockito:mockito-core:5.3.1"
testImplementation 'androidx.arch.core:core-testing:2.2.0'
testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3'
testImplementation 'app.cash.turbine:turbine:0.12.1'
```

- UI Test
```
androidTestImplementation 'androidx.test.ext:junit:1.1.5'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
androidTestImplementation platform('androidx.compose:compose-bom:2023.03.00')
androidTestImplementation 'androidx.compose.ui:ui-test-junit4'
androidTestImplementation 'androidx.navigation:navigation-testing:2.6.0'
debugImplementation 'androidx.compose.ui:ui-tooling'
debugImplementation 'androidx.compose.ui:ui-test-manifest'
```
## Complete Project Structure

```
            ├───data
            │   ├───api
            │   ├───model
            │   └───repository
            ├───di
            │   └───module
            ├───navigation
            ├───ui
            │   ├───base
            │   ├───newslist
            │   ├───search
            │   ├───selections
            │   ├───sources
            │   ├───theme
            │   └───topheadlines
            └───utils
                NewsApplication.kt

```

### Screenshots 

<p align="center">
<img src="https://github.com/gunishjain/NewsApp-MVVM-Architecture/blob/main/assets/homescreen.png" width="200" height="400">
<img src="https://github.com/gunishjain/NewsApp-MVVM-Architecture/blob/main/assets/selectionscreen.png" width="200" height="400">
<img src="https://github.com/gunishjain/NewsApp-MVVM-Architecture/blob/main/assets/searchscreen.png" width="200" height="400">
<img src="https://github.com/gunishjain/NewsApp-MVVM-Architecture/blob/main/assets/newssources.png" width="200" height="400">
</p>

### News App Demo

![](https://github.com/gunishjain/NewsApp-MVVM-Architecture/blob/main/assets/instant-search.gif) | ![](https://github.com/gunishjain/NewsApp-MVVM-Architecture/blob/main/assets/pagination.gif)



