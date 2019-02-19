# MVVM with Screen States
Sample project to showcase mvvm with screen states architecture. On this pattern, we have our view model emiting view states to the view, that are basically classes that describes what the user should see on the screen. Using this pattern, the view simply observe the changes on the states.

The advantages to have this pattern on the project:</br>

-Remove the logic from the view, which is hard to test</br>
-Make almost everything easily unit testable, even the presentation layer</br>
-Code clean and easy to understand what is going just looking at the emitions of the view model or the tests, facilitating the on board of new devs</br></br>

For this sample I have used:

-Live Data and ViewModel, from <a href="https://developer.android.com/topic/libraries/architecture/">Arch Components</a></br></br>
-<a href="https://github.com/Kotlin/kotlinx.coroutines">Coroutines</a> for the async tasks and a couple of Kotlin features to make it possible sweet "syncrhonous like" code even when doing requests</br></br>
-<a href="https://square.github.io/retrofit/">Retrofit</a> for http requests and <a href="https://github.com/square/okhttp/tree/master/mockwebserver">Mock Web Server</a> to set up expected test scenarios on requests</br></br>
-<a href="https://site.mockito.org/">Mockito</a> for mocks</br></br>

Remember that how you implement it does not matter, the point here is to make some experimentantions about how effective this aproach can be to solve commom problems when talking about Android projects archtectures. That is the way I found 
