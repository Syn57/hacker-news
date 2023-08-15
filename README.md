# Hacker News App - Mini project

## Overview
An application to retrieve and show top stories data from Hacker News API. The app can also store a certain favorite story.


![App](https://github.com/Syn57/asset-hacker-apps/blob/main/mockup_0_5.png?raw=true) 



## Design 
The design is made using Figma.
- The wireframe design can be found [here](https://www.figma.com/file/RQ2etNstHVVe0HsbGrbTxY66/Untitled?node-id=0%3A1)
![Wireframe](https://github.com/Syn57/asset-hacker-apps/blob/main/wireframe.png?raw=true)
- The mockup design can be found [here](https://www.figma.com/file/1HMeWuoKpKatjpCY5bAEcT/HackerNewsApp?node-id=5%3A135&t=x4iYfmOg8uufxozI-1)
![Mockups](https://github.com/Syn57/asset-hacker-apps/blob/main/mockup.png?raw=true)

## App development
The development is integrated in Android Studio. The app contains 2 activity. First, Main activity to show list of top stories. Second, Detail Story to give the detail of certain story and to add it into favorite list. Here are the several informations about the app development.
- **Language:** Kotlin
- **Design Pattern and Architecture:** MVVM Design Pattern, Jetpack
- **Networking and Support:** Retrofit, Coroutines, OkHTTP, Logging Interceptor for debug variants
- **View Support:** AndroidX, Ktx, Recycle View
- **Jetpack:** ViewModel, LiveData, Android KTX, AppCompat, DataBinding, Room

## API Reference
Only three API is used by this app, Get story, get comment, and get top stories. The complete documentation about Hacker News is [here](https://github.com/HackerNews/API)
#### Get story

```http
  GET /v0/item/{id}.json
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | The item's unique id. |

For example, a story: https://hacker-news.firebaseio.com/v0/item/8863.json?print=pretty

```javascript
{
  "by" : "dhouston",
  "descendants" : 71,
  "id" : 8863,
  "kids" : [ 8952, 9224, 8917, 8884, 8887, 8943, 8869, 8958, 9005, 9671, 8940, 9067, 8908, 9055, 8865, 8881, 8872, 8873, 8955, 10403, 8903, 8928, 9125, 8998, 8901, 8902, 8907, 8894, 8878, 8870, 8980, 8934, 8876 ],
  "score" : 111,
  "time" : 1175714200,
  "title" : "My YC app: Dropbox - Throw away your USB drive",
  "type" : "story",
  "url" : "http://www.getdropbox.com/u/2/screencast.html"
}
```
Retrofit interface (note: `{id}` parameter inside this interface has been converted first into format -> `{id}.json` according to the the requirement from Hacker News API documentation)
```javascript
@GET("v0/item/{id}")
    fun getStory(
        @Path("id") id: String
    ): Call<StoryResponse>
```


#### Get comment

```http
  GET /v0/item/{id}.json
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | The item's unique id. |

For example, a comment: https://hacker-news.firebaseio.com/v0/item/2921983.json?print=pretty

```javascript
{
  "by" : "norvig",
  "id" : 2921983,
  "kids" : [ 2922097, 2922429, 2924562, 2922709, 2922573, 2922140, 2922141 ],
  "parent" : 2921506,
  "text" : "Aw shucks, guys ... you make me blush with your compliments.<p>Tell you what, Ill make a deal: I'll keep writing if you keep reading. K?",
  "time" : 1314211127,
  "type" : "comment"
}
```

Retrofit interface (note: `{id}` parameter inside this interface has been converted first into format -> `{id}.json` according to the the requirement from Hacker News API documentation)
```javascript
@GET("v0/item/{id}")
    fun getComment(
        @Path("id") id: String
    ): Call<CommentsResponse>
```
#### Get top stories

```http
  GET /v0/topstories.json
```

Example: https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty

```javascript
[ 9129911, 9129199, 9127761, 9128141, 9128264, 9127792, 9129248, 9127092, 9128367, ..., 9038733 ]
```

Retrofit interface
```javascript
@GET("v0/topstories.json")
    fun getTop(): Call<List<Int>>
```



## Authors

- [@Syn57](https://www.github.com/Syn57)

